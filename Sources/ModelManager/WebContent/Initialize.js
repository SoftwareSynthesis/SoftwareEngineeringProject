window.onload = function() {
    //CREAZIONI EVENTI PERSONALIZZATI
    changeAddressBooksContactState = new CustomEvent("changeAddressBooksContactState");
    loadedView = new CustomEvent("loadedView");
    login = new CustomEvent("login");
    //eventi creazione pannelli
    showLoginPanel = new CustomEvent("showLoginPanel");
    showRegistrationPanel = new CustomEvent("showRegistrationPanel");
    showUIPanels = new CustomEvent("showUIPanels");
    showAddressBookPanel = new CustomEvent("showAddressBookPanel");
    showToolsPanel = new CustomEvent("showToolsPanel");
    showMainPanel = new CustomEvent("showMainPanel");
    showContactPanel = new CustomEvent("showContactPanel");
    showAccountSettingPanel = new CustomEvent("showAccountSettingPanel");
    showCallHistoryPanel = new CustomEvent("showCallHistoryPanel");
    showGroupPanel = new CustomEvent("showGroupPanel");
    showMessagePanel = new CustomEvent("showMessagePanel");
    showSearchResultPanel = new CustomEvent("showSearchResultPanel");
    showCommunicationPanel = new CustomEvent("showCommunicationPanel");
    showReturnToCommunicationPanelButton = new CustomEvent("showReturnToCommunicationPanelButton");
    //eventi rimozione pannelli
    removeAllPanel = new CustomEvent("removeAllPanel");
    removeLoginPanel = new CustomEvent("removeLoginPanel");
    removeRegistrationPanel = new CustomEvent("removeRegistrationPanel");
    removeAddressBookPanel = new CustomEvent("removeAddressBookPanel");
    removeToolsPanel = new CustomEvent("removeToolsPanel");
    removeMainPanel = new CustomEvent("removeMainPanel");
    removeContactPanel = new CustomEvent("removeContactPanel");
    removeCommunicationPanel = new CustomEvent("removeCommunicationPanel");

    //Inizializzazione delle funzioni
    window.AudioContext = window.AudioContext || window.webkitAudioContext || window.mozAudioContext || window.msAudioContext;
    navigator.getUserMedia = navigator.getUserMedia || navigator.webkitGetUserMedia || navigator.mozGetUserMedia || navigator.msGetUserMedia;
    window.URL = window.URL || window.webkitURL || window.mozURL || window.msURL;
    navigator.getUserMedia = navigator.getUserMedia || navigator.webkitGetUserMedia || navigator.mozGetUserMedia || navigator.msGetUserMedia;
    navigator.RTCPeerConnection = navigator.RTCPeerConnection || navigator.webkitRTCPeerConnection || navigator.mozRTCPeerConnection || navigator.msRTCPeerConnection;

    // VARIABILI GLOBALI per cui i programmatori meritano il taglio delle dita
    mediator = new PresenterMediator();
    communicationcenter = new CommunicationCenter();

    // Link connessione a servlet
    var host = "localhost";
    var stub = true;
    if (stub) {
        commandURL = "http://" + host + "/webalizer/ModelManager/WebContent/conf/controllerManagerStub.php";
        urlChannelServlet = "ws://" + host + ":8080/MyTalk/CommandManager";
    } else {
        commandURL = "http://" + host + ":8080/MyTalk/CommandManager";
        urlChannelServlet = "ws://" + host + ":8080/MyTalk/CommandManager";
    }
    // crea l'interfaccia di autenticazione

    //Creo la UI per il Login
    document.dispatchEvent(showLoginPanel);
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