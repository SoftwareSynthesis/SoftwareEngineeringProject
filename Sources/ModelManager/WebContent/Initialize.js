window.onload = function() {
    // VARIABILI GLOBALI per cui i programmatori meritano il taglio delle dita
    configurationFile = "Conf/servletlocation.xml";
    mediator = new PresenterMediator();
    communicationcenter = new CommunicationCenter();
    // crea l'interfaccia di autenticazione
    mediator.buildLoginUI();
};

window.onbeforeunload = function() {
    //if (confirm("Sei sicuro di volere uscire?")) {
    if (pc != null) {
        setTimeout(function() {
            endCall();
        }, 3000);
    }
    communicationcenter.disconnect();
    //}else{
    //}
}

Object.size = function(obj) {
    var size = 0, key;
    for (key in obj) {
        if (obj.hasOwnProperty(key))
            size++;
    }
    return size;
}; 