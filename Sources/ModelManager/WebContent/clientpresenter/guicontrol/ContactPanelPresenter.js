/**
 * Presenter incaricato di gestire il pannello che visualizza le informazioni del contatto
 * 
 * @constructor
 * @this {ContactPanelPresenter}
 * @author Diego Beraldin
 * @author Riccardo tresoldi
 * @author Stefano Farronato
 * @author Elena Zecchinato
 */
function ContactPanelPresenter() {
 
  /**
   * Inizializza il pannello che mostra le informazioni dei contatti
   * 
   * @author Elena Zecchinato
   */
  this.createPanel = function() {
	var element = document.createElement("div");
	element.setAttribute("id", "ContactPanel");
	
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
    element.appendChild(ulData);
    return element;
  };
  
  /**
   * Visualizza un contatto nel pannello principale popolando il contenuto
   * dei <li> del pannello oppure impostando il percorso dell'immagine
   * 
   * @param contact il concatto le cui informazioni devono essere visualizzates
   * @author Diego Beraldin
   * @author Riccardo Tresoldi
   */
  this.display = function(contact) {
    //FIXME: si può fare con un ciclo, se imposto una classe?
    document.getElementById("contactName").appendChild(createTextNode(contact.name));
    document.getElementById("contactSurname").appendChild(createTextNode(contact.surname));
    document.getElementById("contactEmail").appendChild(createTextNode(contact.mail));
    document.getElementById("contactAvatar").setAttribute("src", contact.image);
  };
  
  /* TODO:
   * - contattare il CommunicationCenter per avviare una chiamata
   * - contattare il PresenterMediatoer per ottenere il contatto da visualizzare [serve?]
   * - implementare metodo che aggiunge l'utente visualizato alla rubrica
   * - implementare metodo che blocca l'utente visualizzato 
   */
}
