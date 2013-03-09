/**
 * Presenter incaricato di gestire il pannello che visualizza i risultati di ricerca
 * 
 * @constructor
 * @this {SearchResultPanelPresenter}
 */
function SearchResultPanelPresenter() {
/**********************************************************
                      VARIABILI PRIVATE
***********************************************************/
	// elemento controllato da questo presenter
	var element = document.getElementById("SearchResultPanel");
	// lista di elementi controllata
	var userList;

/**********************************************************
                       METODI PRIVATI
***********************************************************/
	/**
	 * Aggiunge un contatto ad una lista (pensato per essere visualizzato
	 * nel pannello dei risultati di una ricerca, in cui è quindi possibile
	 * anche aggiungere un nuovo contatto alla propria rubrica
	 * 
	 * @param {HTMLUListElement} list
	 * @param {Object} contact
	 */
	function addListItem(list, contact) {
		var item = document.createElement("li");
        item.setAttribute("id", contact.id);
		// costruisce il nodo
        var name = "";
        if (contact.name != null)
            name += contact.name;
        if (contact.surname != null) {
            if (name != "")
                name += " ";
            name += contact.surname;
        }
        if (name != "")
        	name += " ";
        // visualizza sempre l'email
        name += contact.mail;
        var avatarNode = document.createElement('img');
        avatarNode.setAttribute("src", contact.picturePathatar);
        var textNode = document.createTextNode(name);
        // aggiunge i sottonodi al 'li' appena creato
        item.appendChild(avatarNode);
        item.appendChild(textNode);
        item.appendChild(statusNode);
		// aggiunge il 'li' alla lista ricevuta come parametro
		list.appendChild(item);
	}
	
/**********************************************************
                      METODI PUBBLICI
***********************************************************/
	/**
	 * Inizializza il pannello dei risultati ricerca
	 * 
	 * @author Diego Beraldin
	 */
	this.initialize = function() {
		element.display.style = "block";
		userList = document.createElement("ul");
		// aggiunge al sottoalbero il nuovo elemento
		element.appendChild(userList);
	};
	
	/**
	 * Visualizza all'interno del pannello una lista di contatti che
	 * è stata ottenuta dal server a seguito di una ricerca
	 * 
	 * @param {Array} contacts la lista di contatti che deve essere visualizzata
	 * @author Diego Beraldin
	 */
	this.displayContactList = function(contacts) {
		userList.innerHTML = "";
		for (var c in contacts) {
			addListItem(userList, c);
		}
	};
	
	/**
	 * Rende invisibile il pannello contenente i risultati di una ricerca
	 * fra gli utenti registrati nel sistema
	 * 
	 * @author Diego Beraldin
	 */
	this.hide = function() {
		element.style.display = "none";
	};
}