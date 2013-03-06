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
	var invocatedElementId = this.element.getAttribute("id");
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
 * Inizializzazione del <div> ToolsPanel
 *
 * @author Zecchinato Elena
 */
function inizializeToolsPanel() {
	//creo i due div principali
	var divState = document.createElement('div')
	var divFuntion = document.createElement('div');
	
	//creo contenuto divFuntion
	var ulFuntion = document.createElement('ul');
	select.setAttribute("id", "ToolsList");
	
	var liAnswering = document.createElement('li')
	var liSetting = document.createElement('li')
	var liCallList = document.createElement('li')
	var liLanguage = document.createElement('li')
	
	//creo contenuto divState
	var state = document.createElement('select');
	select.setAttribute("state", "selectState");
	
	//appendo i sottonodi ai nodi principali
	ulFunction.appendChild(liAnswering);
	ulFunction.appendChild(liSetting);
	ulFunction.appendChild(liCallist);
	ulFunction.appendChild(liLanguage);
	
	divFunction.appendChild(ulFunction);
	divState.appendChild(state);
	
	//apendo il sottoalbero al DOM
	this.element.innerHTML="";
	this.element.appendChild(divFunction);
	this.element.appendChild(divState);
	}
	
	
/**
 * Inizializzazione del <div> MainPanel
 *
 * @author Zecchinato Elena
 */
 
function inizializeMainPanel() {
	this.element.innerHTML="";
	}
	
	
/**
 * Inizializzazione del <div> CallHistoryPanel
 *
 * @author Zecchinato Elena
 */
 //NOTA: è stata messa una lista ma nel file html per ora c'è una tabella: si deciderà in seguito come organizzare i dati
function inizializeCallHistoryPanel() {

	//creo contenuto di CallHistory
	var ulHistory = document.createElement('ul');
	
	//apendo il sottoalbero al MainPanel
	this.element.innerHTML="";
	this.element.appendChild(ulHisotry);
	}

	
	
	

/**
 * Inizializzazione di ContactPannel
 *
 * @author Zecchinato Elena
 */
 
 //NOTA: nn è definitivo potrebbe cambiare
 
function inizializeContactPanel() {
	var ulDati=document.createElement('ul'); 
	
	var name = document.createElement('li');
	var surname = document.createElement('li');
	var email = document.createElement('li');

	var profilo=document.createElement('img');
	profilo.setAttribute("src");
	
	var chiama = document.createElement('button');
	chiama.setAttribute("type", "button");
	
	var videoChiama = document.createElement('button');
	videoChiama.setAttribute("type", "button");
	
	var chat = document.createElement('button');
	chat.setAttribute("type", "button");
		
	
	//appendo i sottonodi ai nodi 
	ulDati.appendChild(name);
	ulDati.appendChild(surname);
	ulDati.appendChild(email);
	
	
	//apendo il sottoalbero al DOM
	this.element.appendChild(ulDatiUtente);
	this.element.appendChild(profilo);
	this.element.appendChild(chiama);
	this.element.appendChild(videoChiama);
	this.element.appendChild(Chatta);
	}



/**
 * Inizializzazione del CommunicationPanel
 *
 * @author Zecchinato Elena
 */
function inizializeCommunicationPanel() {
	//creo i due div principali
	var divProfile = document.createElement('div')
	var divChat = document.createElement('div');
	
	//apendo il sottoalbero al DOM
	this.element.innerHTML="";
	this.element.appendChild(divProfile);
	this.element.appendChild(divChat);
	}
	

/**
 * Inizializzazione di AccountSettingPannel
 *
 * @author Zecchinato Elena
 */
 

 
function inizializeAccountSettingPanel() {
	//creo i div principali
	var ulDati=document.createElement('ul'); 
	
	var nome = document.createElement('li');
	var cognome = document.createElement('li');
	var mail = document.createElement('li');

	var immagine=document.createElement('img');
	immagine.setAttribute("src");
	
	var cambia = document.createElement('button');
	chiama.setAttribute("type", "button");
	
	
	//appendo i sottonodi ai nodi principali
	ulDati.appendChild(nome);
	ulDati.appendChild(cognome);
	ulDati.appendChild(mail);
	
	//apendo il sottoalbero al DOM
	this.element.appendChild(immagine);
	this.element.appendChild(ulDati);
	this.element.appendChild(cambia);
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
}