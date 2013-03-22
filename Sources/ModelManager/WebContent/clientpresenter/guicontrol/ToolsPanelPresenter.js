/**
 * Presenter incaricato di gestire il pannello degli strumenti
 *
 * @constructor
 * @this {ToolsPanelPresenter}
 * @author Elena Zecchinato
 * @author Diego Beraldin
 */
function ToolsPanelPresenter() {
    /**********************************************************
    VARIABILI PRIVATE
    ***********************************************************/
    //elemento controllato da questo presenter
    var element = document.getElementById("ToolsPanel");
    
    /**********************************************************
    METODI PRIVATI
    ***********************************************************/

    /**********************************************************
    METODI PUBBLICI
    ***********************************************************/
    /**
     * Inizializza il pannello degli strumenti dell'applicazione
     * 
     * @author Elena Zecchinato
     * @author Diego Beraldin
     */
    this.initialize = function() {
    	// imposta il pannello come visibile
    	element.style.display = "block";
        

        // contenuto del '<div>' delle funzionalità
        var divFunction = document.createElement("div");
        var ulFunction = document.createElement("ul");
        ulFunction.id="ToolsList";
        
        // funzione messaggi
        var liAnswering = document.createElement("li");
        var buttonAnswering = document.createElement("button");
        buttonAnswering.appendChild(document.createTextNode("Segreteria"));
        buttonAnswering.onclick = function() {
        	mediator.displayMessagePanel();
        };
        liAnswering.appendChild(buttonAnswering);
        
        // funzione impostazioni account
        var liSetting = document.createElement("li");
        var buttonSetting = document.createElement("button");
        buttonSetting.appendChild(document.createTextNode("Impostazioni"));
        buttonSetting.onclick = function() {
        	mediator.displayAccountSettingsPanel();
        };
        liSetting.appendChild(buttonSetting);
        
        // funzione lista chiamate
        var liCallList = document.createElement("li");
        var buttonCallList = document.createElement("button");
        buttonCallList.appendChild(document.createTextNode("Lista chiamate"));
        buttonCallList.onclick = function() {
        	mediator.displayCallHistoryPanel();
        };
        liCallList.appendChild(buttonCallList);
        
        // funzione gestione contatti
        //TODO aggiungere amministrazione dei gruppi
        
        // funzione selezione lingua
        var liLanguage = document.createElement("li");
        var selectLanguage = document.createElement("select");
        var optionItalian = document.createElement("option");
        optionItalian.setAttribute("value", "italian");
        optionItalian.appendChild(document.createTextNode("Italiano"));
        var optionEnglish = document.createElement("option");
        optionEnglish.setAttribute("value", "english");
        optionEnglish.appendChild(document.createTextNode("Inglese"));
        selectLanguage.appendChild(optionItalian);
        selectLanguage.appendChild(optionEnglish);
        liLanguage.appendChild(selectLanguage);
        // TODO attaccare il comportamento all'elemento '<select>'
        selectLanguage.onchange = function() {};
        
        // costruisce la lista aggiungendo tutti gli elementi
        ulFunction.appendChild(liAnswering);
        ulFunction.appendChild(liSetting);
        ulFunction.appendChild(liCallList);
        ulFunction.appendChild(liLanguage);
        divFunction.appendChild(ulFunction);

        // contenuto del '<div>' per gli stati dell'utente
        var divState = document.createElement("div");
        var state = document.createElement("select");
        select.setAttribute("state", "selectState");
        divState.appendChild(state);

        // aggiunge il sottoalbero al DOM dell'elemento controllato
        element.appendChild(divState);
        element.appendChild(divFunction);
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
