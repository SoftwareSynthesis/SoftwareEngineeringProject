/**
 * Verifica del Monolite (simulando il più possibile)
 * 
 * @author Diego Beraldin
 * @version 2.0
 */
module("CommunicationCenterTest", {
	setup : function() {
		urlChannelServlet = "http://localhost:8080";
		message = "";
		// brutti eventi cattivi
		changeMyState = new CustomEvent("changeMyState");
		appendMessageToChat = new CustomEvent("appendMessageToChat");
		// oggetto da testare
		monolith = new CommunicationCenter();
		// stub di mediator
		mediator = {
			getCommunicationPPOtherVideo : function() {
				return document.createElement("video");
			},
			getCommunicationPPMyVideo : function() {
				return document.createElement("video");
			}
		};
		// et alii...
		timer = {};
		statCollector = {};
	},
	teardown : function() {
	}
});

// stub-ghost di WebSocket
ws = new Object();
function WebSocket(url) {
	var event = new CustomEvent("WSCreated");
	event.url = url;
	document.dispatchEvent(event);
	return ws;
}
ws.send = function(string) {
	message = string;
};
ws.close = function() {
	document.dispatchEvent(new CustomEvent("WSClosed"));
};
// stub-ghost della RTCPeerConnection
pc = new Object();
function webkitRTCPeerConnection(conf) {
	var event = new CustomEvent("PCCreated");
	event.configuration = conf;
	document.dispatchEvent(event);
	return pc;
}
pc.close = function() {
	document.dispatchEvent(new CustomEvent("PCClosed"));
};
pc.removeStream = function() {
	document.dispatchEvent(new CustomEvent("streamRemoved"));
};
pc.createOffer = function() {
	document.dispatchEvent(new CustomEvent("offerCreated"));
};
// tarpa le ali al getUserMedia
navigator.webkitGetUserMedia = function() {
	document.dispatchEvent(new CustomEvent("getUserMediaCalled"));
};
// tarpa le ali all'alert
window.alert = function() {
};
// altro ghost
localStream = new Object();
localStream.stop = function() {
	document.dispatchEvent(new CustomEvent("streamStopped"));
};

/**
 * Verifica il comportamento del metodo connect, in particolare verifica che sia
 * creata una WebSocket con l'indirizzo corretto.
 * 
 * @author Diego Beraldin
 * @version 2.0
 */
test("testConnect()", function() {
	var resultingURL;
	document.addEventListener("WSCreated", function(evt) {
		resultingURL = evt.url;
	});
	monolith.connect();
	equal(resultingURL, urlChannelServlet);
});

/**
 * Testa la disconnessione, in particolare verifica che giunga al server il
 * messaggio di tipo 4 che questo si aspetta e che sia invocato il metodo
 * close() sulla WebSocket. E poi dite che non si poteva fare!!
 * 
 * @author Diego Beraldin
 * @version 2.0
 */
test("testDisconnect()", function() {
	var i = 0;
	var bool = false;
	document.addEventListener("WSClosed", function() {
		bool = true;
	});
	monolith.connect();

	monolith.disconnect();

	ok(bool);
	i++;
	equal(message, "[\"4\"]");
	i++;
	expect(i);
});

/**
 * Verifica che la chiamata (in uscita) sia gestita correttamente invocando il
 * getUserMedia e impostando il proprio stato a occupato.
 * 
 * @author Diego Beraldin
 * @version 2.0
 */
test("testCall()", function() {
	var i = 0;
	var wasPCCreated = false;
	var wasGetUserMediaCalled = false;
	document.addEventListener("PCCreated", function() {
		wasPCCreated = true;
	});
	document.addEventListener("getUserMediaCalled", function() {
		wasGetUserMediaCalled = true;
	});
	var contact = {
		id : 42
	};
	monolith.connect();

	monolith.call(true, contact, true);

	ok(wasPCCreated);
	i++;
	ok(wasGetUserMediaCalled);
	i++;
	equal(message, "[\"5\",\"occupied\"]");
	i++;
	expect(i);
});

/**
 * Verifica che la fine della chiamata sia gestita in modo corretto arrestando e
 * rimuovendo il flusso audio/video associato alla RTCPeerConnection.
 * 
 * @author Diego Beraldin
 * @version 2.0
 */
test("testEndCall()", function() {
	var i = 0;
	var wasStreamStopped = false;
	var wasStreamRemoved = false;
	var wasOfferCreated = false;
	document.addEventListener("streamStopped", function() {
		wasStreamStopped = true;
	});
	document.addEventListener("streamRemoved", function() {
		wasStreamRemoved = true;
	});
	document.addEventListener("offerCreated", function() {
		wasOfferCreated = true;
	});
	var contact = {
		id : 42
	};
	monolith.call(true, contact, false);

	monolith.endCall();

	ok(wasStreamStopped);
	i++;
	ok(wasStreamRemoved);
	i++;
	ok(wasOfferCreated);
	i++;

	expect(i);
});

/**
 * Verifica che sia gestito correttamente l'invio di un messaggio testuale,
 * inviando al server la stringa contenente un messaggio di tipo 7 con l'id del
 * destinatario e il testo del messaggio.
 * 
 * @author Diego Beraldin
 * @version 2.0
 */
test("testOnSendMessage()", function() {
	var i = 0;
	var contact = {
		id : 42
	};
	monolith.connect();
	var event = new CustomEvent("sendMessage");
	event.contact = contact;
	event.messageText = "ciao, mona!";

	document.dispatchEvent(event);

	equal(message, "[\"7\"," + contact.id + ",\"ciao, mona!\"]");
	i++;

	expect(i);
});