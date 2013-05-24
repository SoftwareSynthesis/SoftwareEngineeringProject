window.onload = function() {
    /**********************************************
    ********CREAZIONI EVENTI PERSONALIZZATI********
    **********************************************/
    // eventi per Medietor
    loadedView = new CustomEvent("loadedView");
    // eventi creazione pannelli
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
    // eventi rimozione pannelli
    removeAllPanel = new CustomEvent("removeAllPanel");
    removeLoginPanel = new CustomEvent("removeLoginPanel");
    removeRegistrationPanel = new CustomEvent("removeRegistrationPanel");
    removeAddressBookPanel = new CustomEvent("removeAddressBookPanel");
    removeToolsPanel = new CustomEvent("removeToolsPanel");
    removeMainPanel = new CustomEvent("removeMainPanel");
    removeContactPanel = new CustomEvent("removeContactPanel");
    removeCommunicationPanel = new CustomEvent("removeCommunicationPanel");
    // eventi per LoginPresenter
    login = new CustomEvent("login");
    logout = new CustomEvent("logout");
    // eventi per AddressBookPresenter
    changeAddressBooksContactState = new CustomEvent("changeAddressBooksContactState");
    /*TODO*/addContactToAddressBook = new CustomEvent("addContactToAddressBook");
    /*TODO*/removeContactFromAddressBook = new CustomEvent("removeContactFromAddressBook");
    addContactToGroup = new CustomEvent("addContactToGroup");
    /*TODO*/removeContactFromGroup = new CustomEvent("removeContactFromGroup");
    /*TODO*/blockContact = new CustomEvent("blockContact");
    /*TODO*/unlockContact = new CustomEvent("unlockContact");
    /*TODO*/deleteGroup = new CustomEvent("deleteGroup");
    createGroup = new CustomEvent("createGroup");
    // eventi per ToolsPresenter
    showReturnToCommunicationPanelButton = new CustomEvent("showReturnToCommunicationPanelButton");
    // eventi per chiamata e chat
    changeMyState = new CustomEvent("changeMyState");
    sendMessage = new CustomEvent("sendMessage");
    appendMessageToChat = new CustomEvent("appendMessageToChat");

    /**************************************
    ****Inizializzazione delle funzioni****
    **************************************/
    window.AudioContext = window.AudioContext || window.webkitAudioContext || window.mozAudioContext || window.msAudioContext;
    navigator.getUserMedia = navigator.getUserMedia || navigator.webkitGetUserMedia || navigator.mozGetUserMedia || navigator.msGetUserMedia;
    window.URL = window.URL || window.webkitURL || window.mozURL || window.msURL;
    navigator.getUserMedia = navigator.getUserMedia || navigator.webkitGetUserMedia || navigator.mozGetUserMedia || navigator.msGetUserMedia;
    navigator.RTCPeerConnection = navigator.RTCPeerConnection || navigator.webkitRTCPeerConnection || navigator.mozRTCPeerConnection || navigator.msRTCPeerConnection;

    /*****************************************
    ****Inizializzazione variabili globali**** || per cui i programmatori meritano il taglio delle dita
    *****************************************/
    mediator = new PresenterMediator();
    communicationcenter = new CommunicationCenter();

    /**********************************************************
    ****Inizializzazione variabili per connessione all'host****
    **********************************************************/
    var host = "localhost/webalizer/ModelManager/WebContent";
    var stub = true;
    if (stub) {
        commandURL = "http://" + host + "/Conf/controllerManagerStub.php";
        urlChannelServlet = "ws://" + host + "/CommandManager";
    } else {
        commandURL = "http://" + host + "/CommandManager";
        urlChannelServlet = "ws://" + host + "/CommandManager";
    }

    /******************************
    ****Creo la UI per il Login****
    ******************************/
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
        return ("Prima di chiudere il browser effettua il Logout.");
}
