/**
 *
 * @author Elena Zecchinato
 * @author Diego Beraldin
 */
function CallHistoryPanel() {
    //elemento controllato da questo presenter
    this.element = document.getElementById("CallHistoryPanel");

    /**
     * @author Elena Zecchinato
     */
    this.initialize = function() {
        //creo contenuto di CallHistory
        var ulHistory = document.createElement('ul');

        //apendo il sottoalbero al MainPanel
        this.element.innerHTML = "";
        this.element.appendChild(ulHistory);
    };
}
