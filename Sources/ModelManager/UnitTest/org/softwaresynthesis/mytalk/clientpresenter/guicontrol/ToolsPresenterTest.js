module("ToolsPanelPresenter", {
	setup : function() {
		// stub di mediator
		mediator = {
			getView : function(someString) {
				var viewRequest = new XMLHttpRequest();
				viewRequest.open("POST", "clientview/ToolsView.html", false);
				viewRequest.send();
				var div = document.createElement("div");
				div.innerHTML = viewRequest.responseText;
				return div.childNodes[0];
			}
		};
		// inizializza l'oggetto da testare
		tester = new ToolsPanelPresenter();
		// evento
		changeMyState = Object();
	},
	teardown : function() {
	}
});

test("testDestroy()", function() {
	var i = 0;
	tester.destroy();
	var element = document.getElementById("ToolsPanel");
	equal(element, null, "pannello rimosso");
	i++;
	expect(i);
});

test("testInitialize()", function() {
	var i = 0;
	var toolsView = mediator.getView("tools");

	tester.initialize(toolsView);
	var element = document.getElementById("ToolsPanel");
	element.style.display = "none";

	equal(element.nodeName, "DIV", "elemento del tipo corretto");
	i++;

	var children = element.childNodes;
	equal(children.length, 7, "numero di figli corretto");
	i++;

	var child = document.getElementsByClassName("panelHeader")[0];
	equal(child.nodeName, "DIV", "corretto");
	i++;

	child = document.getElementById("selectState");
	equal(child.nodeName, "SELECT", "corretto");
	i++;

	child = document.getElementById("ToolsList");
	equal(child.nodeName, "UL", "corretto");
	i++;

	child = document.getElementById("liAnswering");
	equal(child.nodeName, "LI", "corretto");
	i++;
	equal(child.innerHTML, "Segreteria", "testo corretto");
	i++;

	child = document.getElementById("liSetting");
	equal(child.nodeName, "LI", "corretto");
	i++;
	equal(child.innerHTML, "Impostazioni", "testo corretto");
	i++;

	child = document.getElementById("liCallList");
	equal(child.nodeName, "LI", "corretto");
	i++;
	equal(child.innerHTML, "Lista chiamate", "testo corretto");
	i++;

	child = document.getElementById("liGroup");
	equal(child.nodeName, "LI", "corretto");
	i++;
	equal(child.innerHTML, "Gruppi", "testo corretto");
	i++;

	child = document.getElementById("liSearch");
	equal(child.nodeName, "LI", "corretto");
	i++;
	equal(child.innerHTML, "Ricerca", "testo corretto");
	i++;

	child = document.getElementById("liLogout");
	equal(child.nodeName, "LI", "corretto");
	i++;
	equal(child.innerHTML, "Logout", "testo corretto");
	i++;

	expect(i);
});

test("testAddCommunication()", function() {
	var i = 0;
	tester.addCommunicationFunction();

	var element = document.getElementById("CallFunction");
	equal(element.nodeName, "LI", "corretto");
	i++;
	equal(element.innerHTML, "Chiamata", "testo corretto");
	i++;

	expect(i);
});

test("testRemoveCommunication()", function() {
	var i = 0;
	tester.removeCommunicationFunction();

	var element = document.getElementById("CallFunction");
	equal(element, null, "rimossa funzione correttamente");
	i++;

	expect(i);
});
