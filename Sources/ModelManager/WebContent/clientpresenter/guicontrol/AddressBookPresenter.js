/**
 * Presenter incaricato di gestire il pannello della rubrica, contiene le
 * funzioni associate ai widget grafici della vista relativi alla rubrica e ha
 * la responsabilità di aggiornare la vista sulla base dei dati ricevuti dal
 * server
 *
 * @constructor
 * @this {AddressBookPanelPresenter}
 * @author Marco Schivo
 * @author Riccardo Tresoldi
 * @author Diego Beraldin
 */
function AddressBookPanelPresenter() {
    /***************************************************************************
     * VARIABILI PRIVATE
     **************************************************************************/
    var thisPresenter = this;
    var thisPanel;
    // array dei contatti della rubrica dell'utente
    var contacts = new Array();
    // array dei gruppi della rubrica
    var groups = new Array();

    /***************************************************************************
     * METODI PRIVATI
     **************************************************************************/
    /** PRESENTER
     * Recupera i contatti e i gruppi della propria rubrica dal server tramite
     * AJAX
     *
     * @author Marco Schivo
     * @author Riccardo Tresoldi
     */
    function getAddressBookContacts() {
        // apro due XMLHttprequest rispettivamente per contatti e gruppi
        var contactRequest = new XMLHttpRequest();
        var groupRequest = new XMLHttpRequest();
        contactRequest.open("POST", commandURL, false);
        contactRequest.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        contactRequest.send("operation=getContacts");
        contacts = JSON.parse(contactRequest.responseText);
        groupRequest.open("POST", commandURL, false);
        groupRequest.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        groupRequest.send("operation=getGroups");
        groups = JSON.parse(groupRequest.responseText);
    }

    /** PRESENTER
     * Recupera l'indirizzo dell'immagine dello stato in base allo stato del
     * contatto passato come parametro
     *
     * @author Diego Beraldin
     * @param {Object} contact oggetto rappresentante il contatto
     */
    function getImageSrc(contact) {
        var path = "";
        switch (contact.state) {
            case "available":
                path = "img/stateavailable.png";
                break;
            case "occupied":
                path = "img/stateoccupied.png";
                break;
            default:
                path = "img/stateoffline.png";
                break;
        }
        return path;
    }

    /** VIEW
     * Aggiunge una voce di rubrica a una lista
     *
     * @author Riccardo Tresoldi
     * @param {HTMLUListElement}
     *            list lista cui il contatto deve essere aggiunto
     * @param {Object}
     *            contact contatto da aggiungere
     */
    function addListItem(list, contact) {
        // creo l'elemento <li>
        var item = document.createElement("li");

        var name = mediator.createNameLabel(contact);

        // imposto gli attributi corretti
        item.id = contact.id;
        item.className = contact.state;
        item.onclick = function() {
            showContactPanel.contact = contact;
            document.dispatchEvent(showContactPanel);
        };

        // genero i valori da attribuire all'<li>
        var avatarNode = document.createElement('img');
        var avatar = contact.picturePath;
        avatarNode.src = avatar;

        var textNode = document.createTextNode(name);

        var stateNode = document.createElement('img');
        stateNode.src = getImageSrc(contact);

        // imposto il valore dell'<li>
        item.appendChild(avatarNode);
        item.appendChild(textNode);
        item.appendChild(stateNode);

        // aggiungo il <li> al elemento <ul> dell'oggetto ulList su cui viene
        // invocata la funzione
        list.appendChild(item);
    }

    /** VIEW
     * Aggiungere una <option> ad una Select
     *
     * @author Riccardo Tresoldi
     * @param {HTMLSelectElement}
     *            select rappresenta la select a cui aggiungere la <option>
     * @param {String}
     *            value rappresenta il valore da attribuire alla nuova <option>
     * @param {String}
     *            text rappresenta il testo da attribuire alla nuova <option>
     */
    function addOptionToSelect(select, value, text) {
        // creo la option
        var option = document.createElement("option");
        option.value = value;
        if (text == "addrBookEntry") {
            option.selected = true;
            option.appendChild(document.createTextNode("Rubrica"));
        } else {
            option.appendChild(document.createTextNode(text));
        }

        // appendo la nuova option alla select
        select.appendChild(option);
    }

    /** PRESENTER
     * Controlla se un contatto è presente nel gruppo passato come parametro
     *
     * @author Riccardo Tresoldi
     * @param {Object}
     *            contact contatto da verificare se presente nel gruppo
     * @param {Object}
     *            group gruppo su cui verificare
     * @return {Boolean} ritorna true solo se il contatto è presente nel gruppo
     */
    function contactExistInGroup(contact, group) {
        // ciclo l'array di contatti del gruppo
        for (var c in group.contacts) {
            if (c == contact.id)
                return true;
        }
        return false;
    }

    /** PRESENTER/VIEW
     * Funzione per cambiare l'immagine dell'indicatore di stato di un contatto
     * nella rubrica
     *
     * @param {Array} contact rappresenta il contatto a cui settare lo stato
     * @param {String} state rappresenta lo stato da settare
     * @author Riccardo Tresoldi
     */
    function setStateToContact(contact, state) {
        // controllo che il contatto sia presente nella rubrica
        if (contacts[contact.id]) {
            // imposto il valore in contacts
            contacts[contact.id].state = state;
            // imposto l'src dell'immagine giusta
            var liUser = document.getElementById(contact.id);
            var imgState = liUser.children[1];
            imgState.src = getImageSrc(contact);
        }
    }
    
    /**VIEW
     * Data una select e l'id del gruppo rappresentato dalla value della select,
     * ritorna il valore dell'index
     * @author Riccardo Tresoldi
     * @param {HTMLSelectelement} select la select in cui cercare
     * @param {Number} idGroup id del gruppo all'interno dell'object Groups e dunque anche della select con value
     */
    function getIndexOnSelect(select, idGroup){
        var index;
        for(var i=0; i<select.length; i++){
            if(select.options[i].value == idGroup){
                index = i;
                break;
            }
        }
        return index;
    }

    /***************************************************************************
     * METODI PUBBLICI
     **************************************************************************/
    /** VIEW
     * Distruttore del pannello
     * @author Riccardo Tresoldi
     */
    this.destroy = function() {
        if (thisPanel) {
            var thisPanelParent = thisPanel.parentElement.parentElement;
            thisPanelParent.removeChild(thisPanel.parentElement);
            thisPanel = null;
        }
    };

    /** VIEW
     * Inizializza 'AddressBookPanel' e lo popola con i contatti della rubrica
     *
     * @author Riccardo Tresoldi
     * @author Diego Beraldin
     */
    this.initialize = function(view) {
        // posiziona il pannello sulla pagina
        var dummyDiv = document.createElement("div");
        var mainPanel = document.getElementById("MainPanel");
        if (mainPanel) {
            document.body.insertBefore(dummyDiv, mainPanel.parentElement);
        } else {
            document.body.insertBefore(dummyDiv, document.getElementsByTagName("footer")[0]);
        }
        dummyDiv.innerHTML = view.outerHTML;

        //salvo un riferimento all'elemento DOM appena creato
        thisPanel = document.getElementById("AddressBookPanel");

        // configura il comportamento della vista
        var inputButton = document.getElementById("inputButton");
        inputButton.onclick = function() {
            var serchField = inputText.value;
            var filtredContacts = thisPresenter.applyFilterByString(serchField);
            thisPresenter.showFilter(filtredContacts);
        };

        var selectGroup = document.getElementById("selectGroup");
        selectGroup.onchange = function() {
            var idGroupSelected = selectGroup.options[selectGroup.selectedIndex].value;
            var filtredContacts = thisPresenter.applyFilterByGroup(idGroupSelected);
            var isWhitelist = groups[idGroupSelected].name == "addrBookEntry";
            thisPresenter.showFilter(filtredContacts, isWhitelist);
        };

        // visualizza i contatti nel pannello
        this.setup();
    };

    /** PRESENTER/VIEW
     * Scarica la rubrica dal server popolando la lista dei contatti, quindi
     * inserisce i contatti estratti dal server all'interno della lista
     * all'interno di 'AddressBookPanel'
     *
     * NOTA PER I VERIFICATORI Richiede che il 'document' abbia al suo interno
     * un elemento di tipo '
     * <ul>' con 'id' uguale a 'AddressBookList' Al termine della funzione la
     * lista dei contatti contacts deve essere popolata con i contatti che passa
     * lo stub
     *
     * @author Riccardo Tresoldi
     */
    this.setup = function() {
        // scarica i contatti dal server
        getAddressBookContacts();

        // popolo la parte dei contatti
        // estraggo l'<ul> del Addressbook e lo inizializzo
        var ulList = document.getElementById("AddressBookList");
        ulList.innerHTML = "";

        if (Object.size(contacts) == 0) {
            //TODO Aggiungere <li> per indicare che non ci sono contatti in
            // rubrica
        }
        // ciclo i contatti e agiungo un <li> per ogni contatto
        for (var contact in contacts) {
            addListItem(ulList, contacts[contact]);
        }

        // popolo la parte dei gruppi
        // estraggo la <select> dal DOM e la inizializzo
        var selectGroup = document.getElementById("selectGroup");
        selectGroup.innerHTML = "";
        // ciclo i gruppi e li aggiungo alla <select>
        for (var group in groups) {
            addOptionToSelect(selectGroup, group, groups[group].name);
        }
    };

    /** VIEW
     * Rende invisibile il pannello della rubrica all'occorrenza
     *
     * @author Diego Beraldin
     */
    this.hide = function() {
        if (thisPanel) {
            thisPanel.style.display = "none";
        }
    };

    /** PRESENTER
     * Aggiunge un contatto alla rubrica se non già presente
     *
     * @author Riccardo Tresoldi
     * @param {Object}
     *            contact rappresenta il contatto da aggiungere
     * @returns {Boolean} true solo se l'aggiunta ha avuto successo
     */
    this.addContact = function(contact) {
        // controllo che non sia già presente nella rubrica
        if (this.contactAlreadyPresent(contact)) {
            throw "Contatto già presente nella rubrica.";
        }

        // invio la richiesta al server e attendo il risultato
        var request = new XMLHttpRequest();
        request.open("POST", commandURL, false);
        request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        request.send("operation=addContact&contactId=" + contact.id);

        // visualizzo l'esito della richiesta. Se esito positivo refresh della
        // rubrica
        result = JSON.parse(request.responseText);
        if (result == true) {
            this.setup();
            return true;
        }
        throw "Ops... qualcosa è andato storto nel server.";
    };

    /** PRESENTER
     * Rimovere un contatto alla rubrica se presente
     *
     * @author Riccardo Tresoldi
     * @param {Object}
     *            contact rappresenta il contatto da eliminare
     * @returns {Boolean} true solo se l'eliminazione ha avuto successo
     */
    this.removeContact = function(contact) {
        // controllo se presente nella rubrica
        if (!this.contactAlreadyPresent(contact))
            throw "Non puoi eliminare un contatto non presente in rubrica.";

        // invio la richiesta al server e attendo il risultato
        var request = new XMLHttpRequest();
        request.open("POST", commandURL, false);
        request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        request.send("operation=deleteContact&contactId=" + contact.id);

        // visualizzo l'esito della richiesta. Se esito positivo refresh della
        // rubrica
        result = JSON.parse(request.responseText);
        if (result == true) {
            this.setup();
            return true;
        }
        throw "Ops... qualcosa è andato storto nel server.";
    };

    /** PRESENTER
     * Aggiungere un contatto della propria rubrica ad un proprio gruppo
     *
     * @author Riccardo Tresoldi
     * @param {Object}
     *            contact rappresenta il contatto da aggiungere al gruppo
     * @param {Object}
     *            group rappresenta il gruppo in cui aggiungere il contatto
     * @returns {Boolean} true solo se l'aggiunta ha avuto successo
     */
    this.addContactInGroup = function(contact, group) {
        // controllo che il contatto non sia già presente nel gruppo
        if (contactExistInGroup(contact, group))
            throw "Il contatto è già presente nel gruppo.";

        // invio la richiesta al server e attendo il risultato
        var request = new XMLHttpRequest();
        request.open("POST", commandURL, false);
        request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        request.send("operation=insertInGroup&contactId=" + contact.id + "&groupId=" + group.id);

        // visualizzo l'esito della richiesta. Se esito positivo refresh della
        // rubrica
        result = JSON.parse(request.responseText);
        if (result == true) {
            this.setup();
            return true;
        }
        throw "Ops... qualcosa è andato storto nel server.";
    };

    /** PRESENTER
     * Elimina un contatto della propria rubrica da un proprio gruppo
     *
     * @author Riccardo Tresoldi
     * @param {Object}
     *            contact rappresenta il contatto da eliminare dal gruppo
     * @param {Object}
     *            group rappresenta il gruppo in cui eliminare il contatto
     * @returns {Boolean} true solo se l'eliminazione ha avuto successo
     */
    this.removeContactFromGroup = function(contact, group) {
        // controllo che il contatto sia presente nel gruppo
        if (!contactExistInGroup(contact, group))
            throw "Il contatto non è presente nel gruppo.";

        // invio la richiesta al server e attendo il risultato
        var request = new XMLHttpRequest();
        request.open("POST", commandURL, false);
        request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        request.send("operation=deleteFromGroup&contactId=" + contact.id + "&groupId=" + group.id);

        // visualizzo l'esito della richiesta. Se esito positivo refresh della
        // rubrica
        result = JSON.parse(request.responseText);
        if (result == true) {
            this.setup();
            return true;
        }
        throw "Ops... qualcosa è andato storto nel server.";
    };

    /** PRESENTER
     * Aggiunge un gruppo alla rubrica
     *
     * @author Riccardo Tresoldi
     * @param {String}
     *            name rappresenta il nome del gruppo da aggiungere
     * @throws {String}
     *             errore nel caso in cui il gruppo esista già
     * @returns {Boolean} true solo se l'aggiunta ha avuto successo
     */
    this.addGroup = function(name) {
        for (var key in groups) {
            if (groups[key].name == name) {
                throw "Il gruppo che stai cercando di inserire esiste già.";
            }
        }

        // invio la richiesta al server e attendo il risultato
        var request = new XMLHttpRequest();
        request.open("POST", commandURL, false);
        request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        request.send("operation=addGroup&groupName=" + name);

        // visualizzo l'esito della richiesta. Se esito positivo refresh della
        // rubrica
        result = JSON.parse(request.responseText);
        if (result == true) {
            this.setup();
            return true;
        }
        throw "Ops... qualcosa è andato storto nel server.";
    };

    /** PRESENTER
     * Rimuovere un gruppo alla rubrica, gli utenti appartenenti a quel gruppo
     * non verranno eliminati
     *
     * @author Riccardo Tresoldi
     * @param {Object}
     *            group rappresenta l'id del gruppo da eliminare
     * @returns {Boolean} true solo se la rimozione ha avuto successo
     */
    this.deleteGroup = function(group) {
        console.log(groups.length);
        // controllo che il gruppo esista
        var existGroup = false;
        for (var g in groups) {
            if (g == group.id)
                existGroup = true;
        }
        if (!existGroup)
            throw "Il gruppo che stai cercando di eliminare non esiste.";

        // invio la richiesta al server e attendo il risultato
        var request = new XMLHttpRequest();
        request.open("POST", commandURL, false);
        request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        request.send("operation=deleteGroup&groupId=" + group.id);

        // visualizzo l'esito della richiesta. Se esito positivo refresh della
        // rubrica
        result = JSON.parse(request.responseText);
        if (result == true) {
            this.setup();
            return true;
        }
        throw "Ops... qualcosa è andato storto nel server.";
    };

    /** VIEW
     * Dato una stringa in ingresso questa funzione filtra i contatti presenti
     * in locale
     *
     * @author Riccardo Tresoldi
     * @param {String}
     *            param string da cercare tra i contatti
     * @returns {Array} lista di ID dei contatti filtrati
     */
    this.applyFilterByString = function(param) {
        // creo array di utenti filtrati
        var filtred = new Array();

        // specifico aspessione regolare
        var pattern = new RegExp(param);
        for (var contact in contacts) {
            if ((pattern.test(contacts[contact].name)) || (pattern.test(contacts[contact].surname)) || (pattern.test(contacts[contact].email))) {
                filtred.push(contact);
            }
        }

        // visualizzo l'utente filtrato
        return filtred;
    };

    /** VIEW
     * Dato l'id di un gruppo in ingresso questa funzione filtra i contatti
     * presenti in locale
     *
     * @author Riccardo Tresoldi
     * @param {Number}
     *            idGroup id del gruppo su cui filtrare i contatti
     * @returns {Array} lista di ID dei contatti filtrati
     */
    this.applyFilterByGroup = function(idGroup) {
        //ottengo i contatti del gruppo passato per parametro
        var filtredContactsID = groups[idGroup].contacts;
        //ritorno la lista di ID
        return filtredContactsID;
    };

    /** VIEW
     * Mostra la lista di contatti passata come parametro all'interno della
     * AddressBookList
     *
     * NOTA PER I VERIFICATORI Richiede che il 'document' abbia al suo interno
     * un elemento di tipo '<ul>' con 'id' uguale a 'AddressBookList'
     *
     * @param {Array}
     *            filtredContacts Array di ID di contatti
     *  @author Ricardo tresoldi
     *  @author Diego Beraldin
     */
    this.showFilter = function(filtredContacts, isDefaultGroup) {
        // estraggo l'<ul> del Addressbook e lo inizializzo
        var ulList = document.getElementById("AddressBookList");
        ulList.innerHTML = "";

        if (!isDefaultGroup) {
            // etichetta per filtraggio
            var liFilter = document.createElement("li");
            liFilter.id = "filterLabel";
            liFilter.appendChild(document.createTextNode("Filtraggio"));
            // attribuisco al listoItem la capacità di eliminare il filtraggio
            liFilter.onclick = function() {
                // elimina il filtraggio
                var idWhitelist = -1;
                for (var key in groups) {
                    if (groups[key].name == "addrBookEntry") {
                        idWhitelist = key;
                        var select = document.getElementById("selectGroup");
                        select.selectedIndex = getIndexOnSelect(select, idWhitelist);
                        break;
                    }
                }
                thisPresenter.showFilter(thisPresenter.applyFilterByGroup(idWhitelist), true);
            };
            ulList.appendChild(liFilter);
        }

        if (filtredContacts.length == 0) {
            var noContactLI = document.createElement("li");
            noContactLI.appendChild(document.createTextNode("Nessun risultato"));
            ulList.appendChild(noContactLI);
        }
        for (var id in filtredContacts) {
            // ciclo i contatti e agiungo un <li> per ogni contatto
            addListItem(ulList, contacts[filtredContacts[id]]);
        }
    };

    /** PRESENTER
     * Dato un contatto verifica se appartiene alla rubrica
     *
     * @author Riccardo Tresoldi
     * @param {Object}
     *            contact contatto da verificare
     * @return {Boolean} true solo se presente in rubrica
     */
    this.contactAlreadyPresent = function(contact) {
        // scorro la rubrica e controllo se contact.id=rubrica.contact.id.
        // ritorno true solo se presente
        for (var key in contacts) {
            if (contacts[key].id == contact.id) {
                return true;
            }
        }
        return false;
    };

    /** PRESENTER
     * Funzione che blocca l'utente passato per parametro
     *
     * @author Riccardo Tresoldi
     * @param {Object}
     *            contact contatto da bloccare
     * @return {Boolean} true solo se l'utente è stato bloccato correttemente
     */
    this.blockContact = function(contact) {
        // controllo che sia presente nella rubrica
        if (!this.contactAlreadyPresent(contact)) {
            throw "Contatto non presente nella rubrica.";
        }
        if (contact.blocked) {
            throw "Contatto già bloccato.";
        }

        // invio la richiesta di bloccaggio dell'utente
        var request = new XMLHttpRequest();
        request.open("POST", commandURL, false);
        request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        request.send("operation=blockContact&contactId=" + contact.id);

        // controllo il risultato
        result = JSON.parse(request.responseText);
        if (result == true) {
            this.setup();
            mediator.displayContact(contact);
            return true;
        }
        throw "Ops... qualcosa è andato storto nel server.";
    };

    /** PRESENTER
     * Funzione che sblocca l'utente passato per parametro
     *
     * @author Riccardo Tresoldi
     * @param {Object}
     *            contact contatto da sbloccare
     * @return {Boolean} true solo se l'utente è stato sbloccato correttemente
     */
    this.unlockContact = function(contact) {
        // controllo che sia presente nella rubrica
        if (!this.contactAlreadyPresent(contact)) {
            throw "Contatto non presente nella rubrica.";
        }
        if (!contact.blocked) {
            throw "Contatto già sbloccato.";
        }

        // invio la richiesta di bloccaggio dell'utente
        var request = new XMLHttpRequest();
        request.open("POST", commandURL, false);
        request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        request.send("operation=unblockContact&contactId=" + contact.id);

        // controllo il risultato
        result = JSON.parse(request.responseText);
        if (result == true) {
            this.setup();
            mediator.displayContact(contact);
            return true;
        }
        throw "Ops... qualcosa è andato storto nel server.";
    };

    /** PRESENTER
     * Funzione che dato un utente restituisce i gruppi in cui è stato inserito
     * nell'addressBook
     *
     * @author Riccardo Tresoldi
     * @param {Object}
     *            contact contatto di cui ricercare i gruppi in cui è inserito
     * @return {Object} array associativo che presenta la lista dei gruppi in
     *         cui è presente il contatto
     */
    this.getGroupsWhereContactsIs = function(contact) {
        // creo l'array da tornare
        var groupSelected = new Object();
        // ciclo tutti i gruppi
        for (var group in groups) {
            // ciclo tutti i contatti di un gruppo
            for (var groupContact in groups[group].contacts) {
                // controllo se il contatto è uguale al contatto del gruppo
                if (groupContact == contact.id) {
                    // se i contatti coincidono aggiungo il gruppo alla lista di
                    // ritorno e blocco lo scorrimento dell'array
                    groupSelected[group] = groups[group];
                    break;
                }
            }
        }
        // ritorno l'array formato
        return groupSelected;
    };

    /** PRESENTER
     * Funzione che dato in input l'ID di un contatto ritorna il contatto
     *
     * @author Riccardo Tresoldi
     * @param {Number} idContact ID del contatto da ritornare
     * @return {Object} contatto con ID passato come parametro
     */
    this.getContact = function(idContact) {
        return contacts[idContact];
    }
    
    /***************************************************************************
     * HANDLER EVENTI
     **************************************************************************/
    /**
     * Funzione per gestire l'evento in cui viene visualizzato il pannello della
     * rubrica indirizzi
     * @author Riccardo Tresoldi
     */
    function onShowAddressBookPanel() {
        if (!thisPanel) {
            mediator.getView('addressBook');
        }
    }

    /**
     * Funzione per gestire l'evento in cui viene rimosso il pannello della
     * rubrica indirizzi
     * @author Riccardo Tresoldi
     */
    function onRemoveAddressBookPanel() {
        thisPresenter.destroy();
    }
    
    /**
     * Funzione per gestire l'evento in cui viene aggiunto un contatto nella rubrica
     * @author Riccardo Tresoldi
     */
    function onAddContactToAddressBook(contact){
        try {
            thisPresenter.addContact(contact);
            thisPresenter.setup();
            alert(mediator.createNameLabel(contact) + " aggiunto");
        } catch (err) {
            alert(err);
        }
    }
    
    /**
     * Funzione per gestire l'evento in cui viene rimosso un contatto dalla rubrica
     * @author Riccardo Tresoldi
     */
    function onRemoveContactFromAddressBook(contact){
        try {
            thisPresenter.removeContact(contact);
        } catch (err) {
            alert(err);
        }
    }
    
    /**
     * Funzione per gestire l'evento in cui viene aggiunto un contatto in un gruppo della rubrica
     * @author Riccardo Tresoldi
     */
    function onAddContactToGroup(contact, group){
        try{
            thisPresenter.addContactInGroup(contact, group);
        } catch (err) {
            alert(err);
        }
    }
    
    /**
     * Funzione per gestire l'evento in cui viene rimosso un contatto da un gruppo della rubrica
     * @author Riccardo Tresoldi
     */
    function onRemoveContactFromGroup(contact, group){
        try{
            thisPresenter.removeContactFromGroup(contact, group);
        } catch (err) {
            alert(err);
        }
    }
    
    /**
     * Funzione per gestire l'evento in cui viene creato un gruppo della rubrica
     * @author Riccardo Tresoldi
     */
    function onCreateGroup(groupName){
        try{
            thisPresenter.addGroup(groupName);
        } catch (err) {
            alert(err);
        }
    }
    
    /**
     * Funzione per gestire l'evento in cui viene eliminato un gruppo della rubrica
     * @author Riccardo Tresoldi
     */
    function onDeleteGroup(group){
        try{
            thisPresenter.deleteGroup(group);
        } catch (err) {
            alert(err);
        }
    }
    
    /**
     * Funzione per gestire l'evento in cui viene bloccato un contatto
     * @author Riccardo Tresoldi
     */
    function onBlockContact(contact){
        try{
            thisPresenter.blockContact(contact);
        } catch (err) {
            alert(err);
        }
    }
    
    /**
     * Funzione per gestire l'evento in cui viene sbloccato un contatto
     * @author Riccardo Tresoldi
     */
    function onUnlockContact(contact){
        try{
            thisPresenter.unlockContact(contact);
        } catch (err) {
            alert(err);
        }
    }

    /***************************************************************************
     * LISTNER DEGLI EVENTI
     **************************************************************************/
    document.addEventListener("changeAddressBooksContactState", function(evt) {
        setStateToContact(evt.idUserChange, statusUserChange);
    });
    document.addEventListener("showAddressBookPanel", onShowAddressBookPanel);
    document.addEventListener("removeAddressBookPanel", onRemoveAddressBookPanel);
    document.addEventListener("addContactToAddressBook", function(evt){
        onAddContactToAddressBook(evt.contact);
    });
    document.addEventListener("removeContactFromAddressBook", function(evt){
        onRemoveContactFromAddressBook(evt.contact);
    });
    document.addEventListener("addContactToGroup", function(evt){
        onAddContactToGroup(evt.contact, evt.group)
    });
    document.addEventListener("removeContactFromGroup", function(evt){
        onRemoveContactFromGroup(evt.contact, evt.group);
    });
    document.addEventListener("createGroup", function(evt){
        onCreateGroup(evt.groupName);
    });
    document.addEventListener("deleteGroup", function(evt){
        onDeleteGroup(evt.group);
    });
    document.addEventListener("blockContact", function(evt){
        onBlockContact(evt.contact);
    });
    document.addEventListener("unlockContact", function(evt){
        onUnlockContact(evt.contact);
    });

    /***************************************************************************
     * MISCELLANEOUS
     **************************************************************************/
    /*
     * FILE JSON CHE RAFFIGURA LA RUBRICA { "idUser1":{ "name": "", "surname":
     * "", "email": "", "id": "", "picturePath": "", "state": "", "blocked":
     * "true|false" },
     *
     * "idUser2": {.......}, }
     *
     * FILE JSON CHE RAFFIGURA I GRUPPI { "idGruppo1":{ "name": "", "id": "",
     * "contacts": [ 1, 2, 3 ] },
     *
     * "idGruppo2": {........}. }
     */
    this.getContacts = function() {
        return contacts;
    };
    this.setContacts = function(cont) {
        contacts = cont;
    };
    this.getGroups = function() {
        return groups;
    };
    this.setGroups = function(cont) {
        groups = cont;
    };
}
