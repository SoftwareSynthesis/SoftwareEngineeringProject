/**
 * Verifica della classe CommunicationPresnter
 * 
 * @version 2.0
 * @author Stefano Farronato
 * @author Diego Beraldin
 */
module(
		"CommunicationPanelPresenter",
		{
			setup : function() {// calcola l'indirizzo di base
				host = window.location.protocol + "//" + window.location.host
						+ window.location.pathname;
				host = host.substr(0, host.length - 10);
				// stub di communicationcenter
				communicationcenter = new Object();
				communicationcenter.my = {
					name : "Paolino",
					surname : "Paperino",
					email : "indirizzo5@dominio.it",
					picturePath : "img/contactImg/Default.png"
				};
				// configura il percorso del ControllerManager
				commandURL = "http://localhost/ModelManager/WebContent/Conf/controllerManagerStub.php";
				// brutti eventi cattivi (globali -_-)
				showReturnToCommunicationPanelButton = new CustomEvent(
						"showReturnToCommunicationPanelButton");
				// questo non è il cesso ma è una turca
				mediator = {
					getView : function(someString) {
						var viewRequest = new XMLHttpRequest();
						viewRequest.open("POST",
								"clientview/CommunicationView.html", false);
						viewRequest.send();
						var div = document.createElement("div");
						div.innerHTML = viewRequest.responseText;
						div.style.display = "none";
						document.body.appendChild(div);
					},
					createNameLabel : function(something) {
						return "pluto";
					}
				};
				var ul = document.createElement("ul");
				ul.id = "ToolsList";
				ul.style.display = "none";
				document.body.appendChild(ul);
				// oggetto da testare
				tester = new CommunicationPanelPresenter();
			},
			teardown : function() {
				var element = document.getElementById("CommunicationPanel");
				if (element) {
					document.body.removeChild(element.parentElement);
				}
				document.body.removeChild(document.getElementById("ToolsList"));
			}
		});

/**
 * Verifica la corretta inizializzazione degli elementi grafici del pannello
 * 
 * @version 2.0
 * @author Stefano Farronato
 */
test("testDisplay()", function() {
	var i = 0;
	mediator.getView("communication");
	tester.display();

	var element = document.getElementById("CommunicationPanel");
	equal(element.children.length, 3);
	i++;
	var divHead = element.children[0];
	var divCall = element.children[1];
	var divChat = element.children[2];
	equal(divHead.className, "panelHeader");
	i++;
	equal(divCall.nodeName, "DIV");
	i++;
	equal(divChat.nodeName, "DIV");
	i++;
	equal(divCall.id, "divCall");
	i++;
	equal(divChat.id, "divChat");
	i++;
	var list = divCall.children;
	equal(list.length, 4);
	i++;
	var myVideo = list[0];
	var otherVideo = list[1];
	var statDiv = list[2];
	var close = list[3];
	equal(myVideo.nodeName, "VIDEO");
	i++;
	equal(otherVideo.nodeName, "VIDEO");
	i++;
	equal(myVideo.id, "myVideo");
	i++;
	equal(myVideo.autoplay, true);
	i++;
	equal(otherVideo.id, "otherVideo");
	i++;
	equal(otherVideo.autoplay, true);
	i++;
	equal(statDiv.nodeName, "DIV");
	i++;
	equal(statDiv.id, "statDiv");
	i++;
	equal(close.nodeName, "BUTTON");
	i++;
	equal(close.type, "submit");
	i++;
	equal(close.id, "closeButton");
	i++;
	list = statDiv.children;
	equal(list.length, 2);
	i++;
	var statSpan = list[0];
	var timerSpan = list[1];
	equal(statSpan.nodeName, "SPAN");
	i++;
	equal(timerSpan.nodeName, "SPAN");
	i++;
	equal(statSpan.id, "statSpan");
	i++;
	equal(timerSpan.id, "timerSpan");
	i++;
	list = statSpan.children;
	equal(list.length, 2);
	i++;
	var statReceived = list[0];
	var statSent = list[1];
	equal(statReceived.nodeName, "SPAN");
	i++;
	equal(statReceived.id, "statRecevd");
	i++;
	equal(statSent.nodeName, "SPAN");
	i++;
	equal(statSent.id, "statSend");
	i++;
	list = divChat.children;
	equal(list.length, 1);
	i++;
	var ulOpenChat = list[0];
	equal(ulOpenChat.nodeName, "UL");
	i++;
	equal(ulOpenChat.id, "ulOpenChat");
	i++;
	equal(ulOpenChat.children.length, 0);
	i++;

	expect(i);
});

