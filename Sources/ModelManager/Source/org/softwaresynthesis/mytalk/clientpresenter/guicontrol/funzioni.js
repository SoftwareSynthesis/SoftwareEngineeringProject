/*****************************************************************************/
/*****                                  **************************************/
/*****  DA SISTEMARE NEL POSTO GIUSTO!  **************************************/
/*****                                  **************************************/
/*****************************************************************************/
//Inizializzare le variabili di DOM [da posizionare nel <script> della pagina]
var idUser = "";
var AddressBookList = new Array();

var AddressBookPannel = new DOMElement(document.getElementById("AddressBookPannel"));
var MainPannel = new DOMElement(document.getElementById("MainPannel"));
var ToolsPannel = new DOMElement(document.getElementById("ToolsPannel"));
/*****************************************************************************/
/*****************************************************************************/

/**
 * Funzione per ottenere i contatti della proipria rubrica dal server
 *
 * HowToUse:
 *
 * @author Tresoldi Riccardo
 */
function getAddressBookContacts() {
	//eseguo la richiesta dei contatti al server
	var addressBookRequest = new Array("", idutente);
	//websocket.send(JSON.stringify(addressBookRequest));

	//ricevo e parso il JSON ricevuto dal server contenente la rubrica
	//TODO: DA CAPIRE COME RICEVERE I DATI, E ANCHE COME INVIARLI

	//salvo i contatti ricevuti nella variabile AddressBookList
	for (var contact in AddressBookList) {
		//ciclo i contatti
		for (var contactField in contact) {
			//ciclo i campi di ogni contatto
		}
	}
}

/**
 * Funzione per inserire i contatti esratti dal server all'interno della lista
 * 'AddressBookList' all'interno di 'AddressBookPannel'
 *
 * HowToUse:	setAddressBook();
 *
 * @author Tresoldi Riccardo
 */
function setAddressBook() {
	//inizializzo la AddressBookPannel
	AddressBookPannel.inizialize();
	
	//estraggo l'<ul> del Addressbook
	var ulList = AddressBookPannel.getElementById("AddressBookList");
	
	//<ul id="AddressBookList"></ul>
	for (var contact in AddressBookList) {
		//ciclo i contatti e agiungo un <li> per ogni contatto.
		ulList.addListItem(AddressBookList(contact));
	}
}

/**
 * Inizializzazione del elemento DOM passato per se esso è tra quelli inizializzabili
 * Gli elementi inizializzabili sono quelli con id=[ AddressBookPannel, MainPannel, ToolsPannel ]
 *
 * HowToUse:	DOMElement.inizialize();
 *
 * @author Tresoldi Riccardo
 */
function inizialize() {
	var invocatedElementId = this.element.getAttribute('id');
	if (invocatedElementId == "AddressBookPannel") {
		this.inizializeAddressBookPannel();
	} else if (invocatedElementId == "MainPannel") {
		this.inizializeMainPannel();
	} else if (invocatedElementId == "ToolsPannel") {
		this.inizializeToolsPannel();
	} else
		alert("Invalid Element");
}

/**
 * Inizializzazione del <div> AddressBookPannel
 *
 * @author Tresoldi Riccardo
 */
function inizializeAddressBookPannel() {

}

/**
 * Inizializzazione del <div> MainPannel
 *
 * @author Tresoldi Riccardo
 */
function inizializeMainPannel() {

}

/**
 * Inizializzazione del <div> ToolsPannel
 *
 * @author Tresoldi Riccardo
 */
function inizializeToolsPannel() {

}

/**
 * Aggiungere un listItem <li> ad una lista <ul> o <ol> !!!!!!!DA MODIFICARE IL PARAMETRO PASSATO!!!!!!!
 *
 * HowToUse:	var ul = new DOMElement(document.getElementById("elementId"));
 * 				var text = "text";
 *				ul.addListItem(text);
 *
 * @author Tresoldi Riccardo
 * @param AddressBookContact Array contenente i campi dati di un contatto da attribuire al elemento <li> da aggiungere
 */
function addListItem(AddressBookContact) {
	//creo l'elemento <li>
	var item = document.createElement("li");

	//creo le variabili contenenti i dati del contatto da attribuire all'li
	var name;
	if (AddressBookContact['name'] != null)
		name += AddressBookContact['name'];
	if (AddressBookContact['surname'] != null) {
		if (name != null)
			name += " ";
		name += AddressBookContact['surname'];
	}
	if (name == null)
		name += AddressBookContact['email'];
	var status;
	var avatar = AddressBookContact['image'];

	//imposto gli attributi corretti
	item.setAttribute("attributeName", "attributeValue");
	//TODO: settare i parametri giusti!

	//genero i valori da attribuire all'<li>
	var avatarNode = document.createElement('img');
	avatarNode.setAttribute("src", avatar);
	avatarNode.setAttribute("id", "");
	avatarNode.setAttribute("class", "");
	var textNode = document.createTextNode(name);
	var statusNode = document.createElement('img');
	statusNode.setAttribute("src", "");
	statusNode.setAttribute("id", "");
	statusNode.setAttribute("class", "");

	//imposto il valore dell'<li>
	item.appendChild(avatarNode);
	item.appendChild(textNode);
	item.appendChild(statusNode);

	//aggiungo l'<li> al elemento <ul> dell'oggetto ulList su cui viene invocata la funzione
	this.element.appendChild(item);
}

/**
 * Costruttore dell'elemento DOMElement che contiene l'elemento del DOM
 * Viene generato una sorta di oggetto che rappresenta l'elemento passato come parametro
 *
 * HowToUse:	var element = new DOMElement(document.getElementById("elementId"));
 *
 * @author Tresoldi Riccardo
 * @param element Elemento DOM da associare all'oggetto DOMElement
 */
function DOMElement(element) {
	this.element = element;
	this.assListItem = addListItem;
	this.inizialize = inizialize;
}
