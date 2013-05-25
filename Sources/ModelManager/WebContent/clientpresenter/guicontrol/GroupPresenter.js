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
			deleteContactImg.src = "img/deleteContactImg.png";
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

	/** VIEW
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
			    var form = createAddToGroupForm(group);
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
	
	/** PRESENTER
	 * Seleziona l'insieme di contatti che possono essere aggiunti a un
	 * determinato gruppo, in particolare si tratta di tutti i contatti
	 * presenti in rubrica ma che non appartengono già a quel gruppo.
	 * 
	 * @param {Object} group
	 *        il gruppo di riferimento per cui selezionare i candidati
	 * @returns {Object} un array associativo dei contatti selezionati
	 * @author Riccardo Tresoldi
	 */
	this.selectCandidates = function(group) {
		// effettua una copia PROFONDA dell'oggetto.
		var candidates = JSON.parse(JSON.stringify(contacts));
		for ( var key in group.contacts) {
		    delete candidateContacts[group.contacts[key]];
		}
		return candidates;
	};

	/***************************************************************************
	 * METODI PUBBLICI
	 **************************************************************************/
	/** VIEW
	 * Crea il form per l'aggiunta di un nuovo contatto all'interno del gruppo
	 * 
	 * @author Diego Beraldin
	 */
	createAddToGroupForm = function(group) {
		// crea il form
		var form = document.createElement("form");

		// recupera i contatti che non appartengono al gruppo
		candidateContacts = thisPresenter.selectCandidates(group);

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
			var name = mediator.createNameLabel(candidateContacts[contact]);
			label.appendChild(document.createTextNode(name));
			set.appendChild(box);
			set.appendChild(label);
		}
		form.appendChild(set);

		// appende al form i pulsanti per l'aggiunta e la chiusura
		var addButton = document.createElement("button");
		addButton.appendChild(document.createTextNode("Aggiungi"));
		addButton.onclick = function() {
			var checkedContacts = form.getElementsByTagName("input");
			for ( var contactbox in checkedContacts)
				if (contactbox.type == "checkbox" && contactbox.checked == true) {
					// FIXME questo non dovrebbe essere un evento?
					mediator.addContactInGroup(contacts[contactbox.value], group);
				}
		};
		var closeButton = document.createElement("button");
        closeButton.appendChild(document.createTextNode("Annulla"));
        closeButton.onclick = function() {
            try {
                var formParent = form.parentElement;
                formParent.removeChild(form);
            } catch(err) {
                alert(err);
            }
        };
        
		form.appendChild(addButton);
		form.appendChild(closeButton);

		return form;
	};

	/** VIEW
	 * Metodo che recupera i dati necessari alla visualizzazione del contenuto
	 * del pannello, quindi ne avvia la creazione
	 * 
	 * @author Diego Beraldin
	 */
	this.display = function() {
		thisPanel = document.getElementById("GroupPanel");
		var groupList = document.getElementById("groupList");
		groupList.innerHTML = "";
		thisPresenter.displayList();
	};

	/** PRESENTER
	 * Visualizza all'interno del pannello la lista dei gruppi della mia rubrica
	 * 
	 * @author Riccardo Tresoldi
	 */
	this.displayList = function() {
		contacts = mediator.getAddressBookContacts();
		groups = mediator.getAddressBookGroups();
		for ( var g in groups) {
		    if(groups[g].name != "addrBookEntry"){
		        addListItem(groups[g]);
		    }
		}
	};

    /***************************************************************************
     * HANDLER DEGLI EVENTI
     **************************************************************************/	
	/** PRESENTER
     * Funzione per gestire l'evento in cui viene visualizzato
     * il pannello della gestione dei gruppi
     * 
     * @author Riccardo Tresoldi
     */
    function onShowGroupPanel() {
        mediator.getView('group');
    }
	
	/***************************************************************************
     * LISTENER DEGLI EVENTI
     **************************************************************************/
    document.addEventListener("showGroupPanel", onShowGroupPanel);
}
