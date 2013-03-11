/* Presenter incaricato di gestire il pannello della rubrica, contiene le funzioni
 * associate ai widget grafici della vista relativi alla rubrica e ha la
 * responsabilità di aggiornare la vista sulla base dei dati ricevuti dal server
 *
 * @constructor
 * @this {AddressBookPanelPresenter}
 * @author Marco Schivo
 * @author Riccardo Tresoldi
 * @author Diego Beraldin
 */
function AddressBookPanelPresenter() {
/**********************************************************
                     VARIABILI PRIVATE
***********************************************************/
    // elemento controllato da questo presenter
    var element = document.getElementById("AddressBookPanel");
    // da configurare con url della servlet
    var urlServlet = "http://localhost:8080/AddressBookManager";
    // array dei contatti della rubrica dell'utente
	//FIXME avrebbe più senso salvare i contatti i communicationcenter.my
    var contacts = new Array();

/**********************************************************
                        METODI PRIVATI
***********************************************************/
    /**
     * Recupera i contatti della propria rubrica dal server tramite AJAX
     *
     * @author Marco Schivo
     */
    function getAddressBookContacts() {
        var request = new XMLHttpRequest();
        request.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {
                contacts = JSON.parse(request.responseText);
            }
        };

        request.open("POST", urlServlet, "true");
        request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        request.send("id=" + communicationcenter.my.id);
    }
    
    /**
     * Aggiunge una voce di rubrica a una lista
     *
     * @author Riccardo Tresoldi
     * @param {HTMLUListElement} list lista cui il contatto deve essere aggiunto
     * @param {Object} contact  contatto da aggiungere
     */
    function addListItem(list, contact) {
        //creo l'elemento <li>
        var item = document.createElement("li");

        /* creo le variabili contenenti i dati del contatto da attribuire
         * al 'li', dove le proprietà name, surname e presenter sono le
         * tipiche degli oggetti JSUserData
         */
        var name = "";
        if (contact.name != null)
            name += contact.name;
        if (contact.surname != null) {
            if (name != "")
                name += " ";
            name += contact.surname;
        }
        if (name == "")
            name += contact.mail;
        var state = contact.state;
        var avatar = contact.picturePath;

        //imposto gli attributi corretti
        item.setAttribute("id", contact.id);
        item.setAttribute("class", contact.status);
        //la variabile 'mediator' è una variabile globale
        item.onclick = function() {
            mediator.onContactSelected(contact);
        };

        //genero i valori da attribuire all'<li>
        var avatarNode = document.createElement('img');
        avatarNode.setAttribute("src", avatar);
        //avatarNode.setAttribute("class", "");

        var textNode = document.createTextNode(name);

        var stateNode = document.createElement('img');
        var statePictureUrl;
        switch (state) {
            case "available":
                statePictureUrl = "img/stateavailable.png";
                break;
            case "occupied":
                statePictureUrl = "img/stateoccupied.png";
                break;
            default:
                statePictureUrl = "img/stateoffline.png";
                break;
        }
        stateNode.setAttribute("src", statePictureUrl);
        //stateNdoe.setAttribute("class", "");

        //imposto il valore dell'<li>
        item.appendChild(avatarNode);
        item.appendChild(textNode);
        item.appendChild(statusNode);

        //aggiungo il <li> al elemento <ul> dell'oggetto ulList su cui viene invocata la funzione
        list.appendChild(item);
    }
    
/**********************************************************
                        METODI PUBBLICI
***********************************************************/
    /**
     * Inizializza 'AddressBookPanel' e lo popola con i contatti della rubrica
     *
     * @author Riccardo Tresoldi
     */
    this.initialize = function() {
        // mi imposto come visibile
        element.style.display = "block";
        //creo i tre div principali
        var divSearch = document.createElement('div');
        var divSort = document.createElement('div');
        var divList = document.createElement('div');

        // creo contenuto divSearch
        var inputText = document.createElement('input');
        inputText.setAttribute("type", "text");
        var inputButton = document.createElement('input');
        inputText.setAttribute("type", "image");

        // creo contenuto divSort
        var select = document.createElement('select');
        select.setAttribute("id", "selectSort");

        // creo contenuto divList
        var ul = document.createElement('ul');
        select.setAttribute("id", "AddressBookList");

        // appendo i sottonodi ai nodi principali
        divSearch.appendChild(inputText);
        divSearch.appendChild(inputButton);
        divSort.appendChild(select);
        divList.appendChild(ul);

        //appendo il sottoalbero al DOM
        this.element.innerHTML = "";
        this.element.appendChild(divSearch);
        this.element.appendChild(divSort);
        this.element.appendChild(divList);

    	// scarica i contatti dal server
    	getAddressBookContacts();
        // visualizza i contatti nel pannello
        this.setup();
    };

    /**
     * Inserisce i contatti estratti dal server all'interno della lista
     * 'AddressBookList' all'interno di 'AddressBookPanel'
     *
     * @author Riccardo Tresoldi
     */
    this.setup = function() {	
        // estraggo l'<ul> del Addressbook e lo inizializzo
        var ulList = this.element.getElementById("AddressBookList");
        ulList.innerHTML = "";

        for (var contact in contacts) {
            //ciclo i contatti e agiungo un <li> per ogni contatto
            addListItem(ulList, contact);
        }
    };

    /**
     * Popola la 'AddressBookList' con la lista ricevuta come parametro
     *
     * @param {Array} list  lista di contatti che deve essere stampata
     * @author Diego Beraldin
     */
    this.displayContactList = function(list) {
        //estraggo l'<ul> del Addressbook e lo inizializzo
        var ulList = this.element.getElementById("AddressBookList");
        ulList.innerHTML = "";

        for (var contact in list) {
            //ciclo i contatti e agiungo un <li> per ogni contatto
            addListItem(ulList, contact);
        }
    };

    /**
     * Rende invisibile il pannello della rubrica all'occorrenza
     *
     * @author Diego Beraldin
     */
    this.hide = function() {
        this.element.style.display = "none";
    };
    
    
    /**
     * Aggiunge un contatto alla rubrica se non già presente
     * 
     * @author Riccardo Tresoldi
     * @param {Object} contact rappresenta l'id del contatto da aggiungere
     */
    this.addContact = function(contact){
        //controllo che non sia già presente nella rubrica [controllo da fare anche lato server]
        
        //invio la richiesta al server e attendo il risultato
        
        //visualizzio l'esito della richiesta
        
        //se esito positivo refresh della rubrica
    };

    /* TODO:
     * - aggiunta di un contratto alla rubrica
     * - eliminazione di un contatto
     * - aggiungere un utente a un gruppo
     * - rimuovere un utente da un gruppo
     * - gestire le ricerche nella rubrica
     * - aggiungere un gruppo alla rubrica
     * - rimuovere un gruppo dalla rubrica
     */
}
