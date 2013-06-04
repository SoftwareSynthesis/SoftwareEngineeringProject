/**
 * Verifica della classe AddressBookPresenter
 * 
 * @version 2.0
 * @author Elena Zecchinato
 * @author Diego Beraldin
 */
module(
		"AddressBookPresenterTest",
		{
			setup : function() {
				// calcola l'indirizzo di base
				host = window.location.protocol + "//" + window.location.host
						+ window.location.pathname;
				host = host.substr(0, host.length - 10);
				// indirizzo dello stub del front controller
				commandURL = "http://localhost/ModelManager/WebContent/Conf/controllerManagerStub.php";
				// stub di mediator
				mediator = {
					getView : function(someString) {
						var viewRequest = new XMLHttpRequest();
						viewRequest.open("POST",
								"clientview/AddressBookView.html", false);
						viewRequest.send();
						return viewRequest.response;
					},
					createNameLabel : function(someContact) {
						return someContact.email;
					}
				};
				// brutti eventi cattivi (globali)
				showContactPanel = new CustomEvent("showContactPanel");
				// oggetto da testare
				tester = new AddressBookPanelPresenter();
			},
			teardown : function() {
				var element = document.getElementById("AddressBookPanel");
				if (element) {
					document.body.removeChild(element.parentElement);
				}
			}
		});

/**
 * Verifica la corretta costruzione e inizializzazione degli elementi grafici
 * del pannello della rubrica
 * 
 * @version 2.0
 * @author Elena Zecchinato
 * @author Diego Beraldin
 */
test("testInitialize()", function() {
	var i = 0;
	tester.initialize(mediator.getView("addressBook"));
	document.getElementById("AddressBookPanel").style.display = "none";

	var element = document.getElementById("AddressBookPanel");
	equal(element.children.length, 4);
	i++;
	element = element.children[0];
	equal(element.nodeName, "DIV");
	i++;
	equal(element.className, "panelHeader");
	i++;
	element = element.children[0];
	equal(element.nodeName, "H1");
	i++;
	equal(element.innerHTML.trim(), "RUBRICA");
	i++;
	element = element.parentElement.parentElement.children[1];
	equal(element.nodeName, "DIV");
	i++;
	equal(element.id, "divFilter");
	i++;
	element = element.children[0];
	equal(element.nodeName, "INPUT");
	i++;
	equal(element.id, "inputText");
	i++;
	element = element.parentElement.children[1];
	equal(element.nodeName, "INPUT");
	i++;
	equal(element.nodeName, "INPUT");
	i++;
	equal(element.id, "inputButton");
	;
	i++;
	equal(element.src, host + "img/search.png");
	i++;
	element = element.parentElement.parentElement.children[2];
	equal(element.nodeName, "DIV");
	i++;
	equal(element.id, "divList");
	i++;
	element = element.children[0];
	equal(element.nodeName, "UL");
	i++;
	equal(element.id, "AddressBookList");
	i++;
	element = element.parentElement.parentElement.children[3];
	equal(element.nodeName, "DIV");
	i++;
	equal(element.id, "divGroup");
	i++;
	element = element.children[0];
	equal(element.nodeName, "SELECT");
	i++;
	equal(element.id, "selectGroup");
	i++;

	expect(i);
});

/**
 * Verifica la visualizzazione della lista dei contatti
 * 
 * @version 2.0
 * @author Diego Beraldin
 */
test("testSetup()", function() {
	var i = 0;
	tester.initialize(mediator.getView("addressBook"));
	document.getElementById("AddressBookPanel").style.display = "none";

	var element = document.getElementById("AddressBookList");

	equal(element.children.length, 5);
	i++;
	element = element.children[0];
	equal(element.nodeName, "LI");
	i++;
	equal(element.id, "contact-1");
	i++;
	equal(element.className, "available");
	i++;
	element = element.children[0];
	equal(element.nodeName, "IMG");
	i++;
	equal(element.src, host + "img/contactImg/Default.png");
	i++;
	equal(element.parentElement.childNodes[1].nodeValue.trim(),
			"a.rizzi@gmail.com");
	i++;
	element = element.parentElement.children[1];
	equal(element.nodeName, "IMG");
	i++;
	equal(element.src, host + "img/stateavailable.png");
	i++;

	expect(i);
});

/**
 * Verifica che il pannello sia effettivamente distrutto
 * 
 * @version 2.0
 * @author Diego Beraldin
 */
