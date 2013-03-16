module("RegisterPanelPresenterTest", {
	setup: function() {
		element = document.createElement("div");
		element.setAttribute("id", "RegisterPanel");
		element.style.position = "absolute";
		element.style.left ="-999em";
		document.body.appendChild(element);
		mediator = {buildUI: function() {}};
		communicationcenter = new Object();
		communicationcenter.my = {name: null, surname: null, username: null, picturePath: null};
		tester = new RegisterPanelPresenter("LoginManager.php");
	},
	teardown: function() {
		document.body.removeChild(element);
	}
});


test("initializeTest()", function() {
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

test("hideTest()", function() {
	tester.hide();
	equal(element.style.display, "none", "il pannello è stato correttamente nascosto");
});

// richiede di essere in un WEB SERVER per essere eseguito (altrimenti non interpreta lo script PHP)
test("registerTest()", function() {
	var i = 0;
	// questo è uno stub
	var data = {name: "Flavia", surname: "Bacco", username: "flabacco@gmail.com",
			    password: "farfalla", picturePath: "flavietta.png",
			    question: "di che colore e' la mia gatta?", answer: "tricolore"};
	var querystring = tester.register(data);
	equal(querystring,
			"username=flabacco%40gmail.com&password=farfalla&question=di%20che%20colore%20e'%20la%20mia%20gatta%3F&answer=tricolore&name=Flavia&surname=Bacco&picturePath=flavietta.png&operation=2",
			"stringa di query generata correttamente"); i++;
    //FIXME non riesco a farlo andare!
    equal(communicationcenter.my.name, "Flavia", "ha effettuato l'accesso al sistema"); i++;
	
	expect(i);
});
