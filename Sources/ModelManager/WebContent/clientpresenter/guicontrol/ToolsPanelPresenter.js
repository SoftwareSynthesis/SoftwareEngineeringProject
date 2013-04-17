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
    // URL delle servlet
    var servlets = new Array();
    getServletURLs();

    /**********************************************************
     METODI PRIVATI
     ***********************************************************/
    function getServletURLs() {
        var configurationRequest = new XMLHttpRequest();
        configurationRequest.open("POST", configurationFile, false);
        configurationRequest.send();
        var XMLDocument = configurationRequest.responseXML;
        var baseURL = XMLDocument.getElementsByTagName("baseURL")[0].childNodes[0].data;
        var name = XMLDocument.getElementById("close").childNodes[0].data;
        servlets.push(baseURL + name);
    }

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
        element.innerHTML = "";
        // imposta il pannello come visibile
        element.style.display = "block";

        //header
        var header = document.createElement("h1");
        header.appendChild(document.createTextNode("STRUMENTI"));

        // contenuto del '<div>' delle funzionalità
        var divFunction = document.createElement("div");
        var ulFunction = document.createElement("ul");
        ulFunction.id = "ToolsList";

        // funzione messaggi
        var liAnswering = document.createElement("li");
        liAnswering.appendChild(document.createTextNode("Segreteria"));
        liAnswering.onclick = function() {
            mediator.displayMessagePanel();
        };

        // funzione impostazioni account
        var liSetting = document.createElement("li");
        liSetting.appendChild(document.createTextNode("Impostazioni"));
        liSetting.onclick = function() {
            mediator.displayAccountSettingsPanel();
        };

        // funzione lista chiamate
        var liCallList = document.createElement("li");
        liCallList.appendChild(document.createTextNode("Lista chiamate"));
        liCallList.onclick = function() {
            mediator.displayCallHistoryPanel();
        };

        // funzione gestione contatti
        var liGroup = document.createElement("li");
        liGroup.appendChild(document.createTextNode("Gruppi"));
        liGroup.onclick = function() {
            mediator.displayGroupPanel();
        };

        // funzione selezione lingua
        /*var liLanguage = document.createElement("li");
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
        //attaccare il comportamento all'elemento '<select>'
        selectLanguage.onchange = function() {
        };*/
        
        // funzione di ricerca
        var liSearch = document.createElement("li");
        liSearch.appendChild(document.createTextNode("Ricerca"));
        liSearch.onclick = function() {
        	mediator.displaySearchResultPanel();
        };

        // costruisce la lista aggiungendo tutti gli elementi
        ulFunction.appendChild(liAnswering);
        ulFunction.appendChild(liSetting);
        ulFunction.appendChild(liCallList);
        ulFunction.appendChild(liGroup);
        //ulFunction.appendChild(liLanguage);
        ulFunction.appendChild(liSearch);
        divFunction.appendChild(ulFunction);

        // contenuto del '<div>' per gli stati dell'utente
        var divState = document.createElement("div");
        var state = document.createElement("select");
        state.id = "selectState";
        divState.appendChild(state);

        // aggiunge il sottoalbero al DOM dell'elemento controllato
        element.appendChild(header);
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

    /**
     * Effettua il logout comunicandolo alla servlet
     *
     * @author Riccardo Tresoldi
     */
    this.logout = function() {
    	communicationcenter.disconnect();
        var request = new XMLHttpRequest();
        request.open("POST", servlets[0], false);
        request.send();
        var result = JSON.parse(request.responseText);
        if (!result) {
            alert("Ops... qualcosa &egrave; andato storto nel server!");
        }

    };
    
    /**
     * Aggiunge il pulsante che permette di ritornare al pannello delle
     * comunicazioni, se ve ne sono di attive
     * 
     * @author Diego Beraldin
     */
    this.addCommunicationFunction = function() {
    	// crea il nuovo elemento della lista
        var liCommunication = document.createElement("li");
        liCommunication.id = "CallFunction";
        liCommunication.appendChild(document.createTextNode("Chiamata"));
        liCommunication.onclick = function() {
        	mediator.displayCommunicaionPanel();
        }
        
        // lo aggiunge in coda alla lista
        var ulFunctions = document.getElementById("ToolsList");
        ulFunctions.appendChild(liCommunication);
    };
    
    /**
     * Nasconde il pulsante che permette di ritornare al pannello
     * delle comunicazioni dal ToolsPanel
     * 
     * @author Diego Beraldin
     */
    this.removeCommunicationFunction = function() {
    	var ulFunctions = document.getElementById("ToolsList");
    	var liCommunication = document.getElementById("CallFunction");
    	ulFunctions.removeChild(liCommunication);
    };
}
