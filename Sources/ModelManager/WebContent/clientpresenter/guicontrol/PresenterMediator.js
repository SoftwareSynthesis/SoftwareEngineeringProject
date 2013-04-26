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
			AccountSettingsView: "AccountSettingsView.html",
			AddressBookView: "AddressBookView.html",
			CallHistoryView: "CallHistoryView.html",
			GroupView: "CommunicationView.html",
			LoginView: "LoginView.html",
			MainView: "MainView.html",
			MessageView: "MessageView.html",
			PhoneCallsRegistry: "PhoneCallsRegistryView.html",
			RegisterView: "RegisterView.html",
			SearchResultView: "SearchResultView.html",
			ToolsView: "ToolsView.html"
	};
	
    // array associativo contentente i riferimenti ai presenter di primo livello
    var presenters = new Array();
    presenters["login"] = new LoginPanelPresenter();
    presenters["register"] = new RegisterPanelPresenter();
    presenters["addressbook"] = new AddressBookPanelPresenter();
    presenters["tools"] = new ToolsPanelPresenter();
    presenters["main"] = new MainPanelPresenter();

    // presenter di secondo livello (pannelli contenuti nel MainPanel)
    var accountsettingspp = new AccountSettingsPanelPresenter();
    var communicationpp = new CommunicationPanelPresenter();
    var contactpp = new ContactPanelPresenter();
    // presenter che non sono ancora utilizzati
    // var callhistorypp = new CallHistoryPanelPresenter();
    // var messagepp = new MessagePanelPresenter();
    var searchresultpp = new SearchResultPanelPresenter();
    var grouppp = new GroupPanelPresenter();

    /***************************************************************************
     * METODI PUBBLICI
     **************************************************************************/

    /**
     * Inizializza l'interfaccia grafica delegando ai presenter il compito di
     * disegnare gli elementi principali dell'interfaccia, incaricando i
     * presenter di primo livello di creare e popolare i rispettivi pannelli
     *
     * @author Diego Beraldin
     */
    this.buildUI = function() {
        presenters["register"].hide();
        presenters["login"].hide();
        presenters["addressbook"].initialize();
        presenters["main"].initialize();
        presenters["tools"].initialize();
    };

    /**
     * Visualizza l'interfaccia di autenticazione al sistema, che comprende il
     * form di login (a.k.a. LoginPanel), chiamando in causa il {@link
     * LoginPanelPresenter} per la sua costruzione
     *
     * @see LoginPanelPresenter#initialize()
     * @author Diego Beraldin
     */
    this.buildLoginUI = function() {
        presenters["login"].initialize();
    };

    /**
     * Visualizza il form di registrazione (a.k.a. RegisterPanel) al sistema per
     * gli utenti che devono creare un nuovo account, demandando al {@link
     * RegisterPanelPresenter} il compito di creare il pannello per
     * l'inserimento dei dati di registrazione
     *
     * @see RegisterPanelPresenter#initialize()
     * @author Diego Beraldin
     */
    this.buildRegistrationUI = function() {
        presenters["register"].initialize();
    };

    /**
     * Crea l'etichett che visualizza i dati dell'utente (se sono presenti) e in
     * ogni caso mostra l'email memorizzata nel sistema. La funzione è
     * utilizzata per generare la lista dei contatti
     *
     * @param contact
     *            il contatto a partire dal quale deve essere generata
     *            l'etichetta del nome
     * @returns {String} l'etichetta di nome
     * @author Marco Schivo
     */
    this.createNameLabel = function(contact) {
        var name = "";
        if (contact.name != null)
            name += contact.name;
        if (contact.surname != null) {
            if (name != "")
                name += " ";
            name += contact.surname;
        }
        if (name != "") {
            name += contact.mail;
        }
        return name;
    };

    /**
     * Funzione da scatenare nel momento in cui è selezionato un contatto, ne
     * provoca la visualizzazione nel pannello dei contatti
     *
     * @see ContactPanelPresenter#display()
     * @param {Object}
     *            contact contatto che deve essere visualizzato
     * @author Diego Beraldin
     */
    this.onContactSelected = function(contact) {
        var element = contactpp.createPanel();
        presenters["main"].displayChildPanel(element);
        contactpp.display(contact);
    };

    /**
     * Funzione di callback richiamata dai pulsanti di SearchResultPanel che
     * comunica all'AddressBookPanelPresenter di aggiungere un contatto
     *
     * @see AddressBookPanelPresenter#addContact({Number})
     * @param {Object}
     *            contact utente che deve essere aggiunto alla rubrica
     * @return {Boolean} true se l'aggiunta è avvenuta con successo
     * @author Diego Beraldin
     * @author Riccardo Tresoldi
     */
    this.onContactAdded = function(contact) {
        try {
            return presenters["addressbook"].addContact(contact);
        } catch (err) {
            alert(err);
        }
    };

    /**
     * Funzione di callback richiamata dai pulsanti di SearchResultPanel che
     * comunica all'AddressBookPanelPresenter di rimuovere un contatto
     *
     * @author Riccardo Tresoldi
     * @param {Number}
     *            contact contatto da rimuovere
     */
    this.onContactRemoved = function(contact) {
        presenters["addressbook"].removeContact(contact);
    };

    /**
     * Funzione di callback richiamata dai pulsanti di SearchResultPanel che
     * comunica all'AddressBookPanelPresenter di aggiungere un gruppo
     *
     * @author Riccardo Tresoldi
     * @param {String}
     *            name rappresenta il nome del gruppo da aggiungere
     */
    this.onGroupAdded = function(name) {
        presenters["addressbook"].addGroup(name);
    };

    /**
     * Funzione di callback che comunica all'AddressBookPanelPresenter di
     * rimuovere un gruppo
     *
     * @author Riccardo Tresoldi
     * @param {Number}
     *            group rappresenta il gruppo da rimuovere
     */
    this.onGroupRemoved = function(group) {
        presenters["addressbook"].removeGroup(group);
    };

    /**
     * Funzione di callback richiamata dai pulsanti di SearchResultPanel che
     * comunica all'AddressBookPanelPresenter di aggiungere un contatto in un
     * gruppo
     *
     * @author Riccardo Tresoldi
     * @param {Number}
     *            contact rappresenta il contato da aggiungere
     * @param {Number}
     *            group rappresenta il gruppo in cui aggiungere il contatto
     */
    this.onContactAddedInGroup = function(contact, group) {
        presenters["addressbook"].addContactInGroup(contact, group);
    };

    /**
     * Funzione di callback richiamata dai pulsanti di SearchResultPanel che
     * comunica all'AddressBookPanelPresenter di rimuovere un contatto da un
     * gruppo
     *
     * @author Riccardo Tresoldi
     * @param {Number}
     *            contact rappresenta il contato da rimuovere
     * @param {Number}
     *            group rappresenta il gruppo da cui rimuovere il contatto
     */
    this.onContactRemovedFromGroup = function(contact, group) {
        presenters["addressbook"].deleteContactFromGroup(contact, group);
    };

    /**
     * Funzione di callback richiamata per eseguire l'operazione che comunica
     * all'AddressBookPanelPresenter il blocco di un utente
     *
     * @author Riccardo Tresoldi
     * @param {Object}
     *            contact reppresenta il contatto da bloccare
     * @return {Boolean} true solo se il contatto è stato bloccato con successo
     */
    this.onBlockContact = function(contact) {
        try {
            return presenters["addressbook"].blockUser(contact);
        } catch (err) {
            alert(err);
        }
    };

    /**
     * Funzione di callback richiamata per eseguire l'operazione che comunica
     * all'AddressBookPanelPresenter lo sblocco di un utente
     *
     * @author Riccardo Tresoldi
     * @param {Object}
     *            contact reppresenta il contatto da bloccare
     * @return {Boolean} true solo se il contatto è stato sbloccato con successo
     */
    this.onUnlockContact = function(contact) {
        try {
            return presenters["addressbook"].unlockUser(contact);
        } catch (err) {
            alert(err);
        }
    };

    /**
     * Restituisce la lista dei contatti che sono stati scaricati
     * dall'AddressBookPanelPresenter
     *
     * @returns {Object} i contatti presenti nella rubrica
     * @author Diego Beraldin
     */
    this.getAddressBookContacts = function() {
        return presenters["addressbook"].getContacts();
    };

    /**
     * Restituice la lista dei gruppi che sono stati scaricati
     * dall'AddressBookPanelPresenter
     *
     * @returns {Object} i gruppi presenti nella rubrica
     * @author Diego Beraldin
     */
    this.getAddressBookGroups = function() {
        return presenters["addressbook"].getGroups();
    };

    /**
     * Provoca la creazione del pannello della segreteria e la sua
     * visualizzazione all'interno del MainPanel come elemento figlio. La
     * costruzione del pannello è affidata al metodo createPanel che viene reso
     * disponibile da tutti i presenter di secondo livello
     *
     * @see MainPanel#displayChildPanel({HTMLDivElement})
     * @see MessagePanelPresenter#createPanel()
     * @author Diego Beraldin
     */
    this.displayMessagePanel = function() {
        var element = messagepp.createPanel();
        presenters["main"].displayChildPanel(element);
    };

    /**
     * Provoca la creazione del pannello delle impostazioni dell'utente e la sua
     * visualizzazione all'interno del MainPanel.
     *
     * @see MainPanel#displayChildPanel({HTMLDivElement})
     * @see AccountSettingsPanelPresenter#createPanel({HTMLDivElement})
     * @author Diego Beraldin
     */
    this.displayAccountSettingsPanel = function() {
        var element = callhistorypp.createPanel();
        presenters["main"].displayChildPanel(element);
    };

    /**
     * Provoca la creazione del pannello dello storico delle chiamate e la sua
     * visualizzazione all'interno del MainPanel.
     *
     * @see MainPanel#displayChildPanel({HTMLDivElement})
     * @see AccountSettingsPanelPresenter#createPanel({HTMLDivElement})
     * @author Diego Beraldin
     */
    this.displayCallHistoryPanel = function() {
        var element = callhistorypp.createPanel();
        presenters["main"].displayChildPanel(element);
    };

    /**
     * Funzione di callback richiamata dai pulsanti di SearchResultPanel che
     * comunica all'AddressBookPanelPresenter di filtrare la lista dei contatti
     * secondo un parametro
     *
     * @author Riccardo Tresoldi
     * @param {String}
     *            contact rappresenta l'id del contato da rimuovere
     */
    // FIXME non so se serve questa funzione dato che l'applicazione dei filtri
    // avviene tra AddressBookPP e se stesso
    this.onFiltredApplyedByParam = function(param) {
        presenters["addressbook"].applyFilterByString(param);
    };

    /**
     * Funzione di callback richiamata dai pulsanti di SearchResultPanel che
     * comunica all'AddressBookPanelPresenter di filtrare la lista dei contatti
     * appartenenti al gruppo passato
     *
     * @author Riccardo Tresoldi
     * @param {String}
     *            contact rappresenta l'id del contato da rimuovere
     */
    // FIXME non so se serve questa funzione dato che l'applicazione dei filtri
    // avviene tra AddressBookPP e se stesso
    this.onFiltredApplyedByGroup = function(group) {
        presenters["addressbook"].applyFilterByGroup(group);
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
        return presenters["addressbook"].getGroupsWhereContactsIs(contact);
    };

    /**
     * Provoca la visualizzazione del pannello per i risultati di una ricerca
     * fra gli utenti del sistema (a.k.a. SearchResultPanel) nel pannello
     * principale delegando a quest'ultimo tramite {@link MainPanelPresenter} la
     * visualizzazione
     *
     * @see SearchResultPanelPresenter#createPanel()
     * @see MainPanelPresenter#displayChildPanel({HTMLDivElement}
     * @author Diego Beraldin
     */
    this.displaySearchResultPanel = function() {
        var element = searchresultpp.createPanel();
        presenters["main"].displayChildPanel(element);
    };

    /**
     * Provoca la visualizzazione del pannello delle impostazioni del proprio
     * account (a.k.a. AccountSettingsPanel) nel pannello principale delegando a
     * quest'ultimo tramite {@link MainPanelPresenter} la sua visualizzazione
     *
     * @see AccountSettingsPanelPresenter#createPanel()
     * @see MainPanelPresenter#displayChildPanel({HTMLDivElement})
     * @author Diego Beraldin
     */
    this.displayAccountSettingsPanel = function() {
        var element = accountsettingspp.createPanel();
        presenters["main"].displayChildPanel(element);
    };

    /**
     * Provoca la visualizzazione del pannello delle comunicazioni (a.k.a.
     * CommunicationPanel) nel pannello principale delegando a quest'ultimo
     * tramite {@link MainPanelPresenter} la sua visualizzazione
     *
     * @see CommunicationPanelPresenter#createPanel()
     * @see MainPanelPresenter#displayChildPanel({HTMLDivElement})
     * @author Diego Beraldin
     */
    this.displayCommunicationPanel = function() {
        var element = communicationpp.createPanel();
        presenters["main"].displayChildPanel(element);
    };

    /**
     * Provoca la visualizzazione della scheda di un contatto nel
     * ComunicationPanelPresenter
     *
     * @author Riccardo Tresoldi
     * @param {Object}
     *            contact il contatto da visualizzare
     */
    this.displayContact = function(contact) {
        this.displayCommunicationPanel();
        contactpp.display(contact);
    };

    /**
     * Controllo se un utente è rpesente nella rubrica
     *
     * @author Riccardo Tresoldi
     * @param {Object}
     *            contact il contatto da controllare
     */
    this.contactAlreadyPresent = function(contact) {
        return presenters["addressbook"].contactAlreadyPresent(contact);
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
    this.onChatStarted = function(user) {
        var element = communicationpp.createPanel();
        presenters["main"].displayChildPanel(element);
        communicationpp.addChat(user);
        communicationpp.displayChat(user);
        this.addOrRemoveCommunicationToTools();
    };

    /**
     * Funzione per effettuare la vera e propria chiamata con un contatto
     *
     * @author Riccardo Tresoldi
     *
     * @param {Object}
     *            contact il contatto da chiamare
     * @param {Boolean}
     *            onlyAudio true se si vuole effettuare una chiamata solo audio
     */
    this.onCall = function(contact, onlyAudio) {
        var element = communicationpp.createPanel();
        presenters["main"].displayChildPanel(element);
        this.addOrRemoveCommunicationToTools();
        communicationcenter.call(true, contact, onlyAudio);
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
    this.communicationPPUpdateTimer = function(text) {
        communicationpp.updateTimer(text);
    };

    /*
    * EVENTUALMENTE DA TOGLIERE this.getCommunicationPP = function() { return
    * communicationpp; }; this.getMainPanel = function() { return
    * presenters["main"]; };
    */

    /**
     * Funzione per settare l'immagine corretta dello stato di un contatto della
     * rubrica
     *
     * @author Riccardo Tresoldi
     * @param {Number}
     *            contactId Id del contatto della rubrica a cui cambiare
     *            l'immagine
     * @param {String}
     *            state È lo stato da impostare al contatto passato come
     *            parametro
     */
    this.onChangeAddressBooksContactState = function(contactId, state) {
        // ottengo il contatto
        var contact = presenters["addressbook"].contacts[contactId];
        // invoco la funzione del AddressBookPanelPresenter
        presenters["addressbook"].setStateToContact(contact, state);
    };

    /**
     * Permette di aggiungere o rimuovere a seconda delle necessità dal
     * ToolsPanel il pulsante che fa ritornare alle chiamate/chat attive
     *
     * @author Diego Beraldin
     */
    this.addOrRemoveCommunicationToTools = function() {
        if (communicationcenter.openChat.length > 0 || communicationcenter.videoCommunication) {
            presenters["tools"].addCommunicationFunction();
        } else {
            presenters["tools"].removeCommunicationFunction();
        }
    };

    /**
     * Permette di effettuare il logout dal sistema
     *
     * @author Diego Beraldin
     */
    this.logout = function() {
        if (communicationcenter.isPCDefined()) {
            setTimeout(function() {
                communicationcenter.endCall();
            }, 3000);
        }
        presenters["tools"].logout();
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
        return presenters["addressbook"].getContact(idContact);
    }
    /**
     * Funzione richiamata per gestire l'evento di chiamata in arrivo
     *
     * @author Riccardo Tresoldi
     * @param {Object} caller rappresenta il contatto che sta chiamando
     */
    this.onIncomeCall = function(caller) {
        communicationpp.showAnswerBox(caller);
    }
    /**
     * Attiva la suoneria
     *
     * @author Riccardo Tresoldi
     * @param {String} evt tipo di evento che richiede la suoneria
     */
    this.startRinging = function(evt) {
        communicationpp.startRinging(evt);
    }
    /**
     * Ferma la suoneria
     *
     * @author Riccardo Tresoldi
     */
    this.stopRinging = function() {
        communicationpp.stopRinging();
    }
    /**
     * Funzione per mostrare un Popup personalizzato
     * Richede in input una porzione di codice HTMLDivElement da mostrare
     *
     * @author Riccardo Tresoldi
     * @param {HTMLDivElement} content elemento da visualizzare
     * @param {HTMLDivElement} Ritorna l'elemento HTML dell'anwerBox
     */
    this.showPopup = function(content) {
        if (document.getElementsByTagName("answerBox") || document.getElementsByTagName("overlayAnswerBox")) {
            return null;
        }
        var body = document.getElementsByTagName("body").item(0);
        //creo il div di sfondo
        var overlay = document.createElement("div");
        overlay.id = "overlayAnswerBox";
        //creo il div con la richiesta di risposta
        var answerBox = document.createElement("div");
        answerBox.id = "answerBox";
        //appendo l'elemento passato come parametro all'answerBox
        answerBox.innerHTML = content;
        //appendo i div appena creati al body
        if ( bodyFirstChild = body.firstChild) {
            body.insertBefore(overlay, bodyFirstChild);
            body.insertBefore(answerBox, bodyFirstChild);
        } else {
            body.appendChild(overlay);
            body.appendChild(answerBox);
        }
        //ritorno l'elemento HTMLDivlement
        return answerBox;
    };

    /**
     * Funzione che nasconde il popup
     *
     * @author Riccardo Tresoldi
     */
    this.removeAnswerBox = function() {
        //estraggo gli elementi da rimuovere
        overlay = document.getElementById('overlayAnswerBox');
        answerBox = document.getElementById('answerBox');
        //setto la visibilità a 'none'
        overlay.style.display = "none";
        answerBox.style.display = "none";
        //elimino dal DOM gli elementi FIXME
        document.removeChild(overlay);
        document.removeChild(answerBox);
    };

	/**
	 * Restituisce la vista da visualizzare nell'interfaccia grafica
	 * in base alla stringa passata come parametro, utilizzata come chiave
	 * per l'array associativo delle viste contenuto qui.
	 * 
	 * @param {String} key
	 * @returns {HTMLElement}
	 * @author Riccardo Tresoldi
	 */
	this.getView = function(key) {
		// ottengo il frammento di codice HTML dalla view
		var viewRequest = new XMLHttpRequest();
		viewRequest.open("GET", "clientview/" + View[key], false);
		viewRequest.responseType = "document";
		viewRequest.send();
		// ritorno il frammendto di codice ottenuto
		return viewRequest.responseXML.body.childNodes[0];
	};
}
