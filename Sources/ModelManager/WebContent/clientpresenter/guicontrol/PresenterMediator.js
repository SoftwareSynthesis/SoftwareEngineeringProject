/**
 * Mediatore che gestisce la collaborazione fra i vari sotto-presenter
 *
 * @constructor
 * @this {PresenterMediator}
 * @author Diego Beraldin
 */
function PresenterMediator() {
	/**********************************************************
    VARIABILI PRIVATE
    ***********************************************************/
    // array associativo contentente i riferimenti ai presenter di primo livello
    var presenters = new Array();
    presenters["login"] = new LoginPanelPresenter("http://localhost:8080/LoginManager");
    presenter["register"] = new RegisterPanelPresenter("http://localhost:8080/LoginManager");
    presenters["addressbook"] = new AddressBookPanelPresenter("http://localhost:8080/AddressBookManager");
    presenters["tools"] = new ToolsPanelPresenter();
    presenters["main"] = new MainPanelPresenter();

    //presenter di secondo livello (pannelli contenuti nel MainPanel)
    var accountsettingspp = new AccountSettingsPanelPresenter("http://localhost:8080/AccountManager");
    var communicationpp = new CommunicationPanelPresenter();
    var contactpp = new ContactPanelPresenter();
    var callhistorypp = new CallHistoryPanelPresenter("http://localhost:8080/CallHistoryManager");
    var messagepp = new MessagePanelPresenter("http://localhost:8080/MessageManager");
    //FIXME il SearchResultPanelPresenter deve interfacciarsi con qualche servlet?
    var searchresultpp = new SearchResultPanelPresenter();
    var toolspp = new ToolsPanelPresenter();
    //TODO deve esistere anche GroupPanelPresenter?
    
    /**********************************************************
    METODI PUBBLICI
    ***********************************************************/

    /**
     * Inizializza l'interfaccia grafica delegando ai presenter il compito di
     * disegnare gli elementi principali dell'interfaccia, incaricando i presenter
     * di primo livello di creare e popolare i rispettivi pannelli
     *
     * @author Diego Beraldin
     */
    this.buildUI = function() {
        for (var key in presenters) {
            presenters[key].initialize();
        }
    };
    
    /**
     * Visualizza l'interfaccia di autenticazione al sistema, che comprende il form
     * di login (a.k.a. LoginPanel), chiamando in causa il {@link LoginPanelPresenter}
     * per la sua costruzione
     * 
     * @see LoginPanelPresenter#initialize()
     * @author Diego Beraldin
     */
    this.buildLoginUI = function() {
    	presenters["login"].initialize();
    };
    
    /**
     * Visualizza il form di registrazione (a.k.a. RegisterPanel) al sistema per gli utenti
     * che devono creare un nuovo account, demandando al {@link RegisterPanelPresenter} il
     * compito di creare il pannello per l'inserimento dei dati di registrazione
     * 
     * @see RegisterPanelPresenter#initialize()
     * @author Diego Beraldin
     */
    this.buildRegistrationUI = function() {
    	presenter["register"].initialize();
    };

    /** Funzione da scatenare nel momento in cui è selezionato un contatto,
     * ne provoca la visualizzazione nel pannello dei contatti
     *
     * @see ContactPanelPresenter#display()
     * @param {Object} contact contatto che deve essere visualizzato
     * @author Diego Beraldin
     */
    this.onContactSelected = function(contact) {
        contactpp.display(contact);
    };

    /**
     * Funzione di callback richiamata dai pulsanti di SearchResultPanel
     * che comunica all'AddressBookPanelPresenter di aggiungere un contatto
     *
     * @see AddressBookPanelPresenter#addContact({Number})
     * @param {Number} userID id dell'utente che deve essere aggiunto alla rubrica
     * @author Diego Beraldin
     */
    this.onContactAdded = function(userID) {
        this.presenters["addressbook"].addContact(userID);
    };

    /**
     * Funzione di callback richiamata dai pulsanti di SearchResultPanel
     * che comunica all'AddressBookPanelPresenter di rimuovere un contatto
     *
     * @author Riccardo Tresoldi
     * @param {Number} userID rappresenta l'id del contato da rimuovere
     */
    this.onContactRemoved = function(userID) {
        this.presenters["addressbook"].removeContact(userID);
    };

    /**
     * Funzione di callback richiamata dai pulsanti di SearchResultPanel
     * che comunica all'AddressBookPanelPresenter di aggiungere un gruppo
     *
     * @author Riccardo Tresoldi
     * @param {String} name rappresenta il nome del gruppo da aggiungere
     */
    this.onGroupAdded = function(name) {
        this.presenters["addressbook"].addGroup(name);
    };

    /**
     * Funzione di callback richiamata dai pulsanti di SearchResultPanel
     * che comunica all'AddressBookPanelPresenter di rimuovere un gruppo
     *
     * @author Riccardo Tresoldi
     * @param {Number} group rappresenta l'id del gruppo da rimuovere
     */
    this.onGroupRemoved = function(group) {
        this.presenters["addressbook"].removeGroup(contact);
    };

    /**
     * Funzione di callback richiamata dai pulsanti di SearchResultPanel
     * che comunica all'AddressBookPanelPresenter di aggiungere un contatto in un gruppo
     *
     * @author Riccardo Tresoldi
     * @param {Number} contact rappresenta l'id del contato da aggiungere
     * @param {Number} group rappresenta l'id del gruppo in cui aggiungere il contatto
     */
    this.onContactAddeddInGroup = function(contact, group) {
        this.presenters["addressbook"].addContactInGroup(contact, group);
    };

    /**
     * Funzione di callback richiamata dai pulsanti di SearchResultPanel
     * che comunica all'AddressBookPanelPresenter di rimuovere un contatto da un gruppo
     *
     * @author Riccardo Tresoldi
     * @param {Number} contact rappresenta l'id del contato da rimuovere
     * @param {Number} group rappresenta l'id del gruppo da cui rimuovere il contatto
     */
    this.onContactAddeddInGroup = function(contact, group) {
        this.presenters["addressbook"].removeContactFromGroup(contact, group);
    };
    
    /**
     * Provoca la creazione del pannello della segreteria e la sua visualizzazione
     * all'interno del MainPanel come elemento figlio.
     * La costruzione del pannello è affidata al metodo createPanel che viene reso
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
    this.displayCallHistoryPanel= function() {
    	var element = callhistorypp.createPanel();
    	presenters["main"].displayChildPanel(element);
    };

    /**
     * Funzione di callback richiamata dai pulsanti di SearchResultPanel
     * che comunica all'AddressBookPanelPresenter di filtrare la lista dei contatti secondo un parametro
     *
     * @author Riccardo Tresoldi
     * @param {String} contact rappresenta l'id del contato da rimuovere
     */
    this.onFiltredApplyedByParam = function(param) {
        this.presenters["addressbook"].applyFilterByString(param);
    };

    /**
     * Funzione di callback richiamata dai pulsanti di SearchResultPanel
     * che comunica all'AddressBookPanelPresenter di filtrare la lista dei contatti appartenenti al gruppo passato
     *
     * @author Riccardo Tresoldi
     * @param {String} contact rappresenta l'id del contato da rimuovere
     */
    this.onFiltredApplyedByGroup = function(group) {
        this.presenters["addressbook"].applyFilterByGroup(group);
    };
}
