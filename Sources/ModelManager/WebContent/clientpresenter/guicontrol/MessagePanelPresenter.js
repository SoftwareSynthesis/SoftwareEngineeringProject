/**
 * Presenter incaricato di gestire i messaggi in segreteria
 * 
 * @param {String}
 *            url URL della servlet con cui il presenter deve comunicare
 * @author Elena Zecchinato
 * @author Diego Beraldin
 */
function MessagePanelPresenter(url) {
	/***************************************************************************
	 * VARIABILI PRIVATE
	 **************************************************************************/
	// array di messaggi
	var messages = new Array();

	/***************************************************************************
	 * METODI PRIVATI
	 **************************************************************************/	
	/**
	 * Aggiunge un messaggio a una lista per creare l'elenco della segreteria
	 * telefonica creando il list item corrispondente (con la stringa
	 * caratteristica e la funzione che ne definisce il comportamento al clic) e
	 * aggiungendolo alla lista 'messageList' contenuta in questo pannello.
	 * 
	 * @param {Object}
	 *            message messaggio della segreteria che corrisponde a
	 *            'JSMessage' ed è caratterizzato da sender, id,
	 *            status, video, src e date
	 * @author Riccardo Tresoldi, Elena Zecchinato
	 */
	
	
	function addListItem(message) {
		var messageList = document.getElementById("messageList");
		var item = document.createElement("li");
	
		var status = document.createElement("img");
		var elimina  = document.createElement("img");
		
		item.appendChild(status);
		item.appendChild(document.createTextNode(message.sender));
		item.appendChild(document.createTextNode(message.date));
		item.appendChild(elimina);
		
		var self=this;
		
		item.onclick = function() {
		// imposto il messaggio come letto
		var stato=true;
		self.setAsRead(message,stato);
		
		var video=documento.getElementById("messageVideo");
        video.src = ""; // TODO CI VUOLE IL PATH
		};
		
		
		status.onclick = function() {
			self.deleteMessage(message);
		};
		
		
		elimina.onclick = function() {
			stato=message.status;
			self.setAsRead(message,!stato);
		};
		
		// quando ho finito appendo il nuovo elemento appena creato.
		messageList.appendChild(item);	
	}

	/**
	 * Ottiene i messaggi di segreteria dal server contattando la servlet
	 * corrispondente e li salva all'interno dell'array messages contenuto
	 * all'interno di questo presenter
	 * 
	 * @author Riccardo Tresoldi
	 */
	function getMessages() {
		var request = new XMLHttpRequest();
		request.open("POST", commandURL, false);
		request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		request.send("operation=getMessages");
		messages = JSON.parse(request.responseText);
	};

	/***************************************************************************
	 * METODI PUBBLICI
	 **************************************************************************/
	/**
	 * Costruisce il pannello della segreteria telefonica, che deve essere
	 * visualizzato all'interno del MainPanel dell'applicazione quando è
	 * selezionata la funzione corsispondente dal pannello degli strumenti. Il
	 * MessagePanel è costituito da un elemento video seguito da un 'div' che a
	 * sua volta contiene una lista di messaggi.
	 * 
	 * @param {HTMLDivElement}
	 *            il pannello che deve diventare la segreteria telefonica
	 * @returns {HTMLDivElement} pannello contenente la segreteria telefonica
	 * @author Riccardo Tresoldi
	 */
	this.createPanel = function(element) {
		var element = document.createElement("div");
		element.setAttribute("id", "MessagePanel");
		// creo elemento <video>, <audio> e <img> (nel caso non ci sia video)
		
		var header = document.createElement("h1");
		header.appendChild(document.createTextNode("Messaggi Segreteria"));
		divHeader.appendChild(header);
		element.appendChild(divHeader);
		
		var video = document.createElement("video");
		video.setAttribute("id", "messageVideo");
		video.setAttribute("controls", "controls");
		var source = document.createElement("source");
		source.setAttribute("src", "");
		source.setAttribute("type", "");
		video.appendChild(source);

		
		// creo la lista dei messaggi
		var divMessageList = document.createElement("div");
		divMessageList.setAttribute("id", "divMessage");
		var messageList = document.createElement("ul");
		messageList.setAttribute("id", "messageList");
		divMessageList.appendChild(messageList);
		this.setup();
		// appendo al this.element
		element.appendChild(video);
		element.appendChild(divMessageList);
		return element;
	};

	/**
	 * Riscarica i messaggi dal server e li aggiunge nella lista 'messageList'
	 * che è presente nel pannello MessagePanel
	 * 
	 * @author Riccardo Tresoldi
	 */
	this.setup = function() {
		getMessages();
		for ( var message in messages) {
			this.addListItem(message);
		}
	};
	
	
	/**
	 * Elimina un messaggio dalla segreteria contattando la servlet responsabile
	 * dell'operazione e scaricando nuovamente i messaggi
	 * 
	 * @param {Number}
	 *            idMessage id del messaggio che deve essere cancellato
	 *  @author Elena Zecchinato
	 */
	 this.deleteMessage = function(idMessage) {
		
		var request = new XMLHttpRequest();
			request.open("POST", commandURL, false);
			request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
			request.send("operation=deleteMessage&idMessage=" + idMessage);
			result=JSON.parse(request.responseText);
			
			if (result == true) {
	            this.setup();
	            return true;}
		
			throw "Ops... qualcosa è andato storto nel server.";
		
	};
	
	/**
	 * Rende lo stato di un messaggio "letto" oppure "non letto"
	 * 
	 * @author Riccardo Tresoldi
	 * @param {Object} message
	 *            il messaggio che deve essere modificato
	 * @param {Boolean} valueToSet
	 *            [true: "letto"] oppure [false: "non letto"]
	 * @throws {String}
	 *             un errore se il non è stato possibile cambiare lo stato del
	 *             messaggio
	 */
	this.setAsRead = function(message, valueToSet) {
		var request = new XMLHttpRequest();
		request.open("POST", commandURL, false);
		request.setRequestHeader("Content-type",
				"application/x-www-form-urlencoded");
		request.send("operation=updateMessage&idMessage=" + message.id + "&read=" + valueToSet);
		outcome = JSON.parse(request.responseText);
		if (!outcome) {
			throw "Impossibile impostare lo stato del messaggio a "
					+ (valueToSet ? " letto " : " non letto") + "!";
		}
	};
}








