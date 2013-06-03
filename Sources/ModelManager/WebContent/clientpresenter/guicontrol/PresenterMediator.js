/**
 * Mediatore che gestisce la collaborazione fra i vari sotto-presenter
 *
 * @constructor
 * @this {PresenterMediator}
 * @author Diego Beraldin
 */
function PresenterMediator() {
    /***************************************************************************
     * VARIABILI PRIVATE
     **************************************************************************/
    // array delle viste che devono essere visualizzate
    var View = {
        accountSettings : "AccountSettingsView.html",
        addressBook : "AddressBookView.html",
        callHistory : "CallHistoryView.html",
        contact : "ContactView.html",
        communication : "CommunicationView.html",
        group : "GroupView.html",
        login : "LoginView.html",
        main : "MainView.html",
        message : "MessageView.html",
        phoneCallsRegistry : "PhoneCallsRegistryView.html",
        phoneIncomeCallAlert : "PhoneCallsRegistryView.html",
        register : "RegisterView.html",
        searchResult : "SearchResultView.html",
        tools : "ToolsView.html"
    };

    // array associativo contentente i riferimenti ai presenter di primo livello
    var presenters = new Array();
    presenters["login"] = new LoginPanelPresenter();
    presenters["register"] = new RegisterPanelPresenter();
    presenters["addressBook"] = new AddressBookPanelPresenter();
    presenters["tools"] = new ToolsPanelPresenter();
    presenters["main"] = new MainPanelPresenter();

    // presenter di secondo livello (pannelli contenuti nel MainPanel)
    var accountsettingspp = new AccountSettingsPanelPresenter();
    var communicationpp = new CommunicationPanelPresenter();
    var contactpp = new ContactPanelPresenter();
    var callhistorypp = new CallHistoryPanelPresenter();
    var messagepp = new MessagePanelPresenter();
    var searchresultpp = new SearchResultPanelPresenter();
    var grouppp = new GroupPanelPresenter();
    //creo array per rendere accessibili i presenter
    var secondaryPresenter = new Array();
    secondaryPresenter["accountSettings"] = accountsettingspp;
    secondaryPresenter["communication"] = communicationpp;
    secondaryPresenter["contact"] = contactpp;
    secondaryPresenter["callHistory"] = callhistorypp;
    secondaryPresenter["message"] = messagepp;
    secondaryPresenter["searchResult"] = searchresultpp;
    secondaryPresenter["group"] = grouppp;

    // presenter "pop-up" per messaggio segreteria
    var popupPresenters = new Array();
    popupPresenters["phoneCallsRegistry"] = new PhoneCallsRegistryPresenter();
    popupPresenters["phoneIncomeCallAlert"] = new PhoneIncomeCallAlertPresenter();

    /***************************************************************************
     * METODI PUBBLICI
     **************************************************************************/
    /**
     * Crea l'etichett che visualizza i dati dell'utente (se sono presenti) e in
     * ogni caso mostra l'email memorizzata nel sistema. La funzione è
     * utilizzata per generare la lista dei contatti
     *
     * @param contact
     *            il contatto a partire dal quale deve essere generata
     *            l'etichetta del nome
     * @returns {String} l'etichetta di nome
     * @author Riccardo Tresoldi
     */
    this.createNameLabel = function(contact) {
        var name = "";
        if (contact.name != "null")
            name += contact.name;
        if (contact.surname != "null") {
            if (name != "")
                name += " ";
            name += contact.surname;
        }
        if (name == "") {
            name += contact.email;
        }
        return name;
    };

    /**
     * Restituisce la lista dei contatti che sono stati scaricati
     * dall'AddressBookPanelPresenter
     *
     * @returns {Object} i contatti presenti nella rubrica
     * @author Diego Beraldin
     */
    this.getAddressBookContacts = function() {
        return presenters["addressBook"].getContacts();
    };

    /**
     * Restituice la lista dei gruppi che sono stati scaricati
     * dall'AddressBookPanelPresenter
     *
     * @returns {Object} i gruppi presenti nella rubrica
     * @author Diego Beraldin
     */
    this.getAddressBookGroups = function() {
        return presenters["addressBook"].getGroups();
    };

    /**
     * Funzione richiamata che comunica all'AddressBookPanelPresenter di cercare
     * i gruppi a cui appartiene un utente nella propria rubrica
     *
     * @author Riccardo Tresoldi
     * @param {Object}
     *            contact contatto di cui ricercare i gruppi in cui è inserito
     * @return {Object} array associativo che presenta la lista dei gruppi in
     *         cui è presente il contatto
     */
    this.getGroupsWhereContactsIs = function(contact) {
        return presenters["addressBook"].getGroupsWhereContactsIs(contact);
    };

    /**
     * Controllo se un utente è rpesente nella rubrica
     *
     * @author Riccardo Tresoldi
     * @param {Object}
     *            contact il contatto da controllare
     */
    this.contactAlreadyPresent = function(contact) {
        return presenters["addressBook"].contactAlreadyPresent(contact);
    };

    /**
     * Codice da eseguire per coordinare i presenter nel momento in cui ha
     * inizio una nuova comunicazione testuale, incapsula la collaborazione fra
     * {@link ContactPanelPresenter} e {@link CommunicationPanelPresenter}
     *
     * @see CommunicationPanelPresenter#addChat(user)
     * @param {Object}
     *            user utente con cui si vuole avviare la chat
     * @author Diego Beraldin
     */
    // TODO da documentare
    // TODO TRES mi confermi che esiste ancora? (createPanel, WTF?!)
    // XXX
    // XXX
    // XXX
    // XXX
    // XXX
    this.onChatStarted = function(user) {
        var element = communicationpp.createPanel();
        presenters["main"].displayChildPanel(element);
        communicationpp.addChat(user);
        communicationpp.displayChat(user);
        this.addOrRemoveCommunicationToTools();
    };

    /**
     * Funzione per ottenere l'elemento "myVideo" del CommunicationPP
     *
     * @author Riccardo Tresoldi
     * @return {} tag video che contiene il mio stream
     */
    this.getCommunicationPPMyVideo = function() {
        return communicationpp.getMyVideo();
    };

    /**
     * Funzione per ottenere l'elemento "otherVideo" del CommunicationPP
     *
     * @author Riccardo Tresoldi
     * @return {} tag video che contiene lo stream dell'altro utente
     */
    this.getCommunicationPPOtherVideo = function() {
        return communicationpp.getOtherVideo();
    };

    /**
     * Per richiamare la funzione updateStats() del CommunicationPP
     *
     * @author Riccardo Tresoldi
     * @param {String}
     *            text testo da visualizzare
     * @param {Boolean}
     *            isRecevedData rappresenta un flag booleano che determina se i
     *            dati sono ricenuti o inviati
     */
    // TODO da documentare (evento?)
    this.communicationPPUpdateStats = function(text, isRecevedData) {
        communicationpp.updateStats();
    };

    /**
     * Per richiamare la funzione updateTimer() del CommunicationPP
     *
     * @author Riccardo Tresoldi
     * @param {String}
     *            text minuti da visualizzare
     */
    // TODO da documentare (evento?)
    this.communicationPPUpdateTimer = function(text) {
        communicationpp.updateTimer(text);
    };

    /**
     * Permette di aggiungere o rimuovere a seconda delle necessità dal
     * ToolsPanel il pulsante che fa ritornare alle chiamate/chat attive
     *
     * @author Diego Beraldin
     */
    // TODO non c'è l'evento showReturnToCommunicationPanelButton?
    this.addOrRemoveCommunicationToTools = function() {
        if (communicationcenter.openChat.length > 0 || communicationcenter.videoCommunication) {
            presenters["tools"].addCommunicationFunction();
        } else {
            presenters["tools"].removeCommunicationFunction();
        }
    };

    /**
     * Permette di ottenere il contatto avendo solo l'ID dello stesso
     *
     * @author Riccardo Tresoldi
     * @param {Number} idContact ID del contatto di cui si vuole ottenere
     * l'oggetto
     * @return {Object} oggetto che rappresenta il contatto con l'ID passato come
     * parametro
     */
    this.getContactById = function(idContact) {
        return presenters["addressBook"].getContact(idContact);
    };

    /**
     * Restituisce la vista da visualizzare nell'interfaccia grafica
     * in base alla stringa passata come parametro, utilizzata come chiave
     * per l'array associativo delle viste contenuto qui.
     *
     * @param {String} key nome del presenter di cui richiamare la view
     * @returns {HTMLDivElement} elemento HTML da agganciare al documento
     * @author Riccardo Tresoldi
     */
    this.getView = function(key) {
        // ottengo il frammento di codice HTML dalla view
        var viewRequest = new XMLHttpRequest();
        viewRequest.responseType = "document";
        viewRequest.open("GET", "clientview/" + View[key], true);
        viewRequest.send();
        viewRequest.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {
                loadedView.view = viewRequest.responseXML.body.firstChild;
                loadedView.presenter = key;
                document.dispatchEvent(loadedView);
            }
        };
    };

    /***************************************************************************
     * HANDLER EVENTI MEDIATOR
     **************************************************************************/
    /**
     * Event Heandler per gestire il caricamento di una View
     *
     * @author Riccardo Tresoldi
     * @param {String} presenter il presenter di cui si è richiesta la view
     * @param {HTMLDivElement} view la view da passare all'initialize
     */
    function onLoadedView(presenter, view) {
        if (presenters[presenter]) {
            presenters[presenter].initialize(view);
        } else if (secondaryPresenter[presenter]) {
            // dico al mainPresenter di aggiungere il Panel ottenuto dalla view
            presenters["main"].displayChildPanel(view);
            // avviso il presenter indicato per visualizzare i dati corretti nel
            // pannello appena aggiunto
            secondaryPresenter[presenter].display();
        } else if (popupPresenters[presenter]) {
            var body = document.getElementsByTagName("body").item(0);
            //creo il div di sfondo
            var overlay = document.createElement("div");
            overlay.id = "overlayAnswerBox";
            //creo il div con la richiesta di risposta
            var answerBox = document.createElement("div");
            answerBox.id = "answerBox";
            //appendo l'elemento passato come parametro all'answerBox
            answerBox.innerHTML = view.outerHTML;
            //appendo i div appena creati al body
            if ( bodyFirstChild = body.firstChild) {
                body.insertBefore(overlay, bodyFirstChild);
                body.insertBefore(answerBox, bodyFirstChild);
            } else {
                body.appendChild(overlay);
                body.appendChild(answerBox);
            }
            // richiamo la visualizzazione dei contenuti della view da parte del
            // presenter giusto
            popupPresenters[presenter].showView();
        } else {
            alert("onLoadedView non gestita.\nNon esiste il presenter: [" + presenter + "]");
        }
    }

    /**
     * Gestiore dell'evento che rimuove ogni pannello
     * @author Riccardo Tresoldi
     */
    function onRemoveAllPanel() {
        document.dispatchEvent(removeLoginPanel);
        document.dispatchEvent(removeRegistrationPanel);
        document.dispatchEvent(removeAddressBookPanel);
        document.dispatchEvent(removeToolsPanel);
        document.dispatchEvent(removeMainPanel);
    }

    /**
     * Gestisce l'evento che richiama la costruzione dell'interfaccia grafica
     * dell'applicativo, dopo l'avvenuto login
     *
     * @author Riccardo Tresoldi
     */
    function onShowUIPanels() {
        document.dispatchEvent(removeAllPanel);
        document.dispatchEvent(showAddressBookPanel);
        document.dispatchEvent(showMainPanel);
        document.dispatchEvent(showToolsPanel);
    };

    /***************************************************************************
     * LISTNER DEGLI EVENTI
     **************************************************************************/
    document.addEventListener("loadedView", function(evt) {
        onLoadedView(evt.presenter, evt.view);
    });
    document.addEventListener("removeAllPanel", function(evt) {
        onRemoveAllPanel();
    });
    document.addEventListener("showUIPanels", function(evt) {
        onShowUIPanels();
    });
}
