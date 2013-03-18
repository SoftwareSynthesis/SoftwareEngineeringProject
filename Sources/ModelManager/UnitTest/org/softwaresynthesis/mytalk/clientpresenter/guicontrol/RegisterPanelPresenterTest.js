module("RegisterPanelPresenterTest", {
	setup: function() {
		// questo è uno stub
		element = document.createElement("div");
		element.setAttribute("id", "RegisterPanel");
		element.style.position = "absolute";
		element.style.left ="-999em";
		document.body.appendChild(element);
		// anche questo è uno stub
		mediator = {buildUI: function() {}};
		// altro stub
		communicationcenter = new Object();
		/* oggetto da testare, lo script PHP che sta al posto della servlet
		 * LoginManager è anch'esso, ebbene sì, uno stub!
		 */
		tester = new RegisterPanelPresenter("LoginManager.php");
	},
	teardown: function() {
		document.body.removeChild(element);
	}
});


test("testInitialize()", function() {
	var i = 0;
	tester.initialize();
	ok(element, "l'elemento esiste ancora"); i++;
	
	var children = element.childNodes;
	equal(children.length, 1, "l'elemento contiene un solo figlio"); i++;
	var form = children[0];
	equal(form.nodeName, "FORM", "l'elemento è un <form>"); i++;
	
	children = form.childNodes;
	equal(children.length, 1, "il form ha un unico figlio"); i++;
	
	var list = children[0];
	equal(list.nodeName, "UL", "il form contiene una lista"); i++;
	
	children = list.childNodes;
	equal(children.length, 8, "la lista contiene otto elementi"); i++;
	
	var liUsername = children[0];
	var liPassword = children[1];
	var liSecretQ = children[2];
	var liAnswerSQ = children[3];
	var liName = children[4];
	var liSurname = children[5];
	var liPicture = children[6];
	var liButtons = children[7];
	
	// per lo username
	var labelUsername = liUsername.childNodes[0];
	var inputUsername = liUsername.childNodes[1];
	equal(labelUsername.nodeName, "LABEL", "la label ha il tipo corretto"); i++;
	equal(labelUsername.getAttribute("for"), inputUsername.id, "attributo for della label corretto"); i++;
	equal(inputUsername.type, "email", "l'input username ha il tipo corretto"); i++;
	ok(inputUsername.required, "attributo required corretto per username"); i++;
	
	// per la password
	var labelPassword = liPassword.childNodes[0];
	var inputPassword = liPassword.childNodes[1];
	equal(labelPassword.nodeName, "LABEL", "la label ha il tipo corretto"); i++;
	equal(labelPassword.getAttribute("for"), inputPassword.id, "la label ha l'attributo for corretto"); i++;
	equal(inputPassword.type, "password", "tipo corretto dell'input per la password"); i++;
	ok(inputPassword, "attributo required corretto per password"); i++;
	
	// per la domanda segreta
	var labelSecretQ = liSecretQ.childNodes[0];
	var inputSecretQ = liSecretQ.childNodes[1];
	equal(labelSecretQ.nodeName, "LABEL", "la label ha il tipo corretto"); i++;
	equal(labelSecretQ.getAttribute("for"), inputSecretQ.id, "la label ha l'attributo for corretto"); i++;
	equal(inputSecretQ.type, "text", "tipo corretto dell'input per la domanda segreta"); i++;
	ok(inputSecretQ, "attributo required corretto per la domanda segreta"); i++;
	
	// per la risposta alla domanda segreta
	var labelAnswerSQ = liAnswerSQ.childNodes[0];
	var inputAnswerSQ = liAnswerSQ.childNodes[1];
	equal(labelAnswerSQ.nodeName, "LABEL", "la label ha il tipo corretto"); i++;
	equal(labelAnswerSQ.getAttribute("for"), inputAnswerSQ.id, "la label ha l'attributo for corretto"); i++;
	equal(inputAnswerSQ.type, "text", "tipo corretto dell'input per la risposta alla domanda segreta"); i++;
	ok(inputAnswerSQ, "attributo required corretto per la risposta alla domanda segreta"); i++;
	
	// per il nome
	var labelName = liName.childNodes[0];
	var inputName = liName.childNodes[1];
	equal(labelName.nodeName, "LABEL", "la label ha il tipo corretto"); i++;
	// trovato errore: nell'attributo 'for' del '<label>' era scritto firtname invece di firstname ;)
	equal(labelName.getAttribute("for"), inputName.id, "attributo for della label corretto"); i++;
	equal(inputName.type, "text", "l'input per il nome ha il tipo corretto"); i++;
	ok(!inputName.required, "attributo required corretto per name"); i++;
	
	// per il cognome
	var labelSurname = liSurname.childNodes[0];
	var inputSurname = liSurname.childNodes[1];
	equal(labelSurname.nodeName, "LABEL", "la label ha il tipo corretto"); i++;
	equal(labelSurname.getAttribute("for"), inputSurname.id, "attributo for della label corretto"); i++;
	equal(inputSurname.type, "text", "l'input per il cognome ha il tipo corretto"); i++;
	ok(!inputSurname.required, "attributo required corretto per il cognome"); i++;
	
	// per l'immagine
	var labelPicture = liPicture.childNodes[0];
	var inputPicture = liPicture.childNodes[1];
	equal(labelPicture.nodeName, "LABEL", "la label ha il tipo corretto"); i++;
	equal(labelPicture.getAttribute("for"), inputPicture.id, "attributo for della label corretto"); i++;
	equal(inputPicture.type, "file", "l'input per l'immagine ha il tipo corretto"); i++;
	ok(!inputPicture.required, "attributo required corretto per l'immagine"); i++;
	
	// per il pulsante
	var button = liButtons.childNodes[0];
	equal(button.type, "submit", "il pulsante è presente"); i++;
	equal(button.value, "Registrati", "testo del pulsante corretto"); i++;
	
	expect(i);
});

