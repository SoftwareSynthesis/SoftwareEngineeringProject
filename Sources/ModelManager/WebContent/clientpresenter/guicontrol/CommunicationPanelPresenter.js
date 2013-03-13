/**
 *
 * @author Elena Zecchinato
 * @author Diego Beraldin
 */
function CommunicationPanelPresenter(mediator) {
    /**
     * Presenter incaricato di gestire il pannello delle comunicazioni,
     * siano esse di natura testuale oppure di natura audio o audio/video
     *
     * @returns {HTMLDivElement} il 'CommunicationPanel'
     * @author Elena Zecchinato
     */
    this.createPanel = function() {
    	var element = document.createElement("div");
    	element.setAttribute("id", "CommunicationPanel");
    	
        //azzero il div
        this.element.innerHTML = "";
        //creo div contenente la chiamata vera e propria
        var divProfile = document.createElement('div');
        divProfile.setAttribute("id", "divProfile");
        //creo div contenente le chat testuali
        var divChat = document.createElement('div');
        divProfile.setAttribute("id", "divProfile");

        //apendo il sottoalbero al DOM
        element.appendChild(divProfile);
        element.appendChild(divChat);
        
        return element;
    };
}
