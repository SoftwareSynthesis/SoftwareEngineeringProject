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
    var callhistorypp = new CallHistoryPanelPresenter();
    var messagepp = new MessagePanelPresenter();
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
        presenters["tools"].intialize();
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
     * Funzione da scatenare nel momento in cui è selezionato un contatto, ne
     * provoca la visualizzazione nel pannello dei contatti
     *
     * @see ContactPanelPresenter#display()
     * @param {Object}
     *            contact contatto che deve essere visualizzato
     * @author Diego Beraldin
     */
    this.onContactSelected = function(contact) {
        contactpp.display(contact);
    };

    /**
     * Funzione di callback richiamata dai pulsanti di SearchResultPanel che
     * comunica all'AddressBookPanelPresenter di aggiungere un contatto
     *
     * @see AddressBookPanelPresenter#addContact({Number})
     * @param {Number}
     *            userID id dell'utente che deve essere aggiunto alla rubrica
     * @return {Boolean} true se l'aggiunta è avvenuta con successo
     * @author Diego Beraldin
     * @author Riccardo Tresoldi
     */
    this.onContactAdded = function(userID) {
        // FIXME correggere passando il contatto e non l'id di esso
        try {
            return presenters["addressbook"].addContact(userID);
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
     *            userID rappresenta l'id del contato da rimuovere
     */
    this.onContactRemoved = function(userID) {
        // FIXME correggere passando il contatto e non l'id di esso
        presenters["addressbook"].removeContact(userID);
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
     * @param {Number} group rappresenta il gruppo da rimuovere
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
     *            contact rappresenta l'id del contato da aggiungere
     * @param {Number}
     *            group rappresenta l'id del gruppo in cui aggiungere il
     *            contatto
     */
    this.onContactAddeddInGroup = function(contact, group) {
        // FIXME correggere passando il contatto e non l'id di esso
        presenters["addressbook"].addContactInGroup(contact, group);
    };

    /**
     * Funzione di callback richiamata dai pulsanti di SearchResultPanel che
     * comunica all'AddressBookPanelPresenter di rimuovere un contatto da un
     * gruppo
     *
     * @author Riccardo Tresoldi
     * @param {Number}
     *            contact rappresenta l'id del contato da rimuovere
     * @param {Number}
     *            group rappresenta l'id del gruppo da cui rimuovere il contatto
     */
    this.onContactRemovedFromGroup = function(contact, group) {
        // FIXME correggere passando il contatto e non l'id di esso
        presenters["addressbook"].removeContactFromGroup(contact, group);
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
        presenter["main"].displayChildPanel(element);
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
        presenter["main"].displayChildPanel(element);
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
        communicationpp.addChat(user);
        communicationpp.displayChat(user);
        presenter["main"].displayChildPanel(element);
    };
    
    /**
     * Funzione per ottenere il ComunicationPanelPresenter
     * 
     * @author Diego Beraldin
     * @author Riccardo Tresoldi
     * 
     * @return {Object} ritorna il comunicationPP
     */
    this.getCommunicationPP = function() {
        return communicationpp;
    }
}
