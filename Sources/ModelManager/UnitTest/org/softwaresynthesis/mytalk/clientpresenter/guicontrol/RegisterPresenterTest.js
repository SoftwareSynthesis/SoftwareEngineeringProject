/**
 * Verifica della classe RegisterPresenter
 * 
 * @version 2.0
 * @author Diego Beraldin
 */
module(
		"RegisterPresenterTest",
		{
			setup : function() {
				// indirizzo dello stub di fron controller
				commandURL = "http://localhost/ModelManager/WebContent/Conf/controllerManagerStub.php";
				// stub di mediator
				mediator = {
					getView : function(someString) {
						var viewRequest = new XMLHttpRequest();
						viewRequest.open("POST",
								"clientview/RegisterView.html", false);
						viewRequest.send();
						return viewRequest.response;
					}
				};
				// stub di communicationcenter
				communicationcenter = {
					my : null,
					connect : function() {
					}
				};
				// brutti eventi cattivi (globali)
				removeAllPanel = new CustomEvent("removeAllPanel");
				login = new CustomEvent("login");
				// oggetto da testare
				tester = new RegisterPanelPresenter("LoginManager.php");
			},
			teardown : function() {
				var element = document.getElementById("RegisterPanel");
				if (element) {
					document.body.removeChild(element.parentElement);
				}
			}
		});

/**
 * Verifica la costruzione e l'inizializzazione del pannello
 * 
 * @version 2.0
 * @author Diego Beraldin
 */
test(
		"testInitialize()",
		function() {
			var i = 0;
			tester.initialize(mediator.getView("register"));
			document.getElementById("RegisterPanel").style.display = "none";

			var element = document.getElementById("RegisterPanel");
			equal(element.nodeName, "DIV");
			i++;

			var children = element.children;
			equal(children.length, 1);
			i++;
			var form = children[0];
			equal(form.nodeName, "FORM");
			i++;
			equal(form.getAttribute("enctype"), "multipart/form-data");
			i++;
			children = form.children;
			equal(children.length, 2);
			i++;
			var list = children[0];
			equal(list.nodeName, "UL");
			i++;

			var divButtons = children[1];
			children = list.children;
			equal(children.length, 8);
			i++;

			var liUsername = children[0];
			var liPassword = children[1];
			var liSecretQ = children[2];
			var liAnswerSQ = children[3];
			var liName = children[4];
			var liSurname = children[5];
			var liPicture = children[6];
			var required = children[7];

			// per lo username
			var labelUsername = liUsername.children[0];
			var inputUsername = liUsername.children[1];
			equal(labelUsername.nodeName, "LABEL");
			i++;
			equal(labelUsername.getAttribute("for"), inputUsername.id);
			i++;
			equal(inputUsername.type, "email");
			i++;
			ok(inputUsername.required);
			i++;

			// per la password
			var labelPassword = liPassword.children[0];
			var inputPassword = liPassword.children[1];
			equal(labelPassword.nodeName, "LABEL");
			i++;
			equal(labelPassword.getAttribute("for"), inputPassword.id);
			i++;
			equal(inputPassword.type, "password");
			i++;
			ok(inputPassword.required);
			i++;

			// per la domanda segreta
			var labelSecretQ = liSecretQ.children[0];
			var inputSecretQ = liSecretQ.children[1];
			equal(labelSecretQ.nodeName, "LABEL");
			i++;
			equal(labelSecretQ.getAttribute("for"), inputSecretQ.id);
			i++;
			equal(inputSecretQ.type, "text");
			i++;
			ok(inputSecretQ);
			i++;

			// per la risposta alla domanda segreta
			var labelAnswerSQ = liAnswerSQ.children[0];
			var inputAnswerSQ = liAnswerSQ.children[1];
			equal(labelAnswerSQ.nodeName, "LABEL");
			i++;
			equal(labelAnswerSQ.getAttribute("for"), inputAnswerSQ.id);
			i++;
			equal(inputAnswerSQ.type, "text");
			i++;
			ok(inputAnswerSQ.required);
			i++;

			// per il nome
			var labelName = liName.children[0];
			var inputName = liName.children[1];
			equal(labelName.nodeName, "LABEL");
			i++;
			equal(labelName.getAttribute("for"), inputName.id);
			i++;
			equal(inputName.type, "text");
			i++;
			ok(!inputName.required);
			i++;

			// per il cognome
			var labelSurname = liSurname.children[0];
			var inputSurname = liSurname.children[1];
			equal(labelSurname.nodeName, "LABEL");
			i++;
			equal(labelSurname.getAttribute("for"), inputSurname.id);
			i++;
			equal(inputSurname.type, "text");
			i++;
			ok(!inputSurname.required);
			i++;

			// per l'immagine
			var labelPicture = liPicture.children[0];
			var inputPicture = liPicture.children[1];
			equal(labelPicture.nodeName, "LABEL");
			i++;
			equal(labelPicture.getAttribute("for"), inputPicture.id);
			i++;
			equal(inputPicture.type, "file");
			i++;
			ok(!inputPicture.required);
			i++;

			equal(divButtons.children.length, 2);
			i++;

			// per l'avviso
			equal(required.type, "string");
			i++;
			equal(required.className, "requireData");
			i++;
			equal(required.innerHTML.trim(),
					"I dati contrassegnati da * sono obbligatori per la registrazione");
			i++;

			// per il pulsante
			var button = divButtons.children[0];
			equal(button.nodeName, "BUTTON");
			i++;
			equal(button.type, "button");
			i++;
			equal(button.innerHTML, "Indietro");
			i++;
			button = divButtons.children[1];
			equal(button.nodeName, "BUTTON");
			i++;
			equal(button.type, "submit");
			i++;
			equal(button.innerHTML, "Registrati");
			i++;

			expect(i);
		});

