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
		// oggetto da testare
		monolith = new CommunicationCenter();
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
 * 
 * @author Diego Beraldin
 * @version 2.0
 */
test("testCall()", function() {
	var i = 0;
	var wasPCCreated = false;
	var wasGetUserMediaCalled = false;
	document.addEventListener("PCCreated", function() {
		wasPCCcreated = true;
	});
	document.addEventListener("getUserMediaCalled", function() {
		wasGetUserMediaCalled = true;
	});
	var contact = {
		id : 42
	};
	monolith.connect();

	monolith.call(true, contact, true);

	ok(wasGetUserMediaCalled);
	i++;
	equal(message, "[\"5\",\"occupied\"]");
	i++;
	expect(i);
});