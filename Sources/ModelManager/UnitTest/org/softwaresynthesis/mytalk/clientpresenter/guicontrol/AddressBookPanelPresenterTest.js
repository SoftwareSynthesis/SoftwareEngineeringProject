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
	
	tester.setup();
	//controllo che abbia scaricato tutti contatti dal server
	equal(ulList.childNodes.length, 2, "numero corretto di contatti nella rubrica");
	expect(1);
});
/*
test("testInitialize()", function() {
	tester.initialize();

	expect(0);
});*/