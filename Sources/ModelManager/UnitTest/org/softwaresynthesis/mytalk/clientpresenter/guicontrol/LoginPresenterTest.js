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

test("testGetPassword()", function() {
	var i = 0;
	tester.initialize(mediator.getView("login"));
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

test(
		"testLoginUnsuccessfully()",
		function() {
			var i = 0;
			tester.initialize(mediator.getView("login"));
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

test("testBuildRetrievePasswordForm()", function() {
	var i = 0;

	tester.initialize(mediator.getView("main"));
	document.getElementById("username").value = "pr@va.com";
	var button = document.getElementById("inputRetrievePassword");
	var event = new MouseEvent("click");
	button.dispatchEvent(event);

	var form = document.getElementById("passwordretrieval");

	var children = form.childNodes;
	equal(children.length, 3, "il form contiene esattamente tre figli");
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