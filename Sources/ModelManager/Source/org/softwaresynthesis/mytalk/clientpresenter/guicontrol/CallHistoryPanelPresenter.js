/**
 *
 * @author Elena Zecchinato
 * @author Diego Beraldin
 * @param mediator  riferimento al mediatore che gestisce la collaborazione fra i presenter
 */
function CallHistoryPanel(mediator) {
    //FIXME please! Questo ci è stato vietato da ricCARDINo
    this.mediator = mediator;
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
        this.element.appendChild(ulHisotry);
    };
}
