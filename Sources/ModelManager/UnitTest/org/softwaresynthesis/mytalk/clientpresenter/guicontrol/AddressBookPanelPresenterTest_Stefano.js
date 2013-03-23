module("AddressBookPanelPresenterTest", {
	setup: function() {
		// stub di interfaccia grafica
    	element = document.createElement("div");
    	element.setAttribute("id", "AddressBookPanel");
    	element.style.position = "absolute";
    	element.style.left ="-999em";
    	
    	document.body.appendChild(element);
    	configurationFile = "/ModelManager/WebContent/Conf/servletlocationtest.xml";
    	// oggetto da testare
		tester = new AddressBookPanelPresenter("php_stubs/AddressBook", ".php");
		
		
	},
	teardown: function() {}
});



test("testInitialize()", function() {
	
	var i=0;
	tester.initialize();
	

		
	var list = element.childNodes;
		// controllo che abbia esattamente quattro figli
	equal(list.length,4,"il numero di figli dell'elemento restituito e' 4");
	i++;
	
	equal(list[0].nodeName,"DIV", "il primo figlio e' un div");
	i++;
	equal(list[1].nodeName,"DIV", "il secondo figlio e' un div");
	i++;
	equal(list[2].nodeName,"DIV", "il terzo figlio e' un div");
	i++;
	equal(list[3].nodeName,"DIV", "il quarto figlio e' un div");
	i++;
	
	equal(list[0].getAttribute("id"),"divFilter","il primo div si chiama divFilter");
	i++;
	equal(list[1].getAttribute("id"),"divSort","il secondo div si chiama divSort");
	i++;
	equal(list[2].getAttribute("id"),"divList","il terzo div si chiama divList");
	i++;
	equal(list[3].getAttribute("id"),"divGroup","il quarto div si chiama divGroup");
	i++;
	
	
	var Filter=list[0].childNodes;
	equal(Filter.length,2,"il numero di figli di Filter e' 2");
	i++;
	
	equal(Filter[0].nodeName,"INPUT","il primo figlio di divFilter e' un input");
	i++;
	equal(Filter[0].getAttribute("type"),"text","il tipo dell'input e' text");
	i++;
	
	equal(Filter[1].nodeName,"INPUT","il secondo figlio di divFilter e' un input");
	i++;
	equal(Filter[1].getAttribute("type"),"image","il tipo dell'input e' button");
	i++;
	
	var Sort=list[1].childNodes;
	equal(Sort.length,1,"il numero di figli di Sort e' 1");
	i++;
	equal(Sort[0].nodeName,"SELECT","il figlio di Sort e' un select");
	i++;
	equal(Sort[0].getAttribute("id"),"selectSort","il nome del select e' SelectSort");
	i++;
	
	
	var List=list[2].childNodes;
	equal(List.length,1,"il numero di figli di List e' 1");
	i++;
	equal(List[0].nodeName,"UL","il figlio di List e' una lista ul");
	i++;
	
	var Group=list[3].childNodes;
	equal(Group.length,1,"il numero di figli di Group e' 1");
	i++;
	equal(Group[0].nodeName,"SELECT","il figlio di Group e' un select");
	i++;
	equal(Group[0].getAttribute("id"),"selectGroup","il nome del select e' SelectSort");
	i++;
	
	
	
	
	expect(i)
});




