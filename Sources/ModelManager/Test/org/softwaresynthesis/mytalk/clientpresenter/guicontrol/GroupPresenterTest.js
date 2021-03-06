/**
 * Verifica della classe GroupPresenter
 * 
 * @version 2.0
 * @author Elena Zecchinato
 * @author Diego Beraldin
 */
module("GroupPresenter", {
	setup : function() {
		// calcola l'indirizzo di base
		host = window.location.protocol + "//" + window.location.host
				+ window.location.pathname;
		host = host.substr(0, host.length - 10);
		// stub di mediator
		mediator = {
			getAddressBookContacts : function() {
				return {
					0 : {
						name : "Mario",
						surname : "Rossi",
						email : "indirizzo1@dominio.it",
						id : "0",
						picturePath : "img/contactImg/Default.png",
						state : "available",
						blocked : true
					},

					1 : {
						name : "Giuseppe",
						surname : "Verdi",
						email : "indirizzo2@dominio.it",
						id : "1",
						picturePath : "img/contactImg/Default.png",
						state : "offline",
						blocked : false
					},

				};

			},
			getAddressBookGroups : function() {
				return {
					1 : {
						name : "addrBookEntry",
						id : "1",
						contacts : [ 0, 1 ]
					},

					2 : {
						name : "amici",
						id : "2",
						contacts : [ 1 ]
					}
				};
			},
			getView : function(someString) {
				document.dispatchEvent(new CustomEvent("eventRaised"));
				var viewRequest = new XMLHttpRequest();
				viewRequest.open("POST", "clientview/GroupView.html", false);
				viewRequest.send();
				var div = document.createElement("div");
				div.innerHTML = viewRequest.responseText;
				if (document.getElementById("GroupPanel") == null) {
					document.body.appendChild(div);
					div.style.display = "none";
				}
			},
			createNameLabel : function(contact) {
				return contact.email;
			},
			getContactById : function(id) {
				return {
					id : id,
					name : "Paolino",
					surname : "Paperino"
				};
			}
		};
		// brutti eventi cattivi (globali)
		createGroup = new CustomEvent("createGroup");
		showGroupPanel = new CustomEvent("showGroupPanel");
		deleteGroup = new CustomEvent("deleteGroup");
		removeContactFromGroup = new CustomEvent("removeContactFromGroup");
		// oggetto da testare
		tester = new GroupPanelPresenter();
	},
	teardown : function() {
		var element = document.getElementById("GroupPanel");
		if (element) {
			document.body.removeChild(element.parentElement);
		}
	}
});

/**
 * Verifica che sia possibile selezionare i contatti che non appartengono a un
 * gruppo e che sono pertanto candidati ad essere aggiunti.
 * 
 * @version 2.0
 * @author Elena Zecchinato
 * @author Diego Beraldin
 */
test("testSelectCandidates()", function() {
	var i = 0;
	document.dispatchEvent(showGroupPanel);
	tester.display();

	var group = mediator.getAddressBookGroups()[2];
	var list = tester.selectCandidates(group);

	equal(Object.keys(list).length, 1);
	i++;
	var contact = list[0];
	equal(contact.id, 0);
	i++;
	equal(contact.name, "Mario");
	i++;
	equal(contact.surname, "Rossi");
	i++;
	equal(contact.state, "available");
	i++;
	equal(contact.blocked, true);
	i++;
	equal(contact.picturePath, "img/contactImg/Default.png");
	i++;

	expect(i);
});

/**
 * Verifica che il presenter risponda correttamente all'evento showGroupPanel
 * 
 * @version 2.0
 * @author Diego Beraldin
 */
test("testOnShowGroupPanel()", function() {
	var bool = false;
	document.addEventListener("eventRaised", function() {
		bool = true;
	});

	document.dispatchEvent(showGroupPanel);
	ok(bool);
});

/**
 * Verifica la corretta inizializzazione degli elementi grafici del pannello
 * 
 * @version 2.0
 * @author Elena Zecchinato
 * @author Diego Beraldin
 */
test("testDisplay()", function() {
	var i = 0;
	document.dispatchEvent(showGroupPanel);

	tester.display();

	var element = document.getElementById("GroupPanel");
	equal(element.children.length, 3);
	i++;
	element = element.children[0];
	equal(element.nodeName, "DIV");
	i++;
	element = element.children[0];
	equal(element.nodeName, "H1");
	i++;
	equal(element.innerHTML.trim(), "GESTIONE GRUPPI");
	i++;
	element = element.parentElement.parentElement.children[1];
	equal(element.nodeName, "BUTTON");
	i++;
	equal(element.id, "addGroupButton");
	i++;
	equal(element.innerHTML.trim(), "Aggiungi Gruppo");
	i++;
	element = element.parentElement.children[2];
	equal(element.nodeName, "UL");
	i++;
	equal(element.id, "groupList");
	i++;

	expect(i);
});

/**
 * Verifica che la lista dei gruppi sia visualizzata correttamente e che di
 * default la lista dei contatti che apartengono al gruppo sia visualizzata in
 * forma compatta.
 * 
 * @version 2.0
 * @author Diego Beraldin
 */
