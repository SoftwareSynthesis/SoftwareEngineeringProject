/**
 * Presenter incaricato di gestire il pannello della rubrica, contiene le funzioni
 * associate ai widget grafici della vista relativi alla rubrica e ha la
 * responsabilità di aggiornare la vista sulla base dei dati ricevuti dal server
 *
 * @author Marco Schivo
 * @author Riccardo Tresoldi
 * @author Diego Beraldin
 */
function AddressBookPanelPresenter() {
    //elemento controllato da questo presenter
    this.element = document.getElementById("AddressBookPanel");
    //da configurare con url della servlet
    this.urlServlet = "http://localhost:8080/AddressBookManager";
    //array dei contatti della rubrica dell'utente
    this.contacts = new Array();

    /**
     * Inizializza 'AddressBookPannel' e lo popola con i contatti della rubrica
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

        //visualizza i contatti nel pannello
        setup();
    };

    /**
     * Recupera i contatti della propria rubrica dal server tramite AJAX
     *
     * @author Marco Schivo
     */
    this.getAddressBookContacts = function() {
        var request = new XMLHttpRequest();
        //il server ritorna Nome, Cognome, Status, Email, Id, Image ad esempio:
        /*
         [
         {"nome": "Marco", "cognome": "Schivo", "email": "marcoskivo@gmail.com", "id": "1", "image": "sfdsfsd.jpg", "status": "Avaible"},
         {"nome": "Andrea", "cognome": "Rizzi", "email": "asondjs@gmail.com", "id": "2", "image": "sad.jpg", "status": "Not Avaible"}
         ]
         */
        request.onreadystatechange = function() {
            //var that = this;
            if (this.readyState == 4 && this.status == 200) {
                //ricevo un JSON contenente tutti i contatti della mia rubrica e li inserisco nell'array dichiarato globalmente "AddresssBookList"
                contacts = JSON.parse(request.responseText);
                // probabile problema: risolvere con that.contacts
            }
        };

        request.open("POST", urlServlet, "true");
        request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        //se c'è bisogno di passare piu parametri, agganciarli con &
        request.send("id=" + communicationcenter.my.id);
    }
    /**
     * Inserisce i contatti estratti dal server all'interno della lista
     * 'AddressBookList' all'interno di 'AddressBookPanel'
     *
     * @author Riccardo Tresoldi
     */
    this.setup = function() {
        //estraggo l'<ul> del Addressbook e lo inizializzo
        var ulList = this.element.getElementById("AddressBookList");
        ulList.innerHTML = "";

        //<ul id="AddressBookList"></ul>
        for (var contact in this.contacts) {
            //ciclo i contatti e agiungo un <li> per ogni contatto
            //QUESTA È LA SINTASSI PER ARRAY ASSOCIATIVI MA ORA È UN ARRAY NORMALE: addListItem(ulList, this.contacts[contact]);
            this.addListItem(ulList, this.contacts);
        }
    };

    /**
     * Popola la 'AddressBookList' con la lista ricevuta come parametro
     *
     * @param list  lista di contatti che deve essere stampata
     * @author Diego Beraldin
     */
    this.displayContactList = function(list) {
        //estraggo l'<ul> del Addressbook e lo inizializzo
        var ulList = this.element.getElementById("AddressBookList");
        ulList.innerHTML = "";

        for (var contact in list) {
            //ciclo i contatti e agiungo un <li> per ogni contatto
            //QUESTA È LA SINTASSI PER ARRAY ASSOCIATIVI MA ORA È UN ARRAY NORMALE: addListItem(ulist, list[contact]);
            this.addListItem(ulList, contact)
        }
    };

    /**
     * Aggiunge una voce di rubrica a una lista
     *
     * @author Riccardo Tresoldi
     * @param list lista cui il contatto deve essere aggiunto
     * @param contact  contatto da aggiungere
     */
    //TODO deve diventare un metodo privato
    this.addListItem = function(list, contact) {
        //creo l'elemento <li>
        var item = document.createElement("li");

        /* creo le variabili contenenti i dati del contatto da attribuire
         * al li, dove le proprietà name, surname e presenter sono le
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
            name += contact.email;
        var status = contact.status;
        var avatar = contact.image;

        //imposto gli attributi corretti
        item.setAttribute("id", contact.id);
        item.setAttribute("class", contact.status);
        //la variabile 'mediator' è una variabile globale
        //TODO controllare questa chiusura!!
        item.onclick = function(c) {
        	return function() {
        		mediator.onContactSelected(c);
        	};
        } (contact);
        }

        //genero i valori da attribuire all'<li>
        var avatarNode = document.createElement('img');
        avatarNode.setAttribute("src", avatar);
        //avatarNode.setAttribute("id", "");
        //avatarNode.setAttribute("class", "");

        var textNode = document.createTextNode(name);

        var statusNode = document.createElement('img');
        statusNode.setAttribute("src", "");
        //statusNode.setAttribute("id", "");
        //statusNode.setAttribute("class", "");

        //imposto il valore dell'<li>
        item.appendChild(avatarNode);
        item.appendChild(textNode);
        item.appendChild(statusNode);

        //aggiungo il <li> al elemento <ul> dell'oggetto ulList su cui viene invocata la funzione
        list.appendChild(item);
    };

    /* TODO:
     * - gestire le ricerche nella rubrica
     * - aggiungere un gruppo alla rubrica
     * - rimuovere un gruppo dalla rubrica
     */
}
