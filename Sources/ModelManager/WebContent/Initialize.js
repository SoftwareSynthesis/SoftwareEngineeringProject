window.onload = function() {
    //Inizializzazione delle funzioni
    window.AudioContext = window.AudioContext || window.webkitAudioContext || window.mozAudioContext || window.msAudioContext;
    navigator.getUserMedia = navigator.getUserMedia || navigator.webkitGetUserMedia || navigator.mozGetUserMedia || navigator.msGetUserMedia;
    window.URL = window.URL || window.webkitURL || window.mozURL || window.msURL;
    navigator.getUserMedia = navigator.getUserMedia || navigator.webkitGetUserMedia || navigator.mozGetUserMedia || navigator.msGetUserMedia;
    navigator.RTCPeerConnection = navigator.RTCPeerConnection || navigator.webkitRTCPeerConnection || navigator.mozRTCPeerConnection || navigator.msRTCPeerConnection;

    // VARIABILI GLOBALI per cui i programmatori meritano il taglio delle dita
    mediator = new PresenterMediator();
    communicationcenter = new CommunicationCenter();
    commandURL = "http://localhost:8080/MyTalk/CommandManager";
    // crea l'interfaccia di autenticazione
    mediator.buildLoginUI();

    //CREAZIONI EVENTI PERSONALIZZATI
    var changeAddressBooksContactState = new CustomEvent("changeAddressBooksContactState");
};

Object.size = function(obj) {
    var size = 0, key;
    for (key in obj) {
        if (obj.hasOwnProperty(key))
            size++;
    }
    return size;
};

Object.isEmpty = function(obj) {
    for (key in obj) {
        return false;
    }
    return true;
}

window.onbeforeunload = function() {
    if (!Object.isEmpty(communicationcenter.my))
        //la variabile my non è impostata duqnue esco
        return ("Prima di chiudere il browser effettua il LogOut.");
}