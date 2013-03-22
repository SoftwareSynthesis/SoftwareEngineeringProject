/**
 * Presenter incaricato di gestire il pannello di login
 * 
 * @constructor
 * @this {LoginPanelPresenter}
 * @param {String}
 *            url URL della servlet con cui il presenter deve comunicare
 * @author Diego Beraldin
 */
function LoginPanelPresenter() {
	/***************************************************************************
	 * VARIABILI PRIVATE
	 **************************************************************************/
	/*
	 * 0 = LoginAutentication
	 * 1 = LoginClose
	 * 2 = LoginGetSecretQuestion
	 * 3 = LoginDoSecretAnswer
	 * 4 = LoginDoRegistration
	 */

	// elemento controllato da questo presenter
	var element = document.getElementById("LoginPanel");
	// array degli URL  delle servlet che sono utilizzate qui dentro
	var servlets = new Array();
	// file di configurazione che contiene gli URL delle servlet
	// TODO da cambiare in fase di test
	var configurationFile = "../WebContent/Conf/servletlocationtest.xml";
	//inizializza gli URL delle servlet
	getServletURLs();

	/***************************************************************************
	 * METODI PRIVATI
	 **************************************************************************/
	/**
	 * Configura gli URL delle servlet da interrogare leggendoli dal file di configurazione
	 */
	function getServletURLs() {
		var configurationRequest = new XMLHttpRequest();
		configurationRequest.open("POST", configurationFile, false);
		configurationRequest.send();
		var XMLDocument = configurationRequest.responseXML;
		var baseURL = XMLDocument.getElementsByTagName("baseURL")[0].childNodes[0].data;
		
		var names = Array();
		names.push(XMLDocument.getElementById("authentication").childNodes[0].data);;
		names.push(XMLDocument.getElementById("question").childNodes[0].data);
		names.push(XMLDocument.getElementById("answer").childNodes[0].data);
		
		for (var i in names) {
			servlets.push(baseURL + names[i]);
		}
	}
	
	/**
	 * Testa quanto ricevuto dal server e, in caso di login avvenuto
	 * correttamente reindirizza il browser nella pagina finale dopo aver
	 * salvato i dati dell'utente
	 * 
	 * @param {String}
	 *            data la stringa restituita dalla servlet a seguito del login
	 * @author Diego Beraldin
	 */
	function testCredentials(data) {
		var user = JSON.parse(data);
		if (user != null) {
			// 'communicationcenter' deve essere una variabile globale
			communicationcenter.my = user;
			mediator.buildUI();
		}
	}

	/**
	 * Rimuove il form di recupero della password, visualizza un messaggio di
	 * conferma per 2 secondi quindi rimuove anche questo e lascia il controllo
	 * al form di login.
	 * 
	 * @author Diego Beraldin
	 */
	function correctAnswer() {
		var formRetrievePassword = document.getElementById("passwordretrieval");
		element.removeChild(formRetrievePassword);
		var message = document.createElement("p");
		var text = document
				.createTextNode("Recupero password avvenuto correttamente."
						+ "Ti è stata inviata un'email contenente i dati richiesti.");
		message.appendChild(text);
		element.appendChild(message);
		window.setTimeout(function() {
			element.removeChild(message);
		}, 2000);
	}

	/**
	 * In caso di inserimento della risposta non corretta alla domanda segreta
	 * visualizza un messaggio di avvertimento all'utente per 2 secondi quindi
	 * lascia il controllo al form di inserimento della risposta alla domanda
	 * segreta.
	 * 
	 * @author Diego Beraldin
	 */
	function incorrectAnswer() {
		var message = document.createElement("p");
		var text = document
				.createTextNode("Dati non corretti. Inserire nuovamente la risposta.");
		message.appendChild(text);
		element.appendChild(message);
		window.setTimeout(function() {
			element.removeChild(message);
		}, 2000);
	}

	/**
	 * Recupera la domanda segreta con una richiesta asincrona al server
	 * 
	 * @returns {String} la domanda segreta impostata dall'utente
	 * @author Diego Beraldin
	 */
	function getSecretQuestion() {
		var question = "";
		var request = new XMLHttpRequest();
		request.open("POST", servlets[1], false);
		request.setRequestHeader("Content-type",
				"application/x-www-form-urlencoded");
		request.send();
		question = request.responseText;
		return question;
	}

	/***************************************************************************
	 * METODI PUBBLICI
	 **************************************************************************/
	/**
	 * Testa se l'utente ha dato la risposta corretta alla domanda segreta
	 * 
	 * @param {String}
	 *            username nome utente associato alla domanda segreta
	 * @param {String}
	 *            answer risposta alla domanda segreta
	 * @returns {Boolean} true se la risposta è corretta, false altrimenti
	 * @author Diego Beraldin
	 */
	this.hasAnsweredCorrectly = function(username, answer) {
		var request = new XMLHttpRequest();
		request.open("POST", servlets[2], false);
		request.setRequestHeader("Content-type",
				"application/x-www-form-urlencoded");
		request.send("username=" + encodeURIComponent(username) + "&answer=" + encodeURIComponent(answer));
		return JSON.parse(request.responseText);
	};

	/**
	 * Costruisce il form per il recupero della password dell'utente
	 * 
	 * @param {String}
	 *            username l'email dell'utente
	 * @returns {HTMLFormElement} il form per il recupero della password
	 * @author Diego Beraldin
	 */
	this.buildRetrievePasswordForm = function() {
		// costruisce il form con il valore ottenuto
		var formRetrievePassword = document.createElement("fieldset");
		formRetrievePassword.setAttribute("id", "passwordretrieval");
		// etichetta con la domanda
		var labelQuestion = document.createElement("label");
		labelQuestion.setAttribute("for", "inputanswer");
		// procura il testo della domanda segreta
		var text = getSecretQuestion();
		var question = document.createTextNode(text);
		labelQuestion.appendChild(question);
		// campo di immissione
		var inputAnswer = document.createElement("input");
		inputAnswer.setAttribute("id", "inputanswer");
		inputAnswer.setAttribute("name", "inputanswer");
		inputAnswer.setAttribute("placeholder", "risposta");
		inputAnswer.setAttribute("required", "required");
		// pulsante di conferma
		var submitButton = document.createElement("input");
		submitButton.setAttribute("type", "submit");
		submitButton.setAttribute("value", "OK");
		submitButton.onClick = function() {
			// Answer(username, inputAnswer.getAttribute("value"));
			if (this.hasAnsweredCorrectly(username, inputAnswer
					.getAttribute("value"))) {
				correctAnswer();
			} else {
				incorrectAnswer();
			}
		};

		formRetrievePassword.appendChild(labelQuestion);
		formRetrievePassword.appendChild(inputAnswer);
		formRetrievePassword.appendChild(submitButton);
		return formRetrievePassword;
	};

	/**
	 * Recupera lo username dall'interfaccia grafica utente
	 * 
	 * NOTA PER I VERIFICATORI Richiede che il 'document' abbia al suo interno
	 * un elemento di tipo '<input>' con 'id' uguale a 'username' contenente
	 * l'indirizzo email dell'utente che intende autenticarsi al sistema
	 * 
	 * @author Diego Beraldin
	 * @returns {String} lo username dell'utente
	 */
	this.getUsername = function() {
		var username = document.getElementById("username").value;
		if (!username || username.length == 0) {
			throw "valore non specificato";
		}
		var emailRegex = new RegExp("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,4}$",
				"i");
		if (!emailRegex.test(username)) {
			throw "indirizzo email non valido";
		}
		return username;
	};

	/**
	 * Recupera lo username dall'interfaccia grafica utente
	 * 
	 * NOTA PER I VERIFICATORI Richiede che il 'document' abbia al suo interno
	 * un elemento di tipo '<input>' con 'id' uguale a 'password' contenente la
	 * password dell'utente che intende autenticarsi al sistema
	 * 
	 * @author Diego Beraldin
	 * @returns {String} lo username dell'utente
	 */
	this.getPassword = function() {
		var password = document.getElementById("password").value;
		if (!password || password.length == 0) {
			throw "valore non specificato";
		}
		return password;
	};

	/**
	 * Procedura che esegue il login inviando al server i dati di autenticazione
	 * 
	 * NOTE PER I VERIFICATORI Testare communicationcenter.my al termine della
	 * procedura di login!!!
	 * 
	 * @param {Object}
	 *            un oggetto con proprietà 'username' e 'password'
	 * @returns {String} la string di query che viene inviata alla servlet
	 * @author Diego Beraldin
	 */
	this.login = function(data) {
		// invia la richiesta AJAX al server
		var request = new XMLHttpRequest();
		request.onreadystatechange = function() {
			if (this.readyState == 4 && this.status == 200) {
				testCredentials(request.responseText);
			}
		};
		request.open("POST", servlets[0], true);
		request.setRequestHeader("Content-type",
				"application/x-www-form-urlencoded");
		var querystring = "username=" + encodeURIComponent(data.username)
				+ "&password=" + encodeURIComponent(data.password);
		request.send(querystring);
		return querystring;
	};

	/**
	 * Nasconde il form di autenticazione per lasciare spazio nella finestra a
	 * altri elementi grafici come la schermata principale o il pannello di
	 * registrazione
	 * 
	 * @author Diego Beraldin
	 */
	this.hide = function() {
		if (element) {
			element.style.display = "none";
		}
	};

	/**
	 * Inizializzazione del pannello di login con la creazione di tutti i widget
	 * grafici che sono contenuti al suo interno
	 * 
	 * NOTA PER I VERIFICATORI: Richiede che nel 'document' sia presente un
	 * elemento '<div>' che abbia come attributo 'id' il valore 'LoginPanel'
	 * 
	 * @author Diego Beraldin
	 */
	this.initialize = function() {
		element.style.display = "block";
		// creazione dell'elemento form
		var loginForm = document.createElement('fieldset');
		loginForm.id = "loginForm";

		// creazione dell'elemento <ul> contenuto nel form
		var ulData = document.createElement('ul');
		// TODO da spostare nel CSS
		ulData.style.listStyleType = "none";

		// creazione dell'item per lo username
		var liUserName = document.createElement('li');
		// label
		var labelUserName = document.createElement('label');
		labelUserName.setAttribute("for", "username");
		labelUserName.innerHTML = "Nome utente: ";
		// input
		var inputUserName = document.createElement('input');
		inputUserName.setAttribute("type", "email");
		inputUserName.setAttribute("id", "username");
		inputUserName.setAttribute("name", "username");
		inputUserName.setAttribute("placeholder", "yourname@email.com");
		inputUserName.setAttribute("required", "required");
		// costruisce il list item con la label e l'input
		liUserName.appendChild(labelUserName);
		liUserName.appendChild(inputUserName);

		// crea l'item per la password
		var liPassword = document.createElement('li');
		// label
		var labelPassword = document.createElement('label');
		labelPassword.setAttribute("for", "password");
		labelPassword.innerHTML = "Password: ";
		// input
		var inputPassword = document.createElement('input');
		inputPassword.setAttribute("type", "password");
		inputPassword.setAttribute("id", "password");
		inputPassword.setAttribute("name", "password");
		inputPassword.setAttribute("placeholder", "password");
		inputPassword.setAttribute("required", "required");
		// costruisce il list item con la label e l'input
		liPassword.appendChild(labelPassword);
		liPassword.appendChild(inputPassword);

		// pulsante di login
		var inputLogin = document.createElement('button');
		inputLogin.type = "button";
		inputLogin.appendChild(document.createTextNode('Login'));
		var self = this;
		inputLogin.onclick = function() {
			var data = new Object();
			data.username = self.getUsername();
			data.password = self.getPassword();
			self.login(data);
		};
		// pulsante di registrazione
		var inputRegister = document.createElement('button');
		inputRegister.type = "button";
		inputRegister.appendChild(document.createTextNode('Registrazione'));
		inputRegister.onclick = function() {
			self.hide();
			mediator.buildRegistrationUI();
		};
		// pulsante per recuperare la password
		var inputRetrievePassword = document.createElement("button");
		inputRetrievePassword.type = "button";
		inputRetrievePassword.appendChild(document
				.createTextNode('Recupera password'));
		inputRetrievePassword.onclick = function() {
			var form = self.buildRetrievePasswordForm();
			element.appendChild(form);
		};

		// creazione dell'item per i pulsanti
		var liButtons = document.createElement('li');
		liButtons.appendChild(inputLogin);
		liButtons.appendChild(inputRegister);
		liButtons.appendChild(inputRetrievePassword);

		// appende tutti gli elementi al form
		ulData.appendChild(liUserName);
		ulData.appendChild(liPassword);
		ulData.appendChild(liButtons);
		loginForm.appendChild(ulData);

		// appende il form al DOM della pagina
		element.appendChild(loginForm);
	};
}
