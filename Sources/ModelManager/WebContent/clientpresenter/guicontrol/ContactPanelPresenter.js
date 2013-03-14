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
<<<<<<< HEAD
 
  /**
   * Inizializza il pannello che mostra le informazioni dei contatti
   * della rubrica, quando ne viene selezionato uno dal pannello 
   * della rubrica
   * 
   * @returns {HTMLDivElement} pannello 'ContactPanel' costruito
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
   * dei <li> del pannello oppure impostando il percorso dell'immagine.
   * NOTA PER I VERIFICATORI:
   * Richiede la presenza di una lista ul con dei list item che abbiano
   * id rispettivametne 'contactName', 'contactSurname', 'contactEmail',
   * un elemento 'img' che abbia id 'contactAvatar' (di cui viene settato 
   * l'attributo 'src')
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
=======
    //elemento controllato da questo presenter
    this.element = document.getElementById("ContactPanel");

    /**
     * Inizializza il pannello che mostra le informazioni dei contatti
     *
     * @author Elena Zecchinato
     */
    this.createPanel = function() {
        //creare elemento attribuire l'id e alla fine ritornare
        var mainDiv = document.createElement("div");
        mainDiv.setAttribute("id", "contactPanel");

        //creo gli <li> per la visualizzazione dei campi
        var name = document.createElement('li');
        name.setAttribute("id", "contactName");
        var surname = document.createElement('li');
        surname.setAttribute("id", "contactSurname");
        var email = document.createElement('li');
        email.setAttribute("id", "contactEmail");
        var avatar = document.createElement('img');
        avatar.setAttribute("id", "contactAvatar");
        avatar.setAttribute("src", "");
        var ulData = document.createElement('ul');
        ulData.appendChild(name);
        ulData.appendChild(surname);
        ulData.appendChild(email);

        //creo i bottoni
        var divButton = document.createElement("div");
        var callButton = document.createElement('button');
        callButton.setAttribute("type", "button");
        callButton.setAttribute("id", "callButton");
        callButton.onclick; //TODO da creare l'hendler
        var videoCallButton = document.createElement('button');
        videoCallButton.setAttribute("type", "button");
        videoCallButton.setAttribute("id", "videoCallButton");
        videoCallButton.onclick; //TODO da creare l'hendler
        var chatButton = document.createElement('button');
        chatButton.setAttribute("type", "button");
        chatButton.setAttribute("id", "chatButton");
        chatButton.onclick; //TODO da creare l'hendler
        divButton.appendChild(callButton);
        divButton.appendChild(videoCallButton);
        divButton.appendChild(chatButton);

        //appendo i sottonodi al div da ritornare
        mainDiv.appendChild(ulData);
        mainDiv.appendChild(divButton);
        
        //ritorno il div appena creato da aggiungere al MainPanel
        return mainDiv;
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
>>>>>>> UPDATE ContactPanelPresenter.js
}
