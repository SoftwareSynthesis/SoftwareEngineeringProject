/**
 * Presenter incaricatdi gestire il pannello della rubrica, contiene le funzioni
 * associate ai widget grafici della vista relativi alla rubrica e ha la
 * responsabilità di aggiornare la vista sulla base dei dati ricevuti dal server
 * 
 * @author Marco Schivo
 * @author Riccardo Tresoldi
 * @author Diego Beraldin
 */
function AddressBookPanelPresenter(mediator) {
  //FIXME please! Questo ci è stato vietato da ricCARDINo
  this.mediator = mediator;
  //elemento controllato da questo presenter
  this.element = document.getElementById("AddressBookPanel");
  //da configurare con url della servlet 
  this.urlServlet = "http://localhost:8080/AddressBookManager";
  //array dei contatti della rubrica dell'utente
  this.contacts = new Array();
  
  /**
   * Inizializza il <div> AddressBookPannel e popola i contatti della rubrica
   *
   * @author Riccardo Tresoldi
   */
    this.initialize = function() {
      //creo i tre div principali
      var divSearch = document.createElement('div');
      var divSort = document.createElement('div');
      var divList = document.createElement('div');
      
      //creo contenuto divSearch
      var inputText = document.createElement('input');
      inputText.setAttribute("type", "text");
      var inputButton = document.createElement('input');
      inputText.setAttribute("type", "image");
      
      //creo contenuto divSort
      var select = document.createElement('select');
      select.setAttribute("id", "selectSort");
      
      //creo contenuto divList
      var ul = document.createElement('ul');
      select.setAttribute("id", "AddressBookList");
      
      //appendo i sottonodi ai nodi principali
      divSearch.appendChild(inputText);
      divSearch.appendChild(inputButton);
      divSort.appendChild(select);
      divList.appendChild(ul);
      
      //appendo il sottoalbero al DOM
      this.element.innerHTML = "";
      this.element.appendChild(divSearch);
      this.element.appendChild(divSort);
      this.element.appendChild(divList);
      
      //scarica la rubrica dal server
      getAddressBookContacts();
      //visualizza i contatti nel pannello
      setup();
    };
  
 /**
  * Funzione per ottenere i contatti della proipria rubrica dal server
  *
  * @author Marco Schivo
  */
  this.getAddressBookContacts = function() {
    var request = new XMLHttpRequest();
    //il server ritorna Nome, Cognome, Status, Email, Id, Image ad esempio:
    /*
    [
      {"nome": "Marco", "cognome": "Schivo", "email": "marcoskivo@gmail.com", "ID": "1", "Image": "sfdsfsd.jpg", "Status": "Avaible"},
      {"nome": "Andrea", "cognome": "Rizzi", "email": "asondjs@gmail.com", "ID": "2", "Image": "sad.jpg", "Status": "Not Avaible"}
    ]
    */
    request.onreadystatechange = function() {
      //var that = this;
      if (this.readyState == 4 && this.status == 200) {
        //ricevo un JSON contenente tutti i contatti della mia rubrica e li inserisco nell'array dichiarato globalmente "AddresssBookList"
        contacts = JSON.parse(request.responseText); // probabile problema: risolvere con that.contacts
      }
    };
    
    request.open("POST", urlServlet, "true");
    request.setRequestHeader("Content-type","application/x-www-form-urlencoded");
    //se c'è bisogno di passare piu parametri, agganciarli con &
    //idUser deve essere dichiarata variabile globale
    request.send("id=" + idUser);
    }
    

    
  /**
   * Funzione per inserire i contatti estratti dal server all'interno della lista
   * 'AddressBookList' all'interno di 'AddressBookPannel'
   *
   * @author Riccardo Tresoldi
   */
    this.setup = function() { 
      //estraggo l'<ul> del Addressbook e lo inizializzo
      var ulList = this.element.getElementById("AddressBookList");
      ulList.innerHTML = "";
	
      //<ul id="AddressBookList"></ul>
      for (var contact in this.contacts) {
        //ciclo i contatti e agiungo un <li> per ogni contatto.
        addListItem(ulList, this.contacts[contact]);
      }
    };
   
  /**
   * Popola la lista AddressBookList con la lista ricevuta come parametro
   * 
   * @param list  lista di contatti che deve essere stampata
   * @author Diego Beraldin
   */
    this.displayContactList = function(list) {
      //estraggo l'<ul> del Addressbook e lo inizializzo
      var ulList = this.element.getElementById("AddressBookList");
      ulList.innerHTML = "";
	
      for (var contact in list) {
        //ciclo i contatti e agiungo un <li> per ogni contatto.
        addListItem(ulist, list[contact]);
      }
    };
    
   /**
    * Aggiunge un list item ad una lista
    *
    * @author Riccardo Tresoldi
    * @param list lista cui il contatto deve essere aggiunto
    * @param contact  contatto da aggiungere
    */
    this.addListItem = function(list, contact) {
      //creo l'elemento <li>
      var item = document.createElement("li");
    
      /* creo le variabili contenenti i dati del contatto da attribuire
       * al li, dove le proprietà name, surname e presenter sono le
       * tipiche degli oggetti JSUserData
       */
      var name="";
      if (contact.name != null)
        name += contact.name;
      if (contact.surname != null) {
        if (name != "")
          name += " ";
        name += contact.surname;
      }
      if (name == "")
        name += contact.email;
      var status = contact.status;
      var avatar = contact.image;
    
      //imposto gli attributi corretti
      item.setAttribute("id", contact.ID);
      item.setAttribute("class", contact.status);
      item.setAttribute("onclick", mediator.onContactSelected(contact));
    
      //genero i valori da attribuire all'<li>
      var avatarNode = document.createElement('img');
      avatarNode.setAttribute("src", avatar);
      avatarNode.setAttribute("id", "");
      avatarNode.setAttribute("class", "");
      
      var textNode = document.createTextNode(name);
      
      var statusNode = document.createElement('img');
      statusNode.setAttribute("src", "");
      statusNode.setAttribute("id", "");
      statusNode.setAttribute("class", "");
    
      //imposto il valore dell'<li>
      item.appendChild(avatarNode);
      item.appendChild(textNode);
      item.appendChild(statusNode);
    
      //aggiungo il <li> al elemento <ul> dell'oggetto ulList su cui viene invocata la funzione
      list.appendChild(item);
    };
}
