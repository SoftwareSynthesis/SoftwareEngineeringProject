/**
 * Presenter incaricato di gestire il pannello delle comunicazioni, siano esse
 * di natura testuale oppure di natura audio o audio/video
 * 
 * @author Elena Zecchinato
 * @author Diego Beraldin
 */
function CommunicationPanelPresenter() {
	/***************************************************************************
	 * VARIABILI PRIVATE
	 **************************************************************************/
	// array associativo di tutte le chat che ho aperte in un dato momento
	// gli elementi memorizzati in questo array sono {HTMLDivElements}
	var chatElements = new Object();

	/***************************************************************************
	 * METODI PRIVATI
	 **************************************************************************/
	/**
	 * @returns {String} il testo che deve costituire il titolo del list item
	 * @author Diego Beraldin
	 */
	function createLabel(user) {
		var title = "";
		if (user.name != null) {
			title += user.name;
		}
		if (user.surname != null) {
			if (title != "") {
				title += " ";
			}
			title += user.surname;
		}
		if (title == "") {
			title += user.email;
		}
		return title;
	}

	function createChatItem(chat) {
		var item = document.createElement("li");
		var user = chat.user;
		item.id = user.id;
		item.appendChild(document.createTextNode(createLabel(user)));
		item.onclick = function() {
			// operazione da svolgere al click su un list item
			var divChat = document.getElementById("divChat");
			var container = document.getElementById("divContainerChat");
			divChat.removeChild(container);
			divChat.appendChild(chatElements[user.id]);
		};
		return item;
	}

	/**
	 * 
	 * @param {Object}
	 *            chat oggetto che rappresenta una chat nel formato in cui sono
	 *            memorizzate nel CommunicationCenter. In particolare, ogni chat
	 *            è dotata di una proprietà 'user' che corrisponde all'utente
	 *            con cui la comunicazione è instaurata
	 * @returns {HTMLDivElement}
	 * @author Diego Beraldin
	 */
	function createChatElement(chat) {
		var element = document.createElement("div");
		element.id = "divContainerChat";
		var form = document.createElement("form");
		// crea l'area di testo
		var textArea = document.createElement("textarea");
		form.appendChild(textArea);
		// crea il campo per l'immissioen del testo
		var input = document.createElement("input");
		input.id = "text";
		input.name = "text";
		form.appendChild(input);

		// crea il pulsante
		var sendButton = document.createElement("button");
		sendButton.appendChild(document.createTextNode("Invia"));
		
		// aggancia la funzione di callback al pulsante
		sendButton.onclick = function() {
			var text = input.value;
			textArea.value += (text + "\n");
			communicationcenter.send(text);
		};
		form.appendChild(sendButton);

		element.appendChild(form);
		return element;
	}

	/***************************************************************************
	 * METODI PUBBLICI
	 **************************************************************************/
	/**
	 * 
	 * PRE: POST:
	 * 
	 * @param {Object}
	 *            chat chat testuale che deve essere aggiunta al controllo di
	 *            questo presenter
	 * @author Diego Beraldin
	 */
	this.addChat = function(chat) {
		var element = createChatElement(chat);
		var id = chat.user.id;
		chatElements[id] = element;
		var item = createChatItem(chat);
		var ulOpenChat = document.getElementById("ulOpenChat");
		ulOpenChat.appendChild(item);
	};

	/**
	 * 
	 * PRE: POST:
	 * 
	 * @param {Object}
	 *            chat
	 * @returns {Boolean}
	 * @author Diego Beraldin
	 */
	this.removeChat = function(chat) {
		var user = chat.user;
		delete chatElements[user.id];
		var ulOpenChat = document.getElementById("ulOpenChat");
		var liChat = ulOpenChat.getElementById(user.id);
		ulOpenChat.removeChild(liChat);
	};

	/**
	 * Aggiunge una stringa all'interno dell'area di testo che è associata alla
	 * chat con l'utente passato come parametro
	 * 
	 * @param {Object}
	 *            utente con cui è avviata la chat
	 * @param {String}
	 *            text testo da appendere al termine della textarea
	 * @author Diego Beraldin
	 */
	this.appendToChat = function(user, text) {
		var divContainerChat = chatElements[user.id];
		divContainerChat.childNodes[0].value += ("io:" + text + "\n");
	};

	/**
	 * Inizializza il pannello costruendone i widget grafici interni e lo
	 * restituisce in modo che possa essere inserito all'interno del pannello
	 * principale
	 * 
	 * @returns {HTMLDivElement} il 'CommunicationPanel'
	 * @author Elena Zecchinato
	 * @author Riccardo Tresoldi
	 */
	this.createPanel = function() {
		var element = document.createElement('div');
		element.setAttribute("id", "CommunicationPanel");

		// azzero il div
		element.innerHTML = "";

		// creo div contenente la chiamata vera e propria
		var divCall = document.createElement('div');
		divCall.id = "divCall";
		// creo div contenente le chat testuali
		var divChat = document.createElement('div');
		divChat.id = "divChat";

		// creo gli elementi video per la videochat
		var myVideo = document.createElement('video');
		myVideo.id = "myVideo";
		myVideo.setAttribute("autoplay", "autoplay");
		var otherVideo = document.createElement('video');
		otherVideo.id = "otherVideo";
		otherVideo.setAttribute("autoplay", "autoplay");

		// creo i bottoni per per la gestione della chiamata
		var closeButton = document.createElement('button');
		closeButton.type = "button";
		closeButton.id = "closeButton";
		closeButton.onclick = communicationcenter.endCall;

		// appendo i child al divCall
		divCall.appendChild(myVideo);
		divCall.appendChild(otherVideo);
		divCall.appendChild(closeButton);

		// creo gli elementi per la chat testuale
		// creo l'<ul> per le schede delle chat aperte
		var ulOpenChat = document.createElement('ul');
		ulOpenChat.id = "ulOpenChat";

		// crea gli HTMLDivElement con il testo della chat
		for ( var chat in communicationCenter.openChat) {
			// appende l'elemento all'array associativo chatElements
			chatElements[chat.id] = createChatElement(chat);
		}

		// popola la lista delle chat
		for ( var chat in communicationcenter.openChat) {
			var item = createChatItem(chat);
			ulOpenChat.appendChild(item);
		}

		// creo il div per la visualizzazione della chat selezionata.
		// var divContainerChat = document.createElement('div');
		// divContainerChat.id = "divContainerChat";

		// appendo la lista al <div> della chat
		divChat.appendChild(ulOpenChat);
		// divChat.appendChild(divContainerChat);

		// apendo il sottoalbero al DOM
		element.appendChild(divCall);
		element.appendChild(divChat);

		return element;
	};

}
