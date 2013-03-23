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
     * @param {HTMLUListElement} list la lista su cui aggiungere il gruppo
     * @param {Object} group il gruppo da aggiungere alla lista
     * @author Riccardo Tresoldi
     */
    function addListItem(list, group) {
        var item = document.createElement("li");
        item.id = group.id;
        // costruisce il nodo
        var nameSpan = document.createElement("span");
        nameSpan.appendChild(document.createTextNode(group.name));
        // visualizza sempre l'email
        var deleteGroupImg = document.createElement('img');
        deleteGroupImg.src = "";
        deleteGroupImg.className = "deleteGroupImg";
        //attribuisco all'immagine il comando di eliminare il gruppo
        deleteGroupImg.onclick = function() {
            //chiedo conferma per l'eliminazione
            var userConfirm = confirm("Sei sicuro di voler eliminare questo gruppo?\nGli utenti appartenenti a questo gruppo non verranno eliminati.");
            //se viene data conferma infoco la funzione pr eliminare il gruppo
            if (userConfirm){
                mediator.onGroupRemoved(group);
                //FIXME si deve ricaricare la lista?
            }
        }
        // aggiunge i sottonodi al 'li' appena creato
        item.appendChild(nameSpan);
        item.appendChild(deleteGroupImg);

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