test("testDestroy()", function() {
	tester.initialize(mediator.getView("addressBook"));
	document.getElementById("AddressBookPanel").style.display = "none";
	tester.destroy();
	var element = document.getElementById("AddressBookPanel");
	ok(!element);
});

/**
 * Verifica che sia possibile aggiungere un contatto alla rubrica e che vengano
 * sollevate le opportune eccezioni nel caso in cui questo sia già presente in
 * rubrica o in caso di errore nel server.
 * 
 * @version 2.0
 * @author Diego Beraldin
 */
test("testAddContact()", function() {
	var i = 0;
	tester.initialize(mediator.getView("addressBook"));
	document.getElementById("AddressBookPanel").style.display = "none";
	var contact = tester.getContacts()[1];

	try {
		tester.addContact(contact);
		ok(false, "eccezione non sollevata");
	} catch (error) {
		equal(error, "Contatto già presente nella rubrica.");
		i++;
	}
	contact = {
		name : "Paolino",
		surname : "Paperino",
		id : 10,
		blocked : false,
		picturePath : "img/contactImg/Default.png",
		status : "available"
	};

	ok(tester.addContact(contact));
	i++;

	contact.id = 9;
	try {
		tester.addContact(contact);
	} catch (error) {
		equal(error, "Ops... qualcosa è andato storto nel server.");
		i++;
	}

	expect(i);
});

/**
 * Verifica che sia possibile rimuovere un contatto dalla rubrica e che vengano
 * sollevate le opportune eccezioni nel caso in cui questo non sia di fatto
 * presente in rubrica o in caso di errore nel server.
 * 
 * @version 2.0
 * @author Diego Beraldin
 */
test(
		"testRemoveContact()",
		function() {
			var i = 0;
			tester.initialize(mediator.getView("addressBook"));
			document.getElementById("AddressBookPanel").style.display = "none";
			var contact = {
				id : 1
			};

			ok(tester.removeContact(contact));
			i++;

			contact.id = 2;
			try {
				tester.removeContact(contact);
				ok(false, "eccezione non sollevata");
			} catch (error) {
				equal(error, "Ops... qualcosa è andato storto nel server.");
				i++;
			}
			contact.id = 6;
			try {
				tester.removeContact(contact);
				ok(false, "eccezione non sollevata");
			} catch (error) {
				equal(error,
						"Non puoi eliminare un contatto non presente in rubrica.");
				i++;
			}

			expect(i);
		});

/**
 * Verifica che sia possibile aggiungere un gruppo alla rubrica e che vengano
 * sollevate le opportune eccezioni nel caso in cui questo sia già presente o in
 * caso di errore nel server.
 * 
 * @version 2.0
 * @author Diego Beraldin
 */
test("testAddGroup()", function() {
	var i = 0;
	tester.initialize(mediator.getView("addressBook"));
	document.getElementById("AddressBookPanel").style.display = "none";

	var groupName = "mona";

	try {
		tester.addGroup(groupName);
		ok(false, "eccezione non sollevata");
	} catch (error) {
		equal(error, "Il gruppo che stai cercando di inserire esiste già.");
		i++;
	}
	groupName = "colleghi";
	try {
		tester.addGroup(groupName);
		ok(false, "eccezione non sollevata");
	} catch (error) {
		equal(error, "Ops... qualcosa è andato storto nel server.");
		i++;
	}
	groupName = "famiglia";
	ok(tester.addGroup(groupName));
	i++;

	expect(i);
});

/**
 * Verifica che sia possibile rimuovere un gruppo dalla rubrica e che vengano
 * sollevate le opportune eccezioni nel caso in cui questo non sia di fatto
 * presente o in caso di errore nel server.
 * 
 * @version 2.0
 * @author Diego Beraldin
 */
test("testDeleteGroup()", function() {
	var i = 0;
	tester.initialize(mediator.getView("addressBook"));
	document.getElementById("AddressBookPanel").style.display = "none";
	var group = {
		id : 10
	};

	try {
		tester.deleteGroup(group);
		ok(false, "eccezione non sollevata");
	} catch (error) {
		equal(error, "Il gruppo che stai cercando di eliminare non esiste.");
		i++;
	}
	group.id = 1;
	try {
		tester.deleteGroup(group);
		ok(false, "eccezione non sollevata");
	} catch (error) {
		equal(error, "Ops... qualcosa è andato storto nel server.");
		i++;
	}
	group.id = 2;
	ok(tester.deleteGroup(group));
	i++;

	expect(i);
});