test("testSetup()", function() {
		
	
	var i=0;
	tester.setup();
	var list=document.getElementById("AddressBookList").childNodes;
	//controllo che abbia scaricato tutti contatti dal server
	equal(list.length, 2, "numero corretto di contatti nella rubrica");
	i++;
	
	equal(list[0].nodeName, "LI", "il primo figlio dell'elemento e' un figlio della lista");
	i++;
	
	var laura=list[1].childNodes;
	
	equal(laura.length,3, "ci sono tre figli");
	i++;
	
	equal(laura[0].nodeName, "IMG", "il primo figlio dell'elemento e' un immagine(profilo)");
	i++;
	
	equal(laura[1].nodeName, "#text", "il primo figlio dell'elemento e' un nodo testo");
	i++;
	
	equal(laura[2].nodeName, "IMG", "il primo figlio dell'elemento e' un immagine(stato)");
	i++;
	
	
	equal(laura[0].getAttribute("src"), "y.png", "l'immagine (di Laura) e' corretta");
	i++;
	
	
	equal(laura[1].data, "Laura Pausini", "il nome e congnome sono corretti");
	i++;
	
	equal(laura[2].getAttribute("src"), "img/stateoffline.png", "lo stato (di Laura) e' corretto");
	i++;
	
	
	
	var flavia=list[0].childNodes;
	
	equal(flavia.length,3, "ci sono tre figli");
	i++;
	
	equal(flavia[0].nodeName, "IMG", "il primo figlio dell'elemento e' un immagine(profilo)");
	i++;
	
	equal(flavia[1].nodeName, "#text", "il primo figlio dell'elemento e' un nodo testo");
	i++;
	
	equal(flavia[2].nodeName, "IMG", "il primo figlio dell'elemento e' un immagine(stato)");
	i++;
	
	
	equal(flavia[0].getAttribute("src"), "x.png", "l'immagine (di Flavia) e' corretta");
	i++;
	
	
	equal(flavia[1].data, "Flavia Bacco", "il nome e congnome sono corretti");
	i++;
	
	equal(flavia[2].getAttribute("src"), "img/stateavailable.png", "lo stato (di Flavia) e' corretto");
	i++;
	
	expect(i);
	//flavia[2] non dovrebbe essere la mail? (fonte AddressBookGetContacts.php, con 3 parametri e' solo LoginAuthentication.php)
	//mancava test del popolamento e controllo dei gruppi
	var listGroup=document.getElementById("selectGroup").childNodes;
	equal(listGroup.length, 2, "numero corretto di gruppi nella rubrica");
	i++;
	equal(listGroup[0].nodeName, "LI", "il primo figlio dell'elemento e' un figlio della lista");
	i++;
	var famiglia=list[1].childNodes;
	
	equal(famiglia.length,3, "ci sono tre figli");
	i++;
	
	equal(famiglia[0].nodeName, "#text", "il primo figlio dell'elemento e' un nodo testo");
	i++;
	
	equal(famiglia[1].nodeName, "#id", "il secondo figlio dell'elemento e' un id");
	i++;
	
	equal(famiglia[2].nodeName, "#array", "il secondo figlio dell'elemento e' un array di contatti");
	i++;
	
});

//test nascondere il pannello della rubrica
test("testHide()", function() {
	tester.hide();
	equal(element.style.display, "none", "il pannello è stato nascosto correttamente");
});

//test aggiunta contatto in rubrica (nome_contatto da cambiare impostando stub)
//non penso sia corretto, da correggere sicuramente
test("testAddContact()", function(){
	var i=0;
	var newContact= tester.addContact(nome_contatto);
	equal(newContact[0].nodeName, "#text", "il nome del contatto e' stato inserito");
	i++;
});

//test rimozione contatto da rubrica, da verificare/correggere
test("testRemoveContact()", function(){
	var i=0;
	var newList=tester.removeContact(Flavia)
	equal(newList.length, 2, "nuovo numero di contatti nella rubrica corretto");
	i++;
});

//TODO test aggiunta contatto in un gruppo
test("testAddContactInGroup()", function(){
});

//TODO test rimozione contatto da un gruppo	
test("testRemoveContactFromGroup()", function(){

});

//test aggiunta gruppo alla rubrica
test("testAddGroup()", function(){
	var i=0;
	var newGroup= tester.addGroup(nome_gruppo);
	equal(newGroup.length, 3, "nuovo numero di gruppi nella rubrica corretto");
	i++;
});

//test rimozione gruppo dalla rubrica
test("testDeleteGroup()", function(){
	var i=0;
	var newList=tester.removeGroup(familiari)
	equal(newList.length, 2, "nuovo numero di gruppi nella rubrica corretto");
	i++;
});

//TODO test filtro da una stringa (nome contatto)
test("testApplyFilterByString()", function(){
});

//TODO test filtra per gruppi (nome gruppo)
test("testApplyFilterByGroup()", function(){
});

//TODO test mostra filtri preimpostati
test("testShowFilter()", function(){
	//ha TODO nel codice da testare
});

//test se il contatto e' gia' presente in rubrica (sbagliato sicuro)
test("testContactAlreadyPresent()", function(){
	var i=0;
	var presente=tester.ContactAlreadyPresent(Laura)
	equal(presente, true, "contatto gia' presente in rubrica e verificato correttamente");
	i++;
});

//TODO test blocca un contatto
test("testBlockUser()", function(){
});

//TODO test sblocca un contatto
test("testUnlockUser()", function(){	
});

//TODO test ritorna il gruppo in cui e' un contatto
test("testGetGroupsWhereContactsIs()", function(){
	
});
