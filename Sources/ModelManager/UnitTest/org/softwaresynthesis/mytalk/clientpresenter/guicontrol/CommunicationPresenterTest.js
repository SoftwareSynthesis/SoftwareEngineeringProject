module(
	"CommunicationPanelPresenter", {
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
		//brutti eventi cattivi (globali -_-)
		showReturnToCommunicationPanelButton= new CustomEvent("showReturnToCommunicationPanelButton");
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
				createNameLabel:function(something){return "pluto";}
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

test("testDisplay()",function() {
		var i = 0;
		var event = new CustomEvent("showCommunicationPanel");
		document.dispatchEvent(event);

		tester.display();
		var element = document.getElementById("CommunicationPanel");
		var list = element.children;

				equal(list.length, 3);
				i++;
			var divHead = list[0];
			var divCall = list[1];
			var divChat = list[2];
			
			equal(divHead.className, "panelHeader");
			i++;

			equal(divCall.nodeName, "DIV",
					"il primo figlio dell'elemento e' un div");
			i++;

			equal(divChat.nodeName, "DIV",
					"il secondo figlio dell'elemenento e' un div");
			i++;

			equal(divCall.id, "divCall", "l'id del div e' divCall");
			i++;

			equal(divChat.id, "divChat", "l'd del div e' divChat");
			i++;

			list = divCall.children;

			equal(list.length, 4,
					"la lista dei figli di divCall contiene 4 figli");
			i++;

			var myVideo = list[0];
			var otherVideo = list[1];
			var statDiv = list[2];
			var close = list[3];

			equal(myVideo.nodeName, "VIDEO",
					"tipo dell'elemento <video> corretto");
			i++;
			equal(otherVideo.nodeName, "VIDEO",
					"tipo dell'elemento <video> corretto");
			i++;
			equal(myVideo.id, "myVideo",
					"attributo id del primo video corretto");
			i++;
			equal(myVideo.autoplay, true,
					"attributo autoplay del primo video corretto");
			i++;
			equal(otherVideo.id, "otherVideo",
					"attributo id del secondo video corretto");
			i++;
			equal(otherVideo.autoplay, true,
					"attributo autoplay del secondo video corretto");
			i++;

			equal(statDiv.nodeName, "DIV",
					"tipo dell'elemento <div> delle statistiche corretto");
			i++;
			equal(statDiv.id, "statDiv",
					"attributo id delle statistiche impostato correttamente");
			i++;

			equal(close.nodeName, "BUTTON", "tipo del pulsante corretto");
			i++;
			equal(close.type, "submit",
					"attributo type del pulsante impostato correttamente");
			i++;
			equal(close.id, "closeButton", "attributo id del pulsante corretto");
			i++;

			list = statDiv.children;
			equal(list.length, 2, "il div delle statistiche ha due figli");
			i++;

			var statSpan = list[0];
			var timerSpan = list[1];

			equal(statSpan.nodeName, "SPAN", "tipo corretto dello span");
			i++;
			equal(timerSpan.nodeName, "SPAN", "tipo corretto dello span");
			i++;
			equal(statSpan.id, "statSpan", "id corretto");
			i++;
			equal(timerSpan.id, "timerSpan", "id corretto");
			i++;

			list = statSpan.children;
			equal(list.length, 2, "statSpan ha esattamente due figli");
			i++;

			var statReceived = list[0];
			var statSent = list[1];

			equal(statReceived.nodeName, "SPAN", "tipo corretto dello span");
			i++;
			equal(statReceived.id, "statRecevd", "id corretto dello span");
			i++;
			equal(statSent.nodeName, "SPAN", "tipo corretto dello span");
			i++;
			equal(statSent.id, "statSend", "id corretto dello span");
			i++;
			
			list = divChat.children;
			equal(list.length, 1, "l'elemento divChat ha un figlio");
			i++;
			
			var ulOpenChat = list[0];
			equal(ulOpenChat.nodeName, "UL", "tipo dell'elemento lista corretto");
			i++;
			equal(ulOpenChat.id, "ulOpenChat", "attributo id della lista impostato correttamente");
			i++;
			
			equal(ulOpenChat.children.length, 0, "al momento della creazione la lista non ha figli");
			i++;
			expect(37);
		});

/*
test("testAddChat", function(){
	var event = new CustomEvent("showCommunicationPanel");
	document.dispatchEvent(event);
	tester.display();
	
	var user ={	id: "1", 
				name : "Paolino",
				surname : "Paperino",
				email : "indirizzo5@dominio.it",
				picturePath : "img/contactImg/Default.png"
			};
	tester.addChat(1);
	var element= document.getElementById("ulOpenChat");
	var list = element.children[0];
	equal(list.nodeName, "LI");
});

test("testRemoveChat", function(){
	
});
*/

test("testUpdateTimer()", function() {
	var event = new CustomEvent("showCommunicationPanel");
	document.dispatchEvent(event);
	tester.display();
	var string = "io sono un testo";
	tester.updateTimer(string);
	equal(div2.textContent, "Tempo chiamata: "+ string, "testo inserito correttamente nel div");
	document.body.removeChild(div0);
});

test("testGetMyVideo()", function() {
	var event = new CustomEvent("showCommunicationPanel");
	document.dispatchEvent(event);
	tester.display();
	var video = document.createElement("video");
	video.id = "myVideo";
	div.appendChild(video);
	document.body.appendChild(div);
	var result = tester.getMyVideo();
	deepEqual(result, video, "elemento video recuperato correttamente");
	document.body.removeChild(div);
});

test("testGetOtherVideo()", function() {
	var event = new CustomEvent("showCommunicationPanel");
	document.dispatchEvent(event);
	tester.display();
	var video = document.createElement("video");
	video.id = "otherVideo";
	div.appendChild(video);
	document.body.appendChild(div);
	var result = tester.getOtherVideo();
	deepEqual(result, video, "elemento recuperato correttamente");
	document.body.removeChild(div);
});

test("testUpdateStats()", function() {
	int i=0;
	var event = new CustomEvent("showCommunicationPanel");
	document.dispatchEvent(event);
	tester.display();

	var string = "miao";
	// inserisci dati ricevuti
	tester.updateStats(string, true);
	equal(document.getElementById("statRecevd"), "Dati ricevuti: " + string, "stringa impostata correttamente");
	
	// inserisci dati inviati
	tester.updateStats(string, false);
	equal(document.getElementById("statSend"), "Dati inviati: " + string, "stringa impostata correttamente");
	
	expect(2);
	document.body.removeChild(div0);
});
