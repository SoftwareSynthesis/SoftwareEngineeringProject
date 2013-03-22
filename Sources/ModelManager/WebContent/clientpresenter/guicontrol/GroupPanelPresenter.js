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
     FUNZIONI PRIVATE
     ***********************************************************/
    /**
     * Aggiunge un gruppo ad una lista
     *
     * @param {HTMLUListElement} list
     * @param {Object} group
     * @author Riccardo Tresoldi
     */
    function addListItem(list, group) {
        var item = document.createElement("li");
        item.id = group.id;
        // costruisce il nodo
        var nameSpan=document.createElement("span");
        nameSpan.appendChild(document.createTextNode(group.name));
        // visualizza sempre l'email
        var deleteImg = document.createElement('img');
        deleteImg.src = "";
        // aggiunge i sottonodi al 'li' appena creato
        item.appendChild(nameSpan);
        item.appendChild(deleteImg);
        
        // aggiunge il 'li' alla lista ricevuta come parametro
        list.appendChild(item);
    }

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
