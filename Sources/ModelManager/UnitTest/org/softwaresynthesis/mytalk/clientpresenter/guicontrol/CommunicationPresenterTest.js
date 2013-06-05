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
				// stub di mediator
				mediator = {
					getView : function(someString) {
						var viewRequest = new XMLHttpRequest();
						viewRequest.open("POST",
								"clientview/CommunicationView.html", false);
						viewRequest.send();
						var div = document.createElement("div");
						div.innerHTML = viewRequest.responseText;
						if (document.getElementById("CommunicationPanel") == null) {
							document.body.appendChild(div);
							div.style.display = "none";
						}
					},
					createNameLabel : function(something) {
						return "pluto";
					}
				};
				// oggetto da testare
				tester = new CommunicationPanelPresenter();
			},
			teardown : function() {
				var element = document.getElementById("CommunicationPanel");
				if (element) {
					document.body.removeChild(element.parentElement);
				}
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
	document.dispatchEvent(new CustomEvent("showCommunicationPanel"));

	tester.display();

	var element = document.getElementById("CommunicationPanel");
	equal(element.children.length, 3);
	i++;
	var divHead = list[0];
	var divCall = list[1];
	var divChat = list[2];
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
	list = divCall.children;
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

/*
 * test("testAddChat", function(){ var event = new
 * CustomEvent("showCommunicationPanel"); document.dispatchEvent(event);
 * tester.display();
 * 
 * var user ={ id: "1", name : "Paolino", surname : "Paperino", email :
 * "indirizzo5@dominio.it", picturePath : "img/contactImg/Default.png" };
 * tester.addChat(1); var element= document.getElementById("ulOpenChat"); var
 * list = element.children[0]; equal(list.nodeName, "LI"); });
 * 
 * test("testRemoveChat", function(){
 * 
 * });
 * 
 * 
 * test("testUpdateTimer()", function() { var event = new
 * CustomEvent("showCommunicationPanel"); document.dispatchEvent(event);
 * tester.display(); var string = "io sono un testo";
 * tester.updateTimer(string); equal(div2.textContent, "Tempo chiamata: "+
 * string, "testo inserito correttamente nel div");
 * document.body.removeChild(div0); });
 */
test("testGetMyVideo()", function() {
	var event = new CustomEvent("showCommunicationPanel");
	document.dispatchEvent(event);
	tester.display();
	var video = document.createElement("video");
	tester.getMyVideo();
	deepEqual(document.getElementById("myVideo").nodeName, "VIDEO");
});

test("testGetOtherVideo()", function() {
	var event = new CustomEvent("showCommunicationPanel");
	document.dispatchEvent(event);
	tester.display();
	var video = document.createElement("video");
	tester.getOtherVideo()
	equal(document.getElementById("otherVideo").nodeName, "VIDEO");
});

test("testUpdateStats()", function() {
	var i = 0;
	var event = new CustomEvent("showCommunicationPanel");
	document.dispatchEvent(event);
	tester.display();
	var string = "miao";
	// inserisci dati ricevuti
	tester.updateStats(string, true);
	equal(document.getElementById("statRecevd").nodeName, "Dati ricevuti: "
			+ string, "stringa impostata correttamente");

	// inserisci dati inviati
	tester.updateStats(string, false);
	equal(document.getElementById("statSend").nodeName, "Dati inviati: "
			+ string, "stringa impostata correttamente");

	expect(2);
});
