/**
 * Presenter incaricato di gestire il pannello degli strumenti
 *
 * @author Elena Zecchinato
 * @author Diego Beraldin
 */
function ToolsPanelPresenter() {
    //elemento controllato da questo presenter
    this.element = document.getElementById("ToolsPanel");

    /**
     * @author Elena Zecchinato
     */
    this.initialize = function() {
    	// mi rendo visibile
    	this.element.style.display = "block";
        //creo i due div principali
        var divState = document.createElement('div');
        var divFunction = document.createElement('div');

        //creo contenuto divFunction
        var ulFunction = document.createElement('ul');
        select.setAttribute("id", "ToolsList");

        var liAnswering = document.createElement('li');
        var liSetting = document.createElement('li');
        var liCallList = document.createElement('li');
        var liLanguage = document.createElement('li');

        //creo contenuto divState
        var state = document.createElement('select');
        select.setAttribute("state", "selectState");

        //appendo i sottonodi ai nodi principali
        ulFunction.appendChild(liAnswering);
        ulFunction.appendChild(liSetting);
        ulFunction.appendChild(liCallList);
        ulFunction.appendChild(liLanguage);

        divFunction.appendChild(ulFunction);
        divState.appendChild(state);

        //appendo il sottoalbero al DOM
        this.element.innerHTML = "";
        this.element.appendChild(divFunction);
        this.element.appendChild(divState);
    };
    
    /**
     * Rende invisibile il pannello degli strumenti
     * 
     * @author Diego Beraldin
     */
    this.hide = function() {
    	this.element.style.display = "none";
    };
    
/*
 * TODO:
 * - implementare la ricerca degli utenti sul sistema
 * - aggiunta utente [resultpannel->mediator->aBpresenter]
 */    
}
