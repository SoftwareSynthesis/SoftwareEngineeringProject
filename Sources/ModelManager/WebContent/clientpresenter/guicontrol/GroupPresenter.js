/**
 * Presenter incaricato di gestire i gruppi
 * 
 * @param {String}
 *            url URL della servlet con cui il presenter deve comunicare
 * @author Riccardo Tresoldi
 * @author Marco Schivo
 * @author Diego Beraldin
 */
function GroupPanelPresenter(url) {
	/***************************************************************************
	 * VARIABILI PRIVATE
	 **************************************************************************/
	var thisPresenter = this;
	var thisPanel;
	var contacts = new Object();
	var groups = new Object();

	/***************************************************************************
	 * FUNZIONI PRIVATE
	 **************************************************************************/
	/** VIEW
     * Funzione per gestire l'evento in cui viene visualizzato il pannello della gestione dei gruppi
     * @author Riccardo Tresoldi
     */
    function onShowGroupPanel() {
        mediator.getView('group');
    }
	
	/**
	 * Crea la lista dei contatti che sono presenti nel gruppo
	 * 
	 * @param group
	 * @returns {HTMLUListElement} la lista dei contatti appartenenti a un
	 *          gruppo con il pulsante per la rimozione dallo stesso
	 * @author Diego Beraldin
	 */
	function createContactList(group) {
		var list = document.createElement("ul");
		list.className = "collapsedList";
		for ( var id in group.contacts) {
			var contactName = mediator.createNameLabel(contacts[group.contacts[id]]);
			var nameNode = document.createTextNode(contactName);

			// pulsante per la cancellazione del contatto dal gruppo
			var deleteContactImg = document.createElement("img");
			// TODO da impostare questo campo srcF
			deleteContactImg.src = "";
			deleteContactImg.className = "deleteContact";
			deleteContactImg.onclick = function() {
				mediator.onContactRemovedFromGroup(contacts[id], group);
			};

			// crea l'elemento della lista
			var contactItem = document.createElement("li");
			contactItem.appendChild(nameNode);
			contactItem.appendChild(deleteContactImg);

			// appende il nodo in fondo alla lista
			list.appendChild(contactItem);
		}
		return list;
	}

	/**
	 * Aggiunge un gruppo ad una lista
	 * 
	 * @param {HTMLUListElement}
	 *            list la lista su cui aggiungere il gruppo
	 * @param {Object}
	 *            group il gruppo da aggiungere alla lista
	 * @author Riccardo Tresoldi
	 * @author Diego Beraldin
	 */
	function addListItem(group) {
	    // creo l'elemento li e gli attribuisco un id
		var item = document.createElement("li");
		item.id = "g-" + group.id;
		// costruisce il nodo che rappresenta il gruppo
		var nameSpan = document.createElement("span");
		nameSpan.appendChild(document.createTextNode(group.name));
		var expandGroupImg = document.createElement("img");
		var addToGroupImg = document.createElement("img");
		var deleteGroupImg = document.createElement("img");
		expandGroupImg.className = "showGroupContacts";
		addToGroupImg.className = "addContactInGroup";
		deleteGroupImg.className = "deleteGroup";
		deleteGroupImg.src = "img/deleteGroupImg.png";
		expandGroupImg.src = "img/expandGroupImg.png";
		addToGroupImg.src = "img/addToGroupImg.png";

        // creo la lista dei contatti appartenenti al gruppo
		var contactList = createContactList(group);

		// azioni associate ai pulsanti
		expandGroupImg.onclick = function() {
			if (contactList.className == "collapsedList") {
				// se la lista degli utenti non era visibile la mostra
				contactList.className = "uncollapsedList";
			} else {
				// altrimenti la rende invisibile e cancella il form per
				// l'aggiunta di nuovi contatti a un gruppo
				var form = item.lastChild;
				if (form.nodeName == "FORM") {
					item.removeChild(form);
				}
				contactList.className = "collapsedList";
			}
		};

		addToGroupImg.onclick = function() {
			// mostra la lista degli utenti dei gruppo
			if(!document.getElementById("GroupPanel").getElementsByTagName("fieldset")[0]){
			    contactList.className = "uncollapsedList";
			    var form = thisPresenter.createAddToGroupForm(group);
			    contactList.appendChild(form);
			}
		};

		deleteGroupImg.onclick = function() {
			// chiedo conferma per l'eliminazione
			var userConfirm = confirm("Sei sicuro di voler eliminare il gruppo" + group.name + "?\n Gli utenti appartenenti a questo gruppo NON verranno eliminati.");
			// se viene data conferma invoco la funzione per eliminare il gruppo
			if (userConfirm) {
			    //FIXME usare l'evento
				mediator.onGroupRemoved(group);
				this.setup();
			}
		};

		// aggiunge i sottonodi al 'li' appena creato
		item.appendChild(expandGroupImg);
		item.appendChild(nameSpan);
		item.appendChild(deleteGroupImg);
		item.appendChild(addToGroupImg);
		item.appendChild(contactList);

		// aggiunge il 'li' alla lista ricevuta come parametro
		var list = document.getElementById("groupList");
		list.appendChild(item);
	}

	/***************************************************************************
	 * METODI PUBBLICI
	 **************************************************************************/
	/**
	 * Crea il form per l'aggiunta di un nuovo contatto all'interno del gruppo
	 * 
	 * @author Diego Beraldin
	 */
	this.createAddToGroupForm = function(group) {
		// crea il form
		var form = document.createElement("form");

		// recupera i contatti che non appartengono al gruppo
		// TODO copiare per valore l'oggetto.
		var candidateContacts = Object.create(contacts);
		for ( var key in group.contacts) {
		    delete candidateContacts[group.contacts[key]];
			/*if (group.contacts.indexOf(contacts[key].id) < 0) {
				candidateContacts.push(contacts[key]);
			}*/
		}

		// insieme che dovrà contenere i campi del form
		var set = document.createElement("fieldset");

		// appende al form tutte le checkbox
		for ( var contact in candidateContacts) {
			var box = document.createElement("input");
			box.type="checkbox";
			box.id = "candidateContacts-" + candidateContacts[contact].id;
			box.value = candidateContacts[contact].id;
			var label = document.createElement("label");
			label.setAttribute("for", "candidateContacts-" + candidateContacts[contact].id);
			label.appendChild(document.createTextNode(mediator.createNameLabel(candidateContacts[contact])));
			set.appendChild(box);
			set.appendChild(label);
		}
		form.appendChild(set);

		// appende al form il bottone
		var addButton = document.createElement("button");
		addButton.appendChild(document.createTextNode("Aggiungi"));
		addButton.onclick = function() {
			var checkedContacts = form.getElementsByTagName("input");
			for ( var contactbox in checkedContacts)
				if (contactbox.type == "checkbox" && contactbox.checked == true) {
					mediator.addContactInGroup(contacts[contactbox.value], group);
				}
		};
		var closeButton = document.createElement("button");
        closeButton.appendChild(document.createTextNode("Annulla"));
        closeButton.onclick = function() {
            try{
            var formParent = form.parentElement; 
            formParent.removeChild(form);   
            }
            catch(err){
                alert(err);
            }
        };
		form.appendChild(addButton);
		form.appendChild(closeButton);

		return form;
	};

	/**
	 * Metodo che recupera i dati necessari alla visualizzazione del contenuto
	 * del pannello, quindi ne avvia la creazione
	 * 
	 * @author Diego Beraldin
	 */
	this.display = function() {
		contacts = mediator.getAddressBookContacts();
		groups = mediator.getAddressBookGroups();
		this.displayGroupList();
	};

	/**
	 * Visualizza all'interno del pannello la lista dei gruppi della mia rubrica
	 * 
	 * @author Riccardo Tresoldi
	 */
	this.displayGroupList = function() {
		var groupList = document.getElementById("groupList");
		groupList.innerHTML = "";
		for ( var g in groups) {
		    if(groups[g].name != "addrBookEntry"){
		        addListItem(groups[g]);
		    }
		}
	};
	
	/***************************************************************************
     * LISTNER DEGLI EVENTI
     **************************************************************************/
	document.addEventListener("showGroupPanel", onShowGroupPanel);
}
