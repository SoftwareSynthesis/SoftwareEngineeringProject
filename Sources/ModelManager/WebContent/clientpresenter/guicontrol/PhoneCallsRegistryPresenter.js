/**
 * Presenter incaricato di gestire il pannello per la registrazuone di un
 * messaggio audio da inviare come messaggio di segreteria
 *
 * @author Marco Schivo
 * @author Riccardo Tresoldi
 */
function PhoneCallsRegistryPresenter() {
    /*************************************************************
     * CAMPI DATI PRIVATI
     *************************************************************/
    var audio_context;
    var recorder;
    var objAudio;
    var localStream;

    var thisPresenter = this;
    var thisPanel = null;

    var currentReciver = null;

    //variabili pulsanti
    var startRecordButton, stopRecordButton, sendRecordButton, closePopupButton;

    /*************************************************************
     * FUNZIONI PRIVATE
     *************************************************************/
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
        startRecordButton.disabled = false;
    }

    function startRecording() {
        startRecordButton.disabled = true;
        stopRecordButton.disabled = false;
        closePopupButton.disabled = true;
        //inizio la registrazione
        if (record) {
            recorder.record();
        }
    }

    function stopRecording() {
        closePopupButton.disabled = false;
        stopRecordButton.disabled = true;
        sendRecordButton.disabled = false;
        //ferma la registrazione
        if (recorder) {
            recorder.stop();
        }

        //pulisco il buffer di registrazione
        if (recorder) {
            recorder.exportWAV(function(blob) {
                objAudio = blob;
                var urlAudio = URL.createObjectURL(blob);
                var audioElement = document.getElementById("recordAudioElement");
                audioElement.src = urlAudio;
            });
        }
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

    /*************************************************************
     * FUNZIONI PUBBLICHE
     *************************************************************/
    /**
     * Funzione per mostrare la view
     *
     * @author Riccardo Tresoldi
     */
    this.showView = function(receiver) {
        thisPanel = document.getElementById("PhoneCallRecorder");
        startRecordButton = document.getElementById("startRecord");
        stopRecordButton = document.getElementById("stopRecord");
        sendRecordButton = document.getElementById("sendRecord");
        closePopupButton = document.getElementById("closePopup");
        startRecordButton.disabled = true;
        sendRecordButton.disabled = true;
        stopRecordButton.disabled = true;
        startRecordButton.onclick = function() {
            startRecording();
        };
        stopRecordButton.onclick = function() {
            stopRecording();
        };
        sendRecordButton.click = function() {
            sendRecording(receiver);
            document.dispatchEvent(removePhoneCallMessagePanel);
        };
        closePopupButton.onclick = function() {
            document.dispatchEvent(removePhoneCallMessagePanel);
        };
        initializeStream();
    };

    this.destroy = function() {
        if (thisPanel) {
            var thisPanelParent = thisPanel.parentElement.parentElement;
            thisPanelParent.removeChild(thisPanel.parentElement);
            thisPanel = null;
            var overlay = document.getElementById("overlayAnswerBox");
            var overlayParent = overlay.parentElement;
            overlayParent.removeChild(overlay);
        }
    };

    /*******************************************************************
     * HANDLER PER EVENTI
     *******************************************************************/
    function onShowPhoneCallMessagePanel(reciver) {
        currentReciver = reciver;
        mediator.getView("phoneCallsRegistry");
    }

    function onRemovePhoneCallMessagePanel() {
        thisPresenter.destroy();
    }

    /*******************************************************************
     * LISTNER PER EVENTI
     *******************************************************************/
    document.addEventListener("showPhoneCallMessagePanel", function(evt) {
        onShowPhoneCallMessagePanel(evt.reciver);
    });
    document.addEventListener("removePhoneCallMessagePanel", function() {
        onRemovePhoneCallMessagePanel();
    });
}