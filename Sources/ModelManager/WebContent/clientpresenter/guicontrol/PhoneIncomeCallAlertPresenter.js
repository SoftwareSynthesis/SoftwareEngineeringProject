/**
 * Presenter incaricato di gestire il pannello per la registrazuone di un
 * messaggio audio da inviare come messaggio di segreteria
 *
 * @author Marco Schivo
 * @author Riccardo Tresoldi
 */
function PhoneIncomeCallAlertPresenter() {
    /*************************************************************
     * CAMPI DATI PRIVATI
     *************************************************************/
    var thisPresenter = this;
    var thisPanel = null;

    var caller = null;
    var onlyAudio = null;

    //variabili pulsanti
    var refuseCallButton, acceptCallButton, callerLabel;

    /*************************************************************
     * FUNZIONI PRIVATE
     *************************************************************/

    /*************************************************************
     * FUNZIONI PUBBLICHE
     *************************************************************/
    /**
     * Funzione per mostrare la view
     *
     * @author Riccardo Tresoldi
     */
    this.showView = function() {
        // salvo il panel generato
        thisPanel = document.getElementById("IncomeCallAlert");
        // ricavo i riferimenti agli elementi della view
        refuseCallButton = document.getElementById("refuseCallButton");
        acceptCallButton = document.getElementById("acceptCallButton");
        callerLabel = document.getElementById("callerLabel");
        // imposto il nome del chiamante nella label giusta
        callerLabel.appendChild(document.createTextNode(mediator.createNameLabel(caller)));
        // imposto il corretto stato ai pulsanti per lo stato iniziale
        refuseCallButton.onclick = function() {
            document.dispatchEvent(removePhoneIncomeCallAlertPanel);
            document.dispatchEvent(rejectCall);
        };
        acceptCallButton.onclick = function() {
            document.dispatchEvent(removePhoneIncomeCallAlertPanel);
            acceptCall.contact = caller;
            acceptCall.onlyAudio = onlyAudio;
            document.dispatchEvent(acceptCall);
        };
        // faccio suonare il campanile
        startRinging.evento = "income";
        document.dispatchEvent(startRinging);
        
    };

    /**
     * Rimozione dell pannello
     */
    this.destroy = function() {
        // fermo il campanile
        document.dispatchEvent(stopRinging);
        // rimuovo il pannello
        if (thisPanel) {
            var thisPanelParent = thisPanel.parentElement.parentElement;
            thisPanelParent.removeChild(thisPanel.parentElement);
            thisPanel = null;
            var overlay = document.getElementById("overlayAnswerBox");
            var overlayParent = overlay.parentElement;
            overlayParent.removeChild(overlay);
        }
    };

    /*******************************************************************
     * HANDLER PER EVENTI
     *******************************************************************/
    /**
     * Gestione dell'evento che visualizza il pannello
     * @version 2.0
     * @author Riccardo Tresoldi
     * @param {Object} caller rappresenta il contato he sta chiamando
     */
    function onShowPhoneIncomeCallAlertPanel(x, o) {
        caller = x;
        onlyAudio = o;
        mediator.getView("phoneIncomeCallAlert");
    }

    /**
     * Gestore dell'evento che rimuove il pannello
     * @version 2.0
     */
    function onRemovePhoneIncomeCallAlertPanel() {
        thisPresenter.destroy();
    }

    /*******************************************************************
     * LISTNER PER EVENTI
     *******************************************************************/
    document.addEventListener("showPhoneIncomeCallAlertPanel", function(evt) {
        onShowPhoneIncomeCallAlertPanel(evt.caller, evt.onlyAudio);
    });
    document.addEventListener("removePhoneIncomeCallAlertPanel", function() {
        onRemovePhoneIncomeCallAlertPanel();
    });
}