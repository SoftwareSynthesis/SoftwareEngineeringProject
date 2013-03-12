/**
 * Presenter incaricato di gestire il pannello della rubrica, contiene le funzioni
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
    var contacts = new Array();
    // array dei gruppi della rubrica
    var groups = new Array();

    /**********************************************************
     METODI PRIVATI
     ***********************************************************/
    /**
     * Recupera i contatti e i gruppi della propria rubrica dal server tramite AJAX
     *
     * @author Marco Schivo
     * @author Riccardo Tresoldi
     */
    function getAddressBookContacts() {
        //apro due XMLHttprequest rispettivamente per contatti e gruppi
        var contactRequest = new XMLHttpRequest();
        var groupRequest = new XMLHttpRequest();

        //gestisco l'evento di restituzione dei dati
        contactRequest.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {
                contacts = JSON.parse(request.responseText);
            }
        };
        groupRequest.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {
                groups = JSON.parse(request.responseText);
            }
        };

        //effettuo la richiesta vera e propria
        contactRequest.open("POST", urlServlet, "true");
        contactRequest.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        contactRequest.send("operation=" + 0 + "&myId=" + communicationcenter.my.id);
        groupRequest.open("POST", urlServlet, "true");
        groupRequest.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        groupRequest.send("operation=" + 7 + "&myId=" + communicationcenter.my.id);
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
        stateNode.setAttribute("src", statePictureUrl);
        //stateNdoe.setAttribute("class", "");

        //imposto il valore dell'<li>
        item.appendChild(avatarNode);
        item.appendChild(textNode);
        item.appendChild(statusNode);

        //aggiungo il <li> al elemento <ul> dell'oggetto ulList su cui viene invocata la funzione
        list.appendChild(item);
    }

    /**
     * Controlla se un contatto è presente un un gruppo
     *
     * @author Riccardo Tresoldi
     * @param {Number} idContact id del contatto da verificare se presente nel gruppo
     * @param {Number} idGroup id del gruppo su cui verificare
     * @return {Boolean} ritorna true solo se il contatto è presente nel gruppo
     */
    function contactExistInGroup(idContact, idGroup) {
        //FIXME controllare che sia corretta
        var exist = false;
        for (var contactsGroup in contacts[idContact]) {
            if (contactsGroup == idGroup)
                exist = true;
        }
        return exist;
    }

    /**
     * Elimina il contenuto preesistente e visualizza il nuovo contenuto filtrato della <ul> dei contatti
     *
     * @author Ricardo tresoldi
     * @param {Array} filtredContacts Array di contrati
     */
    function showFilter(filtredContacts) {
        // estraggo l'<ul> del Addressbook e lo inizializzo
        var ulList = this.element.getElementById("AddressBookList");
        ulList.innerHTML = "";

        //TODO aggiungere una label per avvisare che i campi visualizzati sono filtrati

        for (var contact in filtredContacts) {
            //ciclo i contatti e agiungo un <li> per ogni contatto
            addListItem(ulList, filtredContacts[contact]);
        }
    };

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
            addListItem(ulList, contacts[contact]);
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
     * @param {Number} contact rappresenta l'id del contatto da aggiungere
     * @returns {Boolean} true solo se l'aggiunta ha avuto successo
     */
    this.addContact = function(contact) {
        //controllo che non sia già presente nella rubrica
        for (var AddressBookContact in contacts) {
            //ciclo i contatti per controllo
            if (AddressBookContact.id == contact)
                return false;
        }

        //invio la richiesta al server e attendo il risultato
        var request = new XMLHttpRequest();
        request.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {
                result = JSON.parse(request.responseText);
            }
        };
        request.open("POST", urlServlet, "true");
        request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        request.send("operation=" + 1 + "&myId=" + communicationcenter.my.id + "&contactId=" + contact);

        //visualizzio l'esito della richiesta. Se esito positivo refresh della rubrica
        if (result == true) {
            getAddressBookContacts();
            this.setup();
            return true;
        } else {
            return false;
        }
    };

    /**
     * Rimovere un contatto alla rubrica se presente
     *
     * @author Riccardo Tresoldi
     * @param {Number} contact rappresenta l'id del contatto da eliminare
     * @returns {Boolean} true solo se l'eliminazione ha avuto successo
     */
    this.removeContact = function(contact) {
        //controllo se presente nella rubrica
        var existContact = false;
        for (var AddressBookContact in contacts) {
            //ciclo i contatti per controllo
            if (AddressBookContact.id == contact)
                existContact = true;
        }
        if (!existContact)
            return false;

        //invio la richiesta al server e attendo il risultato
        var request = new XMLHttpRequest();
        request.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {
                result = JSON.parse(request.responseText);
            }
        };
        request.open("POST", urlServlet, "true");
        request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        request.send("operation=" + 2 + "&myId=" + communicationcenter.my.id + "&contactId=" + contact);

        //visualizzio l'esito della richiesta. Se esito positivo refresh della rubrica
        if (result == true) {
            getAddressBookContacts();
            this.setup();
            return true;
        } else {
            return false;
        }
    };

    /**
     * Aggiungere un contatto della propria rubrica ad un proprio gruppo
     *
     * @author Riccardo Tresoldi
     * @param {Number} contact rappresenta l'id del contatto da aggiungere al gruppo
     * @param {Number} group rappresenta l'id del gruppo in cui aggiungere il contatto
     * @returns {Boolean} true solo se l'aggiunta ha avuto successo
     */
    this.addContactInGroup = function(contact, group) {
        //controllo che il contatto non sia già presente nel gruppo
        if (contactExistInGroup(contact, group))
            return false;

        //invio la richiesta al server e attendo il risultato
        var request = new XMLHttpRequest();
        request.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {
                //deve tornare true o false
                result = JSON.parse(request.responseText);
            }
        };
        request.open("POST", urlServlet, "true");
        request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        request.send("operation=" + 3 + "&myId=" + communicationcenter.my.id + "&contactId=" + contact + "&groupId=" + group);

        //visualizzio l'esito della richiesta. Se esito positivo refresh della rubrica
        if (result == true) {
            getAddressBookContacts();
            this.setup();
            return true;
        } else {
            return false;
        }
    };

    /**
     * Elimina un contatto della propria rubrica ad un proprio gruppo
     *
     * @author Riccardo Tresoldi
     * @param {Number} contact rappresenta l'id del contatto da eliminare al gruppo
     * @param {Number} group rappresenta l'id del gruppo in cui eliminare il contatto
     * @returns {Boolean} true solo se l'eliminazione ha avuto successo
     */
    this.deleteContactFromGroup = function(contact, group) {
        //controllo che il contatto non sia già presente nel gruppo
        if (!contactExistInGroup(contact, group))
            return false;

        //invio la richiesta al server e attendo il risultato
        var request = new XMLHttpRequest();
        request.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {
                result = JSON.parse(request.responseText);
            }
        };
        request.open("POST", urlServlet, "true");
        request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        request.send("operation=" + 4 + "&myId=" + communicationcenter.my.id + "&contactId=" + contact + "&groupId=" + group);

        //visualizzio l'esito della richiesta. Se esito positivo refresh della rubrica
        if (result == true) {
            getAddressBookContacts();
            this.setup();
            return true;
        } else {
            return false;
        }
    };

    /**
     * Aggiunge un gruppo alla rubrica
     *
     * @author Riccardo Tresoldi
     * @param {String} name rappresenta il nome del gruppo da aggiungere
     * @returns {Boolean} true solo se l'aggiunta ha avuto successo
     */
    this.addGroup = function(name) {
        //TODO può avere senso fare una funzione per controllare che il nome non esista già [andrebbe qua]

        //invio la richiesta al server e attendo il risultato
        var request = new XMLHttpRequest();
        request.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {
                result = JSON.parse(request.responseText);
            }
        };
        request.open("POST", urlServlet, "true");
        request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        request.send("operation=" + 5 + "&myId=" + communicationcenter.my.id + "&groupName=" + name);

        //visualizzio l'esito della richiesta. Se esito positivo refresh della rubrica
        if (result == true) {
            getAddressBookContacts();
            this.setup();
            return true;
        } else {
            return false;
        }
    };

    /**
     * Rimuovere un gruppo alla rubrica, gli utenti appartenenti a quel gruppo non verranno eliminati
     *
     * @author Riccardo Tresoldi
     * @param {Number} idGroup rappresenta l'id del gruppo da eliminare
     * @returns {Boolean} true solo se la rimozione ha avuto successo
     */
    this.deleteGroup = function(idGroup) {
        //controllo che il gruppo esista
        var existGroup = false;
        for (var group in groups) {
            if (group.id == idGroup)
                existGroup = true;
        }
        if (!existGroup)
            return false;

        //invio la richiesta al server e attendo il risultato
        var request = new XMLHttpRequest();
        request.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {
                result = JSON.parse(request.responseText);
            }
        };
        request.open("POST", urlServlet, "true");
        request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        request.send("operation=" + 6 + "&myId=" + communicationcenter.my.id + "&groupId=" + idGroup);

        //visualizzio l'esito della richiesta. Se esito positivo refresh della rubrica
        if (result == true) {
            getAddressBookContacts();
            this.setup();
            return true;
        } else {
            return false;
        }
    };

    /**
     * Dato una stringa in ingresso questa funzione mostra gli utenti filtrati per quel parametro
     *
     * @author Riccardo Tresoldi
     * @param {String} param string da cercare tra i contatti
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
        showFilter(filtred);
    };

    /**
     * Dato l'id di un gruppo in ingresso questa funzione mostra gli utenti filtrati
     *
     * @author Riccardo Tresoldi
     * @param {Number} idGroup id del gruppo su cui filtrare i contatti
     */
    this.applyFilterByGroup = function(idGroup) {
        //mi creo un array di contatti filtrati
        var filtred = new Array();

        //ciclo i contatti e cerco quelli appartenenti al gruppo popolando con essi l'array
        for (var contact in contacts) {
            for (var group in contacts[contact].groups) {
                if (group == idGroup)
                    filtred.push(contact);
            }
        }

        //chiamo la funzione che dato un array di contatti mi ripopola l'<ul>
        showFilter(filtred);
    };

    /* TODO:
     * - bloccare un utente
     */

    /*FILE JSON CHE RAFFIGURA LA RUBRICA
     * {
     *      "idUser1":{"campo1": "valore", "campo2": "valore", "campo3": "valore", "campo4": "valore"},
     *      "idUser2":{"campo1": "valore", "campo2": "valore", "campo3": "valore", "campo4": "valore"},
     * }
     */
}
