module("MainPanelPresenter", {
	setup : function() {
		// stub di interfaccia grafica
		var element = document.createElement("div");
		element.id = "MainPanel";
		element.style.position = "absolute";
		element.style.left = "-999em";
		document.body.appendChild(element);
		// inizializza l'oggetto da testare
		tester = new MainPanelPresenter();
		
	},
	teardown : function() {
	}
});

test("testHide()", function() {
	element = document.getElementById("MainPanel");
	tester.hide();
	equal(element.style.display, "none",
	"la proprietà display è stata settata correttamente");
});


test("testInitialize()", function() {
	var element = document.getElementById("MainPanel");
	tester.initialize();
	equal(element.style.display, "block", "il pannello è visualizzato correttamente");
	equal(element.innerHTML, "", "il pannello ha contenuto vuoto come atteso");
	expect(2);
});

test("testDisplayChildPanel()", function() {
	var i = 0;
	var child = document.createElement("div");
	tester.displayChildPanel(child);
	var element = document.getElementById("MainPanel");
	equal(element.childNodes.length, 1, "il pannello viene effettivamente aggiunto");
	i++;
	equal(element.childNodes[0], child, "l'inserimento non altera il pannello");
	i++;
	expect(i);
});