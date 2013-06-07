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
	var thisPresenter = this;
	var thisPanel;

	/***************************************************************************
	 * METODI PRIVATI
	 **************************************************************************/
	/** PRESENTER
	 * Funzione per effettuare la richiesta di una ricerca al server
	 * @version 2.0
	 * @author Riccardo Tresoldi
	 * @param {String} pattern stringa da far cercare nal server
	 * @return {Object} Oggetto rappresentante un insieme di contatti
	 */
	function sendSearchRequest(pattern){
	    var searchRequest = new XMLHttpRequest();
        searchRequest.open("POST", commandURL, false);
        searchRequest.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        searchRequest.send("operation=search&param=" + pattern);
        var contacts = JSON.parse(searchRequest.responseText);
        return contacts;
	}
	
	/** VIEW
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

	/** VIEW
	 * Aggiunge un contatto ad una lista (pensato per essere visualizzato nel
	 * pannello dei risultati di una ricerca), in cui è quindi possibile anche
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
		item.id = "resultList-" + contact.id;

		// immagine del contatto
		var avatarNode = document.createElement('img');
		avatarNode.src = contact.picturePath;

		// nome del contatto
		var name = mediator.createNameLabel(contact);
		var textNode = document.createTextNode(name);
		
		// stato del contatto
		var avatarState = document.createElement("img");
		avatarState.src = getImageSrc(contact);

		// aggiunge i sottonodi al 'li' appena creato
		item.appendChild(avatarNode);
		item.appendChild(textNode);
		item.appendChild(avatarState);

		// comportamento del list item al click del mouse
		item.onclick = function() {
		    showContactPanel.contact = contact;
		    document.dispatchEvent(showContactPanel);
		};

		// aggiunge il 'li' alla lista ricevuta come parametro
		var list = document.getElementById("userList");
		list.appendChild(item);
	}

	/***************************************************************************
	 * METODI PUBBLICI
	 **************************************************************************/
	/** VIEW
	 * Funzione per inizializzare il pannello associando gli eventi corretti ai vari elementi HTML
	 * @version 2.0
	 * @author Riccardo Tresoldi
	 */
	this.display = function(){
	    var searchInputField = document.getElementById("searchInputField");
	    var searchInputButton = document.getElementById("searchInputButton");
	    searchInputButton.onclick = function(){
	        var textToSearch = searchInputField.value;
	        var foundContacts = sendSearchRequest(textToSearch);
	        thisPresenter.handleResult(foundContacts);
	    }
	};
	
	/** PRESENTER
	 * Visualizza all'interno del pannello una lista di contatti che è stata
	 * ottenuta dal server a seguito di una ricerca
	 * @version 2.0
	 * @param {Object} contacts la lista di contatti che deve essere visualizzata
	 * @author Diego Beraldin
	 */
	this.handleResult = function(contacts) {
		var userList = document.getElementById("userList");
		userList.innerHTML = "";
		if (Object.keys(contacts).length == 0) {
		    var noContactAlertListItel = document.createElement("li");
            noContactAlertListItel.appendChild(document.createTextNode("Nessun risultato"));
            userList.appendChild(noContactAlertListItel);
		}
		for ( var con in contacts) {
			addListItem(contacts[con]);
		}
	};

    /***************************************************************************
     * HANDLER DEGLI EVENTI
     **************************************************************************/
    /** PRESENTER
     * Funzione per gestire l'evento in cui viene visualizzato il pannello per effettuare una ricerca e leggerne i risultati
     * @version 2.0
     * @author Riccardo Tresoldi
     */
    function onShowSearchResultPanel() {
        mediator.getView('searchResult');
    }
	
	/***************************************************************************
     * LISTENER DEGLI EVENTI
     **************************************************************************/
    document.addEventListener("showSearchResultPanel", onShowSearchResultPanel);
}
