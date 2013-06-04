/**
 * Verifica di ToolsPresenter
 * 
 * @version 2.0
 * @author Diego Beraldin
 */
module("ToolsPanelPresenter", {
	setup : function() {
		// stub di mediator
		mediator = {
			getView : function(someString) {
				var viewRequest = new XMLHttpRequest();
				viewRequest.open("POST", "clientview/ToolsView.html", false);
				viewRequest.send();
				return viewRequest.response;
			}
		};
		// stub di communicationcenter
		communicationcenter = {
			disconnect : function() {
				document.dispatchEvent(new CustomEvent("eventRaised"));
			}
		};
		// inizializza l'oggetto da testare
		tester = new ToolsPanelPresenter();
		// evento
		changeMyState = Object();
	},
	teardown : function() {
		var element = document.getElementById("ToolsPanel");
		if (element) {
			document.body.removeChild(element.parentElement);
		}
	}
});

/**
 * Verifica la creazione e l'inizializzazione del pannello
 * 
 * @version 2.0
 * @author Diego Beraldin
 */
test("testInitialize()", function() {
	var i = 0;
	tester.initialize(mediator.getView("tools"));
	document.getElementById("ToolsPanel").style.display = "none";
	var element = document.getElementById("ToolsPanel");
	element.style.display = "none";

	equal(element.nodeName, "DIV");
	i++;

	var children = element.children;
	equal(children.length, 3);
	i++;

	var child = document.getElementsByClassName("panelHeader")[0];
	equal(child.nodeName, "DIV");
	i++;

	child = document.getElementById("selectState");
	equal(child.nodeName, "SELECT");
	i++;

	child = document.getElementById("ToolsList");
	equal(child.nodeName, "UL");
	i++;

	child = document.getElementById("liAnswering");
	equal(child.nodeName, "LI");
	i++;
	equal(child.innerHTML, "Segreteria");
	i++;

	child = document.getElementById("liSetting");
	equal(child.nodeName, "LI");
	i++;
	equal(child.innerHTML, "Impostazioni");
	i++;

	child = document.getElementById("liCallList");
	equal(child.nodeName, "LI");
	i++;
	equal(child.innerHTML, "Lista chiamate");
	i++;

	child = document.getElementById("liGroup");
	equal(child.nodeName, "LI");
	i++;
	equal(child.innerHTML, "Gruppi");
	i++;

	child = document.getElementById("liSearch");
	equal(child.nodeName, "LI");
	i++;
	equal(child.innerHTML, "Ricerca");
	i++;

	child = document.getElementById("liLogout");
	equal(child.nodeName, "LI");
	i++;
	equal(child.innerHTML, "Logout");
	i++;

	expect(i);
});

/**
 * Verifica la distruzione del pannello
 * 
 * @version 2.0
 * @author Diego Beraldin
 */
test("testDestroy()", function() {
	var i = 0;
	tester.initialize(mediator.getView("tools"));
	document.getElementById("ToolsPanel").style.display = "none";
	tester.destroy();
	var element = document.getElementById("ToolsPanel");
	equal(element, null);
	i++;
	expect(i);
});

/**
 * Verifica che sia possibile aggiungere il pulsante 'Comunicazione'
 * 
 * @version 2.0
 * @author Diego Beraldin
 */
test("testAddCommunication()", function() {
	var i = 0;
	tester.initialize(mediator.getView("tools"));
	document.getElementById("ToolsPanel").style.display = "none";
	tester.addCommunicationFunction();

	var element = document.getElementById("CallFunction");
	equal(element.nodeName, "LI");
	i++;
	equal(element.innerHTML, "Chiamata");
	i++;

	expect(i);
});

/**
 * Verifica che sia possibile rimuovere il pulsante 'Comunicazione'
 * 
 * @version 2.0
 * @author Diego Beraldin
 */
test("testRemoveCommunication()", function() {
	var i = 0;
	tester.initialize(mediator.getView("tools"));
	document.getElementById("ToolsPanel").style.display = "none";
	tester.addCommunicationFunction();
	tester.removeCommunicationFunction();

	var element = document.getElementById("CallFunction");
	equal(element, null);
	i++;

	expect(i);
});

