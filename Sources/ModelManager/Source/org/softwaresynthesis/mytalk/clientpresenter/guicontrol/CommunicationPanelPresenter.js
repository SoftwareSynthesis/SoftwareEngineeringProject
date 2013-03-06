/**
 * 
 * @author Elena Zecchinato
 * @author Diego Beraldin
 * @param mediator  riferimento al mediatore che gestisce la collaborazione fra i presenter
 */
function CommunicationPanelPresenter(mediator) {
  //FIXME please! Questo ci è stato vietato da ricCARDINo
  this.mediator = mediator;
  //elemento controllato da questo presenter
  this.element = document.getElementById("CommunicationPanel");
  
  /**
   * 
   * @author Elena Zecchinato
   */
  this initialize = function() {
    //creo i due div principali
    var divProfile = document.createElement('div')
    var divChat = document.createElement('div');
    
    //apendo il sottoalbero al DOM
    this.element.innerHTML="";
    this.element.appendChild(divProfile);
    this.element.appendChild(divChat);
     };
}
