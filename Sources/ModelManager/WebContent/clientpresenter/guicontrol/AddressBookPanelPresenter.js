/**
 * Presenter incaricato di gestire il pannello della rubrica, contiene le
 * funzioni
 * associate ai widget grafici della vista relativi alla rubrica e ha la
 * responsabilità di aggiornare la vista sulla base dei dati ricevuti dal server
 *
 * @constructor
 * @this {AddressBookPanelPresenter}
 * @param {String} url URL della servlet con cui il presenter deve comunicare
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

    /* OPERATION ON SERVER:
    * -  0 = ottieni i contatti della rubrica
    * -  1 = aggiungi contatto ad una rubrica
    * -  2 = elimina contatto da una rubrica
    * -  3 = aggiunge un contatto ad un gruppo
    * -  4 = eliminare un contatto da un gruppo
    * -  5 = aggiunta di un gruppo
    * -  6 = eliminazione di un gruppo
    * -  7 = ottieni i gruppi della rubrica
    * -  8 = bloccare un utente
    * -  9 = sbloccare un utente
    * - 10 = restituire una ricerca
    */

    // array dei contatti della rubrica dell'utente
    var contacts = new Array();
    // array dei gruppi della rubrica
    var groups = new Array();
    // URL delle servlet
    var servlets = new Array();
    // inizializza l'array delle servlet
    getServletURLs();

    /**********************************************************
     METODI PRIVATI
     ***********************************************************/
	/**
	 * Configura gli URL delle servlet da interrogare leggendoli dal file di configurazione
	 * @author Diego Beraldin
	 */
	function getServletURLs() {
		var configurationRequest = new XMLHttpRequest();
		configurationRequest.open("POST", configurationFile, false);
		configurationRequest.send();
		var XMLDocument = configurationRequest.responseXML;
		var baseURL = XMLDocument.getElementsByTagName("baseURL")[0].childNodes[0].data;
		
		var names = Array();
		names.push(XMLDocument.getElementById("getcontacts").childNodes[0].data);;
		names.push(XMLDocument.getElementById("addcontact").childNodes[0].data);
		names.push(XMLDocument.getElementById("deletecontact").childNodes[0].data);
		names.push(XMLDocument.getElementById("insert").childNodes[0].data);
		names.push(XMLDocument.getElementById("remove").childNodes[0].data);
		names.push(XMLDocument.getElementById("creategroup").childNodes[0].data);
		names.push(XMLDocument.getElementById("deletegroup").childNodes[0].data);
		names.push(XMLDocument.getElementById("getgroups").childNodes[0].data);
		names.push(XMLDocument.getElementById("block").childNodes[0].data);
		names.push(XMLDocument.getElementById("unblock").childNodes[0].data);
		
		for (var i in names) {
			servlets.push(baseURL + names[i]);
		}
	}
	
    /**
     * Recupera i contatti e i gruppi della propria rubrica dal server tramite
     * AJAX
     *
     * @author Marco Schivo
     * @author Riccardo Tresoldi
     */
    function getAddressBookContacts() {
        //apro due XMLHttprequest rispettivamente per contatti e gruppi
        var contactRequest = new XMLHttpRequest();
        var groupRequest = new XMLHttpRequest();
        contactRequest.open("POST", servlets[0], false);
        contactRequest.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        contactRequest.send();
        contacts = JSON.parse(contactRequest.responseText);
        groupRequest.open("POST", servlets[7], false);
        groupRequest.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        groupRequest.send();
        groups = JSON.parse(groupRequest.responseText);
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
            name += contact.email;
        var state = contact.state;
        var avatar = contact.picturePath;

        //imposto gli attributi corretti
        item.setAttribute("id", contact.id);
        item.setAttribute("class", contact.state);
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
        stateNode.src = statePictureUrl;
        //stateNdoe.class="";

        //imposto il valore dell'<li>
        item.appendChild(avatarNode);
        item.appendChild(textNode);
        item.appendChild(stateNode);

        //aggiungo il <li> al elemento <ul> dell'oggetto ulList su cui viene
        // invocata la funzione
        list.appendChild(item);
    }

    /**
     * Aggiungere una <option> ad una Select
     *
     * @author Riccardo Tresoldi
     * @param {HTMLSelectElement} select rappresenta la select a cui aggiungere
     * la <option>
     * @param {String} value rappresenta il valore da attribuire alla nuova
     * <option>
     * @param {String} text rappresenta il testo da attribuire alla nuova
     * <option>
     */
    function addOptionToSelect(select, value, text) {
        //creo la option
        var option = document.createElement("option");
        option.value = value;
        option.appendChild(document.createTextNode(text));

        //appendo la nuova option alla select
        select.appendChild(option);
    }

    /**
     * Controlla se un contatto è presente nel gruppo passato come parametro
     *
     * @author Riccardo Tresoldi
     * @param {Object} contact contatto da verificare se presente nel
     * gruppo
     * @param {Object} group gruppo su cui verificare
     * @return {Boolean} ritorna true solo se il contatto è presente nel gruppo
     */
    function contactExistInGroup(contact, group) {
        //ciclo l'array di contatti del gruppo
        for (var c in group.contacts) {
            if (c == contact.id)
                return true;
        }
        return false;
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
        // creo i tre div principali
        var divFilter = document.createElement('div');
        var divSort = document.createElement('div');
        var divList = document.createElement('div');
		var divGroup = document.createElement('div');
        divFilter.id = "divFilter";
        divSort.id = "divSort";
        divList.id = "divList";
		divGroup.id = "divGroup";
		
        // creo contenuto divFilter
        var inputText = document.createElement('input');
        inputText.type = "text";
        var inputButton = document.createElement('input');
        inputButton.type = "image";
        var selectGroup = document.createElement('select');
        selectGroup.id = "selectGroup";
        //attribuisco gli eventi per la ricerca
        var self = this;
        inputButton.onclick = function() {
            var serchField = inputText.value;
            var filtredContacts = self.applyFilterByString(serchField);
            self.showFilter(filtredContacts);
        };
        selectGroup.onchange = function() {
            var idGroupSelected = selectGroup.options[selectGroup.selectedIndex].value;
            var filtredContacts = self.applyFilterByGroup(idGroupSelected);
            self.showFilter(filtredContacts);
        };
        // creo contenuto divSort
        var selectSort = document.createElement('select');
        selectSort.id = "selectSort";

        // creo contenuto divList
        var ul = document.createElement('ul');
        ul.id = "AddressBookList";

        // appendo i sottonodi ai nodi principali
        divFilter.appendChild(inputText);
        divFilter.appendChild(inputButton);
        divSort.appendChild(selectSort);
        divList.appendChild(ul);
		divGroup.appendChild(selectGroup);
	

        //appendo il sottoalbero al DOM
        element.innerHTML = "";
        element.appendChild(divFilter);
        element.appendChild(divSort);
        element.appendChild(divList);
		element.appendChild(divGroup);

        // visualizza i contatti nel pannello
        this.setup();
    };

    /**
     * Scarica la rubrica dal server popolando la lista dei contatti, quindi
     * inserisce i contatti estratti dal server all'interno della lista
     * all'interno di 'AddressBookPanel'
     *
     * NOTA PER I VERIFICATORI
     * Richiede che il 'document' abbia al suo interno un elemento di
     * tipo '<ul>' con 'id' uguale a 'AddressBookList'
     * Al termine della funzione la lista dei contatti contacts deve essere
     * popolata con i contatti che passa lo stub
     *
     * @author Riccardo Tresoldi
     */
    this.setup = function() {
        // scarica i contatti dal server
        getAddressBookContacts();

        //popolo la parte dei contatti
        // estraggo l'<ul> del Addressbook e lo inizializzo
        var ulList = document.getElementById("AddressBookList");
        ulList.innerHTML = "";
        // ciclo i contatti e agiungo un <li> per ogni contatto
        for (var contact in contacts) {
            addListItem(ulList, contacts[contact]);
        }

        //popolo la parte dei gruppi
        //estraggo la <select> dal DOM e la inizializzo
        var selectGroup = document.getElementById("selectGroup");
        selectGroup.innerHTML = "";
        //ciclo i gruppi e li aggiungo alla <select>
        for (var group in groups) {
            addOptionToSelect(selectGroup, group, groups[group].name);
        }
    };

    /**
     * Rende invisibile il pannello della rubrica all'occorrenza
     *
     * @author Diego Beraldin
     */
    this.hide = function() {
        element.style.display = "none";
    };

    /**
     * Aggiunge un contatto alla rubrica se non già presente
     *
     * @author Riccardo Tresoldi
     * @param {Object} contact rappresenta il contatto da aggiungere
     * @returns {Boolean} true solo se l'aggiunta ha avuto successo
     */
    this.addContact = function(contact) {
        //controllo che non sia già presente nella rubrica
        if (this.contactAlreadyPresent(contact)) {
            throw "Contatto già presente nella rubrica.";
        }

        //invio la richiesta al server e attendo il risultato
        var request = new XMLHttpRequest();
        request.open("POST", servlets[1] + suffix, false);
        request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        request.send("contactId=" + contact.id);

        //visualizzo l'esito della richiesta. Se esito positivo refresh della
        // rubrica
        result = JSON.parse(request.responseText);
        if (result == true) {
            this.setup();
            return true;
        }
        throw "Ops... qualcosa è andato storto nel server.";
    };

    /**
     * Rimovere un contatto alla rubrica se presente
     *
     * @author Riccardo Tresoldi
     * @param {Object} contact rappresenta il contatto da eliminare
     * @returns {Boolean} true solo se l'eliminazione ha avuto successo
     */
    this.removeContact = function(contact) {
        //controllo se presente nella rubrica
        if (!this.contactAlreadyPresent(contact))
            throw "Non puoi eliminare un contatto non presente in rubrica.";

        //invio la richiesta al server e attendo il risultato
        var request = new XMLHttpRequest();
        request.open("POST", servlets[2], false);
        request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        request.send("contactId=" + contact.id);

        //visualizzo l'esito della richiesta. Se esito positivo refresh della
        // rubrica
        result = JSON.parse(request.responseText);
        if (result == true) {
            this.setup();
            return true;
        }
        throw "Ops... qualcosa è andato storto nel server.";
    };

    /**
     * Aggiungere un contatto della propria rubrica ad un proprio gruppo
     *
     * @author Riccardo Tresoldi
     * @param {Object} contact rappresenta il contatto da aggiungere al
     * gruppo
     * @param {Object} group rappresenta il gruppo in cui aggiungere il
     * contatto
     * @returns {Boolean} true solo se l'aggiunta ha avuto successo
     */
    this.addContactInGroup = function(contact, group) {
        //controllo che il contatto non sia già presente nel gruppo
        if (contactExistInGroup(contact, group))
            throw "Il contatto è già presente nel gruppo.";

        //invio la richiesta al server e attendo il risultato
        var request = new XMLHttpRequest();
        request.open("POST", servlets[3] + suffix, false);
        request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        request.send("contactId=" + contact.id + "&groupId=" + groupid);

        //visualizzo l'esito della richiesta. Se esito positivo refresh della
        // rubrica
        result = JSON.parse(request.responseText);
        if (result == true) {
            this.setup();
            return true;
        }
        throw "Ops... qualcosa è andato storto nel server.";
    };

    /**
     * Elimina un contatto della propria rubrica da un proprio gruppo
     *
     * @author Riccardo Tresoldi
     * @param {Object} contact rappresenta il contatto da eliminare dal
     * gruppo
     * @param {Object} group rappresenta il gruppo in cui eliminare il
     * contatto
     * @returns {Boolean} true solo se l'eliminazione ha avuto successo
     */
    this.deleteContactFromGroup = function(contact, group) {
        //controllo che il contatto sia presente nel gruppo
        if (!contactExistInGroup(contact, group))
            throw "Il contatto non è presente nel gruppo.";

        //invio la richiesta al server e attendo il risultato
        var request = new XMLHttpRequest();
        request.open("POST", servlets[4] + suffix, false);
        request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        request.send("contactId=" + contact.id + "&groupId=" + group.id);

        //visualizzo l'esito della richiesta. Se esito positivo refresh della
        // rubrica
        result = JSON.parse(request.responseText);
        if (result == true) {
            this.setup();
            return true;
        }
        throw "Ops... qualcosa è andato storto nel server.";
    };

    /**
     * Aggiunge un gruppo alla rubrica
     *
     * @author Riccardo Tresoldi
     * @param {String} name rappresenta il nome del gruppo da aggiungere
     * @returns {Boolean} true solo se l'aggiunta ha avuto successo
     */
    this.addGroup = function(name) {
        //TODO può avere senso fare una funzione per controllare che il nome non
        // esista già [andrebbe qua]

        //invio la richiesta al server e attendo il risultato
        var request = new XMLHttpRequest();
        request.open("POST", servlets[5] + suffix, false);
        request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        request.send("groupName=" + name);

        //visualizzo l'esito della richiesta. Se esito positivo refresh della
        // rubrica
        result = JSON.parse(request.responseText);
        if (result == true) {
            this.setup();
            return true;
        }
        throw "Ops... qualcosa è andato storto nel server.";
    };

    /**
     * Rimuovere un gruppo alla rubrica, gli utenti appartenenti a quel gruppo
     * non verranno eliminati
     *
     * @author Riccardo Tresoldi
     * @param {Object} group rappresenta l'id del gruppo da eliminare
     * @returns {Boolean} true solo se la rimozione ha avuto successo
     */
    this.deleteGroup = function(group) {
        //controllo che il gruppo esista
        var existGroup = false;
        for (var g in groups) {
            if (g == group.id)
                existGroup = true;
        }
        if (!existGroup)
            throw "Il gruppo che stai cercando di eliminare non esiste.";

        //invio la richiesta al server e attendo il risultato
        var request = new XMLHttpRequest();
        request.open("POST", servlets[6] + suffix, false);
        request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        request.send("groupId=" + group.id);

        //visualizzo l'esito della richiesta. Se esito positivo refresh della
        // rubrica
        result = JSON.parse(request.responseText);
        if (result == true) {
            this.setup();
            return true;
        }
        throw "Ops... qualcosa è andato storto nel server.";
    };

    /**
     * Dato una stringa in ingresso questa funzione filtra i contatti presenti in
     * locale
     *
     * @author Riccardo Tresoldi
     * @param {String} param string da cercare tra i contatti
     * @returns {Array} lista di contatti filtrata
     */
    this.applyFilterByString = function(param) {
        //creo array di utenti filtrati
        var filtred = new Array();

        //specifico aspessione regolare
        var pattern = new RegExp(param);
        for (var contact in contacts) {
            if ((pattern.test(contacts[contact].name)) || (pattern.test(contacts[contact].surname)) || (pattern.test(contacts[contact].email))) {
                filtred.push(contact);
            }
        }

        //visualizzo l'utente filtrato
        return filtred;
    };

    /**
     * Dato l'id di un gruppo in ingresso questa funzione filtra i contatti
     * presenti in locale
     *
     * @author Riccardo Tresoldi
     * @param {Number} idGroup id del gruppo su cui filtrare i contatti
     * @returns {Array} lista di contatti filtrata
     */
    this.applyFilterByGroup = function(idGroup) {
        //mi creo un array di contatti filtrati
        var filtred = new Array();

        //ciclo i contatti e cerco quelli appartenenti al gruppo popolando con
        // essi l'array
        for (var contact in contacts) {
            for (var group in contacts[contact].groups) {
                if (group == idGroup)
                    filtred.push(contact);
            }
        }

        //chiamo la funzione che dato un array di contatti mi ripopola l'<ul>
        return filtred;
    };

    /**
     * Elimina il contenuto preesistente e visualizza il nuovo contenuto filtrato
     * della <ul> dei contatti
     *
     * NOTA PER I VERIFICATORI
     * Richiede che il 'document' abbia al suo interno un elemento di
     * tipo '<ul>' con 'id' uguale a 'AddressBookList'
     *
     * @author Ricardo tresoldi
     * @param {Array} filtredContacts Array di contrati
     */
    this.showFilter = function(filtredContacts) {
        // estraggo l'<ul> del Addressbook e lo inizializzo
        var ulList = this.element.getElementById("AddressBookList");
        ulList.innerHTML = "";

        //TODO aggiungere una label per avvisare che i campi visualizzati sono
        // filtrati

        for (var contact in filtredContacts) {
            //ciclo i contatti e agiungo un <li> per ogni contatto
            addListItem(ulList, filtredContacts[contact]);
        }
    };

    /**
     * Dato un contatto verifica se appartiene alla rubrica
     *
     * @author Riccardo Tresoldi
     * @param {Object} contact contatto da verificare
     * @return {Boolean} true solo se presente in rubrica
     */
    this.contactAlreadyPresent = function(contact) {
        //riscarco la rubrica
        getAddressBookContacts();
        //scorro la rubrica e controllo se contact.id=rubrica.contact.id. ritorno
        // true solo se presente
        for (var AddressBookContact in contacts) {
            if (AddressBookContact == contact.id)
                return true;
        }
        return false;
    };

    /**
     * Funzione che blocca l'utente passato per parametro
     *
     * @author Riccardo Tresoldi
     * @param {Object} contact contatto da bloccare
     * @return {Boolean} true solo se l'utente è stato bloccato correttemente
     */
    this.blockUser = function(contact) {
        //controllo che sia presente nella rubrica
        if (!this.contactAlreadyPresent(contact)) {
            throw "Contatto non presente nella rubrica.";
        }
        if (contact.blocked) {
            throw "Contatto già bloccato.";
        }

        //invio la richiesta di bloccaggio dell'utente
        var request = new XMLHttpRequest();
        request.open("POST", servlets[8] + suffix, false);
        request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        request.send("contactId=" + contact.id);

        //controllo il risultato
        result = JSON.parse(request.responseText);
        if (result == true) {
            this.setup();
            mediator.displayContact(contact);
            return true;
        }
        throw "Ops... qualcosa è andato storto nel server.";
    };

    /**
     * Funzione che sblocca l'utente passato per parametro
     *
     * @author Riccardo Tresoldi
     * @param {Object} contact contatto da sbloccare
     * @return {Boolean} true solo se l'utente è stato sbloccato correttemente
     */
    this.unlockUser = function(contact) {
        //controllo che sia presente nella rubrica
        if (!this.contactAlreadyPresent(contact)) {
            throw "Contatto non presente nella rubrica.";
        }
        if (!contact.blocked) {
            throw "Contatto già sbloccato.";
        }

        //invio la richiesta di bloccaggio dell'utente
        var request = new XMLHttpRequest();
        request.open("POST", servlets[9] + suffix, false);
        request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        request.send("contactId=" + contact.id);

        //controllo il risultato
        result = JSON.parse(request.responseText);
        if (result == true) {
            this.setup();
            mediator.displayContact(contact);
            return true;
        }
        throw "Ops... qualcosa è andato storto nel server.";
    };

    /**
     * Funzione che dato un utente restituisce i gruppi in cui è stato inserito
     * nell'addressBook
     *
     * @author Riccardo Tresoldi
     * @param {Object} contact contatto di cui ricercare i gruppi in cui è
     * inserito
     * @return {Object} array associativo che presenta la lista dei gruppi in cui
     * è presente il contatto
     */
    this.getGroupsWhereContactsIs = function(contact) {
        //creo l'array da tornare
        var groupSelected = new Object();
        //ciclo tutti i gruppi
        for (var group in groups) {
            //ciclo tutti i contatti di un gruppo
            for (var groupContact in groups[group].contacts) {
                //contriollo se il contatto è uguale al contatto del gruppo
                if (groupContact == contact.id) {
                    //se i contatti coincidono aggiungo il gruppo alla lista di
                    // ritorno e blocco lo scorrimento dell'array
                    groupSelected[groups[group].id] = groups[group].name;
                    break;
                }
            }
        }
        //ritorno l'array formato
        return groupSelected;
    };

    /*
     * FILE JSON CHE RAFFIGURA LA RUBRICA
     * {
     *      "idUser1":{ "name": "",
     *                  "surname": "",
     *                  "email": "",
     *                  "id": "",
     *                  "picturePath": "",
     *                  "state": "",
     *                  "blocked": "true|false"
     *                },
     *
     *      "idUser2": {.......},
     * }
     *
     * FILE JSON CHE RAFFIGURA I GRUPPI
     * {
     *      "idGruppo1":{   "name": "",
     *                      "id": "",
     *                      "contacts": [
     *                                      1,
     *                                      2,
     *                                      3
     *                                  ]
     *                 },
     *
     *      "idGruppo2": {........}.
     * }
     */
}
