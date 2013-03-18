module ("LoginPanelPresenterTest", {
    setup:
        function() {
    	element = document.createElement("div");
    	element.setAttribute("id", "LoginPanel");
    	element.style.position = "absolute";
    	element.style.left ="-999em";
    	document.body.appendChild(element);
    	tester = new LoginPanelPresenter("LoginManager.php");
    },
	teardown: 
		function(){
		document.body.removeChild(element);
	}
}
);


test("testInitialize()", function () {
	var i = 0;
	tester.initialize();
	
	equal(element.style.display, "block", "la proprietà display è settata correttamente");
	i++;
	
	var children = element.childNodes;
	equal(children.length, 1, "il div contiene un solo elemento");
	i++;
	
	var loginForm = children[0];
	equal(loginForm.nodeName, "FORM", "l'elemento contenuto è un <form>");
	i++;
	
	children = loginForm.childNodes;
	equal(children.length, 1, "il form contiene un solo elemento");
	i++;
	
	var ulData = children[0];
	equal(ulData.nodeName, "UL", "il form contiene effettivamente una lista");
	i++;
	
	children = ulData.childNodes;
	equal(children.length, 3, "la lista contiene esattamente 3 elementi");
	i++;
	
	// estrae i tre elementi della lista
	var liUsername = children[0];
	var liPassword = children[1];
	var liButtons = children[2];
	
	// test per username
	var labelUsername = liUsername.childNodes[0];
	var inputUsername = liUsername.childNodes[1];
	equal(labelUsername.getAttribute("for"), inputUsername.id,
			"l'attributo for della label è impostato correttamente");
	i++;
	equal(labelUsername.innerHTML, "Nome utente: ", "il testo della label è corretto");
	i++;
	equal(inputUsername.type, "email", "il tipo dell'input è quello corretto");
	i++;
	equal(inputUsername.required, true, "il campo di testo è effettivamente richiesto");
	i++;
	
	// test per password
	var labelPassword = liPassword.childNodes[0];
	var inputPassword = liPassword.childNodes[1];
	equal(labelPassword.innerHTML, "Password: ", "il testo della label è corretto");
	i++;
	equal(labelPassword.getAttribute("for"), inputPassword.id,
			"l'attributo for della label è corretto");
	i++;
	equal(inputPassword.type, "password", "il tipo dell'input è corretto");
	i++;
	equal(inputPassword.required, true, "il campo di testo è effettivamente richiesto");
	i++;
	
	// test per i pulsanti
	equal(liButtons.childNodes.length, 3, "ci sono tre pulsanti nel form");
	i++;
	var loginButton = liButtons.childNodes[0];
	var registerButton = liButtons.childNodes[1];
	var retrievePasswordButton = liButtons.childNodes[2];
	equal(loginButton.type, "submit", "il pulsante di login è corretto");
	i++;
	equal(loginButton.value, "Login", "testo del pulsante di login è corretto");
	i++;
	equal(registerButton.type, "submit", "il pulsante di registrazione è corretto");
	i++;
	equal(registerButton.value, "Registrati", "il testo del pulsante di registrazione è corretto");
	i++;
	equal(retrievePasswordButton.type, "submit", "il tipo del pulsante di recupero password è corretto");
	i++;
	equal(retrievePasswordButton.value, "Recupera password", "il testo del pulsante di recupero password è corretto");
	i++;
	
	expect(i);
});

test("testHide()", function() {
	var i = 0;
	tester.hide();
	
	equal(element.style.display, "none", "la proprietà display è stata settata correttamente");
	i++;

	expect(i);
});

//TODO da testare in maniera più approfondita
test("testLogin()", function() {
	var loginData = new Object();
	loginData.username = "laurapausini@gmail.com";
	loginData.password = "opera";
	var string = tester.login(loginData);
	equal(string, "username=laurapausini%40gmail.com&password=opera&operation=1");
});


test("testGetUsername()", function() {
	var i = 0;
	
	// questo è uno stub di interfaccia grafica
	var form = document.createElement("form");
	form.style.display = "none";
	var input = document.createElement("input");
	input.id = "username";
	input.value = "laurapausini@gmail.com";
	form.appendChild(input);
	document.body.appendChild(form);
	
	// invoca il metodo da testare
	var username = tester.getUsername();
	equal(username, "laurapausini@gmail.com", "nome utente recuperato correttamente"); i++;
	
	// verifica se rileva username mancante
	input.value = "";
	try {
		tester.getUsername();
		ok(false, "indirizzo email mancante non rilevato"); i++;
	} catch (err) {
		equal(err, "valore non specificato", "indirizzo email mancante rilevato correttamente"); i++;
	}
	
	//verifica se rileva indirizzo email malformato
	input.value = "laupau-gmail.com";
	try {
		tester.getUsername();
		ok(false, "indirizzo email malformato non rilevato"); i++;
	} catch (err) {
		equal(err, "indirizzo email non valido"); i++;
	}
	
	document.body.removeChild(form);
	expect(i);
});

test("testGetPassword()", function() {
	var i = 0;
	// questo è uno stub
	var form = document.createElement("form");
	form.style.display = "none";
	var input = document.createElement("input");
	input.id = "password";
	input.value = "opera";
	form.appendChild(input);
	document.body.appendChild(form);
	
	// invoca il metodo da testare
	var password = tester.getPassword();
	equal(password, "opera", "password recuperata correttamente"); i++;
	
	// verifica se rileva password mancante
	input.value = "";
	try {
		tester.getPassword();
		ok(false, "password mancante non rilevata"); i++;
	} catch (err) {
		equal(err, "valore non specificato", "password mancante rilevata correttamente"); i++;
	}
	
	document.body.removeChild(form);
	expect(i);
});

//TODO
//test("buildRetrievePasswordFormTest()", function() 
