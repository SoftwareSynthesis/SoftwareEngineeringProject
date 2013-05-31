module("SearchResultPanelPresenter", {
	setup : function() {
		// stub di mediator
		mediator = {
			getView : function(someString) {
				var viewRequest = new XMLHttpRequest();
				viewRequest.open("POST", "clientview/SearchResultView.html",
						false);
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
		document.body.removeChild(element.parentElement);
	}
});

test("testDisplay()", function() {
	var i = 0;
	var event = new CustomEvent("showSearchResultPanel");
	document.dispatchEvent(event);

	tester.display();

	var element = document.getElementById("SearchResultPanel");
	equal(element.nodeName, "DIV", "corretto");
	i++;

	element = document.getElementById("searchInputField");
	equal(element.nodeName, "INPUT", "corretto");
	i++;
	element = document.getElementById("searchInputButton");
	equal(element.nodeName, "BUTTON", "corretto");
	i++;
	equal(element.innerHTML.trim(), "Cerca", "testo corretto");
	i++;

	element = document.getElementById("userList");
	equal(element.nodeName, "UL", "corretto");
	i++;

	expect(i);
});

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
	equal(element.childNodes.length, 2, "non andra' mai!");
	i++;
	
	var item = element.childNodes[0];
	equal(item.nodeName, "LI", "corretto");
	i++;
	equal(item.id, "resultList-1", "id corretto");
	i++;
	var avatar = item.childNodes[0];
	equal(avatar.nodeName, "IMG", "corretto");
	i++;
	
	item = element.childNodes[1];
	equal(item.nodeName, "LI", "corretto");
	i++;
	equal(item.id, "resultList-2", "id corretto");
	i++;
	avatar = item.childNodes[0];
	equal(avatar.nodeName, "IMG", "corretto");
	i++;
	
	expect(i);
});