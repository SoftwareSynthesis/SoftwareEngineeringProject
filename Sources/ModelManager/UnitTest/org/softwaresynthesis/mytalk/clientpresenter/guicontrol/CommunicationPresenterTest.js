module("CommunicationPanelPresenter", {
	setup : function() {
		// stub di communicationcenter
		communicationcenter = new Object();
		// oggetto da testare
		tester = new CommunicationPanelPresenter();
	},
	teardown : function() {
	}
});

test(
		"testCreatePanel()",
		function() {
			var i = 0;
			var element = tester.createPanel();
			var list = element.childNodes;

			equal(list.length, 2,
					"il numero di figli dell'elemento restituito e' 2");
			i++;

			var divCall = list[0];
			var divChat = list[1];

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

			list = divCall.childNodes;

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
			equal(close.type, "button",
					"attributo type del pulsante impostato correttamente");
			i++;
			equal(close.id, "closeButton", "attributo id del pulsante corretto");
			i++;

			list = statDiv.childNodes;
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

			list = statSpan.childNodes;
			equal(list.length, 2, "statSpan ha esattamente due figli");
			i++;

			var statReceived = list[0];
			var statSent = list[1];

			equal(statReceived.nodeName, "SPAN", "tipo corretto dello span");
			i++;
			equal(statReceived.id, "statReceved", "id corretto dello span");
			i++;
			equal(statSent.nodeName, "SPAN", "tipo corretto dello span");
			i++;
			equal(statSent.id, "statSend", "id corretto dello span");
			i++;

			list = divChat.childNodes;
			equal(list.length, 1, "l'elemento divChat ha un figlio");
			i++;
			
			var ulOpenChat = list[0];
			equal(ulOpenChat.nodeName, "UL", "tipo dell'elemento lista corretto");
			i++;
			equal(ulOpenChat.id, "ulOpenChat", "attributo id della lista impostato correttamente");
			i++;
			
			equal(ulOpenChat.childNodes.length, 0, "al momento della creazione la lista non ha figli");
			i++;
			expect(i);
		});

function createHiddenDiv() {
	var div = document.createElement("div");
	div.id = "statDiv";
	div.style.position = "absolute";
	div.style.left = "-999";
	return div;
}

test("testUpdateTimer()", function() {
	// stub di interfaccia grafica
	var div0 = createHiddenDiv();
	document.body.appendChild(div0);
	var div1 = document.createElement("div");
	var div2 = document.createElement("div");
	div0.appendChild(div1);
	div0.appendChild(div2);
	
	var string = "io sono un testo";
	tester.updateTimer(string);
	equal(div2.textContent, string, "testo inserito correttamente nel div");
	document.body.removeChild(div0);
});

test("testGetMyVideo()", function() {
	var div = createHiddenDiv();
	var video = document.createElement("video");
	video.id = "myVideo";
	div.appendChild(video);
	document.body.appendChild(div);
	var result = tester.getMyVideo();
	deepEqual(result, video, "elemento video recuperato correttamente");
	document.body.removeChild(div);
});

test("testGetOtherVideo()", function() {
	var div = createHiddenDiv();
	var video = document.createElement("video");
	video.id = "otherVideo";
	div.appendChild(video);
	document.body.appendChild(div);
	var result = tester.getOtherVideo();
	deepEqual(result, video, "elemento recuperato correttamente");
	document.body.removeChild(div);
});

test("testUpdateStarts()", function() {
	// stub di interfaccia grafica
	var div0 = createHiddenDiv();
	var span0 = document.createElement("span");
	var span1 = document.createElement("span");
	span1.id = "spanReceved";
	var span2 = document.createElement("span");
	span2.id = "spanSend";
	span0.appendChild(span1);
	span0.appendChild(span2);
	div0.appendChild(span0);
	document.body.appendChild(div0);
	
	var string = "miao";
	
	// inserisci dati ricevuti
	tester.updateStarts(string, true);
	equal(span1.textContent, "Dati ricevuti: " + string, "stringa impostata correttamente");
	
	// inserisci dati inviati
	tester.updateStarts(string, false);
	equal(span2.textContent, "Dati inviati: " + string, "stringa impostata correttamente");
	
	expect(2);
	document.body.removeChild(div0);
});
