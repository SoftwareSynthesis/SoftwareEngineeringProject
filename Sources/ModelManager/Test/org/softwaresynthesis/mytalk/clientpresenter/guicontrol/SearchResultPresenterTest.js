/**
 * Verifica della classe SearchResultPresenter
 * 
 * @version 2.0
 * @author Diego Beraldin
 */
module(
		"SearchResultPanelPresenter",
		{
			setup : function() {
				// indirizzo base
				host = window.location.protocol + "//" + window.location.host
						+ window.location.pathname;
				host = host.substr(0, host.length - 10);
				// indirizzo dello stub del front controller
				commandURL = "http://localhost/ModelManager/WebContent/Conf/controllerManagerStub.php";
				// stub di mediator
				mediator = {
					getView : function(someString) {
						var viewRequest = new XMLHttpRequest();
						viewRequest.open("POST",
								"clientview/SearchResultView.html", false);
						viewRequest.send();
						var div = document.createElement("div");
						div.innerHTML = viewRequest.responseText;
						if (document.getElementById("SearchResultPanel") == null) {
							document.body.appendChild(div);
							div.style.display = "none";
						}
					},
					createNameLabel : function(something) {
						return "pippo";
					}
				};
				// oggetto da testare
				tester = new SearchResultPanelPresenter();
			},
			teardown : function() {
				var element = document.getElementById("SearchResultPanel");
				if (element) {
					document.body.removeChild(element.parentElement);
				}
			}
		});

/**
 * Verifica l'inizializzazione degli elementi grafici nel pannello
 * 
 * @version 2.0
 * @author Diego Beraldin
 */
test("testDisplay()", function() {
	var i = 0;
	var event = new CustomEvent("showSearchResultPanel");
	document.dispatchEvent(event);

	tester.display();

	var element = document.getElementById("SearchResultPanel");
	equal(element.nodeName, "DIV");
	i++;

	element = document.getElementById("searchInputField");
	equal(element.nodeName, "INPUT");
	i++;
	element = document.getElementById("searchInputButton");
	equal(element.nodeName, "BUTTON");
	i++;
	equal(element.innerHTML.trim(), "Cerca");
	i++;

	element = document.getElementById("userList");
	equal(element.nodeName, "UL");
	i++;

	expect(i);
});

/**
 * Verifica che una lista di possibili risultati dal server sia visualizzata
 * correttamente nel pannello
 * 
 * @version 2.0
 * @author Diego Beraldin
 */
test("testHandleResult", function() {
	var event = new CustomEvent("showSearchResultPanel");
	document.dispatchEvent(event);

	var i = 0;
	var contacts = {
		1 : {
			id : 1,
			name : "paolino",
			surname : "paperino",
			email : "indirizzo5@dominio.it",
			picturePath : "img/contactImg/Default.png",
			state : "available"
		},
		2 : {
			id : 2,
			name : "gastone",
			surname : "paperone",
			email : "indirizzo4@dominio.it",
			picturePath : "img/contactImg/Default.png",
			state : "offline"
		}
	};

	tester.handleResult(contacts);

	var element = document.getElementById("userList");
	equal(element.childNodes.length, 2);
	i++;

	var item = element.childNodes[0];
	equal(item.nodeName, "LI");
	i++;
	equal(item.id, "resultList-1");
	i++;
	var avatar = item.childNodes[0];
	equal(avatar.nodeName, "IMG");
	i++;
	equal(avatar.src, host + "img/contactImg/Default.png");
	i++;
	avatar = item.childNodes[1];
	equal(avatar.nodeValue, "pippo");
	i++;
	avatar = item.childNodes[2];
	equal(avatar.src, host + "img/stateavailable.png");
	i++;

	item = element.childNodes[1];
	equal(item.nodeName, "LI");
	i++;
	equal(item.id, "resultList-2");
	i++;
	avatar = item.childNodes[0];
	equal(avatar.nodeName, "IMG");
	i++;
	equal(avatar.src, host + "img/contactImg/Default.png");
	i++;
	avatar = item.childNodes[1];
	equal(avatar.nodeValue, "pippo");
	i++;
	avatar = item.childNodes[2];
	equal(avatar.src, host + "img/stateoffline.png");
	i++;

	expect(i);
});

/**
 * Verifica che siano mandati al server i parametri di ricerca alla pressione
 * del pulsante di ricerca e che siano visualizzati correttamente i risultati
 * 
 * @version 2.0
 * @author Diego Beraldin
 */
test("testSendSearch()", function() {
	var i = 0;
	var event = new CustomEvent("showSearchResultPanel");
	document.dispatchEvent(event);
	tester.display();

	var input = document.getElementById("searchInputField");
	var button = document.getElementById("searchInputButton");
	input.value = "pippo";
	event = new MouseEvent("click");
	button.dispatchEvent(event);

	var list = document.getElementById("userList");
	var children = list.childNodes;

	equal(children.length, 5);
	i++;

	expect(i);
});