/**
 * Presenter incaricato di gestire il pannello principale
 *
 * @author Elena Zecchinato
 * @author Diego Beraldin
 */
function MainPanelPresenter() {
    //elemento controllato da questo presenter
    this.element = document.getElementById("MainPanel");

    /**
     * Costruisce il pannello principale dell'applicazione che occupa il posto
     * centrale della finestra
     * 
     * @author Elena Zecchinato
     */
    this.initialize = function() {
        this.element.innerHTML = "";
        //immagine di spondo per quando il pannello è vuoto
        var img = document.createElement("img");
        img.setAttribute("src", "img/mytalk.png");

        this.element.appendChild(img);
    };
    
    /**
     * Visualizza un elemento interno al pannello principale
     * 
     * @param node 'div' che deve essere visualizzato all'interno del pannello principale
     * @author Diego Beraldin
     */
    this.displayChildPanel = function(node) {
    	this.element.innerHTML = "";
    	this.element.appendChild(node);
    };
}
