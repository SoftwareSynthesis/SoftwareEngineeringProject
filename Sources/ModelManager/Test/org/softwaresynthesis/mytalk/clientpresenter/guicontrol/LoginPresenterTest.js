/**
 * Verifica della classe LoginPresenter
 * 
 * @version 2.0
 * @author Diego Beraldin
 */
module(
		"LoginPanelPresenterTest",
		{
			setup : function() {
				// indirizzo dello stub del front controller
				commandURL = "http://localhost/ModelManager/WebContent/Conf/controllerManagerStub.php";
				// stub di mediator
				mediator = {
					getView : function(someString) {
						var viewRequest = new XMLHttpRequest();
						viewRequest.open("POST", "clientview/LoginView.html",
								false);
						viewRequest.send();
						return viewRequest.response;
					},
				};
				// stub di communicationcenter
				communicationcenter = {
					my : null,
					connect : function() {
					}
				};
				// eventi
				login = new CustomEvent("login");
				showUIPanels = new CustomEvent("showUIPanels");
				// oggetto da testare
				tester = new LoginPanelPresenter();
			},
			teardown : function() {
				var element = document.getElementById("LoginPanel");
				if (element) {
					document.body.removeChild(element.parentElement);
				}
			}
		});

/**
 * Verifica l'inizializzazione degli elementi grafici del pannello
 * 
 * @version 2.0
 * @author Diego Beraldin
 */
test("testInitialize()", function() {
	var i = 0;
	tester.initialize(mediator.getView("login"));
	document.getElementById("LoginPanel").style.display = "none";

	var element = document.getElementById("LoginPanel");
	equal(element.nodeName, "DIV");
	i++;
	element = element.children[0];
	equal(element.nodeName, "FIELDSET");
	i++;
	element = element.children[0];
	equal(element.nodeName, "DIV");
	i++;
	element = element.parentElement.children[1];
	equal(element.nodeName, "UL");
	i++;
	equal(element.children.length, 3);
	i++;
	element = element.children[0];
	equal(element.nodeName, "LI");
	i++;
	var label = element.children[0];
	var input = element.children[1];
	equal(label.nodeName, "LABEL");
	i++;
	equal(input.nodeName, "INPUT");
	i++;
	equal(label.getAttribute("for"), input.id);
	i++;
	element = element.parentElement.children[1];
	equal(element.nodeName, "LI");
	i++;
	label = element.children[0];
	input = element.children[1];
	equal(label.nodeName, "LABEL");
	i++;
	equal(input.nodeName, "INPUT");
	i++;
	equal(label.getAttribute("for"), input.id);
	i++;
	element = element.parentElement.children[2];
	equal(element.children.length, 3);
	i++;
	element = element.children[0];
	equal(element.nodeName, "BUTTON");
	i++;
	equal(element.innerHTML.trim(), "Login");
	i++;
	element = element.parentElement.children[1];
	equal(element.nodeName, "BUTTON");
	i++;
	equal(element.innerHTML.trim(), "Registrazione");
	i++;
	element = element.parentElement.children[2];
	equal(element.nodeName, "BUTTON");
	i++;
	equal(element.innerHTML.trim(), "Recupera password");
	i++;

	expect(i);
});

/**
 * Verifica la rimozione del panello
 * 
 * @version 2.0
 * @author Diego Beraldin
 */
test("testDestroy()", function() {
	var i = 0;
	tester.initialize(mediator.getView("main"));
	document.getElementById("LoginPanel").style.display = "none";

	tester.destroy();
	var element = document.getElementById("LoginPanel");
	equal(element, null);
	i++;
	expect(i);
});

/**
 * Verifica il recupero dello username dall'interfaccia grafica
 * 
 * @version 2.0
 * @author Diego Beraldin
 */
test("testGetUsername()", function() {
	var i = 0;
	tester.initialize(mediator.getView("main"));
	document.getElementById("LoginPanel").style.display = "none";

	var input = document.getElementById("username");
	input.value = "topolino";
	try {
		tester.getUsername();
	} catch (error) {
		equal(error, "indirizzo email non valido");
		i++;
	}

	input.value = "";
	try {
		tester.getUsername();
	} catch (error) {
		equal(error, "indirizzo email non specificato");
		i++;
	}

	input.value = "indirizzo5@dominio.it";
	var result = tester.getUsername();
	equal(result, "indirizzo5@dominio.it");
	i++;

	expect(i);
});

/**
 * Verifica il recupero della password dall'interfaccia grafica
 * 
 * @version 2.0
 * @author Diego Beraldin
 */
test("testGetPassword()", function() {
	var i = 0;
	tester.initialize(mediator.getView("login"));
	document.getElementById("LoginPanel").style.display = "none";
	var input = document.getElementById("password");
	input.value = "";

	try {
		tester.getPassword();
	} catch (error) {
		equal(error, "password non specificata");
		i++;
	}

	input.value = "pluto";
	var result = tester.getPassword();
	equal(result, "pluto");
	i++;

	expect(i);
});

/**
 * Verifica il comportamento in caso di login con dati errati
 * 
 * @version 2.0
 * @author Diego Beraldin
 */
test(
		"testLoginUnsuccessfully()",
		function() {
			var i = 0;
			tester.initialize(mediator.getView("login"));
			document.getElementById("LoginPanel").style.display = "none";
			var loginData = {
				username : "indirizzo5@dominio.it",
				password : "password"
			};

			var string = tester.login(loginData);
			equal(string,
					"operation=login&username=indirizzo5%40dominio.it&password=password");
			i++;

			var element = document.getElementById("username");
			equal(element.className.trim(), "error");
			i++;
			element = document.getElementById("password");
			equal(element.className.trim(), "error");
			i++;

			expect(i);
		});