test("testHide()", function() {
	tester.hide();
	equal(element.style.display, "none", "il pannello è stato correttamente nascosto");
});

// richiede di essere in un WEB SERVER per essere eseguito (altrimenti non interpreta lo script PHP)
test("testRegister()", function() {
	var i = 0;
	// questo è uno stub (simula i dati recuperati dal form dell'interfaccia)
	var data = {name: "Flavia", surname: "Bacco", username: "flabacco@gmail.com",
			    password: "farfalla", picturePath: "flavietta.png",
			    question: "di che colore e' la mia gatta?", answer: "tricolore"};
	var querystring = tester.register(data);
	equal(querystring,
			"username=flabacco%40gmail.com&password=farfalla&question=di%20che%20colore%20e'%20la%20mia%20gatta%3F&answer=tricolore&name=Flavia&surname=Bacco&picturePath=flavietta.png&operation=2",
			"stringa di query generata correttamente"); i++;
    equal(communicationcenter.my.name, "Flavia", "nome registrato correttamente"); i++;
    equal(communicationcenter.my.surname, "Bacco", "cognome registrato correttamente"); i++;
    equal(communicationcenter.my.username, "flabacco@gmail.com", "username registrato correttamente"); i++;
    equal(communicationcenter.my.id, 0, "id del nuovo utente impostato correttamente"); i++;
    equal(communicationcenter.my.picturePath,
    		"http://softwaresynthesis.org/pictures/flavietta.png",
    		"immagine del nuovo utente salvata correttamente"); i++;
	
	expect(i);
});

test("testGetPicturePath()", function() {
	// crea lo stub di interfaccia grafica
	var inputPicture = document.createElement("input");
	inputPicture.type ="file";
	inputPicture.id = "picture";
	inputPicture.setAttribute("value", "home/flavia/Immagini/flavietta.png");
	element.appendChild(inputPicture);
	
	// invoca il metodo da testare
	var picturePath = tester.getPicturePath();
	equal(picturePath, inputPicture.value, "percorso dell'immagine recuperato correttamente");
	element.removeChild(inputPicture);
});

