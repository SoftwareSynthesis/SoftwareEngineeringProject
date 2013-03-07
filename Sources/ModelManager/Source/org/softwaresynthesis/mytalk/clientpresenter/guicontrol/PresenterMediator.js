/**
 * Mediatore che gestisce la collaborazione fra i vari sotto-presenter
 *
 * @author Diego Beraldin
 */
function PresenterMediator() {
    // array associativo contentente i riferimenti a tutti i presenter
    this.presenters = new Array();
    // presenter del pannello della rubrica
    this.presenters[addressbookpp] = new AddressBookPanelPresenter(this);
    //presenter del pannello degli strumenti
    this.presenters[toolspp] = new ToolsPanelPresenter(this);
    // presenter del pannello del contatto
    this.presenters[contactpp] = new ContactPanelPresenter(this);

    /**
     * Inizializza l'interfaccia grafica delegando ai presenter il compito di
     * disegnare gli elementi
     *
     * @author Diego Beraldin
     */
    this.buildUI = function() {
        for (key in this.presenters) {
            this.presenters[key].initialize();
        }
    };

    /** Funzione da scatenare nel momento in cui è selezionato un contatto
     *
     * @author Diego Beraldin
     * @param contact contatto che deve essere visualizzato
     */
    this.onContactSelected = function(contact) {
        this.contactpp.display(contact);
    };
}

mediator = new PresenterMediator();
mediator.buildUI();
