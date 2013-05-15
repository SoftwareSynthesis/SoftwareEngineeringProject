/**
 * Presenter incaricato di gestire il pannello principale
 *
 * @constructor
 * @this {MainPanelPresenter}
 * @author Elena Zecchinato
 * @author Diego Beraldin
 */
function MainPanelPresenter() {
    /***************************************************************************
     * VARIABILI PRIVATE
     **************************************************************************/
    // elemento controllato da questo presenter
    var thisPresenter = this;
    var thisPanel;

    /***************************************************************************
     * METODI PRIVATI
     **************************************************************************/
    /**
     * Funzione per gestire l'evento in cui viene visualizzato il pannello principale
     * @author Riccardo Tresoldi
     */
    function onShowMainPanel() {
        mediator.getView('main');
    }

    /**
     * Funzione per gestire l'evento in cui viene rimosso il pannello principale
     * @author Riccardo Tresoldi
     */
    function onRemoveMainPanel() {
        thisPresenter.destroy();
    }
    
    /***************************************************************************
     * METODI PUBBICI
     **************************************************************************/
    /** VIEW
     * Distruttore del pannello
     * @author Riccardo Tresoldi
     */
    this.destroy = function() {
        var thisPanelParent = thisPanel.parentElement.parentElement;
        thisPanelParent.removeChild(thisPanel.parentElement);
    };
    
    /**
     * Costruisce il pannello principale dell'applicazione che occupa il posto
     * centrale della finestra
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
        thisPanel = document.getElementById("MainPanel");
    };

    /**
     * Visualizza un elemento interno al pannello principale
     *
     * @param {HTMLDivElement}
     *            node nodo che deve essere visualizzato all'interno del
     *            pannello principale
     * @author Diego Beraldin
     */
    this.displayChildPanel = function(node) {
        element.innerHTML = "";
        element.appendChild(node);
    };

    /**
     * Rende invisibile il pannello principale
     *
     * @author Diego Beraldin
     */
    this.hide = function() {
        if (element) {
            document.body.removeChild(element);
        }
    };
    
    /***************************************************************************
     * LISTNER DEGLI EVENTI
     **************************************************************************/
    document.addEventListener("showMainPanel", function(evt){
        onShowMainPanel();
    });
    document.addEventListener("removeMainPanel", function(evt){
        onRemoveMainPanel();
    });
}
