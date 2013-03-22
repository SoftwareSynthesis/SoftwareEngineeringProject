/**
 * Presenter incaricato di gestire i gruppi
 *
 * @param {String} url URL della servlet con cui il presenter deve comunicare
 */
function GroupPanelPresenter(url) {
    /**********************************************************
     VARIABILI PRIVATE
     ***********************************************************/
    var servletURL = url;

    /**********************************************************
     METODI PUBBLICI
     ***********************************************************/
    /**
     * Crea il pannello per la gestione dei gruppi che dev'essere richiamato nel
     * mainPanelPresenter
     *
     * @returns {HTMLDivElement} il pannello dello storico delle chiamate
     * inizializzato
     * 
     * @author Riccardo Tresoldi
     */
    this.createPanel = function() {
        var element = document.createElement("div");
        element.id = "groupPanel";
        groupList = document.createElement("ul");
        // aggiunge al sottoalbero il nuovo elemento
        element.appendChild(groupList);
        return element;
    };

    /**
     * Visualizza all'interno del pannello la lista dei gruppi della mia rubrica
     *
     * @author Riccardo Tresoldi
     */
    this.displayContactList = function() {
        groupList.innerHTML = "";
        for (var g in groups) {
            addListItem(groupList, g);
        }
    };

}
