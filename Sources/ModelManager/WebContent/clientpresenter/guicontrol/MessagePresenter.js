/**
 * Presenter incaricato di gestire i messaggi in segreteria
 * 
 * @author Elena Zecchinato
 * @author Diego Beraldin
 * @author Riccardo Tresoldi
 */
function MessagePanelPresenter() {
	/***************************************************************************
	 * VARIABILI PRIVATE
	 **************************************************************************/
	var thisPresenter = this;
	var thisPanel;
	// array di messaggi
	var messages = new Array();

	/***************************************************************************
	 * METODI PRIVATI
	 **************************************************************************/
	/** VIEW
	 * Restituisce il percorso dell'immagine che rappresenta lo stato del messaggio
	 * in base al valore della proprietà status del messaggio passato coem parametro
	 * 
	 * @param {Object} message
	 * 		  messaggio della segreteria da visualizzata
	 * @returns {String} il percorso dell'immagine sul server
	 * @author Diego Beraldin
	 */
	function getStatusSrc(message) {
		// message.status rappresenta se il messaggio è nuovo/non nuovo
		var result = "";
		if (!message.status) {
			result = "img/readmessage.png";
		} else {
			result = "img/unreadmessage.png";
		}
		return result;
	}
	
	/** VIEW
	 * Aggiunge un messaggio a una lista per creare l'elenco della segreteria
	 * telefonica creando il list item corrispondente (con la stringa
	 * caratteristica e la funzione che ne definisce il comportamento al clic) e
	 * aggiungendolo alla lista 'messageList' contenuta in questo pannello.
	 * 
	 * @param {Object}
	 *            message messaggio della segreteria che corrisponde a
	 *            'JSMessage' ed è caratterizzato da sender, id,
	 *            status, video, src e date
	 * @author Riccardo Tresoldi
	 */
	function addListItem(message) {
		// elemento da aggiungere alla lista
		var item = document.createElement("li");
		var status = document.createElement("img");
		var elimina  = document.createElement("img");
		
		// estrae il nome del mittente del messaggio
		var contact = mediator.getContactById(message.sender);
		var contactName = mediator.createNameLabel(contact);
		
		item.appendChild(status);
		item.appendChild(document.createTextNode(contactName));
		item.appendChild(document.createTextNode(message.date));
		item.appendChild(elimina);
		
		// imposta il source delle immagini
		status.src = getStatusSrc(message);
		elimina.src = "img/deleteGroupImg.png";
		
		// comportamento del list item
		item.onclick = function() {
			thisPresenter.setStatus(message, false);
			status.src = getStatusSrc(message);
			var video = document.getElementById("messageVideo");
			video.src = message.src;
		};
		
		// comportamento del pulsante elimina
		elimina.onclick = function() {
			thisPresenter.deleteMessage(message);
		};
		
		// comportamento del pulsante toggleState
		status.onclick = function() {
			var oldStatus = message.status;
			thisPresenter.setStatus(message, !oldStatus);
			status.src = getStatusSrc(message);
		};
		
		// appendo il nuovo elemento appena creato
		var messageList = document.getElementById("messageList");
		messageList.appendChild(item);	
	}

	/** PRESENTER
	 * Ottiene i messaggi di segreteria dal server
	 * 
	 * @returns {Array} i messagi scaricati dal server
	 * @author Riccardo Tresoldi
	 */
	function getMessages() {
		var request = new XMLHttpRequest();
		request.open("POST", commandURL, false);
		request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		request.send("operation=getMessages");
		return JSON.parse(request.responseText);
	};

	/***************************************************************************
	 * METODI PUBBLICI
	 **************************************************************************/
	/** PRESENTER
	 * Riscarica i messaggi dal server e li aggiunge nella lista 'messageList'
	 * che è presente nel pannello MessagePanel
	 * 
	 * @author Riccardo Tresoldi
	 */
	this.displayList = function() {
		messages = getMessages();
		for ( var message in messages) {
			this.addListItem(message);
		}
	};
	
	/** VIEW
	 * Inizializza e popola con i dati il pannello della segreteria telefonica
	 * 
	 * @author Diego Beraldin
	 */
	this.display = function() {
		thisPanel = document.getElementById("MessagePanel");
		thisPresenter.displayList();
	};
	
	
	/** PRESENTER
	 * Elimina un messaggio dalla segreteria contattando la servlet responsabile
	 * dell'operazione e scaricando nuovamente i messaggi
	 * 
	 * @param {Number}
	 *            idMessage id del messaggio che deve essere cancellato
	 *  @author Elena Zecchinato
	 */
	 this.deleteMessage = function(message) {
		var request = new XMLHttpRequest();
			request.open("POST", commandURL, false);
			request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
			request.send("operation=deleteMessage&idMessage=" + message.id);
			result=JSON.parse(request.responseText);
			
			if (result == true) {
	            this.setup();
	            return true;
	        }
		
			throw "Ops... qualcosa è andato storto nel server.";
	};
	
	/** PRESENTER
	 * Rende lo stato di un messaggio nuovo oppure non nuovo
	 * 
	 * @author Riccardo Tresoldi
	 * @param {Object} message
	 *            il messaggio che deve essere modificato
	 * @param {Boolean} valueToSet
	 *            [true: "nuovo"] oppure [false: "letto"]
	 * @throws {String}
	 *             un errore se il non è stato possibile cambiare lo stato del
	 *             messaggio
	 */
	this.setStatus = function(message, valueToSet) {
		message.status = valueToSet;
		var request = new XMLHttpRequest();
		request.open("POST", commandURL, false);
		request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		request.send("operation=updateMessage&idMessage=" + message.id + "&read=" + valueToSet);
		outcome = JSON.parse(request.responseText);
		if (!outcome) {
			throw "Impossibile impostare lo stato del messaggio a " + (valueToSet ? " letto " : " non letto") + "!";
		}
	};
	
	/***************************************************************************
     * HANDLER DEGLI EVENTI
     **************************************************************************/
	/** PRESENTER
	 * Funzione per gestire l'evento in cui viene visualizzato il pannello dei
	 * messaggi di segreteria
	 * 
	 * @author Riccardo Tresoldi
	 */
    function onShowMessagePanel() {
        mediator.getView('message');
    }
	
	/***************************************************************************
     * LISTENER DEGLI EVENTI
     **************************************************************************/
    document.addEventListener("showMessagePanel", onShowMessagePanel);
}