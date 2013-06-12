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
						document.dispatchEvent(new CustomEvent("eventRaised"));
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
			var labelPassword = liPassword.children[1];
			var inputPassword = liPassword.children[2];
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

/**
 * Lo scopo del test è verificare che sia recuperato correttamente nel caso in
 * cui si tratta di un indirizzo email valido, che viene rilevato (e gestito) il
 * caso in cui il campo non sia stato completato oppure che si tratti di una
 * stringa non valida come indirizzo email.
 * 
 * @version 2.0
 * @author Diego Beraldin
 */
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

/**
 * Il test crea un simula la compilazione da parte dell'utente del campo per la
 * password, provando in seguito a recuperarla mediante il metodo getPassword().
 * Il test verifica anche il sollevamento di un'eccezione nel momento in cui non
 * sia stato inserito alcun valore in questo campo.
 * 
 * @version 2.0
 * @author Diego Beraldin
 */
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

/**
 * Il test crea un simula la compilazione da parte dell'utente del campo per il
 * nome, provando in seguito a recuperarlo mediante il metodo etName() e
 * verificando che il risultato corrisponda a quanto immesso.
 * 
 * @version 2.0
 * @author Diego Beraldin
 */
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

/**
 * Il test simula la compilazione da parte dell'utente del campo per il cognome,
 * provando in seguito a recuperarlo mediante il metodo getSurname() e
 * verificando che il risultato corrisponda a quanto immesso.
 * 
 * @version 2.0
 * @author Diego Beraldin
 */
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

/**
 * Il test simula la compilazione da parte dell'utente del campo per la domanda
 * segreta, provando in seguito a recuperarla mediante il metodo getQuestion() e
 * verificando che il risultato corrisponda a quanto immesso. Dal momento che si
 * tratta di un dato obbligatorio, il test verifica anche che sia sollevata
 * un'eccezione nel caso in cui non sia fornito alcun valore.
 * 
 * @version 2.0
 * @author Diego Beraldin
 */
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

/**
 * Il test simula la compilazione da parte dell'utente del campo per la risposta
 * alla domanda segreta, provando in seguito a recuperarla mediante il metodo
 * getAnswer() e verificando che il risultato corrisponda a quanto immesso. Dal
 * momento che si tratta di un dato obbligatorio, il test verifica anche che sia
 * sollevata un'eccezione nel caso in cui questo valore non sia fornito.
 * 
 * @version 2.0
 * @author Diego Beraldin
 */
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

/**
 * Il test simula il caricamento di un'immagine per il profilo personale,
 * provando in seguito a recuperarla mediante il metodo getPicturePath() e
 * verificando che il risultato corrisponda a quanto immesso.
 * 
 * @version 2.0
 * @author Diego Beraldin
 */
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

/**
 * Verifica che sia sollevato un evento con stringa identificativa 'login' se la
 * registrazione va a buon fine e che la proprietà 'user' associata all'evento
 * di autenticazione sia uguale in ogni sua parte all'oggetto utilizzato per
 * popolare il form di registrazione con i dati di test.
 * 
 * @version 2.0
 * @author Diego Beraldin
 */
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
	equal(user.picturePath, "Default.png");
	i++;

	expect(i);
});

/**
 * Verifica che premendo il tasto di registrazione con parametri errati, e in
 * particolare se non viene completato un campo dato obbligatorio, venga
 * sollevata l'eccezione corrispondente (che sarà in seguito tradotta in un
 * messaggio visualizzato a schermo).
 * 
 * @version 2.0
 * @author Diego Beraldin
 */
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

/**
 * Verifica che venga visualizzato il pannello di registrazione quando il
 * presenter reagisce all'evento 'showRegistrationPanel', controllando tramite
 * un mock di PresenterMediator che sia invocato il metodo getView.
 * 
 * @version 2.0
 * @author Diego Beraldin
 */
test("testOnShowRegistrationPanel()", function() {
	var bool = false;
	document.addEventListener("eventRaised", function() {
		bool = true;
	});
	document.dispatchEvent(new CustomEvent("showRegistrationPanel"));
	ok(bool);
});

/**
 * Verifica che il punteggio sia calcolato correttamente tramite il metodo
 * calculateScore, passando come parametro un carattere alfabetico minuscolo, un
 * carattere numerico, una lettera maiuscola e un carattere speciale
 * controllando che fornisca i risultati attesi.
 * 
 * @version 2.0
 * @author Diego Beraldin
 */
test("testCalculateScore()", function() {
	var i = 0;
	var result = 0;

	result = tester.calculateScore("a");
	equal(result, 1);
	i++;
	result = tester.calculateScore("1");
	equal(result, 2);
	i++;
	result = tester.calculateScore(".");
	equal(result, 3);
	i++;
	result = tester.calculateScore("A");
	equal(result, 2);
	i++;

	expect(i);
});

/**
 * Verifica il comportamento del metodo processPassword simulando la
 * compilazione da parte dell'utente del campo di inserimento per la password e
 * l'evento 'input' sull'elemento <input>, controllando che sullo span sia
 * scritto il testo corretto e che sia impostato l'attributo class in maniera
 * corretta.
 * 
 * @version 2.0
 * @author Diego Beraldin
 */
test("testProcessPassword()", function() {
	var i = 0;
	tester.initialize(mediator.getView("register"));
	document.getElementById("RegisterPanel").style.display = "none";
	var element = document.getElementById("password");
	element.value = "pippoPluto10!";
	element.dispatchEvent(new UIEvent("input"));
	element = document.getElementById("complexitySpan");

	equal(element.className, "high");
	i++;
	equal(element.innerHTML.trim(), "compl. elevata");
	i++;

	expect(i);
});