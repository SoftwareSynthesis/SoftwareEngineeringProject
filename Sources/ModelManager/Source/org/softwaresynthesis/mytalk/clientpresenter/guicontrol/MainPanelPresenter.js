/**
 * Presenter incaricato di gestire il pannello principale
 *
 * @author Elena Zecchinato
 * @author Diego Beraldin
 * @param mediator  riferimento al mediatore che gestisce la collaborazione fra i presenter
 */
function MainPanelPresenter(mediator) {
    //FIXME please! Questo ci è stato vietato da ricCARDINo
    this.mediator = mediator;
    //elemento controllato da questo presenter
    this.element = document.getElementById("MainPanel");

    /**
     * @author Elena Zecchinato
     */
    this.initialize = function() {
        this.element.innerHTML = "";
        //immagine di spondo per MainPannel vuoto
        var img = document.createElement("img");
        //FIXME: Sistema l'SRC dell'immagine
        img.setAttribute("src", "");

        this.element.appendChild(img);
    };
}
