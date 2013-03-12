/**
 *
 * @author Elena Zecchinato
 * @author Diego Beraldin
 */
function CallHistoryPanelPresenter() {
    //elemento controllato da questo presenter
    //this.element = document.getElementById("CallHistoryPanel");

    /**
     * Crea il pannello dello storico delle chiamate che deve essere
     * visualizzato all'interno del MainPanel come elemento figlio
     * 
     * @returns {HTMLDivElement}
     * @author Elena Zecchinato
     */
    this.createPanel = function() {
    	var element = document.createElement("div");
        // creo contenuto di CallHistory
        var ulHistory = document.createElement('ul');

        //apendo il sottoalbero al MainPanel
        element.innerHTML = "";
        element.appendChild(ulHistory);
        return element;
    };
}
