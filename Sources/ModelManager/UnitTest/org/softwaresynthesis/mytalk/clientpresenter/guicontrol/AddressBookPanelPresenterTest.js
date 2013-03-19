module("AddressBookPanelPresenterTest", {
	setup: function() {
		// stub di interfaccia grafica
    	element = document.createElement("div");
    	element.setAttribute("id", "AddressBookPanel");
    	element.style.position = "absolute";
    	element.style.left ="-999em";
    	
    	document.body.appendChild(element);
    	
    	// oggetto da testare
		tester = new AddressBookPanelPresenter("AddressBook");
	},
	teardown: function() {}
});

test("testInitialize()", function() {
	tester.initialize();

	console.debug(tester.contacts);
	expect(0);
});