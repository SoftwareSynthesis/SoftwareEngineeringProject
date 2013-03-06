/**
 * Mediatore che gestisce la collaborazione fra i vari sotto-presenter
 * 
 * @author Diego Beraldin
 */
function PresenterMediator() {
  // presenter del pannello della rubrica
  var addressbookpp = new AddressBookPanelPresenter(this);
  // presenter del pannello del contatto
  var contactpp = new ContactPanelPresenter(this);
  
  /* Funzione da scatenare nel momento in cui è selezionato un contatto
   * 
   * @param contact contatto che deve essere visualizzato
   */
  this.onContactSelected = function(contact) {
    contactpp.display(contact);
  };
}

mediator = new PresenterMediator();