/**
 * Verifica che sia possibile accedere al video dell'utente
 * 
 * @version 2.0
 * @author Stefano Farronato
 * @author Diego Beraldin
 */
test("testGetMyVideo()", function() {
	var i = 0;
	mediator.getView("communication");
	tester.display();

	var element = tester.getMyVideo();

	equal(element.nodeName, "VIDEO");
	i++;
	equal(element.id, "myVideo");
	i++;

	expect(i);
});

/**
 * Verifica che sia possibile accedere al video dell'altro utente
 * 
 * @version 2.0
 * @author Stefano Farronato
 * @author Diego Beraldin
 */
test("testGetOtherVideo()", function() {
	var i = 0;
	mediator.getView("communication");
	tester.display();

	var element = tester.getOtherVideo();
	equal(element.nodeName, "VIDEO");
	i++;
	equal(element.id, "otherVideo");
	i++;

	expect(i);
});

/**
 * Verifica l'aggiornameto delle statistiche relative alla comunicazione in
 * corso sia per i dati ricevuti che per i dati inviati.
 * 
 * @version 2.0
 * @author Stefano Farronato
 * @author Diego Beraldin
 */
test("testOnUpdateStats()", function() {
	var i = 0;
	mediator.getView("communication");
	tester.display();

	var string = "miao";
	tester.updateStats(string, true);
	equal(document.getElementById("statRecevd").innerHTML.trim(),
			"Dati ricevuti: " + string);
	i++;
	tester.updateStats(string, false);
	equal(document.getElementById("statSend").innerHTML.trim(),
			"Dati inviati: " + string);
	i++;

	expect(i);
});

/**
 * Verifica l'aggiornamento del timer per il tempo di comunicazione
 * 
 * @version 2.0
 * @author Diego Beraldin
 */
test("testOnUpdateTimer()", function() {
	mediator.getView("communication");
	tester.display();
	var string = "ciao";

	tester.updateTimer(string);

	equal(document.getElementById("timerSpan").innerHTML.trim(),
			"Tempo chiamata: " + string);
});

/**
 * Verifica la corretta costruzione del list item che corrisponde a una chat
 * 
 * @version 2.0
 * @author Diego Beraldin
 */
test("testOnChatAdded()", function() {
	var i = 0;
	mediator.getView("communication");
	tester.display();
	var user = {
		id : "1",
		name : "Paolino",
		surname : "Paperino",
		email : "indirizzo5@dominio.it",
		picturePath : "img/contactImg/Default.png"
	};

	tester.addChat(user);

	var element = document.getElementById("ulOpenChat");
	equal(element.children.length, 1);
	i++;
	element = element.children[0];
	equal(element.nodeName, "LI");
	i++;
	equal(element.id, "chat-" + user.id);
	i++;

	expect(i);
});

/**
 * TODO da documentare
 * 
 * @version 2.0
 * @author Diego Beraldin
 */
test("testOnChatRemoved", function() {
	var i = 0;
	mediator.getView("communication");
	tester.display();
	var user = {
		id : "1",
		name : "Paolino",
		surname : "Paperino",
		email : "indirizzo5@dominio.it",
		picturePath : "img/contactImg/Default.png"
	};
	tester.addChat(user);
	tester.displayChat(user);
	tester.removeChat(user);
	
	var element = document.getElementById("ulOpenChat");
	equal(element.children.length, 0);
	i++;
	element = document.getElementById("divContainerChat");
//	ok(!element);
//	i++;
	
	expect(i);
});
