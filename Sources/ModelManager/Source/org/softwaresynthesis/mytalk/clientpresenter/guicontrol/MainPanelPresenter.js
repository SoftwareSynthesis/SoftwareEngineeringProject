/**
 * Presenter incaricato di gestire il pannello principale
 * 
 * @author Elena Zecchinato
 * @author Diego Beraldin
 * @param mediator  riferimento al mediatore che gestisce la collaborazione fra i presenter
 */
 function MainPanelPresenter(mediator) {
   //FIXME please! Questo ci è stato vietato da ricCARDINo
   this.mediator = mediator;
   //elemento controllato da questo presenter
   this.element = document.getElementById("MainPanel");
   
   /**
    * FIXME: qui non doveva esserci un'immagine
    * @author Elena Zecchinato
    */
   this.initialize = function() {
       this.element.innerHTML="";
     };
 }
