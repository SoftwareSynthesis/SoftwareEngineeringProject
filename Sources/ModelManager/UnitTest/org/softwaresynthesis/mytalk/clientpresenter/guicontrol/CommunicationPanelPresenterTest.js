module("CommunicationPanelPresenter", {
	setup : function() {
		communicationcenter = new Object();
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
