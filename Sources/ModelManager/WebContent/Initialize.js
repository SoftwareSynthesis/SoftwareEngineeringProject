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
    showPhoneCallMessagePanel = new CustomEvent("showPhoneCallMessagePanel");
    showPhoneIncomeCallAlertPanel = new CustomEvent("showPhoneIncomeCallAlertPanel");
    // eventi rimozione pannelli
    removeAllPanel = new CustomEvent("removeAllPanel");
    removeLoginPanel = new CustomEvent("removeLoginPanel");
    removeRegistrationPanel = new CustomEvent("removeRegistrationPanel");
    removeAddressBookPanel = new CustomEvent("removeAddressBookPanel");
    removeToolsPanel = new CustomEvent("removeToolsPanel");
    removeMainPanel = new CustomEvent("removeMainPanel");
    removeContactPanel = new CustomEvent("removeContactPanel");
    removeCommunicationPanel = new CustomEvent("removeCommunicationPanel");
    removePhoneCallMessagePanel = new CustomEvent("removePhoneCallMessagePanel");
    removePhoneIncomeCallAlertPanel = new CustomEvent("removePhoneIncomeCallAlertPanel");
    // eventi per LoginPresenter
    login = new CustomEvent("login");
    logout = new CustomEvent("logout");
    // eventi per AddressBookPresenter
    changeAddressBooksContactState = new CustomEvent("changeAddressBooksContactState");
    addContactToAddressBook = new CustomEvent("addContactToAddressBook");
    removeContactFromAddressBook = new CustomEvent("removeContactFromAddressBook");
    addContactToGroup = new CustomEvent("addContactToGroup");
    removeContactFromGroup = new CustomEvent("removeContactFromGroup");
    blockContact = new CustomEvent("blockContact");
    unlockContact = new CustomEvent("unlockContact");
    deleteGroup = new CustomEvent("deleteGroup");
    createGroup = new CustomEvent("createGroup");
    // eventi per ToolsPresenter
    showReturnToCommunicationPanelButton = new CustomEvent("showReturnToCommunicationPanelButton");
    // eventi per chiamata e chat
    changeMyState = new CustomEvent("changeMyState");
    sendMessage = new CustomEvent("sendMessage");
    appendMessageToChat = new CustomEvent("appendMessageToChat");
    startRinging = new CustomEvent("startRinging");
    stopRinging = new CustomEvent("stopRinging");
    call = new CustomEvent("call");
    rejectedCall = new CustomEvent("rejectedCall");
    acceptCall = new CustomEvent("acceptCall");
    rejectCall = new CustomEvent("rejectCall");
    // eventi per MainPresenter
    showGeneralPanel = new CustomEvent("showGeneralPanel");
    
    
    
    
    


    /**************************************
    ****Inizializzazione delle funzioni****
    **************************************/
    window.AudioContext = window.AudioContext || window.webkitAudioContext || window.mozAudioContext || window.msAudioContext;
    // navigator.getUserMedia = navigator.getUserMedia || navigator.webkitGetUserMedia || navigator.mozGetUserMedia || navigator.msGetUserMedia;
    window.URL = window.URL || window.webkitURL || window.mozURL || window.msURL;
    // RTCPeerConnection = RTCPeerConnection || webkitRTCPeerConnection || mozRTCPeerConnection || msRTCPeerConnection;

    /*****************************************
    ****Inizializzazione variabili globali**** || per cui i programmatori meritano il taglio delle dita
    *****************************************/
    mediator = new PresenterMediator();
    communicationcenter = new CommunicationCenter();

    /**********************************************************
    ****Inizializzazione variabili per connessione all'host****
    **********************************************************/
    var host =  window.location.protocol + "//" + window.location.host + window.location.pathname;
    if (window.location.host == "localhost") {
        commandURL = host + "Conf/controllerManagerStub.php";
    } else {
        commandURL =  host + "CommandManager";
    }
    urlChannelServlet = "ws://" + window.location.host + "/MyTalk/CommandManager";

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
    document.dispatchEvent(logout)
}
