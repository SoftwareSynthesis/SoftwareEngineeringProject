/**
 * Presenter incaricato di gestire il pannello per la registrazuone di un
 * messaggio audio da inviare come messaggio di segreteria
 *
 * @author Marco Schivo
 * @author Riccardo Tresoldi
 */
function PhoneCallsRegistryPresenter() {
    var audio_context;
    var recorder;
	var localStream;

    //FIXME potrebbe essere nel mediator
    /**
     * Funzione che ritorna il codice HTML della view
     *
     * @author Riccardo Tresoldi
     * @return {HTMLElement}
     */
    this.getView = function() {
        // ottengo il frammento di codice HTML dalla view
        var viewRequest = new XMLHttpRequest();
        viewRequest.open("POST", View["PhoneCallsRegistry"], false);
        viewRequest.send();
        // ritorno il frammendto di codice ottenuto
        return viewRequest.responseXML;
    };

    /**
     * Funzione per mostrare la view
     *
     * @author Riccardo Tresoldi
     */
    this.showView = function() {
        // ottengo la view con l'apposita funzione
        var view = this.getview();
        // mostro la view con il popup
        /*TODO la seguente funzione deve essere creata in mediator e deve
         permettere di passare un frammento di codice da visualizzare*/
        mediator.showPupup(view);
    };

    /**
     *
     */
    this.startUserMedia = function(stream) {
        // creo lo stream media
        var input = audio_context.createMediaStreamSource(stream);
        // collego lo stream audio
        input.connect(audio_context.destination);
        // Inizializzo l'oggetto record per registrare l'imput
        recorder = new Recorder(input);
    };

    /**
     *
     */
    this.startRecording = function(button) {
        //inizio la registrazione
        recorder && recorder.record();
        //gestione GUI
        button.disabled = true;
        button.nextElementSibling.disabled = false;
    };

    /**
     * @param {Object} button
     */
    this.stopRecording = function(button) {
        //ferma la registrazione
        recorder && recorder.stop();
		//chiude l'input dalla fotocamera
		localStream.stop();
        //gestione GUI
        button.disabled = true;
        button.previousElementSibling.disabled = false;
        // creo link per il download
        //createDownloadLink();
        //pulisco il buffer di registrazione
        recorder.clear();
    };

    /**
     *
     * @param {Object} audio
     */
    this.sendRecording = function(audio) {
        var xhr = new XMLHttpRequest();
        // invio chiamata servlet da modificare
        xhr.open("POST", "http://localhost:8080/Channel/Segreteria", false);
        //id dell'utente a cui inviare la registrazione
        var id = 55;
        //creo elemento che invio alla servlet contenente la registrazione
        var formData = new FormData();
        formData.append("msg", audio);
        formData.append("contactId", id);
        xhr.send(formData);
    };

    /**
     *
     */
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

    //inizializza
    window.onload = function init() {
        try {
            // webkit shim
            window.AudioContext = window.AudioContext || window.webkitAudioContext;
            navigator.getUserMedia = navigator.getUserMedia || navigator.webkitGetUserMedia;
            window.URL = window.URL || window.webkitURL;

            audio_context = new AudioContext;
            __log('Audio context set up.');
            __log('navigator.getUserMedia ' + (navigator.getUserMedia ? 'available.' : 'not present!'));
        } catch (e) {
            alert('No web audio support in this browser!');
        }

        navigator.getUserMedia({
            audio : true,
            video : false
        }, startUserMedia, function(e) {
            __log('No live audio input: ' + e);
        });
    };
}