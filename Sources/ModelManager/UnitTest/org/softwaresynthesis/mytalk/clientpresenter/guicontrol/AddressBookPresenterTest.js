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
				var element = document.getElementById("MainPanel");
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
	equal(element.id, "inputButton");;
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
	
	tester.setup();
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
	equal(element.parentElement.childNodes[1].nodeValue.trim(), "a.rizzi@gmail.com");
	i++;
	element = element.parentElement.children[1];
	equal(element.nodeName, "IMG");
	i++;
	equal(element.src, host + "img/stateavailable.png");
	i++;

	expect(i);
});



// test("testDeleteGroup()", function() {
// var groups = {
// 0 : {
// name : "famiglia",
// id : 0,
// contacts : []
// }
// };
//
// tester.setGroups(groups);
//
// var group = tester.getGroups();
// var famiglia = group[0];
// var amici = {
// name : "amici",
// id : 1,
// contacts : []
// };
// var i = 0;
// try {
// tester.deleteGroup(amici);
// ok(false, "errore non rilevato");
// i++;
// } catch (err) {
// equal(err, "Il gruppo che stai cercando di eliminare non esiste.",
// "il gruppo coi id 2 non e' presente");
// i++;
// }
// equal(tester.deleteGroup(famiglia), true, "");
// i++;
//
// expect(i);
// });
//
// test("testHide()", function() {
// tester.hide();
// var element = document.getElementById("AddressBookPanel");
// equal(element.style.display, "none",
// "il pannello viene nascosto correttamente");
// });
//
// test(
// "testAddContact()",
// function() {
//
// tester.setContacts({
// 0 : {
// name : "Laura",
// surname : "Pausini",
// email : "laupau@gmail.com",
// id : "0"
// },
// 1 : {
// name : "Enrico",
// surname : "Botti",
// email : "enribot@gmail.com",
// id : "1"
// }
// });
//
// var flavia = {
// name : "Flavia",
// surname : "Bacco",
// email : "flaba@gmail.com",
// id : "4",
// picturePath : "yy.png",
// state : "offline",
// blocked : "false"
// };
// var contacts = tester.getContacts();
// var laura = contacts[0];
//
// var i = 0;
// try {
// tester.addContact(laura);
// ok(false, "errore non rilevato");
// i++;
// } catch (err) {
// equal(err, "Contatto già presente nella rubrica.",
// "aggiunta di laura non funziona perche e' gia presente in rubrica!");
// i++;
// }
//
// equal(tester.addContact(flavia), true,
// "flavia e' stata aggiunta correttamente");
// i++;
//
// expect(i);
// });
//
// test("testRemoveContact()", function() {
// tester.setContacts({
// 0 : {
// name : "Laura",
// surname : "Pausini",
// email : "laupau@gmail.com",
// id : "0"
// },
// 1 : {
// name : "Enrico",
// surname : "Botti",
// email : "enribot@gmail.com",
// id : "1"
// }
// });
//
// var i = 0;
// var contact = tester.getContacts();
// var laura = contact[0];
// var antonio = contact[1];
//
// var flavia = {
// name : "Flavia",
// surname : "Bacco",
// email : "flaba@gmail.com",
// id : "4",
// picturePath : "yy.png",
// state : "offline",
// blocked : "false"
// };
//
// try {
// tester.removeContact(flavia);
// ok(false, "errore non rilevato");
// i++;
// } catch (err) {
// equal(err, "Non puoi eliminare un contatto non presente in rubrica.",
// "rimozione di flavia non possibile perchè non esite!");
// i++;
// }
//
// equal(tester.removeContact(laura), true,
// "rimozione di laura andata a buon fine");
// i++;
// equal(tester.removeContact(antonio), true,
// "rimozione di laura andata a buon fine");
// i++;
//
// expect(i);
// });
//
// test(
// "testApplyFilterByString()",
// function() {
//
// tester.setContacts({
// 0 : {
// name : "Laura",
// surname : "Pausini",
// email : "laupau@gmail.com"
// },
// 1 : {
// name : "Serena",
// surname : "Pausini",
// email : "serpau@gmail.com"
// }
// });
//
// var i = 0;
//
// // controllo che funzioni il filtro per un contatto esistente
// // tramite il nome
// var cerca_nome = "Laura";
// var ele = tester.applyFilterByString(cerca_nome);
// equal(ele.length, 1, "Trova un solo contatto che ha nome Laura");
// i++;
// equal(ele[0], "0", "Il contatto Laura ha id 0");
// i++;
//
// // controllo che funzioni il filtro per un contatto esistente
// // tramite il cognome
// var cerca_cognome = "Pausini";
// ele = tester.applyFilterByString(cerca_cognome);
// equal(ele.length, 2, "Trova due contatti avente cogome Pausini");
// i++;
// equal(ele[0], "0", "il primo contatto Pausini ha id 0");
// i++;
// equal(ele[1], "1", "il secondo contatto Pausini ha id 1");
// i++;
//
// // controllo che funzioni il filtro per un contatto esistente
// // tramite il cognome
// var cerca_mail = "laupau@gmail.com";
// ele = tester.applyFilterByString(cerca_mail);
// equal(ele.length, 1,
// "Trova un solo contatto che ha email laupau@gmail.com");
// i++;
// equal(ele[0], "0",
// "il contatto che ha email laupau@gmail.com ha id 0");
// i++;
//
// // controllo che la funzione filtro non trovi un nome non presente
// var cerca_nome_non_presente = "Flavia";
// ele = tester.applyFilterByString(cerca_nome_non_presente);
// notEqual(ele.length, 1, "Non trova nessun contatto con nome Flavia");
// i++;
//
// expect(i);
// });
//
// test("testAddGroup()", function() {
//
// var element = document.getElementById("AddressBookPanel");
//
// var i = 0;
// var groups = {
// 1 : {
// name : "amici",
// id : "1",
// contacts : [ 0 ]
// }
// };
// tester.setGroups(groups);
//
// try {
// tester.addGroup("amici");
// ok(false, "errore non rilevato");
// i++;
// } catch (err) {
// equal(err, "Il gruppo che stai cercando di inserire esiste già.",
// "rilevato errore");
// i++;
// }
//
// expect(i);
// });
//
// test("testBlockUser()", function() {
// var i = 0;
// tester.setContacts({
// 0 : {
// name : "Laura",
// surname : "Pausini",
// email : "laupau@gmail.com",
// id : "0",
// picturePath : "xx.png",
// state : "offline",
// blocked : true
// },
// 1 : {
// name : "Flavia",
// surname : "Bacco",
// email : "flaba@gmail.com",
// id : "1",
// picturePath : "xx.png",
// state : "offline",
// blocked : false
// }
// });
//
// var contact = tester.getContacts();
// var laura = contact[0];
// var flavia = contact[1];
//
// try {
// tester.blockUser(laura);
// ok(false, "errore non rilevato");
// i++;
// } catch (err) {
// equal(err, "Contatto già bloccato.",
// "blocco di Laura non possibile...già bloccato!");
// i++;
// }
//
// equal(tester.blockUser(flavia), true, "verificato blocco attivato");
// i++;
// expect(i);
// });
//
// test("testUnlockUser()", function() {
// var i = 0;
// var element = document.getElementById("AddressBookPanel");
// var laura = {
// name : "Laura",
// surname : "Pausini",
// email : "laupau@gmail.com",
// id : "12",
// picturePath : "xx.png",
// state : "offline",
// blocked : "false"
// };
// tester.setContacts({
// 0 : {
// name : "Laura",
// surname : "Pausini",
// email : "laupau@gmail.com",
// id : "0",
// picturePath : "xx.png",
// state : "offline",
// blocked : true
// },
// 1 : {
// name : "Flavia",
// surname : "Bacco",
// email : "flaba@gmail.com",
// id : "1",
// picturePath : "xx.png",
// state : "offline",
// blocked : false
// }
// });
//
// var contact = tester.getContacts();
// var laura = contact[0];
// var flavia = contact[1];
//
// try {
// tester.unlockUser(flavia);
// ok(false, "errore non rilevato");
// i++;
// } catch (err) {
// equal(err, "Contatto già sbloccato.",
// "sblocco di Laura non possibile...già sbloccato!");
// i++;
// }
//
// equal(tester.unlockUser(laura), true, "blocco avvenuto con successo");
// i++;
//
// expect(i);
// });
//
// test("testapplyFilterByGroup()", function() {
//
// tester.setContacts({
// 0 : {
// name : "Laura",
// surname : "Pausini",
// email : "laupau@gmail.com",
// id : "0",
// picturePath : "xx.png",
// state : "offline",
// blocked : true
// },
// 1 : {
// name : "Flavia",
// surname : "Bacco",
// email : "flaba@gmail.com",
// id : "1",
// picturePath : "xx.png",
// state : "offline",
// blocked : false
// },
// 2 : {
// name : "Antonio",
// surname : "Rossi",
// email : "antros@gmail.com",
// id : "1",
// picturePath : "xx.png",
// state : "offline",
// blocked : false
// }
// });
//
// var contact = tester.getContacts();
// var laura = contact[0];
//
// tester.setGroups({
// 0 : {
// name : "famiglia",
// id : "0",
// contacts : [ 0, 1 ]
// }
// });
//
// var group = tester.getGroups();
// var famiglia = group[0];
// var amici = {
// name : "amici",
// id : "1",
// contacts : ""
// };
//
// var i = 0;
//
// // controllo che funzioni il filtro per un contatto esistente tramite il
// // nome
//
// var lista_contatti = tester.applyFilterByGroup(famiglia.id);
// equal(lista_contatti.length, 2,
// "Trova un solo contatto del gruppo contatti");
// i++;
//
// expect(i);
// });
//
// test(
// "testAddContactInGroup()",
// function() {
// var i = 0;
// tester.setContacts({
// 1 : {
// id : 1,
// name : "Laura",
// surname : "Pausini",
// email : "laupau@gmail.com"
// },
// 0 : {
// id : 0,
// name : "Antonio",
// surname : "Rossi",
// email : "antros@gmail.com"
// }
// });
// tester.setGroups({
// 0 : {
// id : 0,
// name : "famiglia",
// contacts : [ 1 ]
// }
// });
//
// var contact = tester.getContacts();
// var laura = contact[1];
// var antonio = contact[0];
// var group = tester.getGroups();
// var famiglia = group[0];
//
// try {
// tester.addContactInGroup(antonio, famiglia);
// ok(false, "errore non rilevato");
// i++;
// } catch (err) {
// equal(err, "Il contatto è già presente nel gruppo.",
// "Non è possibile aggiungere antonio...è già presente nel gruppo");
// i++;
// }
//
// equal(tester.addContactInGroup(laura, famiglia), true,
// "laura aggiunta correttamente");
// i++;
//
// expect(i);
// });
//
// test(
// "testDeleteContactFromGroup()",
// function() {
// var i = 0;
// tester.setContacts({
// 0 : {
// id : 0,
// name : "Laura",
// surname : "Pausini",
// email : "laupau@gmail.com"
// },
// 1 : {
// id : 1,
// name : "Antonio",
// surname : "Rossi",
// email : "antros@gmail.com"
//
// }
// });
// tester.setGroups({
// 0 : {
// id : 0,
// name : "famiglia",
// contacts : [ 0 ]
// }
// });
// var contact = tester.getContacts();
// var laura = contact[0];
// var antonio = contact[1];
// var group = tester.getGroups();
// var famiglia = group[0];
//
// try {
// tester.deleteContactFromGroup(antonio, famiglia);
// ok(false, "errore non rilevato");
// i++;
// } catch (err) {
// equal(err, "Il contatto non è presente nel gruppo.",
// "eliminazione di antonio dal gruppo fallita!Antonio non e' presente nel
// gruppo");
// i++;
// }
//
// tester.deleteContactFromGroup(laura, famiglia);
// equal(tester.deleteContactFromGroup(laura, famiglia), true,
// "laura rimossa correttamente");
// i++;
// expect(i);
// });
//
// // test se il contatto e' gia' presente in rubrica
// test("testContactAlreadyPresent()", function() {
// var i = 0;
// tester.setContacts({
// 0 : {
// name : "Laura",
// surname : "Pausini",
// email : "laupau@gmail.com"
// }
// });
// var contact = tester.getContacts();
// var laura = contact[0];
// equal(tester.contactAlreadyPresent(laura), true, "laura e' nella rubrica");
// i++;
// expect(i);
// });
//
// // TODO test ritorna il gruppo in cui e' un contatto
// test("testGetGroupsWhereContactsIs()", function() {
// var i = 0;
// tester.setContacts({
// 0 : {
// id : 0,
// name : "Laura",
// surname : "Pausini",
// email : "laupau@gmail.com"
// }
// });
// tester.setGroups({
// 0 : {
// id : 0,
// name : "famiglia",
// contacts : [ 0 ]
// },
// 1 : {
// id : 1,
// name : "amici",
// contacts : [ 0 ]
// }
// });
// var contact = tester.getContacts();
// var laura = contact[0];
// var groups = tester.getGroupsWhereContactsIs(laura);
// equal(groups[0], "famiglia", "laura e' correttamente nel gruppo famiglia");
// i++;
// equal(groups[1], "amici", "laura e' correttamente nel gruppo amici");
// i++;
//
// expect(i);
// });
//
// test("testShowFiltered", function() {
// var i = 0;
//
// var filter = {
// 0 : {
// name : "Fiorella",
// surname : "Mannoia",
// id : 0
// },
// 1 : {
// name : "Fiorella",
// surname : "Gaggi",
// id : 1
// }
// };
// tester.setContacts(filter);
// var ul = document.getElementById("AddressBookList");
// ul.innerHTML = "";
// tester.showFilter(filter);
// equal(ul.childNodes.length, 2, "numero di elementi corretto");
//
// i++;
// expect(i);
// });
