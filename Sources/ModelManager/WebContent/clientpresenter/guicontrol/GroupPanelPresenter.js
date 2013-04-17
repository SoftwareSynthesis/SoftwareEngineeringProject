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
	var contacts = new Object();
	var groups = new Object();

	/***************************************************************************
	 * FUNZIONI PRIVATE
	 **************************************************************************/
	/**
	 * Crea l'etichetta che contiene il nome di un determinato contatto
	 * 
	 * FIXME dovrebbe essere portato a fattor comune per tutti i contatti
	 * 
	 * @param {Object}
	 *            contact il contatto da cui deve essere estratto il nome
	 * @returns {String} la stringa che contiene il nome del contatto
	 * @author Marco Schivo
	 */
	function createNameLabel(contact) {
		var name = "";
		if (contact.name != null)
			name += contact.name;
		if (contact.surname != null) {
			if (name != "")
				name += " ";
			name += contact.surname;
		}
		if (name == "") {
			name += contact.email;
		}
		return name;
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
		list.style.display = "none";
		for ( var id in group.contacts) {
			var contactName = createNameLabel(contacts[id]);
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
		var item = document.createElement("li");
		item.id = group.id;
		// costruisce il nodo che rappresenta il gruppo
		var nameSpan = document.createElement("span");
		nameSpan.appendChild(document.createTextNode(group.name));
		var expandGroupImg = document.createElement("img");
		var addToGroupImg = document.createElement("img");
		var deleteGroupImg = document.createElement("img");
		expandGroupImg.className = "showGroupContacts";
		addToGroupImg.className = "addContactInGroup";
		deleteGroupImg.className = "deleteGroup";
		// TODO da stabilire questi campi src
		deleteGroupImg.src = "";
		expandGroupImg.src = "";
		addToGroupImg.src = "";

		var list = createContactList(group);
		item.appendChild(list);

		// azioni associate ai pulsanti
		expandGroupImg.onclick = function() {
			if (list.style.display == "none") {
				// se la lista degli utenti non era visibile la mostra
				list.style.display = "block";
			} else {
				// altrimenti la rende invisibile e cancella il form per
				// l'aggiunta di nuovi contatti a un gruppo
				var form = item.lastChild();
				if (form.nodeName == "FORM") {
					item.removeChild(form);
				}
				list.style.display = "none";
			}
		};

		addToGroupImg.onclick = function() {
			// mostra la lista degli utenti dei gruppo
			list.style.display = block;
			var form = this.createAddToGroupForm(group);
			item.appendChild(form);
		};

		deleteGroupImg.onclick = function() {
			// chiedo conferma per l'eliminazione
			var userConfirm = confirm("Sei sicuro di voler eliminare questo gruppo?\n"
					+ "Gli utenti appartenenti a questo gruppo non verranno eliminati.");
			// se viene data conferma infoco la funzione per eliminare il gruppo
			if (userConfirm) {
				mediator.onGroupRemoved(group);
				this.setup();
			}
		};

		// aggiunge i sottonodi al 'li' appena creato
		item.appendChild(expandGroupImg);
		item.appendChild(nameSpan);
		item.appendChild(deleteGroupImg);
		item.appendChild(addToGroupImg);

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
		var candidateContacts = new Array();
		for ( var key in contacts) {
			if (group.contacts.indexOf(contacts[key].id) < 0) {
				candidateContacts.push(contacts[key]);
			}
		}

		// insieme che dovrà contenere i campi del form
		var set = document.createElement("fieldset");

		// appende al form tutte le checkbox
		for ( var contact in candidateContacts) {
			var box = document.createElement("checkbox");
			box.id = contact.id;
			var label = document.createElement("label");
			label.setAttribute("for", contact.id);
			label
					.appendChild(document
							.createTextNode(createNameLabel(contact)));
			set.appendChild(box);
		}
		form.appendChild(set);

		// appende al form il bottone
		var button = document.createElement("button");
		button.appendChild(document.createTextNode("Aggiungi"));
		button.onclick = function() {
			var checkedContacts = form.getElementsByTagName("checkbox");
			for ( var contact in checkedContacts)
				if (contact.checked == "checked") {
					mediator.addContactInGroup(contact, group);
				}
		};
		form.appendChild(button);

		return form;
	};

	/**
	 * Crea il pannello per la gestione dei gruppi che dev'essere richiamato nel
	 * mainPanelPresenter
	 * 
	 * @returns {HTMLDivElement} il pannello dello storico delle chiamate
	 *          inizializzato
	 * 
	 * @author Riccardo Tresoldi
	 */
	this.createPanel = function() {
		// creazione dell'elemento
		var element = document.createElement("div");
		element.id = "groupPanel";

		// creazione dell'intestazione
		var header = document.createElement("h1");
		header.appendChild(document.createTextNode("Gestione gruppi"));
		var divHeader = document.createElement("div");
		divHeader.className = "panelHeader";
		divHeader.appendChild(header);

		// creazione della lista dei gruppi
		groupList = document.createElement("ul");
		groupList.id = "groupList";

		// aggiunge al sottoalbero i nuovi elementi
		element.appendChild(divHeader);
		element.appendChild(groupList);
		this.setup();
		return element;
	};

	/**
	 * Metodo che recupera i dati necessari alla visualizzazione del contenuto
	 * del pannello, quindi ne avvia la creazione
	 * 
	 * @author Diego Beraldin
	 */
	this.setup = function() {
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
			addListItem(g);
		}
	};
}
