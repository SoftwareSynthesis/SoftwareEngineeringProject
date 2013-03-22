module ("LoginPanelPresenterTest", {
    setup:
        function() {
    	element = document.createElement("div");
    	element.setAttribute("id", "LoginPanel");
    	element.style.position = "absolute";
    	element.style.left ="-999em";
    	document.body.appendChild(element);
    	
    	communicationcenter = {my: null};
    	mediator = {buildUI: function() {}};
    	configurationFile = "/ModelManager/WebContent/Conf/servletlocationtest.xml";
    	
    	tester = new LoginPanelPresenter("php_stubs/Login", ".php");

    },
	teardown: 
		function(){
		document.body.removeChild(element);
	}
});

test("testInitialize()", function () {
	var i = 0;
	tester.initialize();
	
	equal(element.style.display, "block", "la proprietà display è settata correttamente");
	i++;
	
	var children = element.childNodes;
	equal(children.length, 1, "il div contiene un solo elemento");
	i++;
	
	var loginForm = children[0];
	equal(loginForm.nodeName, "FIELDSET", "l'elemento contenuto è un <form>");
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
	equal(loginButton.nodeName, "BUTTON", "il pulsante di login è corretto");
	i++;
	equal(loginButton.type, "submit", "tipo del pulsante di login corretto"); i++;
	equal(loginButton.innerHTML, "Login", "testo del pulsante di login è corretto");
	i++;
	equal(registerButton.nodeName, "BUTTON", "il pulsante di registrazione è corretto");
	i++;
	equal(registerButton.type, "submit", "tipo del pulsante di registrazione corretto"); i++;
	equal(registerButton.innerHTML, "Registrazione", "il testo del pulsante di registrazione è corretto");
	i++;
	equal(retrievePasswordButton.nodeName, "BUTTON", "il tipo del pulsante di recupero password è corretto");
	i++;
	equal(retrievePasswordButton.type, "submit", "tipo del pulsante di recupero password corretto"); i++;
	equal(retrievePasswordButton.innerHTML, "Recupera password", "il testo del pulsante di recupero password è corretto");
	i++;
	
	expect(i);
});

test("testHide()", function() {
	tester.hide();
	equal(element.style.display, "none", "la proprietà display è stata settata correttamente");
});

test("testLogin()", function() {
	var loginData = new Object();
	loginData.username = "laurapausini@gmail.com";
	loginData.password = "opera";
	var string = tester.login(loginData);
	equal(string, "username=laurapausini%40gmail.com&password=opera");
	//TODO da testare communicationcenter.my
	// che dopo il login dovrebbe essere stato settato
	// console.debug(communicationcenter.my);
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

test("testBuildRetrievePasswordForm()", function() {
	var i = 0;
	
	// stub di interfaccia grafica
	var input = document.createElement("input");
	input.id = "username";
	input.type = "email";
	input.value = "laupau@gmail.com";
	element.appendChild(input);
	
	var form = tester.buildRetrievePasswordForm();
	
	var children = form.childNodes;
	equal(children.length, 3, "il form contiene esattamente tre figli"); i++;
	
	var labelQuestion = children[0];
	var inputAnswer = children[1];
	var submitButton = children[2];
	equal(labelQuestion.nodeName, "LABEL", "tipo della label corretta"); i++;
	equal(inputAnswer.nodeName, "INPUT", "tipo del campo di test corretto"); i++;
	equal(submitButton.nodeName, "INPUT", "tipo del pulsante corretto"); i++;
	equal(labelQuestion.getAttribute("for"), inputAnswer.id, "attributo for della label settato correttamente"); i++;
	equal(submitButton.type, "submit", "attributo type del pulsante corretto"); i++;
	equal(submitButton.value, "OK", "testo del pulsante corretto"); i++;
	equal(inputAnswer.required, true, "input per la risposta settato come obbligatorio"); i++;
	
	//si appella allo stub della servlet per vedere se la domanda viene creata correttamente
	var question = labelQuestion.innerHTML;
	equal(question,
		  "Come si chiama la mia gatta?",
		  "testo della domanda recuperato correttamente"); i++;
	
	expect(i);
});

test("testHasAnsweredCorrectly()", function() {
	var result = tester.hasAnsweredCorrectly("laupau@gmail.com", "tricolore");
	equal(result, true, "risposta corretta ricevuta correttamente");
	result = tester.hasAnsweredCorrectly("laupau@gmail.com", "rossa");
	equal(result, false, "risposta errata ricevuta correttamente");
	expect(2);
	
});
