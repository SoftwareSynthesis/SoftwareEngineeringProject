/**
 * Presenter incaricato di gestire il pannello degli strumenti
 *
 * @author Elena Zecchinato
 * @author Diego Beraldin
 * @param mediator  riferimento al mediatore che gestisce la collaborazione fra i presenter
 */
function ToolsPanelPresenter(mediator) {
    //FIXME please! Questo ci è stato vietato da ricCARDINo
    this.mediator = mediator;
    //elemento controllato da questo presenter
    this.element = document.getElementById("ToolsPanel");

    /**
     * TODO: da riguardare
     * @author Elena Zecchinato
     */
    this.initialize = function() {
        //creo i due div principali
        var divState = document.createElement('div')
        var divFunction = document.createElement('div');

        //creo contenuto divFunction
        var ulFuntion = document.createElement('ul');
        select.setAttribute("id", "ToolsList");

        var liAnswering = document.createElement('li')
        var liSetting = document.createElement('li')
        var liCallList = document.createElement('li')
        var liLanguage = document.createElement('li')

        //creo contenuto divState
        var state = document.createElement('select');
        select.setAttribute("state", "selectState");

        //appendo i sottonodi ai nodi principali
        ulFunction.appendChild(liAnswering);
        ulFunction.appendChild(liSetting);
        ulFunction.appendChild(liCallist);
        ulFunction.appendChild(liLanguage);

        divFunction.appendChild(ulFunction);
        divState.appendChild(state);

        //appendo il sottoalbero al DOM
        this.element.innerHTML = "";
        this.element.appendChild(divFunction);
        this.element.appendChild(divState);
    };
}
