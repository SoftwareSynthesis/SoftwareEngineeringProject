/**
 *
 * @author Elena Zecchinato
 * @author Diego Beraldin
 * @param mediator riferimento al mediatore che gestisce la collaborazione fra i presenter
 */
function CommunicationPanelPresenter(mediator) {
    //FIXME please! Questo ci è stato vietato da ricCARDINo
    this.mediator = mediator;
    //elemento controllato da questo presenter
    this.element = document.getElementById("CommunicationPanel");

    /**
     *
     * @author Elena Zecchinato
     */
    this
    initialize = function() {
        //azzero il div
        this.element.innerHTML = "";
        //creo div contenente la chiamata vera e propria
        var divProfile = document.createElement('div');
        divProfile.setAttribute("id", "divProfile");
        //creo div contenente le chat testuali
        var divChat = document.createElement('div');
        divProfile.setAttribute("id", "divProfile");

        //apendo il sottoalbero al DOM
        this.element.appendChild(divProfile);
        this.element.appendChild(divChat);
    };
}
