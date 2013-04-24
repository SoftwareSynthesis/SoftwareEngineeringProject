/**
 * Presenter incaricato di gestire il pannello per la registrazuone di un
 * messaggio audio da inviare come messaggio di segreteria
 *
 * @author Marco Schivo
 * @author Riccardo Tresoldi
 */
function PhoneCallsRegistryPresenter() {
    /*CAMPI DATI PRIVATI*/
    var audio_context;
    var recorder;

    /*FUNZIONI PRIVATE*/
    function initializeStream() {
        audio_context = new AudioContext;
        navigator.getUserMedia({
            audio : true,
            video : false
        }, startUserMedia);
    };

    /*FUNZIONI PUBBLICHE*/
    /**
     * Funzione per mostrare la view
     *
     * @author Riccardo Tresoldi
     */
    this.showView = function(receiver) {
        // ottengo la view con l'apposita funzione
        var view = mediator.getView("PhoneCallRegistry");
        // mostro la view con il popup
        mediator.showPopup();
        // modifico la view
        var phonecallsregistrypresenter = this;
        var startRecordButton = document.getElementById("startRedord");
        startRecordButton.disabled = false;
        var stopRecordButton = document.getElementById("stopRedord");
        stopRecordButton.disabled = true;
        var sendRecordButton = document.getElementById("sendRedord");
        sendRecordButton.disabled = true;
        startRecordButton.onclick = function() {
            startRecordButton.disabled = true;
            initializeStream();
            stopRecordButton.disabled = false;
        };
        stopRecordButton.onclick = function() {
            stopRecordButton.disabled = true;
            stopRecording();
            startRecordButton.disabled = false;
        };
        sendRecordButton.click = function() {
            startRecordButton.disabled = true;
            stopRecordButton.disabled = true;
            sendRecording(receiver);
            mediator.removePopup();
        };
    };
    /********************/

    function startUserMedia(stream) {
        // creo lo stream media
        var input = audio_context.createMediaStreamSource(stream);
        // collego lo stream audio
        input.connect(audio_context.destination);
        // Inizializzo l'oggetto record per registrare l'imput
        recorder = new Recorder(input);
    }

    function startRecording() {
        //inizio la registrazione
        recorder && recorder.record();
    };

    function stopRecording() {
        //ferma la registrazione
        recorder && recorder.stop();
        // creo link per il download
        //createDownloadLink();
        //pulisco il buffer di registrazione
        recorder.clear();
    };

    function sendRecording(audio, recever) {
        //TODO gestire audio
        var xhr = new XMLHttpRequest();
        // invio chiamata servlet da modificare FIXME
        xhr.open("POST", "http://localhost:8080/Channel/Segreteria", false);
        //creo elemento che invio alla servlet contenente la registrazione
        var formData = new FormData();
        formData.append("msg", audio);
        formData.append("contactId", recever.id);
        xhr.send(formData);
    };

    //TODO da valutare la necessità
    function createDownloadLink() {
        recorder && recorder.exportWAV(function(blob) {
            objAudio = blob;
            var url = URL.createObjectURL(blob);
            var li = document.createElement('li');
            var au = document.createElement('audio');
            var hf = document.createElement('a');
            var btn = document.createElement('button');

            au.controls = true;
            au.src = url;
            hf.href = url;
            hf.download = new Date().toISOString() + '.wav';
            hf.innerHTML = hf.download;
            btn.onclick = function() {
                sendRecording(objAudio)
            };
            btn.innerHTML = 'INVIA';
            li.appendChild(au);
            li.appendChild(hf);
            li.appendChild(btn);
            recordingslist.appendChild(li);
        });
    }

}