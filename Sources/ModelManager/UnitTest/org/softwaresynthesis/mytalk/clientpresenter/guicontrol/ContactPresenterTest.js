/**
 * Verifica della classe ContactPresenter
 * 
 * @version 2.0
 * @author Diego Beraldin
 */
module("ContactPanelPresenterTest", {
	setup : function() {
		// calcola l'indirizzo base
		host = window.location.protocol + "//" + window.location.host
				+ window.location.pathname;
		host = host.substr(0, host.length - 10);
		// stub di mediator
		mediator = {
			getView : function(someString) {
				var viewRequest = new XMLHttpRequest();
				viewRequest.open("POST", "clientview/ContactView.html", false);
				viewRequest.send();
				var div = document.createElement("div");
				div.innerHTML = viewRequest.responseText;
				if (document.getElementById("ContactPanel") == null) {
					document.body.appendChild(div);
					div.style.display = "none";
				}
			},
			getGroupsWhereContactIs : function(someContact) {
				return {
					1 : {
						id : 1,
						name : "amici",
						contacts : [ 2, 0 ]
					}
				};
			},
			contactAlreadyPresent : function(someContact, someGroup) {
				return true;
			}
		};
		// brutti eventi cattivi (globali)
		showContactPanel = new CustomEvent("showContactPanel");
		blockContact = new CustomEvent("blockContact");
		unlockContact = new CustomEvent("unlockContact");
		removeContactFromGroup = new CustomEvent("removeContactFromGroup");
		// oggetto da testare
		tester = new ContactPanelPresenter();
	},
	teardown : function() {
		var element = document.getElementById("ContactPanel");
		if (element) {
			document.body.removeChild(element.parentElement);
		}
	}
});

/**
 * Verifica che un contatto online, presente in rubrica e non bloccato sia
 * visualizzato correttamente.
 * 
 * @version 2.0
 * @author Diego Beraldin
 */
test("testShowUnblockedOnlineContact()", function() {
	var i = 0;
	var event = new CustomEvent("showContactPanel");
	event.contact = {
		name : "Gastone",
		surname : "Paperone",
		id : 2,
		email : "indirizzo4@dominio.it",
		picturePath : "img/contactImg/Default.png",
		state : "available",
		blocked : false
	};
	document.dispatchEvent(event);

	tester.display();

	var element = document.getElementById("ContactPanel");
	equal(element.nodeName, "DIV");
	i++;
	equal(element.children.length, 6);
	i++;
	element = element.children[0];
	equal(element.nodeName, "DIV");
	i++;
	element = element.children[0];
	equal(element.nodeName, "H1");
	i++;
	equal(element.innerHTML.trim(), "SCHEDA CONTATTO");
	i++;
	element = element.parentElement.parentElement.children[1];
	equal(element.nodeName, "DIV");
	i++;
	equal(element.style.display, "none");
	i++;
	element = element.parentElement.children[2];
	equal(element.nodeName, "IMG");
	i++;
	equal(element.id, "contactAvatar");
	i++;
	equal(element.src, host + event.contact.picturePath);
	i++;
	element = element.parentElement.children[3];
	equal(element.nodeName, "UL");
	i++;
	equal(element.id, "ulData");
	i++;
	equal(element.children.length, 3);
	i++;
	element = element.children[0];
	equal(element.nodeName, "LI");
	i++;
	equal(element.innerHTML.trim(), "Nome: " + event.contact.name);
	i++;
	element = element.parentElement.children[1];
	equal(element.nodeName, "LI");
	i++;
	equal(element.innerHTML.trim(), "Cognome: " + event.contact.surname);
	i++;
	element = element.parentElement.children[2];
	equal(element.nodeName, "LI");
	i++;
	equal(element.innerHTML.trim(), "Email: " + event.contact.email);
	i++;
	element = element.parentElement.parentElement.children[4];
	equal(element.nodeName, "DIV");
	i++;
	equal(element.children.length, 1);
	i++;
	element = element.children[0];
	equal(element.nodeName, "SPAN");
	i++;
	equal(element.childNodes[0].nodeValue, "amici");
	i++;
	equal(element.childNodes[1].nodeName, "IMG");
	i++;
	equal(element.childNodes[1].src, host + "img/close.png");
	i++;
	element = element.parentElement.parentElement.children[5];
	equal(element.nodeName, "DIV");
	i++;
	equal(element.id, "buttonsDiv");
	i++;
	equal(element.children.length, 8);
	i++;
	element = element.children[0];
	equal(element.innerHTML.trim(), "Chiama");
	i++;
	equal(element.style.display, "inline");
	i++;
	element = element.parentElement.children[1];
	equal(element.innerHTML.trim(), "Video-chiama");
	i++;
	equal(element.style.display, "inline");
	i++;
	element = element.parentElement.children[2];
	equal(element.innerHTML.trim(), "Avvia chat testuale");
	i++;
	equal(element.style.display, "inline");
	i++;
	element = element.parentElement.children[3];
	equal(element.innerHTML.trim(), "Lascia messaggio in Segreteria");
	i++;
	equal(element.style.display, "none");
	i++;
	element = element.parentElement.children[4];
	equal(element.innerHTML.trim(), "Aggiungi in rubrica");
	i++;
	equal(element.style.display, "none");
	i++;
	element = element.parentElement.children[5];
	equal(element.innerHTML.trim(), "Rimuovi dalla rubrica");
	i++;
	equal(element.style.display, "inline");
	i++;
	element = element.parentElement.children[6];
	equal(element.innerHTML.trim(), "Blocca");
	i++;
	equal(element.style.display, "inline");
	i++;
	element = element.parentElement.children[7];
	equal(element.innerHTML.trim(), "Sblocca");
	i++;
	equal(element.style.display, "none");
	i++;

	expect(i);
});