/**
 * Verifica la possibilità di effettuare logout tramite evento
 * 
 * @version 2.0
 * @author Diego Beraldin
 */
test("testOnLogout()", function() {
	var bool = false;
	document.addEventListener("eventRaised", function() {
		bool = true;
	});

	document.dispatchEvent(new CustomEvent("logout"));
	ok(bool);
});

/**
 * Verifica il comportamento al cambiamento di stato
 * 
 * @version 2.0
 * @author Diego Beraldin
 */
test("testUpdateStateValue()", function() {
	var i = 0;
	changeMyState = new CustomEvent("changeMyState");
	tester.initialize(mediator.getView("tools"));
	var element = document.getElementById("selectState");
	element.selectedIndex = 0;

	element = tester.updateStateValue();
	equal(element.nodeName, "SELECT");
	i++;
	equal(element.children.length, 2);
	i++;

	expect(i);
});

/**
 * Verifica che il presenter risponda all'evento showToolsPanel
 * 
 * @version 2.0
 * @author Diego Beraldin
 */
test("testOnShowToolsPanel()", function() {
	var bool = false;
	document.addEventListener("eventRaised", function() {
		bool = true;
	});
	document.dispatchEvent(new CustomEvent("showToolsPanel"));
	ok(bool);
});

/**
 * Verifica il funzionamento del pulsante di ricerca
 * 
 * @version 2.0
 * @author Diego Beraldin
 */
test("testPerformSearch", function() {
	showSearchResultPanel = new CustomEvent("showSearchResultPanel");
	tester.initialize(mediator.getView("tools"));
	document.getElementById("ToolsPanel").style.display = "none";
	var bool = false;
	document.addEventListener("showSearchResultPanel", function() {
		bool = true;
	});
	var liSearch = document.getElementById("liSearch");
	liSearch.dispatchEvent(new MouseEvent("click"));
	ok(bool);
});

/**
 * Verifica il funzionamento del pulsante per i gruppi
 * 
 * @version 2.0
 * @author Diego Beraldin
 */
test("testPerformSearch", function() {
	showGroupPanel = new CustomEvent("showGroupPanel");
	tester.initialize(mediator.getView("tools"));
	document.getElementById("ToolsPanel").style.display = "none";
	var bool = false;
	document.addEventListener("showGroupPanel", function() {
		bool = true;
	});
	var liSearch = document.getElementById("liGroup");
	liSearch.dispatchEvent(new MouseEvent("click"));
	ok(bool);
});

/**
 * Verifica il funzionamento del pulsante per lo storico chiamate
 * 
 * @version 2.0
 * @author Diego Beraldin
 */
test("testPerformSearch", function() {
	showCallHistoryPanel = new CustomEvent("showCallHistoryPanel");
	tester.initialize(mediator.getView("tools"));
	document.getElementById("ToolsPanel").style.display = "none";
	var bool = false;
	document.addEventListener("showCallHistoryPanel", function() {
		bool = true;
	});
	var liSearch = document.getElementById("liCallList");
	liSearch.dispatchEvent(new MouseEvent("click"));
	ok(bool);
});

/**
 * Verifica il funzionamento del pulsante per le impostazioni utente
 * 
 * @version 2.0
 * @author Diego Beraldin
 */
test("testPerformSearch", function() {
	showAccountSettingPanel = new CustomEvent("showAccountSettingPanel");
	tester.initialize(mediator.getView("tools"));
	document.getElementById("ToolsPanel").style.display = "none";
	var bool = false;
	document.addEventListener("showAccountSettingPanel", function() {
		bool = true;
	});
	var liSearch = document.getElementById("liSetting");
	liSearch.dispatchEvent(new MouseEvent("click"));
	ok(bool);
});

/**
 * Verifica il funzionamento del pulsante per la segreteria
 * 
 * @version 2.0
 * @author Diego Beraldin
 */
test("testPerformSearch", function() {
	showMessagePanel = new CustomEvent("showMessagePanel");
	tester.initialize(mediator.getView("tools"));
	document.getElementById("ToolsPanel").style.display = "none";
	var bool = false;
	document.addEventListener("showMessagePanel", function() {
		bool = true;
	});
	var liSearch = document.getElementById("liAnswering");
	liSearch.dispatchEvent(new MouseEvent("click"));
	ok(bool);
});