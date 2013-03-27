module("TestLoginServlet", {
	setup : function() {
		loginServletURL = "localhost:8080/MyTalk/LoginServlet";
	},
	teardown : function() {
	}
});

test("testDoPost()", function() {
	var querystring = "username=" + encodeURIComponent("indirizzo1@dominio.it")
			+ "&password=" + encodeURIComponent("password");
	var request = new XMLHttpRequest();
	request.open("POST", loginServletURL, false);
	request.setRequestHeader("Content-type",
			"application/x-www-form-urlencoded");
	request.send(querystring);
	var result = JSON.parse(request.responseText);
	var expected = {
		name : "pippo",
		surname : "rossi",
		email : "indirizzo1@dominio.it",
	}
	equal(result, expected, "utente recuperato correttamente");
});