/**
 * Verifica della classe MainPresenter.
 * 
 * @version 2.0
 * @author Diego Beraldin
 */
module("MainPanelPresenter", {
	setup : function() {
		// stub di mediator
		mediator = {
			getView : function(someString) {
				document.dispatchEvent(new CustomEvent("eventRaised"));
				var viewRequest = new XMLHttpRequest();
				viewRequest.open("POST", "clientview/MainView.html", false);
				viewRequest.send();
				return viewRequest.response;
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
 * 
 * @version 2.0
 * @author Diego Beraldin
 */
test("testInitialize()", function() {
	var i = 0;

	var mainView = mediator.getView("main");
	tester.initialize(mainView);
	document.getElementById("MainPanel").style.display = "none";

	var element = document.getElementById("MainPanel");
	equal(element.nodeName, "DIV");
	i++;
	equal(element.id, "MainPanel");
	i++;
	expect(i);
});

/**
 * Verifica che sia possibile aggiungere un pannello figlio dentro il MainPanel.
 * 
 * @version 2.0
 * @author Diego Beraldin
 */
test("testDisplayChildPanel()", function() {
	var i = 0;
	tester.initialize(mediator.getView("main"));
	document.getElementById("MainPanel").style.display = "none";

	tester.displayChildPanel("<div></div>");

	var element = document.getElementById("MainPanel");
	equal(element.children.length, 1);
	i++;

	equal(element.children[0].nodeName, "DIV");
	i++;

	expect(i);
});

/**
 * Verifica che sia possibile distruggere il pannello
 * 
 * @version 2.0
 * @author Diego Beraldin
 */
test("testDestroy()", function() {
	var i = 0;
	tester.initialize(mediator.getView("main"));
	document.getElementById("MainPanel").style.display = "none";

	tester.destroy();
	var element = document.getElementById("MainPanel");
	equal(element, null);
	i++;

	expect(i);
});

/**
 * Verifica che il presenter risponda all'evento
 * 
 * @version 2.0
 * @author Diego Beraldin
 */
test("testOnShowMainPanel()", function() {
	var bool = false;
	// così catturo il sollevamento dell'evento :)
	document.addEventListener("eventRaised", function() {
		bool = true;
	});
	document.dispatchEvent(new CustomEvent("showMainPanel"));
	ok(bool);
});