/**
 * Verifica che sia possibile ottenere tutti i gruppi a cui appartiene un
 * determinato contatto.
 * 
 * @version 2.0
 * @author Diego Beraldin
 */
test("testGetGroupsWhereContactsIs()", function() {
	var i = 0;
	tester.initialize(mediator.getView("addressBook"));
	document.getElementById("AddressBookPanel").style.display = "none";
	var contact = {
		name : "Andrea",
		surname : "Rizzi",
		email : "a.rizzi@gmail.com",
		id : 1,
		picturePath : "img/contactImg/Default.png",
		state : "available",
		blocked : false
	};

	var result = tester.getGroupsWhereContactsIs(contact);

	equal(Object.keys(result).length, 2);
	i++;
	equal(result[2].name, "mona");
	i++;
	equal(result[3].name, "addrBookEntry");
	i++;

	expect(i);
});

/**
 * Verifica che sia possibile aggiungere un contatto a un gruppo se questo è
 * possibile e che vengano sollevate le opportune eccezioni in caso contrario,
 * in particolare se il contatto già appartiene al gruppo cui se ne chiede
 * l'aggiunta o nel caso in cui si verifichi un errore nel server.
 * 
 * @version 2.0
 * @author Diego Beraldin
 */
test("testAddContactInGroup()", function() {
	var i = 0;
	tester.initialize(mediator.getView("addressBook"));
	document.getElementById("AddressBookPanel").style.display = "none";
	var contact = {
		name : "Andrea",
		surname : "Rizzi",
		email : "a.rizzi@gmail.com",
		id : 1,
		picturePath : "img/contactImg/Default.png",
		state : "available",
		blocked : false
	};
	var group = {
		id : 2,
		name : "mona",
		contacts : [ 1 ]
	};
	try {
		tester.addContactInGroup(contact, group);
		ok(false, "eccezione non sollevata");
		i++;
	} catch (error) {
		equal(error, "Il contatto è già presente nel gruppo.");
		i++;
	}
	group = {
		id : 1,
		name : "amici",
		contacts : [ 2, 3, 4, 5 ]
	};

	ok(tester.addContactInGroup(contact, group));
	i++;

	group.id = 10;
	try {
		tester.addContactInGroup(contact, group);
		ok(false, "eccezione non sollevata");
		i++;
	} catch (error) {
		equal(error, "Ops... qualcosa è andato storto nel server.");
		i++;
	}

	expect(i);
});

/**
 * Verifica che sia possibile applicare alla lista di contatti un filtro
 * testuale, e che la lista visualizzata contenga solo i contatti che soddisfano
 * i criteri di ricerca specificati.
 * 
 * @version 2.0
 * @author Diego Beraldin
 */
test("testApplyFilterByString()", function() {
	var i = 0;
	tester.initialize(mediator.getView("addressBook"));
	document.getElementById("AddressBookPanel").style.display = "none";

	var result = tester.applyFilterByString("rizzi");
	equal(result.length, 1);
	i++;
	equal(result[0], 1);
	i++;
	var contact = tester.getContact(result[0]);
	equal(contact.name, "Andrea");
	i++;
	equal(contact.surname, "Rizzi");
	i++;

	expect(i);
});

/**
 * Verifica che sia possibile applicare alla lista di contatti un filtro basato
 * su gruppo e che la lista visualizzata contenga solo i contatti che
 * appartengono effettivamente a quel determinato gruppo.
 * 
 * @version 2.0
 * @author Diego Beraldin
 */
test("testApplyFilterByGroup()", function() {
	var i = 0;
	var groupId = 2;
	tester.initialize(mediator.getView("addressBook"));
	document.getElementById("AddressBookPanel").style.display = "none";

	var result = tester.applyFilterByGroup(groupId);

	equal(result.length, 1);
	i++;
	equal(result[0], 1);
	i++;

	expect(i);
});

/**
 * Verifica che sia possibile visualizzare una lista di contatti generica
 * tramite il metodo showFilter e che sia gestito correttamente il caso in cui
 * la lista passata come parametro non contiene alcun elemento.
 * 
 * @version 2.0
 * @author Diego Beraldin
 */
