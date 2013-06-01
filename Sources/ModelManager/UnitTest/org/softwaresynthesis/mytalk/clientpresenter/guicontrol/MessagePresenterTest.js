/**
 * Verifica della classeMessagePresenter
 * 
 * @version 2.0
 * @author Stefano Farronato
 * @author Diego Beraldin
 */
module(
		"MessagePresenterTest",
		{
			setup : function() {
				// calcola l'indirizzo base
				host = window.location.protocol + "//" + window.location.host
						+ window.location.pathname;
				host = host.substr(0, host.length - 10);
				// indirizzo dello stub di front controller
				commandURL = "http://localhost/ModelManager/WebContent/Conf/controllerManagerStub.php";
				// stub di mediator
				mediator = {
					getView : function(someString) {
						var viewRequest = new XMLHttpRequest();
						viewRequest.open("POST", "clientview/MessageView.html",
								false);
						viewRequest.send();
						var div = document.createElement("div");
						div.innerHTML = viewRequest.responseText;
						if (document.getElementById("MessagePanel") == null) {
							document.body.appendChild(div);
							div.style.display = "none";
						}
					},

					getContactById : function(someInteger) {
						return {
							id : someInteger,
							name : "Gastone",
							surname : "Paperone",
							email : "indirizzo5@dominio.it",
							state : "offline",
							picturePath : "img/contactImg/Default.png"
						};
					},
					createNameLabel : function(someString) {
						return "Gastone Paperone";
					}
				};
				// eventi
				showMessagePanel = new CustomEvent("showMessagePanel");
				// oggetto da testare
				tester = new MessagePanelPresenter();
			},
			teardown : function() {
				var element = document.getElementById("MessagePanel");
				if (element) {
					document.body.removeChild(element.parentElement);
				}
			}
		});

/**
 * Verifica l'inizializzazione degli elementi grafici del pannello
 * 
 * @version 2.0
 * @author Stefano Farronato
 * @author Diego Beraldin
 */
test("testDisplay()", function() {
	var i = 0;
	var event = new CustomEvent("showMessagePanel");
	document.dispatchEvent(event);

	tester.display();

	// verifica l'aspetto dell'interfaccia grafica
	var element = document.getElementById("MessagePanel");
	equal(element.nodeName, "DIV");
	i++;
	equal(element.children.length, 3);
	i++;
	element = element.children[0];
	equal(element.nodeName, "DIV");
	i++;
	element = element.children[0];
	equal(element.nodeName, "H1");
	i++;
	equal(element.innerHTML.trim(), "SEGRETERIA");
	i++;
	element = element.parentElement.parentElement.children[1];
	equal(element.nodeName, "AUDIO");
	i++;
	element = element.children[0];
	equal(element.nodeName, "SOURCE");
	i++;
	element = element.parentElement.parentElement.children[2];
	equal(element.nodeName, "DIV");
	i++;
	element = element.children[0];
	equal(element.nodeName, "UL");
	i++;
	equal(element.id, "messageList");
	i++;

	// verifica che sia mostrata la lista di messaggi
	equal(element.children.length, 2);
	i++;
	element = element.children[0];
	equal(element.nodeName, "LI");
	i++;
	equal(element.children.length, 3);
	i++;
	element = element.children[0];
	equal(element.nodeName, "IMG");
	i++;
	equal(element.src, host + "img/unreadMessage.png");
	i++;

	element = element.parentElement.children[1];
	equal(element.nodeName, "SPAN");
	i++;
	equal(element.innerHTML.trim(), "Gastone Paperone 2013-05-31 10:38:27.0");
	i++;

	element = element.parentElement.children[2];
	equal(element.nodeName, "IMG");
	i++;
	equal(element.src, host + "img/deleteGroupImg.png");
	i++;

	expect(i);
});

/**
 * Verifica la possibilità di ascoltare un messaggio cliccando sullo span che
 * contiene le sue informazioni
 * 
 * @version 2.0
 * @author Diego Beraldin
 */
test(
		"testPlayMessageByClick()",
		function() {
			var i = 0;
			document.dispatchEvent(new CustomEvent("showMessagePanel"));
			tester.display();

			var spanInfo = document.getElementById("messageList").children[0].children[1];
			spanInfo.dispatchEvent(new MouseEvent("click"));

			var audio = document.getElementById("messageVideo");
			equal(audio.src, host + "Secretariat/1.wav");
			i++;
			equal(spanInfo.previousSibling.src, host + "img/readMessage.png");
			i++;

			expect(i);
		});

/**
 * Verifica la possibilità di eliminare un messaggio da interfaccia grafica
 * 
 * @version 2.0
 * @author Diego Beraldin
 */
test(
		"testDeleteMessageByClick",
		function() {
			document.dispatchEvent(new CustomEvent("showMessagePanel"));
			tester.display();
			var bool = false;
			
			document.addEventListener("showMessagePanel", function() {
				bool = true;
			});

			var deleteButton = document.getElementById("messageList").children[0].children[2];
			deleteButton.dispatchEvent(new MouseEvent("click"));

			ok(bool);
		});

/**
 * Verifica la possibilità di aggiornare un messaggio da interfaccia grafica
 * 
 * @version 2.0
 * @author Diego Beraldin
 */
test("testUpdateMEssageByClick()", function() {
	var i = 0;
	document.dispatchEvent(new CustomEvent("showMessagePanel"));
	tester.display();
	
	var updateButton = document.getElementById("messageList").children[0].children[0];
	equal(updateButton.src, host + "img/unreadMessage.png");
	i++;
	updateButton.dispatchEvent(new MouseEvent("click"));
	equal(updateButton.src, host + "img/readMessage.png");
	i++;
	
	expect(i);	
});