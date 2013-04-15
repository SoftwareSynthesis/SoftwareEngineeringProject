/**
 * Presenter incaricato di gestire il pannello che visualizza i risultati di
 * ricerca
 * 
 * @constructor
 * @this {SearchResultPanelPresenter}
 * @param {String}
 *            url URL della servlet con cui il presenter deve parlare
 * @author Marco Schivo
 * @author Diego Beraldin
 */
function SearchResultPanelPresenter(url) {
	/***************************************************************************
	 * VARIABILI PRIVATE
	 **************************************************************************/
	// indirizzo della servlet con cui il presenter deve interfacciarsi
	var servlets = new Array();
	// inizializza i normi delle servlet
	getServletURLs();

	/***************************************************************************
	 * METODI PRIVATI
	 **************************************************************************/
	function getServletURLs() {
		var configurationRequest = new XMLHttpRequest();
		configurationRequest.open("POST", configurationFile, false);
		configurationRequest.send();
		var XMLDocument = configurationRequest.responseXML;
		var baseURL = XMLDocument.getElementsByTagName("baseURL")[0].childNodes[0].data;
		var name = XMLDocument.getElementById("search").childNodes[0].data;
		servlets.push(baseURL + name);
	}

	/**
	 * Crea l'etichett che visualizza i dati dell'utente (se sono presenti) e in
	 * ogni caso mostra l'email memorizzata nel sistema. La funzione è
	 * utilizzata per generare la lista dei contatti
	 * 
	 * @param contact
	 *            il contatto a partire dal quale deve essere generata
	 *            l'etichetta del nome
	 * @returns {String} l'etichetta di nome
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
		if (name != "")
			name += " - ";
		// visualizza sempre l'email
		name += contact.mail;
		return name;
	}

	/**
	 * Aggiunge un contatto ad una lista (pensato per essere visualizzato nel
	 * pannello dei risultati di una ricerca, in cui è quindi possibile anche
	 * aggiungere un nuovo contatto alla propria rubrica
	 * 
	 * @param {HTMLUListElement}
	 *            list la lista cui deve essere aggiunta la nuova voce
	 * @param {Object}
	 *            contact il contatto a partire dal quale generare la voce da
	 *            aggiungere alla listas
	 * @see PresenterMediator#onContactSeleted(contact)
	 * @author Diego Beraldin
	 */
	function addListItem(list, contact) {
		var item = document.createElement("li");
		item.setAttribute("id", contact.id);
		// costruisce il nodo
		var avatarNode = document.createElement('img');
		avatarNode.setAttribute("src", contact.picturePathatar);
		var name = createNameLabel(contact);
		var textNode = document.createTextNode(name);
		// aggiunge i sottonodi al 'li' appena creato
		item.appendChild(avatarNode);
		item.appendChild(textNode);
		// FIXME cos'è statusNode??!?!?
		item.appendChild(statusNode);
		// comportamento del list item al click del mouse
		item.onclick = function() {
			mediator.onContactSelected(contact);
		};
		item.appendChild(button);

		// aggiunge il 'li' alla lista ricevuta come parametro
		list.appendChild(item);
	}

	/***************************************************************************
	 * METODI PUBBLICI
	 **************************************************************************/
	/**
	 * Inizializza il pannello dei risultati ricerca
	 * 
	 * @author Diego Beraldin
	 */
	this.createPanel = function() {
		var element = document.createElement("div");
		element.setAttribute("id", "SearchResultPanel");
		userList = document.createElement("ul");
		userList.id = "userList"
		// aggiunge al sottoalbero il nuovo elemento
		element.appendChild(userList);
		return element;
	};

	/**
	 * Visualizza all'interno del pannello una lista di contatti che è stata
	 * ottenuta dal server a seguito di una ricerca
	 * 
	 * @param {Array}
	 *            contacts la lista di contatti che deve essere visualizzata
	 * @author Diego Beraldin
	 */
	this.displayContactList = function(contacts) {
		var userList = document.getElementById("userList");
		userList.innerHTML = "";
		for ( var c in contacts) {
			addListItem(userList, c);
		}
	};
}