/**
 *
 * @author Elena Zecchinato
 * @author Diego Beraldin
 */
function CallHistoryPanelPresenter() {
	/**********************************************************
    METODI PUBBLICI
    ***********************************************************/
    /**
     * Crea il pannello dello storico delle chiamate che deve essere
     * visualizzato all'interno del MainPanel come elemento figlio
     * 
     * @returns {HTMLDivElement} il pannello dello storico delle chiamate inizializzato
     * @author Elena Zecchinato
     */
    this.createPanel = function() {
    	var element = document.createElement("div");
    	element.setAttribute("id", "CallHistoryPanel");
        // creo contenuto di CallHistory
        var ulHistory = document.createElement('ul');
        element.appendChild(ulHistory);
        return element;
    };
}
