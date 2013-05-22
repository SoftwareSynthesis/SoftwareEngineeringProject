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
     * Funzione che mostra il pulsante per "Tornare al Comunication Panel"
     * @author Riccardo Tresoldi
     */
    function onShowReturnToCommunicationPanelButton() {
        alert("Torna a CommPanel creato!");
    }

    /**
     * funzione per l'inizializzazione della select che gestisce il cambio di
     * stato
     * @version 2.0
     * @author Riccardo Tresoldi
     * @return {HTMLSelectElement} l'elemento HTML select che rappresenta lo
     * stato dell'utente
     */
    function initializeSelectState() {
        //ottengo l'elemento dal DOM
        var selectState = document.getElementById("selectState");
        //Controllo che non sia già stato inizzializzato
        if (selectState.length != 0)
            // se è già stato inizializato lo ritorno senza aggiungere altre
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
     * @version 2.0
     * @author Elena Zecchinato
     * @author Diego Beraldin
     * @author Riccardo Tresoldi
     * @param {HTMLDIVElement} view frammeto di codice HTML ottenuto dal file
     * della view
     */
    this.initialize = function(view) {
        // posiziona il pannello sulla pagina
        var dummyDiv = document.createElement("div");
        var mainPanel = document.getElementById("MainPanel");
        if (mainPanel) {
            document.body.insertBefore(dummyDiv, mainPanel.parentElement);
        } else {
            document.body.insertBefore(dummyDiv, document.getElementsByTagName("footer")[0]);
        }
        dummyDiv.innerHTML = view.outerHTML;

        //salvo un riferimento all'elemento DOM appena creato
        thisPanel = document.getElementById("ToolsPanel");
        var ulFunction = document.getElementById("ToolsList");

        // funzione messaggi
        document.getElementById("liAnswering").onclick = function() {
            document.dispatchEvent(showMessagePanel);
        };

        // funzione impostazioni account
        document.getElementById("liSetting").onclick = function() {
            document.dispatchEvent(showAccountSettingPanel);
        };

        // funzione lista chiamate
        document.getElementById("liCallList").onclick = function() {
            document.dispatchEvent(showCallHistoryPanel);
        };

        // funzione gestione gruppi
        document.getElementById("liGroup").onclick = function() {
            document.dispatchEvent(showGroupPanel);
        };

        // funzione di ricerca
        document.getElementById("liSearch").onclick = function() {
            document.dispatchEvent(showSearchResultPanel);
        };

        // possibilità di effettuare il logout
        document.createElement("liLogout").onclick = function() {
            var answer = confirm("Sei sicuro di voler uscire?");
            if (answer) {
                // effettua la disconnessione dal server
                document.dispatchEvent(logout);
                // ricrea le variabili globali e azzera la UI
                mediator = new PresenterMediator();
                communicationcenter = new communicationCenter();
                // ricostruisce il form di login
                document.dispatchEvent(showLoginPanel);
            }
        };

        //inizializzo la select del
        initializeSelectState();
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
            document.dispatchEvent(showCommunicationPanel);
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
     * @version 2.0
     * @author Riccardo Tresoldi
     * @return {HTMLSelectElement} l'elemento HTML select che rappresenta lo
     * stato dell'utente
     */
    this.updateStateValue = function() {
        //ottengo la select
        var selectState = initializeSelectState();
        //ottengo il valore corrente della select
        var currentValue = selectState.options[selectedIndex].value;
        //Inviare il messaggio con websoket;
        changeMyState.state = (currentValue);
        document.dispatchEvent(changeMyState);
        return selectState;
    };

    /***************************************************************************
     * HANDLER EVENTI
     **************************************************************************/
    /**
     * Effettua il logout comunicandolo alla servlet e chiudendo il canale di
     * comunicazione che era stato aperto con il server
     * @version 2.0
     * @author Riccardo Tresoldi
     */
    function onLogout() {
        communicationcenter.disconnect();
        var request = new XMLHttpRequest();
        request.open("POST", commandURL, false);
        request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        request.send("operation=logout");
        var result = JSON.parse(request.responseText);
        if (!result) {
            alert("Ops... qualcosa &egrave; andato storto nel server!");
        }
    }

    /***************************************************************************
     * LISTNER DEGLI EVENTI
     **************************************************************************/
    document.addEventListener("showToolsPanel", function(evt) {
        onShowToolsPanel();
    });
    document.addEventListener("removeToolsPanel", function(evt) {
        onRemoveToolsPanel();
    });
    document.addEventListener("showReturnToCommunicationPanelButton", onShowReturnToCommunicationPanelButton);
    document.addEventListener("logout", onLogout);
}