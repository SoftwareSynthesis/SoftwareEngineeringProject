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
		// oggetto da testare
		monolith = new CommunicationCenter();
	},
	teardown : function() {
	}
});

// stub-ghost di WebSocket
ws = new Object;
function WebSocket(url) {
	event = new CustomEvent("WSCreated");
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