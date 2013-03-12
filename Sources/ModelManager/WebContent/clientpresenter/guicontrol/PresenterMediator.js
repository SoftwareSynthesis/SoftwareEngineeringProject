/**
 * Mediatore che gestisce la collaborazione fra i vari sotto-presenter
 *
 * @constructor
 * @this {PresenterMediator}
 * @author Diego Beraldin
 */
function PresenterMediator() {
    // array associativo contentente i riferimenti ai presenter di primo livello
    this.presenters = new Array();
    this.presenters["addressbook"] = new AddressBookPanelPresenter();
    this.presenters["tools"] = new ToolsPanelPresenter();
    this.presenters["main"] = new MainPanelPresenter();

    //presenter di secondo livello (pannelli contenuti nel MainPanel)
    this.contactpp = new ContactPanelPresenter();
    //TODO aggiungere anche gli altri che devono essere anche inclusi in index.html

    /**
     * Inizializza l'interfaccia grafica delegando ai presenter il compito di
     * disegnare gli elementi principali dell'interfaccia
     *
     * @author Diego Beraldin
     */
    this.buildUI = function() {
        for (var key in presenters) {
            presenters[key].initialize();
        }
    };

    /** Funzione da scatenare nel momento in cui è selezionato un contatto
     *
     * @author Diego Beraldin
     * @param {Object} contact contatto che deve essere visualizzato
     */
    this.onContactSelected = function(contact) {
        this.contactpp.display(contact);
    };

    /**
     * Funzione di callback richiamata dai pulsanti di SearchResultPanel
     * che comunica all'AddressBookPanelPresenter di aggiungere un contatto
     *
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
     * @param {Number} name rappresenta il nome del gruppo da aggiungere
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
}
