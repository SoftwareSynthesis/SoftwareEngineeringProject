/**
 * Presenter incaricato di gestire il pannello che visualizza le informazioni del contatto
 * 
 * @author Diego Beraldin
 * @author Stefano Farronato
 * @author Elena Zecchinato
 */
function ContactPanelPresenter(mediator) {
  //elemento controllato da questo presenter
  this.element = document.getElementById("ContactPanel");
  
  /**
   * Inizializza il pannello che mostra le informazioni dei contatti
   * 
   * @author Elena Zecchinato
   */
  this.initialize = function() {
    var name = document.createElement('li');
    name.setAttribute("id", "contactName");
    
    var surname = document.createElement('li');
    surname.setAttribute("id", "contactSurname");
    
    var email = document.createElement('li');
    email.setAttribute("id", "contactEmail");
    
    var avatar = document.createElement('img');
    avatar.setAttribute("id", "contactAvatar");
    avatar.setAttribute("src", "");
    
    var callButton = document.createElement('button');
    callButton.setAttribute("type", "button");
    callButton.setAttribute("id", "callButton");
    
    var videoCallButton = document.createElement('button');
    videoCallButton.setAttribute("type", "button");
    videoCallButton.setAttribute("id", "videoCallButton");
    
    var chatButton = document.createElement('button');
    chatButton.setAttribute("type", "button");
    chatButton.setAttribute("id", "chatButton");
      
    
    //appendo i sottonodi alla lista dei dati dell'utente
    var ulData = document.createElement('ul'); 
    ulData.appendChild(name);
    ulData.appendChild(surname);
    ulData.appendChild(email);
    
    
    //apendo il sottoalbero al DOM
    this.element.appendChild(ulData);
  };
  
  /**
   * Visualizza un contatto nel pannello principale popolando il contenuto
   * dei <li> del pannello oppure impostando il percorso dell'immagine
   * 
   * @param contact il concatto le cui informazioni devono essere visualizzates
   * @author Diego Beraldin
   */
  this.display = function(contact) {
    //FIXME: si può fare con un ciclo, se imposto una classe?
    var name = document.getElementById("contactName");
    name.innerHTML = contact.name;
    
    var surname = document.getElementById("contactSurname");
    surname.innerHTML = contact.surname;
    
    var email = document.getElementById("contactEmail");
    email.innerHTML = contact.email;
    
    var avatar = document.getElementById("contactAvatar");
    avatar.setAttribute("src", contact.image);
  };
  
  /* TODO:
   * - contattare il CommunicationCenter per avviare una chiamata
   */
}
