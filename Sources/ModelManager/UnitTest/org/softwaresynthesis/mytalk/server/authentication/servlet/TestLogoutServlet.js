module("TestLoginServlet", {
	setup : function() {
		loginServletURL = "localhost:8080/MyTalk/LoginServlet";
		logoutServletURL = "localhost:8080/MyTalk/LogoutServlet";
	},
	teardown : function() {
	}
});

test("testDoPost()", function() {
	// effettua un finto login
	var querystring = "username=" + encodeURIComponent("indirizzo1@dominio.it")
			+ "&password=" + encodeURIComponent("password");
	var requestLogin = new XMLHttpRequest();
	requestLogin.open("POST", loginServletURL, false);
	requestLogin.setRequestHeader("Content-type",
			"application/x-www-form-urlencoded");
	requestLogin.send(querystring);
	ok(requestLogin.responseText, "effettuato correttamente il finto login");

	// cerca di effettuare il logout
	var requestLogout = new XMLHttpRequest();
	requestLogout.open("POST", logoutServletURL, false);
	requestLogout.setRequestHeader("Content-type",
			"application/x-www-form-urlencoded");
	requestLogout.send();
	var result = JSON.parse(requestLogout.responseText);
	ok(result, "logout andato a buon fine");
});