test("testShowFilter()", function() {
	var i = 0;
	tester.initialize(mediator.getView("addressBook"));
	document.getElementById("AddressBookPanel").style.display = "none";
	var filter = [];

	tester.showFilter(filter, false);

	var element = document.getElementById("AddressBookList");
	equal(element.children.length, 2);
	i++;
	element = element.children[0];
	equal(element.innerHTML.trim(), "Filtraggio");
	i++;
	element = element.parentElement.children[1];
	equal(element.innerHTML.trim(), "Nessun contatto con questo criterio");
	i++;
	filter = [ 2 ];

	tester.showFilter(filter, false);
	element = document.getElementById("AddressBookList");
	equal(element.children.length, 2);
	i++;
	element = element.children[0];
	equal(element.innerHTML.trim(), "Filtraggio");
	i++;
	element = element.parentElement.children[1];
	equal(element.nodeName, "LI");
	i++;
	equal(element.className, "offline");
	i++;
	element = element.children[0];
	equal(element.nodeName, "IMG");
	i++;
	equal(element.src, host + "img/contactImg/Default.png");
	i++;
	equal(element.parentElement.childNodes[1].nodeValue.trim(),
			"s.farro@gmail.com");
	i++;
	element = element.parentElement.children[1];
	equal(element.nodeName, "IMG");
	i++;
	equal(element.src, host + "img/stateoffline.png");
	i++;

	expect(i);
});

/**
 * Verifica che sia gestito correttamente l'evento di pressione del mouse su una
 * voce della lista dei contatti, in particolare, visualizzando il pannello con
 * le informazioni del contatto selezionato.
 * 
 * @version 2.0
 * @author Diego Beraldin
 */
test("testDisplayContactByClick()", function() {
	var i = 0;
	tester.initialize(mediator.getView("addressBook"));
	document.getElementById("AddressBookPanel").style.display = "none";
	var bool = false;
	var element = document.getElementById("AddressBookList");
	element = element.children[0];
	document.addEventListener("showContactPanel", function() {
		bool = true;
	});

	element.dispatchEvent(new MouseEvent("click"));
	ok(bool);
	i++;

	expect(i);
});

/**
 * Verifica che sia gestito correttamente l'evento di un cambiamento di stato da
 * parte di un contatto presente in rubrica, in particolare, controllando che
 * l'immagine dello stato impostata corrisponda al nuovo stato assunto
 * dall'utente.
 * 
 * @version 2.0
 * @author Diego Beraldin
 */
test("testOnChangeAddressBookContactState()", function() {
	tester.initialize(mediator.getView("addressBook"));
	document.getElementById("AddressBookPanel").style.display = "none";
	var event = new CustomEvent("changeAddressBooksContactState");
	event.idUserChange = 1;
	event.statusUserChange = "occupied";

	document.dispatchEvent(event);

	var element = document.getElementById("contact-" + event.idUserChange);
	equal(element.children[1].src, host + "img/stateoccupied.png");
});

/**
 * Verifica la possibilità di avviare una ricerca basata su un criterio testuale
 * tramite interfaccia grafica, simulando l'inserimento di un valore nel campo
 * di testo nel relativo campo di input e la pressione del pulsante di ricerca.
 * 
 * @version 2.0
 * @author Diego Beraldin
 */
test("testSearchByClick()", function() {
	var i = 0;
	tester.initialize(mediator.getView("addressBook"));
	document.getElementById("AddressBookPanel").style.display = "none";
	var element = document.getElementById("inputText");
	element.value = "Rizzi";
	element = document.getElementById("inputButton");

	element.dispatchEvent(new MouseEvent("click"));

	element = document.getElementById("AddressBookList");
	equal(element.children.length, 2);
	i++;
	element = element.children[0];
	equal(element.innerHTML.trim(), "Filtraggio");
	i++;
	element = element.parentElement.children[1];
	equal(element.nodeName, "LI");
	i++;
	equal(element.className, "available");
	i++;
	element = element.children[0];
	equal(element.nodeName, "IMG");
	i++;
	equal(element.src, host + "img/contactImg/Default.png");
	i++;
	equal(element.parentElement.childNodes[1].nodeValue.trim(),
			"a.rizzi@gmail.com");
	i++;
	element = element.parentElement.children[1];
	equal(element.nodeName, "IMG");
	i++;
	equal(element.src, host + "img/stateavailable.png");
	i++;

	expect(i);
});

/**
 * Verifica la possibilità di rimuovere il filtraggio facendo clic sul list item
 * contenente la parola "Filtraggio". Il test si assicura che dopo l'evento sia
 * visualizzata nuovamente l'intera rubrica.
 * 
 * @version 2.0
 * @author Diego Beraldin
 */