test("testDisplayList()", function() {
	var i = 0;
	document.dispatchEvent(showGroupPanel);
	tester.display();

	var element = document.getElementById("groupList");
	equal(element.children.length, 1);
	i++;
	element = element.children[0];
	equal(element.nodeName, "LI");
	i++;
	equal(element.children.length, 5);
	i++;
	equal(element.children[0].nodeName, "IMG");
	i++;
	equal(element.children[0].src, host + "img/expandGroupImg.png");
	i++;
	equal(element.children[1].innerHTML.trim(), "amici");
	i++;
	equal(element.children[2].nodeName, "IMG");
	i++;
	equal(element.children[2].src, host + "img/deleteGroupImg.png");
	i++;
	equal(element.children[3].nodeName, "IMG");
	i++;
	equal(element.children[3].src, host + "img/addToGroupImg.png");
	i++;
	element = element.children[4];
	equal(element.nodeName, "UL");
	i++;
	equal(element.className, "collapsedList");
	i++;
	equal(element.children.length, 1);
	i++;
	element = element.children[0];
	equal(element.nodeName, "LI");
	i++;
	equal(element.childNodes[0].nodeValue, "indirizzo2@dominio.it");
	i++;
	equal(element.children[0].src, host + "img/deleteContactImg.png");
	i++;

	expect(i);
});

/**
 * Verifica che sia possibile aggiungere un nuovo gruppo via interfaccia utente
 * 
 * @version 2.0
 * @author Diego Beraldin
 */
test("testAddGroupByBlick()", function() {
	document.dispatchEvent(showGroupPanel);
	tester.display();
	// questo non dovrei farlo
	window.prompt = function() {
		return "pippo";
	};
	var bool = false;
	document.addEventListener("createGroup", function() {
		bool = true;
	});
	var element = document.getElementById("addGroupButton");

	element.dispatchEvent(new MouseEvent("click"));

	ok(bool);
});

/**
 * Verifica che sia possibile espandere la lista dei contatti di un gruppo
 * tramite un click nell'interfaccia utente
 * 
 * @version 2.0
 * @author Diego Beraldin
 */
test("testExpandGroupByClick()", function() {
	document.dispatchEvent(showGroupPanel);
	tester.display();
	var element = document.getElementById("groupList");
	element = element.children[0].children[0];

	element.dispatchEvent(new MouseEvent("click"));

	element = element.parentElement.children[4];
	equal(element.className, "uncollapsedList");
});

/**
 * Verifica che sia possibile compattare la lista dei contatti di un gruppo
 * tramite un click nell'interfaccia utente
 * 
 * @version 2.0
 * @author Diego Beraldin
 */
test("testCollapseGroupByClick()", function() {
	document.dispatchEvent(showGroupPanel);
	tester.display();
	var element = document.getElementById("groupList");
	element = element.children[0].children[0];

	element.dispatchEvent(new MouseEvent("click"));
	element.dispatchEvent(new MouseEvent("click"));

	element = element.parentElement.children[4];
	equal(element.className, "collapsedList");
});

/**
 * Verifica la possibilità di cancellare un nuovo gruppo da interfaccia grafica
 * 
 * @version 2.0
 * @author Diego Beraldin
 */
test("testDeleteGroupByClick()", function() {
	document.dispatchEvent(showGroupPanel);
	tester.display();
	// questo non dovrei farlo
	window.confirm = function() {
		return true;
	};
	var bool = false;
	document.addEventListener("deleteGroup", function() {
		bool = true;
	});
	var element = document.getElementById("groupList");
	element = element.children[0].children[2];

	element.dispatchEvent(new MouseEvent("click"));

	ok(bool);
});

/**
 * Verifica che la creazione del form per l'aggiunta di un contatto all'interno
 * di un gruppo sia portata a termine correttamente.
 * 
 * @version 2.0
 * @author Diego Beraldin
 */
test("testAddToGroupByClick()", function() {
	var i = 0;
	document.dispatchEvent(showGroupPanel);
	tester.display();
	var element = document.getElementById("groupList");
	element = element.children[0].children[3];

	element.dispatchEvent(new MouseEvent("click"));

	element = element.parentElement.children[4];
	equal(element.className, "uncollapsedList");
	i++;
	element = document.getElementsByTagName("form")[0];
	equal(element.children.length, 3);
	i++;
	element = element.children[0];
	equal(element.nodeName, "FIELDSET");
	i++;
	element = element.parentElement.children[1];
	equal(element.nodeName, "BUTTON");
	i++;
	equal(element.innerHTML.trim(), "Annulla");
	i++;
	element = element.parentElement.children[2];
	equal(element.nodeName, "BUTTON");
	i++;
	equal(element.innerHTML.trim(), "Aggiungi");
	i++;

	expect(i);
});

/**
 * Verifica che sia possibile rimuovere un contatto da un gruppo via UI
 * 
 * @version 2.0
 * @author Diego Beraldin
 */
test("testDeleteFromGroupByClick()", function() {
	var i = 0;
	document.dispatchEvent(showGroupPanel);
	tester.display();
	var element = document.getElementById("groupList");
	element = element.children[0].children[4].children[0].children[0];
	var contact;
	document.addEventListener("removeContactFromGroup", function(evt) {
		contact = evt.contact;
	});

	element.dispatchEvent(new MouseEvent("click"));

	equal(contact.name, "Paolino");
	i++;
	equal(contact.surname, "Paperino");
	i++;

	expect(i);
});