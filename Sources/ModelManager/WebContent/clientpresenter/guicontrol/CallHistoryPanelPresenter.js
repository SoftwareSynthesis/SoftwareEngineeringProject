/**
 * Presenter incaricato di gestire lo storico delle chiamate
 * 
 * @param {String}
 *            url URL della servlet con cui il presenter deve comunicare
 * @author Elena Zecchinato
 * @author Diego Beraldin
 */
function CallHistoryPanelPresenter(url) {
	/***************************************************************************
	 * VARIABILI PRIVATE
	 **************************************************************************/
	// URL delle servlet con cui deve relazionarsi questo presenter
	var servlets = new Array();
	// configura gli indirizzi delle servlet
	getServletURLs();
	// array (enumerativo) delle chiamate scaricate dal server
	var calls = new Array();

	/***************************************************************************
	 * METODI PRIVATI
	 **************************************************************************/
	/**
	 * Configura gli indirizzi delle servlet con cui il presenter ha l'esigenza
	 * di comunicare per svolgere le proprie operazioni
	 * 
	 * @author Diego Beraldin
	 */
	function getServletURLs() {
		var configurationRequest = new XMLHttpRequest();
		configurationRequest.open("POST", configurationFile, false);
		configurationRequest.send();
		var XMLDocument = configurationRequest.responseXML;
		var baseURL = XMLDocument.getElementsByTagName("baseURL")[0].childNodes[0].data;
		var name = XMLDocument.getElementById("getcalls").childNodes[0].data;
		servlets.push(baseURL + name);
	}

	/**
	 * Scarica la lista delle chiamate che sono state effettuate dall'utente
	 * associato a questo client
	 * 
	 * @returns {Object} la lista delle chiamate ottenuta dal server
	 * @author Diego Beraldin
	 */
	function getCalls() {
		var request = new XMLHttpRequest();
		request.open("POST", servlets[0], false);
		request.send();
		return JSON.parse(request.responseText);
	}

	/**
	 * Aggiunge alla lista delle chiamate visualizzata nel CallHistoryPanel una
	 * nuova voce che corrirsponde alla chiamata impostata come parametro. Ogni
	 * voce della lista contiente i dati relativi alla chiamata e non consente
	 * di effettuare alcuna azione, ragion per cui non presenta associato nessun
	 * gestore di eventi (es. onclick).
	 * 
	 * @param {Object}
	 *            call oggetto che corrisponde a 'JSCall' e rappresenta una
	 *            chiamata nello storico delle chiamate di un utente. Le
	 *            chiamate sono caratterizzate dall'altro utente coinvolto
	 *            (user) l'orario di inizio (startdate) e l'orario di fine
	 *            (enddate)
	 * @author Diego Beraldin
	 */
	function addListItem(call) {
		var list = document.getElementById("ulHistory");
		var item = document.createElement("li");
		// TODO probabilmente da modificare in futuro
		var text = document.createTextNode(call.name + " " + call.start
				+ " " + call.end);
		item.appendChild(text);
		list.appendChild(item);
	}

	/***************************************************************************
	 * METODI PUBBLICI
	 **************************************************************************/
	/**
	 * Crea il pannello dello storico delle chiamate che deve essere
	 * visualizzato all'interno del MainPanel come elemento figlio
	 * 
	 * @returns {HTMLDivElement} il pannello dello storico delle chiamate
	 *          inizializzato
	 * @author Elena Zecchinato
	 */
	this.createPanel = function() {
		var element = document.createElement("div");
		element.setAttribute("id", "CallHistoryPanel");
		// creo contenuto di CallHistory
		
		var divHeader = document.createElement("div");
	    divHeader.className = "panelHeader";
	    var header = document.createElement("h1");
	    header.appendChild(document.createTextNode("Storico Chiamate"));
	    divHeader.appendChild(header);
	    element.appendChild(divHeader);
		
		
		var ulHistory = document.createElement('ul');
		ulHistory.id = "ulHistory";
		element.appendChild(ulHistory);
		this.setup();
		return element;
	};

	/**
	 * Scarica la lista delle chiamate dal server, quindi aggiunge alla lista
	 * delle chiamate contenuta all'interno del CallHistoryPanel
	 * 
	 * @author Diego Beraldin
	 */
	this.setup = function() {
		calls = getCalls();
		for (var call in calls) {
			addListItem(call);
		}
	};
}
