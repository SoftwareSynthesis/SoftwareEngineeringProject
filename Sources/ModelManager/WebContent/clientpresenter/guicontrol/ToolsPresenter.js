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
    element.innerHTML = "";

    /**********************************************************
     METODI PRIVATI
     ***********************************************************/
    /**
     * funzione per l'inizializzazione della select che gestisce il cambio di
     * stato
     *
     * @author Riccardo Tresoldi
     * @return {HTMLSelectElement} l'elemento HTML select che rappresenta lo
     * stato dell'utente
     */
    function initializeSelectState() {
        //ottengo l'elemento dal DOM
        var selectState = document.getElementById("selectState");
        //Controllo che non sia già stato inizzializzato
        if (selectState.length != 0)
            //se è già stato inizializato lo ritorno senza aggiungere altre
            // <option>
            return selectState;
        //Se arivo qua vuol dire che non è ancora stata inizializzata e dunque lo
        // inizializzo e aggiungo gli <option> adeguati
        selectState.add(new Option("Disponibile", "available"), null);
        selectState.add(new Option("Occupato", "occupied"), null);
        //aggancio l'evento onChange
        selectState.onChange = function() {
            ToolsPanelPresenter.updateStateValue();
        }
        //ritorno l'elemento <select> inizializzato
        return selectState;
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
        ulFunction.appendChild(liSearch);
        
        // possibilità di effettuare il logout
        var ulLogout = document.createElement("ul");
        var liLogout = document.createElement("li");
        liLogout.appendChild(document.createTextNode("Logout"));
        var self = this;
        liLogout.onclick = function() {
        	var answer = confirm("Sei sicuro di voler uscire?");
    		if (answer) {
    			// effettua la disconnessione dal server
    			self.logout();
    			// ricrea le variabili globali e azzera la UI
    			mediator = new PresenterMediator();
    			communicationcenter = new communicationCenter();
    			// ricostruisce il form di login
    			mediator.buildLoginUI();
    		}
        };
        ulLogout.appendChild(liLogout);
        
        divFunction.appendChild(ulFunction);
        divFunction.appendChild(ulLogout);

        // contenuto del '<div>' per gli stati dell'utente
        var divState = document.createElement("div");
        var state = document.createElement("select");
        state.id = "selectState";
        this.initializeSelectState();
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
	 * Effettua il logout comunicandolo alla servlet e chiudendo il canale di
	 * comunicazione che era stato aperto con il server
	 * 
	 * @author Riccardo Tresoldi
	 */
    this.logout = function() {
		communicationcenter.disconnect();
		var request = new XMLHttpRequest();
		request.open("POST", commandURL, false);
		request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		request.send("operation=logout");
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
        };
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
    	if (liCommunication) {
    		ulFunctions.removeChild(liCommunication);
    	}
    };

    /**
     * Imposta il valore della select che contiene lo stato dell'utente al valore
     * corretto
     *
     * @author Riccardo Tresoldi
     *
     * @return {HTMLSelectElement} l'elemento HTML select che rappresenta lo
     * stato dell'utente
     */
    this.updateStateValue = function() {
       //ottengo la select
        var selectState = initializeSelectState();
        //ottengo il valore corrente della select
        var currentValue = selectState.options[selectedIndex].value;
        //Inviare il messaggio con websoket;
        communicationcenter.changeState(currentValue);
        return selectState;
    };
}
