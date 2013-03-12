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
     * @param {HTMLDivElement} element	'<div>' destinato a diventare il pannello dello storico delle chiamate
     * @returns {HTMLDivElement} il pannello dello storico delle chiamate inizializzato
     * @author Elena Zecchinato
     */
    this.createPanel = function(element) {
        // creo contenuto di CallHistory
        var ulHistory = document.createElement('ul');
        element.appendChild(ulHistory);
        return element;
    };
}
