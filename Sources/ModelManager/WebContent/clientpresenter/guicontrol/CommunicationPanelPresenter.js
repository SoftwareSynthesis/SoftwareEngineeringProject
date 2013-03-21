/**
 * Presenter incaricato di gestire il pannello delle comunicazioni,
 * siano esse di natura testuale oppure di natura audio o audio/video
 *
 * @author Elena Zecchinato
 * @author Diego Beraldin
 */
function CommunicationPanelPresenter() {
    /**********************************************************
    VARIABILI PRIVATE
    ***********************************************************/
	// array associativo di tutte le chat che ho aperte in un dato momento
    // gli elementi memorizzati in questo array sono {HTMLDivElements}
	var chatElements = new Object();
	
    /**********************************************************
    METODI PRIVATI
    ***********************************************************/
	/**
	 * 
	 */
	function populateChatUl(ul) {
		for (var chat in communicationcenter.openChat) {
			var item = document.createElement("li");
			// costruisce il list item
			var user = chat.user;
			item.id = user.id;
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
			item.appendChild(document.createTextNode(title));
			item.onclick = function() {
				// operazione da svolgere al click su un list item
				var divChat = document.getElementById("divChat");
				var  divContainerChat = document.getElementById("divContainerChat");
				divChat.removeChild(divContainerChat);
				divChat.appendChild(chatElements[user.id]);
			};
			ul.appendChild(item);
		}
	}
	
    /**********************************************************
     METODI PUBBLICI
     ***********************************************************/
	
	/**
	 * Aggiunge una stringa all'interno dell'area di testo che è 
	 * associata alla chat con l'utente passato come parametro
	 * 
	 * @param {Object} utente con cui è avviata la chat
	 * @param {String} text testo da appendere al termine della textarea
	 * @author Diego Beraldin
	 */
	this.appendToChat = function(user, text) {
		var divContainerChat = chatElements[user.id];
		divContainerChat.childNodes[0].value += ("io:" + text + "\n");
	};
	
    /**
     * Inizializza il pannello costruendone i widget grafici interni e lo
     * restituisce in modo che possa essere inserito all'interno del pannello principale
     *
     * @returns {HTMLDivElement} il 'CommunicationPanel'
     * @author Elena Zecchinato
     * @author Riccardo Tresoldi
     */
    this.createPanel = function() {
        var element = document.createElement("div");
        element.setAttribute("id", "CommunicationPanel");

        //azzero il div
        element.innerHTML = "";

        //creo div contenente la chiamata vera e propria
        var divCall = document.createElement('div');
        divCall.id = "divCall";
        //creo div contenente le chat testuali
        var divChat = document.createElement('div');
        divChat.id = "divChat";

        //creo gli elementi video per la videochat
        var myVideo = document.createElement('video');
        myVideo.id = "myVideo";
        myVideo.setAttribute("autoplay", "autoplay");
        var otherVideo = document.createElement('video');
        otherVideo.id = "otherVideo";
        otherVideo.setAttribute("autoplay", "autoplay");
        
        //creo i bottoni per per la gestione della chiamata
        var closeButton = document.createElement('button');
        closeButton.type = "button";
        closeButton.id = "closeButton";
        closeButton.onclick = communicationcenter.endCall;

        //appendo i child al divCall
        divCall.appendChild(myVideo);
        divCall.appendChild(otherVideo);
        divCall.appendChild(closeButton);

        //creo gli elementi per la chat testuale
        //creo l'<ul> per le schede delle chat aperte
        var ulOpenChat = document.createElement('ul');
        ulOpenChat.id = "ulOpenChat";
        
        //crea gli HTMLDivElement con il testo della chat
		for (var chat in communicationCenter.openChat) {
			var element = document.createElement("div");
			var form = document.createElement("form");
			element.id = "divContainerChat";
			var textArea = document.createElement("textarea");
			element.appendChild(textArea);
			
			var input = document.createElement("input");
			input.id = "text";
			input.name = "text";
			
			// appende l'elemento all'array associativo chatElements
			chatElements[chat.id] = element;
		}
		
        //popola la lista delle chat
        populateChatUl(ulOpenChat);
        
        //creo il div per la visualizzazione della chat selezionata.
//        var divContainerChat = document.createElement('div');
//        divContainerChat.id = "divContainerChat";

        //appendo la lista  al <div> della chat
        divChat.appendChild(ulOpenChat);
//        divChat.appendChild(divContainerChat);

        //apendo il sottoalbero al DOM
        element.appendChild(divCall);
        element.appendChild(divChat);

        return element;
    };
    
    
    
}
