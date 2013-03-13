/**
 *
 * @author Elena Zecchinato
 * @author Diego Beraldin
 */
function CommunicationPanelPresenter(mediator) {
    /**
     *
     * @author Elena Zecchinato
     */
    this.createPanel = function() {
    	var element = document.createElement("div");
    	element.setAttribute("id", "CommunicationPanel");
    	
        //azzero il div
        this.element.innerHTML = "";
        //creo div contenente la chiamata vera e propria
        var divCall = document.createElement('div');
        divCall.setAttribute("id", "divCall");
        //creo div contenente le chat testuali
        var divChat = document.createElement('div');
        divChat.setAttribute("id", "divChat");

        //apendo il sottoalbero al DOM
        element.appendChild(divCall);
        element.appendChild(divChat);
        
        return element;
    };
}