test("testGetUsername()", function() {
	var i = 0;
	
	// crea lo stub di interfaccia grafica
	var inputUsername = document.createElement("input");
	inputUsername.type ="email";
	inputUsername.id = "username";
	inputUsername.value = "flabacco@gmail.com";
	element.appendChild(inputUsername);
	
	// invoca il metodo da testare la prima volta
	var username = tester.getUsername();
	equal(username, inputUsername.value, "usename recuperato correttamente"); i++;
	
	// controlla che il campo sia compilato corrrettamente
	inputUsername.value = "";
	try {
		tester.getUsername();
		ok(false, "non rilevato username mancante"); i++;
	} catch (err) {
		equal(err, "valore non specificato", "username mancante rilevato correttamente"); i++;
	}
	
	// crea un indirizzo email non valido
	inputUsername.value = "flabacco.gmail.com";
	try {
		tester.getUsername();
		// se arriva qui significa che il metodo non rileva l'errore
		ok(false, "non rilevato indirizzo email malformato"); i++;
	} catch (err) {
		equal(err, "indirizzo email non valido", "email malformato rilevato correttamente"); i++;
	}
	
	element.removeChild(inputUsername);
	expect(i);
});


test("testGetPassword()", function() {
	var i = 0;
	
	// crea lo stub di interfaccia grafica
	var inputPassword = document.createElement("input");
	inputPassword.type = "password";
	inputPassword.id = "password";
	inputPassword.value = "farfalla";
	element.appendChild(inputPassword);
	
	// invoca il metodo da testare con una password legale
	var password = tester.getPassword();
	equal(password, inputPassword.value, "password recuperata correttamente"); i++;
	
	// verifica se rileva password mancante
	inputPassword.value = "";
	try {
		tester.getPassword();
		ok(false, "non rilevata la password mancante");
	} catch (err) {
		equal(err, "valore non specificato", "password mancante rilevata correttamente"); i++;
	}
	
	element.removeChild(inputPassword);
	expect(i);
});

test("testGetQuestion()", function() {
	var i = 0;
	
	// crea lo stub di interfaccia grafica
	var inputSecretQ = document.createElement("input");
	inputSecretQ.id = "question";
	inputSecretQ.value = "Di che colore è il mio gatto?";
	element.appendChild(inputSecretQ);
	
	// invoca il metodo da testare con una domanda legale
	var question = tester.getQuestion();
	equal(question, inputSecretQ.value, "domanda segreta recuperata correttamente"); i++;
	
	// verifica se rileva domanda mancante
	inputSecretQ.value = "";
	try {
		tester.getQuestion();
		ok(false, "non rilevata la domanda segreta mancante");
	} catch (err) {
		equal(err, "valore non specificato", "domanda segreta mancante rilevata correttamente"); i++;
	}
	
	element.removeChild(inputSecretQ);
	expect(i);
});

test("testGetAnswer()", function() {
	var i = 0;
	
	// crea lo stub di interfaccia grafica
	var inputAnswer = document.createElement("input");
	inputAnswer.id = "answer";
	inputAnswer.value = "tricolore";
	element.appendChild(inputAnswer);
	
	// invoca il metodo da testare con una risposta legale
	var answer = tester.getAnswer();
	equal(answer, inputAnswer.value, "risposta recuperata correttamente"); i++;
	
	// verifica se rileva risposta mancante
	inputAnswer.value = "";
	try {
		tester.getAnswer();
		ok(false, "non rilevata la risposta mancante");
	} catch (err) {
		equal(err, "valore non specificato", "risposta mancante rilevata correttamente"); i++;
	}
	
	element.removeChild(inputAnswer);
	expect(i);
});

test("testGetName()", function() {
	// crea lo stub di interfaccia grafica
	var inputName = document.createElement("input");
	inputName.id = "firstname";
	inputName.value = "Flavia";
	element.appendChild(inputName);
	
	// invoca il metodo da testare
	var name = tester.getName();
	equal(name, inputName.value, "nome recuperato correttamente");
	element.removeChild(inputName);
});

test("testGetSurname()", function() {
	// crea lo stub di interfaccia grafica
	var inputSurname = document.createElement("input");
	inputSurname.id = "lastname";
	inputSurname.value = "Bacco";
	element.appendChild(inputSurname);
	
	// invoca il metodo da testare
	var surname = tester.getSurname();
	equal(surname, inputSurname.value, "cognome recuperato correttamente");
	element.removeChild(inputSurname);
});