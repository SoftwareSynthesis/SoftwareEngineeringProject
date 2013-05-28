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
    var objAudio;
    var localStream;
    
    var thisPresenter = this;

    /*FUNZIONI PRIVATE*/
    function initializeStream() {
        audio_context = new AudioContext;
        navigator.getUserMedia({
            audio : true,
            video : false
        }, startUserMedia);
    }

    function startUserMedia(stream) {
        localStream = stream;
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
    }

    function stopRecording() {
        //ferma la registrazione
        recorder && recorder.stop();
        // creo link per il download
        //createDownloadLink();
        //pulisco il buffer di registrazione
        recorder && recorder.exportWAV(function(blob) {
            objAudio = blob;
        });
        localStream.stop();
        recorder.clear();
    }

    function sendRecording(recever) {
        var xhr = new XMLHttpRequest();
        xhr.open("POST", commandURL, false);
        //creo elemento che invio alla servlet contenente la registrazione
        var formData = new FormData();
        formData.append("operation", "addMessage");
        formData.append("msg", objAudio);
        formData.append("contactId", recever.id);
        xhr.send(formData);
    }

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
    
    /*******************************************************************
     * HANDLER PER EVENTI
     *******************************************************************/
    /**
     * 
     */
    function onShowPhoneCallMessagePanel(reciver){
        thisPresenter.showView(reciver);
    }
    
    /*******************************************************************
     * LISTNER PER EVENTI
     *******************************************************************/
    document.addEventListener("showPhoneCallMessagePanel", function(evt){
        onShowPhoneCallMessagePanel(evt.reciver);
    });
}