/**
 * Verifica che un contatto offline, presente in rubrica e bloccato sia
 * visualizzato correttamente.
 * 
 * @version 2.0
 * @author Diego Beraldin
 */
test("testShowUnblockedOnlineContact()", function() {
	var i = 0;

	var event = new CustomEvent("showContactPanel");
	event.contact = {
		name : "Gastone",
		surname : "Paperone",
		id : 2,
		email : "indirizzo4@dominio.it",
		picturePath : "img/contactImg/Default.png",
		state : "offline",
		blocked : true
	};
	document.dispatchEvent(event);

	tester.display();

	var element = document.getElementById("ContactPanel");
	equal(element.nodeName, "DIV");
	i++;
	equal(element.children.length, 6);
	i++;
	element = element.children[0];
	equal(element.nodeName, "DIV");
	i++;
	element = element.children[0];
	equal(element.nodeName, "H1");
	i++;
	equal(element.innerHTML.trim(), "SCHEDA CONTATTO");
	i++;
	element = element.parentElement.parentElement.children[1];
	equal(element.nodeName, "DIV");
	i++;
	equal(element.style.display, "block");
	i++;
	element = element.parentElement.children[2];
	equal(element.nodeName, "IMG");
	i++;
	equal(element.id, "contactAvatar");
	i++;
	equal(element.src, host + event.contact.picturePath);
	i++;
	element = element.parentElement.children[3];
	equal(element.nodeName, "UL");
	i++;
	equal(element.id, "ulData");
	i++;
	equal(element.children.length, 3);
	i++;
	element = element.children[0];
	equal(element.nodeName, "LI");
	i++;
	equal(element.innerHTML.trim(), "Nome: " + event.contact.name);
	i++;
	element = element.parentElement.children[1];
	equal(element.nodeName, "LI");
	i++;
	equal(element.innerHTML.trim(), "Cognome: " + event.contact.surname);
	i++;
	element = element.parentElement.children[2];
	equal(element.nodeName, "LI");
	i++;
	equal(element.innerHTML.trim(), "Email: " + event.contact.email);
	i++;
	element = element.parentElement.parentElement.children[4];
	equal(element.nodeName, "DIV");
	i++;
	equal(element.children.length, 1);
	i++;
	element = element.children[0];
	equal(element.nodeName, "SPAN");
	i++;
	equal(element.childNodes[0].nodeValue, "amici");
	i++;
	equal(element.childNodes[1].nodeName, "IMG");
	i++;
	equal(element.childNodes[1].src, host + "img/close.png");
	i++;
	element = element.parentElement.parentElement.children[5];
	equal(element.nodeName, "DIV");
	i++;
	equal(element.id, "buttonsDiv");
	i++;
	equal(element.children.length, 8);
	i++;
	element = element.children[0];
	equal(element.innerHTML.trim(), "Chiama");
	i++;
	equal(element.style.display, "none");
	i++;
	element = element.parentElement.children[1];
	equal(element.innerHTML.trim(), "Video-chiama");
	i++;
	equal(element.style.display, "none");
	i++;
	element = element.parentElement.children[2];
	equal(element.innerHTML.trim(), "Avvia chat testuale");
	i++;
	equal(element.style.display, "none");
	i++;
	element = element.parentElement.children[3];
	equal(element.innerHTML.trim(), "Lascia messaggio in Segreteria");
	i++;
	equal(element.style.display, "inline");
	i++;
	element = element.parentElement.children[4];
	equal(element.innerHTML.trim(), "Aggiungi in rubrica");
	i++;
	equal(element.style.display, "none");
	i++;
	element = element.parentElement.children[5];
	equal(element.innerHTML.trim(), "Rimuovi dalla rubrica");
	i++;
	equal(element.style.display, "inline");
	i++;
	element = element.parentElement.children[6];
	equal(element.innerHTML.trim(), "Blocca");
	i++;
	equal(element.style.display, "none");
	i++;
	element = element.parentElement.children[7];
	equal(element.innerHTML.trim(), "Sblocca");
	i++;
	equal(element.style.display, "inline");
	i++;

	expect(i);
});

