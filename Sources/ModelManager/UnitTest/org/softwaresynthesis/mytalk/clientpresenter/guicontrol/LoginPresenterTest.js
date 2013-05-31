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
						var div = document.createElement("div");
						div.innerHTML = viewRequest.responseText;

						div.style.display = "none";
						return div.childNodes[0];
					},
				};
				// stub di communicationcenter
				communicationcenter = {
					my : null
				};
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

test("testInitialize()", function() {
	var i = 0;
	tester.initialize(mediator.getView("main"));

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

test("testDestroy()", function() {
	var i = 0;
	tester.initialize(mediator.getView("main"));

	tester.destroy();
	var element = document.getElementById("LoginPanel");
	equal(element, null);
	i++;
	expect(i);
});

test("testGetUsername()", function() {
	var i = 0;
	tester.initialize(mediator.getView("main"));

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

// test("testLogin()", function() {
//	var loginData = new Object();
//	loginData.username = "laurapausini@gmail.com";
//	loginData.password = "opera";
//	var string = tester.login(loginData);
//	equal(string, "username=laurapausini%40gmail.com&password=opera");
//	// TODO da testare communicationcenter.my
//	// che dopo il login dovrebbe essere stato settato
//	// console.debug(communicationcenter.my);
//});
//
//
//test("testGetPassword()", function() {
//	var i = 0;
//	// questo è uno stub
//	var form = document.createElement("form");
//	form.style.display = "none";
//	var input = document.createElement("input");
//	input.id = "password";
//	input.value = "opera";
//	form.appendChild(input);
//	document.body.appendChild(form);
//
//	// invoca il metodo da testare
//	var password = tester.getPassword();
//	equal(password, "opera", "password recuperata correttamente");
//	i++;
//
//	// verifica se rileva password mancante
//	input.value = "";
//	try {
//		tester.getPassword();
//		ok(false, "password mancante non rilevata");
//		i++;
//	} catch (err) {
//		equal(err, "password non specificata",
//				"password mancante rilevata correttamente");
//		i++;
//	}
//
//	document.body.removeChild(form);
//	expect(i);
//});
//
//test("testBuildRetrievePasswordForm()", function() {
//	var i = 0;
//
//	// stub di interfaccia grafica
//	var input = document.createElement("input");
//	input.id = "username";
//	input.type = "email";
//	input.value = "laupau@gmail.com";
//	element.appendChild(input);
//
//	var form = tester.buildRetrievePasswordForm();
//
//	var children = form.childNodes;
//	equal(children.length, 3, "il form contiene esattamente tre figli");
//	i++;
//
//	var labelQuestion = children[0];
//	var inputAnswer = children[1];
//	var submitButton = children[2];
//	
//	equal(labelQuestion.nodeName, "LABEL", "tipo della label corretta");
//	i++;
//	equal(inputAnswer.nodeName, "INPUT", "tipo del campo di test corretto");
//	i++;
//	equal(submitButton.nodeName, "INPUT", "tipo del pulsante corretto");
//	i++;
//	equal(labelQuestion.getAttribute("for"), inputAnswer.id,
//			"attributo for della label settato correttamente");
//	i++;
//	equal(submitButton.type, "submit", "attributo type del pulsante corretto");
//	i++;
//	equal(submitButton.value, "OK", "testo del pulsante corretto");
//	i++;
//	equal(inputAnswer.required, true,
//			"input per la risposta settato come obbligatorio");
//	i++;
//
//	// si appella allo stub della servlet per vedere se la domanda viene creata
//	// correttamente
//	var question = labelQuestion.innerHTML;
//	equal(question, "Come si chiama la mia gatta?",
//			"testo della domanda recuperato correttamente");
//	i++;
//
//	expect(i);
//});
//
//test("testHasAnsweredCorrectly()", function() {
//	var result = tester.hasAnsweredCorrectly("laupau@gmail.com", "tricolore");
//	equal(result, true, "risposta corretta ricevuta correttamente");
//	result = tester.hasAnsweredCorrectly("laupau@gmail.com", "rossa");
//	equal(result, false, "risposta errata ricevuta correttamente");
//	expect(2);
//
//});