/**
 * Verifica l'effettiva distruzione del pannello di registrazione
 * 
 * @version 2.0
 * @author Diego Beraldin
 */
test("testDestroy()", function() {
	tester.initialize(mediator.getView("register"));
	document.getElementById("RegisterPanel").style.display = "none";

	tester.destroy();

	var element = document.getElementById("RegisterPanel");
	equal(element, null);
});

test("testGetUsername()", function() {
	var i = 0;
	tester.initialize(mediator.getView("register"));
	document.getElementById("RegisterPanel").style.display = "none";

	var element = document.getElementById("username");
	element.value = "ThisIsNotValid";
	try {
		tester.getUsername();
	} catch (error) {
		equal(error, "indirizzo email non valido");
		i++;
	}
	element.value = "";
	try {
		tester.getUsername();
	} catch (error) {
		equal(error, "indirizzo email non specificato");
		i++;
	}
	element.value = "pr@va.com";
	var result = tester.getUsername();
	equal(result, "pr@va.com");
	i++;

	expect(i);
});

test("testGetPassword()", function() {
	var i = 0;
	tester.initialize(mediator.getView("register"));
	document.getElementById("RegisterPanel").style.display = "none";

	var element = document.getElementById("password");
	element.value = "";
	try {
		tester.getPassword();
	} catch (error) {
		equal(error, "password non specificata");
		i++;
	}
	element.value = "password";
	var result = tester.getPassword();
	equal(result, "password");
	i++;

	expect(i);
});

test("testGetName()", function() {
	var i = 0;
	tester.initialize(mediator.getView("register"));
	document.getElementById("RegisterPanel").style.display = "none";

	var element = document.getElementById("firstname");
	element.value = "Paolino";
	var result = tester.getName();
	equal(result, "Paolino");
	i++;

	expect(i);
});

test("testGetSurname()", function() {
	var i = 0;
	tester.initialize(mediator.getView("register"));
	document.getElementById("RegisterPanel").style.display = "none";

	var element = document.getElementById("lastname");
	element.value = "Paperino";
	var result = tester.getSurname();
	equal(result, "Paperino");
	i++;

	expect(i);
});

test("testGetQuestion()", function() {
	var i = 0;
	tester.initialize(mediator.getView("register"));
	document.getElementById("RegisterPanel").style.display = "none";

	var element = document.getElementById("question");
	element.value = "";
	try {
		tester.getQuestion();
	} catch (error) {
		equal(error, "domanda segreta non specificata");
		i++;
	}
	element.value = "Come si chiama la mia gatta?";
	var result = tester.getQuestion();
	equal(result, "Come si chiama la mia gatta?");
	i++;

	expect(i);
});

test("testGetAnswer()", function() {
	var i = 0;
	tester.initialize(mediator.getView("register"));
	document.getElementById("RegisterPanel").style.display = "none";

	var element = document.getElementById("answer");
	element.value = "";
	try {
		tester.getAnswer();
	} catch (error) {
		equal(error, "risposta alla domanda segreta non specificata");
		i++;
	}
	element.value = "Camilla";
	var result = tester.getAnswer();
	equal(result, "Camilla");
	i++;

	expect(i);
});

test("testGetPicturePath()", function() {
	var i = 0;
	tester.initialize(mediator.getView("register"));
	document.getElementById("RegisterPanel").style.display = "none";

	var element = document.getElementById("picture");
	element.files = [ "" ];
	var result = tester.getPicturePath();
	equal(result, "");
	i++;

	expect(i);
});

test("testRegister()", function() {
	var i = 0;
	var data = {
		name : "Paolino",
		surname : "Paperino",
		username : "indirizzo5@dominio.it",
		password : "password",
		picturePath : "img/contactImg/Default.png",
		question : "Di che colore e' la mia gatta?",
		answer : "tricolore"
	};

	tester.register(data);

	var user = login.user;
	equal(user.name, "Paolino");
	i++;
	equal(user.surname, "Paperino");
	i++;
	equal(user.id, 0);
	i++;
	equal(user.picturePath, "img/contactImg/Default.png");
	i++;

	expect(i);
});

test("testRegisterByClickUnsuccessfully()", function() {
	window.alert = function() {
	};
	var i = 0;
	tester.initialize(mediator.getView("register"));
	document.getElementById("RegisterPanel").style.display = "none";
	var element = document.getElementById("username");
	element.value = "indirizzo5@dominio.it";
	element = document.getElementById("password");
	element.value = "password";
	element = document.getElementById("firstname");
	element.value = "Paolino";
	element = document.getElementById("lastname");
	element.value = "Paperino";
	element = document.getElementById("question");
	element.value = "Come si chiama la mia gatta?";
	element = document.getElementById("answer");
	element.value = "";

	try {
		var button = document.getElementById("inputRegister");
		button.dispatchEvent(new MouseEvent("click"));
	} catch (error) {
		equal(error, "risposta alla domanda segreta non specificata");
		i++;
	}

	expect(i);
});

test("testOnShowRegistrationPanel", function() {
	var bool = false;
	document.addEventListener("eventRaised", function() {
		bool = true;
	});
	document.dispatchEvent(new CustomEvent("showRegistrationPanel"));
	ok(bool);
});