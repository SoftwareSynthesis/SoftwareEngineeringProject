module("AddressBookPanelPresenterTest", {
	setup: function() {
		// stub di interfaccia grafica
    	element = document.createElement("div");
    	element.setAttribute("id", "AddressBookPanel");
    	element.style.position = "absolute";
    	element.style.left ="-999em";
    	
    	document.body.appendChild(element);
    	
		
    	// oggetto da testare
		tester = new AddressBookPanelPresenter("php_stubs/AddressBook", ".php");
		
		
	},
	teardown: function() {}
});

test("testSetup()", function() {
		
	var ulList = document.createElement('ul');
	ulList.id="AddressBookList";
	element.appendChild(ulList);
	var gr=document.createElement("select");
	gr.id="selectGroup";
	element.appendChild(gr);
	var i=0;
	tester.setup();
	var list=ulList.childNodes;
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
	
	
	equal(laura[0].getAttribute("src"), "y.png", "l'immagine (di Laura) è corretta");
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
	
	
	equal(flavia[0].getAttribute("src"), "x.png", "l'immagine (di Flavia) è corretta");
	i++;
	
	
	equal(flavia[1].data, "Flavia Bacco", "il nome e congnome sono corretti");
	i++;
	
	equal(flavia[2].getAttribute("src"), "img/stateavailable.png", "lo stato (di Flavia) e' corretto");
	i++;
	
	
	
	
	
	expect(i);
});
/*
test("testInitialize()", function() {
	tester.initialize();

	expect(0);
});*/