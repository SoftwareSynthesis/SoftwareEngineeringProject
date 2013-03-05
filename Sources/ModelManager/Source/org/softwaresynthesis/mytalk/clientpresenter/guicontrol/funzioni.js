/*****************************************************************************/
/*****                                  **************************************/
/*****  DA SISTEMARE NEL POSTO GIUSTO!  **************************************/
/*****                                  **************************************/
/*****************************************************************************/
//Inizializzare le variabili di DOM [da posizionare nel <script> della pagina]
var idUser;
var AddressBookList = new Array();

/*var AddressBookPannel = new addressBookPannelPresenter(document.getElementById("AddressBookPannel"));
var MainPannel = new mainPannelPresenter(document.getElementById("MainPannel"));
var ToolsPannel = new toolsPannelPresenter(document.getElementById("ToolsPannel"));*/
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
	//da configurare con url giusta 
	var urlServlet= "http://localhost:8080/nomeservlet";
	var request = new XMLHttpRequest();
	
	//il server ritorna Nome, Cognome, Status, Email, Id, Image
	//esempio
	/*
	{
		"1":{"nome": "Marco", "cognome": "Schivo", "email": "marcoskivo@gmail.com", "ID": "1", "Image": "sfdsfsd.jpg", "Status": "Avaible"},
		"2":{"nome": "Andrea", "cognome": "Rizzi", "email": "asondjs@gmail.com", "ID": "2", "Image": "sad.jpg", "Status": "Not Avaible"}
	}
	*/
	request.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			//ricevo un JSON contenente tutti i contatti della mia rubrica e li inserisco nell'array dichiarato globalmente "AddresssBookList"
			AddressBookList= JSON.parse(request.responseText);
		}
	};
	
	request.open("POST", urlServlet, "true");
	request.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	//se c'è bisogno di passare piu parametri, agganciarli con &
	//id deve essere dichiarata variabili globale
	request.send("id=" + idUser);
	
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
	var ulList = AddressBookPannel.element.getElementById("AddressBookList");
	
	//<ul id="AddressBookList"></ul>
	for (var contact in AddressBookList) {
		//ciclo i contatti e agiungo un <li> per ogni contatto.
		ulList.addListItem(AddressBookList[contact]);
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
	var invocatedElementId = this.element.getAttribute('id')
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
	//creo i tre div principali
	var divSearch = document.createElement('div');
	var divSort = document.createElement('div');
	var divList = document.createElement('div');
	
	//creo contenuto divSearch
	var inputText = document.createElement('input');
	inputText.setAttribute("type", "text");
	var inputButton = document.createElement('input');
	inputText.setAttribute("type", "image");
	
	//creo contenuto divSort
	var select = document.createElement('select');
	select.setAttribute("id", "selectSort");
	
	//creo contenuto divList
	var ul = document.createElement('ul');
	select.setAttribute("id", "AddressBookList");
	
	//appendo i sottonodi ai nodi principali
	divSearch.appendChild(inputText);
	divSearch.appendChild(inputButton);
	divSort.appendChild(select);
	divList.appendChild(ul);
	
	//apendo il sottoalbero al DOM
	this.element.innerHTML = "";
	this.element.appendChild(divSearch);
	this.element.appendChild(divSort);
	this.element.appendChild(divList);
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
	var name="";
	if (AddressBookContact.name != null)
		name += AddressBookContact.name;
	if (AddressBookContact.surname != null) {
		if (name != "")
			name += " ";
		name += AddressBookContact.surname;
	}
	if (name == "")
		name += AddressBookContact.email;
	var status;
	var avatar = AddressBookContact.image;

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
	this.addListItem = addListItem;
}



/**
 * Costruttore dell'elemento addressBookPannelPresenter passato come parametro
 *
 * HowToUse:	var ExemplePannel = new addressBookPannelPresenter(document.getElementById("elementId"));
 *
 * @author Tresoldi Riccardo
 * @param pannel Elemento DOM rappresentante il pannello a cui associare l'oggetto
 */
function addressBookPannelPresenter(pannel) {
	this.element = pannel;
	this.inizialize = inizialize;
	this.inizializeAddressBookPannel = inizializeAddressBookPannel;
}


/**
 * Costruttore dell'elemento mainPannelPresenter passato come parametro
 *
 * HowToUse:	var ExemplePannel = new mainPannelPresenter(document.getElementById("elementId"));
 *
 * @author Tresoldi Riccardo
 * @param pannel Elemento DOM rappresentante il pannello a cui associare l'oggetto
 */
function mainPannelPresenter(pannel) {
	this.element = pannel;
	this.inizialize = inizialize;
	this.inizializeMainPannel = inizializeMainPannel;
}


/**
 * Costruttore dell'elemento toolsPannelPresenter passato come parametro
 *
 * HowToUse:	var ExemplePannel = new toolsPannelPresenter(document.getElementById("elementId"));
 *
 * @author Tresoldi Riccardo
 * @param pannel Elemento DOM rappresentante il pannello a cui associare l'oggetto
 */
function toolsPannelPresenter(pannel) {
	this.element = pannel;
	this.inizialize = inizialize;
	this.inizializeToolsPannel = inizializeToolsPannel;
}