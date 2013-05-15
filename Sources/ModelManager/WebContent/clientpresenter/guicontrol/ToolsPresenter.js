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
    var thisPresenter = this;
    var thisPanel;

    /**********************************************************
     METODI PRIVATI
     ***********************************************************/
    /**
     * Funzione per gestire l'evento in cui viene visualizzato il pannello degli
     * strumenti
     * @author Riccardo Tresoldi
     */
    function onShowToolsPanel() {
        mediator.getView('tools');
    }

    /**
     * Funzione per gestire l'evento in cui viene rimosso il pannello degli
     * strumenti
     * @author Riccardo Tresoldi
     */
    function onRemoveToolsPanel() {
        thisPresenter.destroy();
    }

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
    /** VIEW
     * Distruttore del pannello
     * @author Riccardo Tresoldi
     */
    this.destroy = function() {
        var thisPanelParent = thisPanel.parentElement.parentElement;
        thisPanelParent.removeChild(thisPanel.parentElement);
    };

    /** VIEW
     * Inizializza il pannello degli strumenti dell'applicazione
     *
     * @author Elena Zecchinato
     * @author Diego Beraldin
     */
    this.initialize = function(view) {

        // posiziona il pannello sulla pagina
        var dummyDiv = document.createElement("div");
        document.body.insertBefore(dummyDiv, document.getElementsByTagName("footer")[0]);
        dummyDiv.innerHTML = view.outerHTML;

        //salvo un riferimento all'elemento DOM appena creato
        thisPanel = document.getElementById("ToolsPanel");

        var ulFunction = document.getElementById("ToolsList");

        // funzione messaggi
        document.getElementById("liAnswering").onclick = function() {
            mediator.displayMessagePanel();
        };

        // funzione impostazioni account
        document.getElementById("liSetting").onclick = function() {
            mediator.displayAccountSettingsPanel();
        };

        // funzione lista chiamate
        document.getElementById("liCallList").onclick = function() {
            mediator.displayCallHistoryPanel();
        };

        // funzione gestione contatti
        document.getElementById("liGroup").onclick = function() {
            mediator.displayGroupPanel();
        };

        // funzione di ricerca
        document.getElementById("liSearch").onclick = function() {
            mediator.displaySearchResultPanel();
        };

        // possibilità di effettuare il logout
        document.createElement("liLogout").onclick = function() {
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

        //inizializzo la select del
        this.initializeSelectState();
    };

    /**
     * Rende invisibile il pannello degli strumenti
     *
     * @author Diego Beraldin
     */
    this.hide = function() {
        thisPanel.style.display = "none";
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

    /***************************************************************************
     * LISTNER DEGLI EVENTI
     **************************************************************************/
    document.addEventListener("showToolsPanel", function(evt) {
        onShowToolsPanel();
    });
    document.addEventListener("removeToolsPanel", function(evt) {
        onRemoveToolsPanel();
    });
}