/**
 * Verifica il comportamento nel caso di login con dati corretti
 * 
 * @version 2.0
 * @author Diego Beraldin
 */
test("testLoginSuccessfully()", function() {
	var i = 0;
	var loginData = {
		username : "pr@va.com",
		password : "p"
	};

	var string = tester.login(loginData);
	equal(string, "operation=login&username=pr%40va.com&password=p");
	i++;

	var user = login.user;
	equal(user.id, 2);
	i++;
	equal(user.email, "mario.rossi@gmail.com");
	i++;
	equal(user.name, "Mario");
	i++;
	equal(user.surname, "Rossi");
	i++;
	equal(user.picturePath, "default.png");
	i++;

	equal(user, communicationcenter.my);
	i++;

	expect(i);
});

/**
 * Verifica la costruzione del form per il recupero della password
 * 
 * @version 2.0
 * @author Diego Beraldin
 */
test("testBuildRetrievePasswordForm()", function() {
	var i = 0;
	tester.initialize(mediator.getView("main"));
	document.getElementById("LoginPanel").style.display = "none";
	document.getElementById("username").value = "pr@va.com";
	var button = document.getElementById("inputRetrievePassword");
	var event = new MouseEvent("click");
	button.dispatchEvent(event);

	var form = document.getElementById("passwordretrieval");

	var children = form.childNodes;
	equal(children.length, 3);
	i++;

	var labelQuestion = children[0];
	var inputAnswer = children[1];
	var submitButton = children[2];

	equal(labelQuestion.nodeName, "LABEL");
	i++;
	equal(inputAnswer.nodeName, "INPUT");
	i++;
	equal(submitButton.nodeName, "BUTTON");
	i++;
	equal(labelQuestion.getAttribute("for"), inputAnswer.id);
	i++;
	equal(submitButton.type, "submit");
	i++;
	equal(submitButton.innerHTML.trim(), "Richiedi Password");
	i++;
	equal(inputAnswer.required, true);
	i++;
	var question = JSON.parse(labelQuestion.innerHTML.trim());
	equal(question, "Quale e' la risposta?");
	i++;

	expect(i);
});

/**
 * Verifica il comportamento al click sul pulsante di login
 * 
 * @version 2.0
 * @author Diego Beraldin
 */
test("testOnInputLoginClick()", function() {
	var i = 0;
	tester.initialize(mediator.getView("main"));
	document.getElementById("LoginPanel").style.display = "none";
	document.getElementById("username").value = "pr@va.com";
	document.getElementById("password").value = "p";
	var element = document.getElementById("inputLogin");

	element.dispatchEvent(new MouseEvent("click"));

	var user = login.user;
	equal(user.id, 2);
	i++;
	equal(user.email, "mario.rossi@gmail.com");
	i++;
	equal(user.name, "Mario");
	i++;
	equal(user.surname, "Rossi");
	i++;
	equal(user.picturePath, "default.png");
	i++;

	equal(user, communicationcenter.my);
	i++;

	expect(i);
});

/**
 * Verifica il comportamento se si inserisce la risposta corretta nel form per
 * il recupero della password.
 * 
 * @version 2.0
 * @author Diego Beraldin
 */
test(
		"testRetrievePasswordSuccessfully()",
		function() {
			var i = 0;
			// costriusce il pannello e il form
			tester.initialize(mediator.getView("main"));
			document.getElementById("LoginPanel").style.display = "none";
			document.getElementById("username").value = "pr@va.com";
			var button = document.getElementById("inputRetrievePassword");
			var event = new MouseEvent("click");
			button.dispatchEvent(event);
			// inserisce la risposta nel campo di input
			var element = document.getElementById("inputanswer");
			element.value = "ThisIsNotAnAnswer";
			element = document.getElementById("submitButton");
			element.dispatchEvent(new MouseEvent("click"));
			element = document.getElementById("LoginPanel").lastChild;
			equal(element.nodeName, "P");
			i++;
			equal(
					element.innerHTML,
					"Recupero password avvenuto correttamente.Ti è stata inviata un'email contenente i dati richiesti.");
			i++;
			expect(i);
		});

/**
 * Verifica il comportamento se si inserisce una risposta sbagliata nel form per
 * il recupero della password.
 * 
 * @version 2.0
 * @author Diego Beraldin
 */
test("testRetrievePasswordUnsuccessfully()", function() {
	var i = 0;
	// costriusce il pannello e il form
	tester.initialize(mediator.getView("main"));
	document.getElementById("LoginPanel").style.display = "none";
	document.getElementById("username").value = "pr@va.com";
	var button = document.getElementById("inputRetrievePassword");
	var event = new MouseEvent("click");
	button.dispatchEvent(event);
	// inserisce la risposta nel campo di input
	var element = document.getElementById("inputanswer");
	element.value = "ThisIsAWrongAnswer";
	element = document.getElementById("submitButton");
	element.dispatchEvent(new MouseEvent("click"));
	element = document.getElementById("LoginPanel").lastChild;
	equal(element.nodeName, "P");
	i++;
	equal(element.innerHTML,
			"Dati non corretti. Inserire nuovamente la risposta.");
	i++;
	expect(i);
});