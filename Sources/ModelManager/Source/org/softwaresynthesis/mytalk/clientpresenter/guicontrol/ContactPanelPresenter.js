/**
 * Presenter che gestisce il pannello che visualizza le informazioni del contatto
 * 
 * @author Diego Beraldin
 * @author Stefano Farronato
 * @author Elena Zecchinato
 */
function ContactPanelPresenter(mediator) {
  //FIXME please! Questo ci è stato vietato da ricCARDINo
  this.mediator = mediator;
  //elemento controllato da questo presenter
  this.element = document.getElementById("ContactPanel");
  
  /**
   * Inizializza il pannello che mostra le informazioni dei contatti
   * 
   */
   this.initialize = function() {
     
   };
  
  /**
   * Visualizza un contatto nel pannello principale
   * 
   * @param contact il concatto le cui informazioni devono essere visualizzates
   * @author Diego Beraldin
   */
  this.display = function(contact) {
  };
}
