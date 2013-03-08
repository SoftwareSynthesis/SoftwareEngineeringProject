/**
 *
 * @author Elena Zecchinato
 * @author Diego Beraldin
 */
function CommunicationPanelPresenter(mediator) {
    //elemento controllato da questo presenter
    this.element = document.getElementById("CommunicationPanel");

    /**
     *
     * @author Elena Zecchinato
     */
    this.initialize = function() {
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