test("testRemoveFilteringByClick()", function() {
	var i = 0;
	tester.initialize(mediator.getView("addressBook"));
	document.getElementById("AddressBookPanel").style.display = "none";
	var element = document.getElementById("inputText");
	element.value = "Rizzi";
	element = document.getElementById("inputButton");
	element.dispatchEvent(new MouseEvent("click"));
	element = document.getElementById("AddressBookList").children[0];

	element.dispatchEvent(new MouseEvent("click"));

	element = document.getElementById("AddressBookList").children[0];
	// non deve essere il "Filtraggio"
	equal(element.id, "contact-1");
	i++;
	element = element.parentElement;
	equal(element.children.length, 5);
	i++;

	expect(i);
});

/**
 * Verifica la possibilità di bloccare un contatto.
 * 
 * @version 2.0
 * @author Diego Beraldin
 */
test("testBlockContact()", function() {
	var i = 0;
	tester.initialize(mediator.getView("addressBook"));
	document.getElementById("AddressBookPanel").style.display = "none";
	var contact = {
		name : "Andrea",
		surname : "Rizzi",
		email : "a.rizzi@gmail.com",
		id : 1,
		picturePath : "img/contactImg/Default.png",
		state : "available",
		blocked : false
	};

	ok(tester.blockContact(contact));
	i++;
	contact.id = 2;
	try {
		tester.blockContact(contact);
		ok(false, "non sollevata eccezione");
		i++;
	} catch (error) {
		equal(error, "Ops... qualcosa è andato storto nel server.");
		i++;
	}
	contact.id = 0;
	try {
		tester.blockContact(contact);
		ok(false, "non sollevata eccezione");
		i++;
	} catch (error) {
		equal(error, "Contatto non presente nella rubrica.");
		i++;
	}

	expect(i);
});

/**
 * Verifica la possibilità di sbloccare un contatto.
 * 
 * @version 2.0
 * @author Diego Beraldin
 */
test("testUnlockContact()", function() {
	var i = 0;
	tester.initialize(mediator.getView("addressBook"));
	document.getElementById("AddressBookPanel").style.display = "none";
	var contact = {
		name : "Andrea",
		surname : "Rizzi",
		email : "a.rizzi@gmail.com",
		id : 1,
		picturePath : "img/contactImg/Default.png",
		state : "available",
		blocked : false
	};

	try {
		tester.unlockContact(contact);
		i++;
	} catch (error) {
		equal(error, "Contatto già sbloccato.");
		i++;
	}
	contact.id = 2;
	try {
		tester.unlockContact(contact);
		ok(false, "non sollevata eccezione");
		i++;
	} catch (error) {
		equal(error, "Contatto già sbloccato.");
		i++;
	}
	contact.id = 0;
	try {
		tester.unlockContact(contact);
		ok(false, "non sollevata eccezione");
		i++;
	} catch (error) {
		equal(error, "Contatto non presente nella rubrica.");
		i++;
	}

	expect(i);
});

/**
 * Verifica il comportamento del pannello nel momento in cui è selezionato un
 * gruppo dall'elemento <select> per la visualizzazione dei gruppi. Il test
 * verifica che siano mostrati tutti e soli i contatti appartenenti a quel
 * determinato gruppo.
 * 
 * @version 2.0
 * @author Diego Beraldin
 */
test("testOnChangeSelectGroup()", function() {
	var i = 0;
	tester.initialize(mediator.getView("addressBook"));
	document.getElementById("AddressBookPanel").style.display = "none";
	var element = document.getElementById("selectGroup");

	// seleziona il gruppo 'mona' come sempre
	element.selectedIndex = 1;
	element.dispatchEvent(new UIEvent("change"));

	element = document.getElementById("AddressBookList");
	equal(element.children.length, 2);
	i++;
	element = element.children[0];
	equal(element.innerHTML.trim(), "Filtraggio");
	i++;
	element = element.parentElement.children[1];
	equal(element.nodeName, "LI");
	i++;
	element = element.children[0];
	equal(element.nodeName, "IMG");
	i++;
	equal(element.src, host + "img/contactImg/Default.png");
	i++;
	equal(element.parentElement.childNodes[1].nodeValue.trim(),
			"a.rizzi@gmail.com");
	i++;
	element = element.parentElement.children[1];
	equal(element.nodeName, "IMG");
	i++;
	equal(element.src, host + "img/stateavailable.png");
	i++;

	expect(i);
});