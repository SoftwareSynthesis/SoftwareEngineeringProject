/**
 * Presenter incaricato di gestire il pannello che visualizza i risultati di una
 * ricerca fra gli utenti registrati al sistema
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
	 * FIXME questo dovrebbe essere portato a fattor comune per tutti i contatti
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
	 * Recupera il percoso dell'immagine di stato per un determinato contatto
	 * 
	 * FIXME questo dovrebbe essere portato a fattor comune per tutti i contatti
	 * 
	 * @param {Object}
	 *            contact il contatto di cui recuperare l'immagine di stato
	 * @author Riccardo Tresoldi
	 */
	function getImageSrc(contact) {
		path = "";
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

	/**
	 * Aggiunge un contatto ad una lista (pensato per essere visualizzato nel
	 * pannello dei risultati di una ricerca, in cui è quindi possibile anche
	 * aggiungere un nuovo contatto alla propria rubrica
	 * 
	 * @param {Object}
	 *            contact il contatto a partire dal quale generare la voce da
	 *            aggiungere alla listas
	 * @see PresenterMediator#onContactSeleted(contact)
	 * @author Diego Beraldin
	 */
	function addListItem(contact) {
		var item = document.createElement("li");
		item.setAttribute("id", contact.id);

		// immagine del contatto
		var avatarNode = document.createElement('img');
		avatarNode.setAttribute("src", contact.picturePathatar);

		// nome del contatto
		var name = createNameLabel(contact);
		var textNode = document.createTextNode(name);

		// stato del contatto
		var stateNode = document.createElement('img');
		stateNode.src = getImageSrc(contact);

		// aggiunge i sottonodi al 'li' appena creato
		item.appendChild(avatarNode);
		item.appendChild(textNode);
		item.appendChild(stateNode);

		// comportamento del list item al click del mouse
		item.onclick = function() {
			mediator.onContactSelected(contact);
		};
		item.appendChild(button);

		// aggiunge il 'li' alla lista ricevuta come parametro
		var list = document.getElementById("userList");
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
		// crea l'elemento
		var element = document.createElement("div");
		element.id = "SearchResultPanel";
		// crea l'intestazione
		var header = document.createElement("h1");
		header.appendChild(document.createTextNode("Ricerca utenti"));
		var divHeader = document.createElement("div");
		divHeader.className = "panelHeader";
		divHeader.appendChild(header);
		element.appendChild(divHeader);

		// crea la lista degli utenti
		userList = document.createElement("ul");
		userList.id = "userList";
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
		for ( var con in contacts) {
			addListItem(con);
		}
	};
}