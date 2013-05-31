/**
 * Presenter incaricato di gestire lo storico delle chiamate
 * 
 * @author Elena Zecchinato
 * @author Diego Beraldin
 */
function CallHistoryPanelPresenter() {
	/***************************************************************************
	 * VARIABILI PRIVATE
	 **************************************************************************/
	var thisPresenter = this;
	var thisPanel;
	// array (enumerativo) delle chiamate scaricate dal server
	var calls = new Array();

	/***************************************************************************
	 * METODI PRIVATI
	 **************************************************************************/
	/** PRESENTER
	 * Scarica la lista delle chiamate che sono state effettuate dall'utente
	 * associato a questo client
	 * 
	 * @returns {Object} la lista delle chiamate ottenuta dal server
	 * @author Diego Beraldin
	 */
	function getCalls() {
		var request = new XMLHttpRequest();
		request.open("POST", commandURL, false);
		request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		request.send("operation=getCalls");
		return JSON.parse(request.responseText);
	}

	/** VIEW
	 * Aggiunge alla lista delle chiamate visualizzata nel CallHistoryPanel una
	 * nuova voce che corrirsponde alla chiamata impostata come parametro. Ogni
	 * voce della lista contiente i dati relativi alla chiamata e non consente
	 * di effettuare alcuna azione, ragion per cui non presenta associato nessun
	 * gestore di eventi (es. onclick).
	 * 
	 * @param {Object}
	 *            call oggetto che corrisponde a 'JSCall' e rappresenta una
	 *            chiamata nello storico delle chiamate di un utente. Le
	 *            chiamate sono caratterizzate dall'ID dell'altro utente coinvolto
	 *            (id), dall'orario di inizio (start) e da un flag booleano (caller)
	 *            che determina se la chiamata è in uscita oppure in ingresso
	 * @author Diego Beraldin
	 */
	function addListItem(call) {
		var list = document.getElementById("ulHistory");
		var item = document.createElement("li");
		var contact = mediator.getContactById(call.id);
		var contactName = mediator.createNameLabel(contact);
		var text = document.createTextNode(contactName + " " + call.start);
		if (call.caller) {
			item.className = "outcome";
		} else {
			item.className = "income";
		}		
		item.appendChild(text);
		list.appendChild(item);
	}

	/***************************************************************************
	 * METODI PUBBLICI
	 **************************************************************************/
    /** PRESENTER
	 * Scarica la lista delle chiamate dal server, quindi aggiunge alla lista
	 * delle chiamate contenuta all'interno del CallHistoryPanel
	 * 
	 * @author Diego Beraldin
	 */
	this.displayList = function() {
		if (thisPanel) {
			calls = getCalls();
			for (var call in calls) {
				addListItem(calls[call]);
			}
		}
	};
	
	/** VIEW
	 * Richiama il metodo che costruisce il pannello
	 * 
	 * @author Diego Beraldin
	 */
	this.display = function() {
		thisPanel = document.getElementById("CallHistoryPanel");
		thisPresenter.displayList();
	};
	
	/***************************************************************************
     * HANDLER DEGLI EVENTI
     **************************************************************************/
	/** PRESENTER
     * Funzione per gestire l'evento in cui viene visualizzato il pannello dello
     * storico chiamate
     * @author Riccardo Tresoldi
     */
    function onShowCallHistoryPanel() {
        mediator.getView('callHistory');
    }
    
	/***************************************************************************
     * LISTENER DEGLI EVENTI
     **************************************************************************/
    document.addEventListener("showCallHistoryPanel", onShowCallHistoryPanel);
}
