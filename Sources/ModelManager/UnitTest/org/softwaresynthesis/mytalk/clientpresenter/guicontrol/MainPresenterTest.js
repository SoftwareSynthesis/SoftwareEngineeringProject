/**
 * Verifica della classe MainPresenter.
 */
module("MainPanelPresenter", {
	setup : function() {
		// stub di mediator
		mediator = {
			getView : function(someString) {
				var viewRequest = new XMLHttpRequest();
				viewRequest.open("POST", "clientview/MainView.html", false);
				viewRequest.send();
				var div = document.createElement("div");
				div.innerHTML = viewRequest.responseText;
				document.dispatchEvent(new CustomEvent("eventRaised"));
				return div.childNodes[0];
			}
		};
		// inizializza l'oggetto da testare
		tester = new MainPanelPresenter();

	},
	teardown : function() {
		var element = document.getElementById("MainPanel");
		if (element) {
			document.body.removeChild(element.parentElement);
		}
	}
});

/**
 * Verifica l'inizializzazione del pannello
 */
test("testInitialize()", function() {
	var i = 0;

	var mainView = mediator.getView("main");
	tester.initialize(mainView);

	var element = document.getElementById("MainPanel");
	equal(element.nodeName, "DIV", "elemento creato correttamente");
	i++;
	equal(element.id, "MainPanel", "id impostato correttamente");
	i++;
	expect(i);
});

/**
 * Verifica che sia possibile aggiungere un pannello figlio dentro il MainPanel.
 */
test("testDisplayChildPanel()", function() {
	var i = 0;

	tester.initialize(mediator.getView("main"));

	var child = document.createElement("div");
	tester.displayChildPanel(child);

	var element = document.getElementById("MainPanel");
	equal(element.childNodes.length, 1,
			"il pannello viene effettivamente aggiunto");
	i++;

	equal(element.childNodes[0].nodeName, "DIV", "tipo corretto del figlio");
	i++;

	expect(i);
});

/**
 * Verifica che sia possibile distruggere il pannello
 */
test("testDestroy()", function() {
	var i = 0;
	tester.initialize(mediator.getView("main"));
	tester.destroy();
	var element = document.getElementById("MainView");
	equal(element, null, "pannello rimosso correttamente");
	i++;

	expect(i);
});

/**
 * Verifica che il presenter risponda all'evento
 */
test("testOnShowMainPanel()", function() {
	var bool = false;
	// così catturo il sollevamento dell'evento :)
	document.addEventListener("eventRaised", function() {
		bool = true;
	})
	document.dispatchEvent(new CustomEvent("showMainPanel"));
	ok(bool);
});