/**
 * Verifica del Monolite (simulando il più possibile)
 * 
 * @author Diego Beraldin
 * @version 2.0
 */
module("CommunicationCenterTest", {
	setup : function() {
		commandURL = "http://localhost/ModelManager/WebContent/Conf/controllerManagerStub.php";
		urlChannelServlet = "http://localhost:8080";
		message = "";
		// brutti eventi cattivi
		chatStarted = new CustomEvent("chatStarted");
		changeMyState = new CustomEvent("changeMyState");
		appendMessageToChat = new CustomEvent("appendMessageToChat");
		stopRinging = new CustomEvent("stopRinging");
		showContactPanel = new CustomEvent("showContactPanel");
		showCommunicationPanel = new CustomEvent("showCommunicationPanel");
		startRinging = new CustomEvent("startRinging");
		changeAddressBooksContactState = new CustomEvent(
				"changeAddressBooksContactState");
		// oggetto da testare
		monolith = new CommunicationCenter();
		// stub di mediator
		mediator = {
			getCommunicationPPOtherVideo : function() {
				return document.createElement("video");
			},
			getCommunicationPPMyVideo : function() {
				return document.createElement("video");
			},
			getContactById : function(id) {
				return {
					id : id
				};
			}
		};
		// et alii...
		timer = {};
		statCollector = {};
	},
	teardown : function() {
	}
});

// stub-ghost di LocalStream
localStream = new Object();
localStream.stop = function() {
	document.dispatchEvent(new CustomEvent("streamStopped"));
};
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
pc.removeStream = function(localStream) {
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

/**
 * Verifica che il rifiuto di una chiamata in ingresso sia gestito correttamente
 * mandando un messaggio di tipo 6 al server con l'id del chiamante.
 * 
 * @author Diego Beraldin
 * @version 2.0
 */
test("testOnRejectCall()", function() {
	var contact = {
		id : 42
	};
	monolith.connect();
	var event = new CustomEvent("rejectCall");
	event.caller = contact;

	document.dispatchEvent(event);

	equal(message, "[\"6\"," + contact.id + "]");
});

/**
 * Verifica che sia gestito correttamente il rifiuto di una chiamata in uscita
 * da parte dell'altro utente.
 */
// test("testOnRejectedCall()", function() {
// var i = 0;
// var contact = {
// id : 42
// };
// monolith.call(true, contact, true);
// var wasPCClosed = false;
// var wasRingingStopped = false;
// document.addEventListener("PCClosed", function() {
// wasPCClosed = true;
// });
// document.addEventListener("stopRinging", function() {
// wasRingingStopped = true;
// });
//
// document.dispatchEvent(new CustomEvent("rejectedCall"));
//
// ok(wasPCClosed);
// i++;
// ok(wasRingingStopped);
// i++;
//
// expect(i);
// });
/**
 * Verifica la corretta gestione di una chiamata in uscita tramite evento.
 * 
 * @author Diego Beraldin
 * @version 2.0
 */
test("testOnCall()", function() {
	var contact = {
		id : 42
	};
	var event = new CustomEvent("call");
	event.contact = contact;
	event.onlyAudio = true;
	var wasRingingStarted = false;
	document.addEventListener("startRinging", function() {
		wasRingingStarted = true;
	});

	document.dispatchEvent(event);

	ok(wasRingingStarted);
});

/**
 * Verifica la corretta rimozione dello stream della RTCPeerConnection.
 * 
 * @author Diego Beraldin
 * @version 2.0
 */
test("testOnPCRemoveStream()", function() {
	var state = "";
	document.addEventListener("changeMyState", function(evt) {
		state = evt.state;
	});
	monolith.call(true, {}, true);
	pc.onremovestream();
	equal(state, "available");
});

/**
 * Verifica che invii gli ICECandidate agli altri peer
 * 
 * @author Diego Beraldin
 * @version 2.0
 */
test("testOnPCIceCandidate()", function() {
	monolith.connect();
	monolith.call(true, {
		id : 42
	}, true);
	idOther = 42;
	pc.onicecandidate({
		candidate : "pippo"
	});
	equal(message, "[\"2\",42,\"\\\"pippo\\\"\"]");
});

/**
 * Verifica la corretta gestione di una WebSocket all'apertura della stessa
 * 
 * @author Diego Beraldin
 * @version 2.0
 */
test("testWSOnOpen()", function() {
	monolith.connect();
	monolith.my = {
		id : 1
	};
	ws.onopen();

	// intercetto solo il changeMyState
	equal(message, "[\"5\",\"available\"]");
});

/**
 * Verifica la gestione dell'evento di accettazione di una chiamata in ingresso,
 * facendo visualizzare il pannello della comunicazione.
 * 
 * @author Diego Beraldin
 * @version 2.0
 */
test("testOnAcceptCall()", function() {
	var event = new CustomEvent("acceptCall");
	event.contact = {};
	event.onlyAudio = false;
	var bool = false;
	document.addEventListener("showCommunicationPanel", function() {
		bool = true;
	});

	document.dispatchEvent(event);

	ok(bool);
});

/**
 * Verifica il comportamento del client in risposta a diversi tipi di messaggio
 * provenienti dal server attraverso la WebSocket
 * 
 * @author Diego Beraldin
 * @version 2.0
 */
test("testWSOnMessage()", function() {
	var i = 0;

	var string = "";
	document.addEventListener("appendMessageToChat", function(evt) {
		string = evt.text;
	});
	monolith.connect();
	var msg = "7|2|mona";
	ws.onmessage({
		data : msg
	});
	equal(string, "mona");
	i++;

	msg = "5|2|offline";
	document.addEventListener("changeAddressBooksContactState", function(evt) {
		string = evt.statusUserChange;
	});
	ws.onmessage({
		data : msg
	});
	equal(string, "offline");
	i++;

	msg = "3";
	ws.onmessage({
		data : msg
	});

	expect(i);
});