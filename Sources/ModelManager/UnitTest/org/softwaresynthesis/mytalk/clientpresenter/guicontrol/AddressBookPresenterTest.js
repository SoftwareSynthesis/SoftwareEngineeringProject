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
						var div = document.createElement("div");
						div.innerHTML = viewRequest.responseText;
						div.children[0].style.display = "none";
						return div.childNodes[0];
					},
					createNameLabel : function(someContact) {
						return someContact.email;
					}
				};
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

	var element = document.getElementById("AddressBookList");

	equal(element.children.length, 5);
	i++;
	element = element.children[0];
	equal(element.nodeName, "LI");
	i++;
	equal(element.id, "1");
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
	tester.destroy();
	var element = document.getElementById("AddressBookPanel");
	ok(!element);
});

/**
 * TODO da documentare!
 * 
 * @version 2.0
 * @author Diego Beraldin
 */
test("testAddContact()", function() {
	var i = 0;
	tester.initialize(mediator.getView("addressBook"));
	var contact = tester.getContacts()[1];

	try {
		tester.addContact(contact);
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

	ok(tester.addContact(contact))
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
 * TODO da documentare!
 * 
 * @version 2.0
 * @author Diego Beraldin
 */
test(
		"testRemoveContact()",
		function() {
			var i = 0;
			tester.initialize(mediator.getView("addressBook"));
			var contact = {
				id : 1
			};

			ok(tester.removeContact(contact))
			i++;

			contact.id = 2;
			try {
				tester.removeContact(contact);
			} catch (error) {
				equal(error, "Ops... qualcosa è andato storto nel server.");
				i++;
			}
			contact.id = 6;
			try {
				tester.removeContact(contact);
			} catch (error) {
				equal(error,
						"Non puoi eliminare un contatto non presente in rubrica.");
				i++;
			}

			expect(i);
		});

/**
 * TODO da documentare!
 * 
 * @version 2.0
 * @author Diego Beraldin
 */
test("testAddGroup()", function() {
	var i = 0;
	tester.initialize(mediator.getView("addressBook"));

	var groupName = "mona";

	try {
		tester.addGroup(groupName);
	} catch (error) {
		equal(error, "Il gruppo che stai cercando di inserire esiste già.");
		i++;
	}
	groupName = "colleghi";
	try {
		tester.addGroup(groupName);
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
 * TODO da documentare!
 * 
 * @version 2.0
 * @author Diego Beraldin
 */
test("testDeleteGroup()", function() {
	var i = 0;
	tester.initialize(mediator.getView("addressBook"));
	var group = {
		id : 10
	};

	try {
		tester.deleteGroup(group);
	} catch (error) {
		equal(error, "Il gruppo che stai cercando di eliminare non esiste.");
		i++;
	}
	group.id = 1;
	try {
		tester.deleteGroup(group);
	} catch (error) {
		equal(error, "Ops... qualcosa è andato storto nel server.");
		i++;
	}
	group.id = 2;
	ok(tester.deleteGroup(group));
	i++;

	expect(i);
});

test("testGetGroupsWhereContactsIs()", function() {
	var i = 0;
	tester.initialize(mediator.getView("addressBook"));
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