/**
 * Verifica il comportamento del pannello quando non è impostato alcun contatto
 * da visualizzare (deve comparire a schermo un messaggio di errore)
 * 
 * @version 2.0
 * @author Diego Beraldin
 */
test("testSimulateContactNotSet()", function() {
	var capture = "";
	// questo non dovrei farlo!
	window.alert = function(message) {
		capture = message;
	};
	document.dispatchEvent(new CustomEvent("showContactPanel"));

	tester.display();

	equal(capture, "Contatto da visualizzare non impostato!");
});

/**
 * Verifica la possibilità di bloccare un contatto non bloccato dalla UI
 * 
 * @version 2.0
 * @author Diego Beraldin
 */
test("testSimulateBlockByClick()", function() {
	var i = 0;
	var event = new CustomEvent("showContactPanel");
	event.contact = {
		name : "Gastone",
		surname : "Paperone",
		id : 2,
		email : "indirizzo4@dominio.it",
		picturePath : "img/contactImg/Default.png",
		state : "available",
		blocked : false
	};
	document.dispatchEvent(event);
	tester.display();

	var button = document.getElementById("blockButton");
	button.dispatchEvent(new MouseEvent("click"));

	equal(blockContact.contact, event.contact);
	i++;
	ok(event.contact.blocked);
	i++;

	tester.display();
	var element = document.getElementById("displayBlockedDiv");
	equal(element.style.display, "block");
	i++;
	element = document.getElementById("blockButton");
	equal(element.style.display, "none");
	i++;
	element = document.getElementById("unlockButton");
	equal(element.style.display, "inline");
	i++;

	expect(i);
});

/**
 * Verifica la possibilità di sbloccare un contatto bloccato dalla UI
 * 
 * @version 2.0
 * @author Diego Beraldin
 */
test("testSimulateUnblockByClick()", function() {
	var i = 0;
	var event = new CustomEvent("showContactPanel");
	event.contact = {
		name : "Gastone",
		surname : "Paperone",
		id : 2,
		email : "indirizzo4@dominio.it",
		picturePath : "img/contactImg/Default.png",
		state : "available",
		blocked : true
	};
	document.dispatchEvent(event);
	tester.display();

	var button = document.getElementById("unlockButton");
	button.dispatchEvent(new MouseEvent("click"));

	equal(unlockContact.contact, event.contact);
	i++;
	ok(!event.contact.blocked);
	i++;

	tester.display();
	var element = document.getElementById("displayBlockedDiv");
	equal(element.style.display, "none");
	i++;
	element = document.getElementById("blockButton");
	equal(element.style.display, "inline");
	i++;
	element = document.getElementById("unlockButton");
	equal(element.style.display, "none");
	i++;

	expect(i);
});

/**
 * Verifica la possibilità di rimuovere un contatto da un gruppo via UI
 * 
 * @version 2.0
 * @author Diego Beraldin
 */
test("testSimulateRemoveFromGroupByClick()", function() {
	var event = new CustomEvent("showContactPanel");
	event.contact = {
		name : "Gastone",
		surname : "Paperone",
		id : 2,
		email : "indirizzo4@dominio.it",
		picturePath : "img/contactImg/Default.png",
		state : "available",
		blocked : true
	};
	document.dispatchEvent(event);
	tester.display();

	var button = document.getElementById("groupsDiv").children[0].childNodes[1];
	button.dispatchEvent(new MouseEvent("click"));
	
	equal(removeContactFromGroup.contact, event.contact);
});