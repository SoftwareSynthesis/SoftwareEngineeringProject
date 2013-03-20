module("AddressBookPanelPresenterTest", {
	setup: function() {
		// stub di interfaccia grafica
    	element = document.createElement("div");
    	element.setAttribute("id", "AddressBookPanel");
    	element.style.position = "absolute";
    	element.style.left ="-999em";
    	
    	document.body.appendChild(element);
    	
    	// oggetto da testare
		tester = new AddressBookPanelPresenter("php_stubs/AddressBook");
	},
	teardown: function() {}
});

test("testSetup()", function() {
	tester.setup();

	expect(0);
});

test("testInitialize()", function() {
	tester.initialize();

	expect(0);
});