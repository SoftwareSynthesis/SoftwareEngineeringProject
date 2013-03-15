/**
 * Presenter incaricato di gestire il pannello di login
 *
 * @constructor
 * @this {LoginPanelPresenter}
 * @param {String} url URL della servlet con cui il presenter deve comunicare
 * @author Diego Beraldin
 */
function LoginPanelPresenter(url) {
/**********************************************************
                     VARIABILI PRIVATE
***********************************************************/
    // url della servlet che deve gestire il login
    var servletURL = url;
    // elemento controllato da questo presenter
    var element = document.getElementById("LoginPanel");
    
/**********************************************************
                      METODI PRIVATI
***********************************************************/
    /**
     * Testa quanto ricevuto dal server e, in caso di login avvenuto correttamente
     * reindirizza il browser nella pagina finale dopo aver salvato i dati dell'utente
     *
     * @param {String} data la stringa restituita dalla servlet a seguito del login
     * @author Diego Beraldin
     */
    function testCredentials(data) {
        var user = JSON.parse(data);
        if (user != null) {
            // 'communicationcenter' deve essere una variabile globale
            communicationcenter.my = user;
            this.hide();
            mediator.buildUI();
        }
    }
    
    /**
     * Invia la risposta alla domanda segreta al server
     * 
     * @param {String} username nome utente associato alla domanda segreta
     * @param {String} answer risposta alla domanda segreta
     * @author Diego Beraldin
     */
    function sendAnswer(username, answer) {
    	var request = XMLHttpRequest();
    	request.onclick = function() {
    		if (this.readyState == 4 && this.status == 200) {
    			if (JSON.parse(request.responseText)) {
    				correctAnswer();
    			} else {
    				incorrectAnswer();
    			}
    		}
    	};
    	request.open("POST", servletURL, true);
    	request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    	request.send("username=" + encodeURIComponent(username) + "&answer=" + encodeURIComponent(answer) + "&operation=4");
    }
    
    /**
     * Rimuove il form di recupero della password, visualizza un
     * messaggio di conferma per 2 secondi quindi rimuove anche
     * questo e lascia il controllo al form di login.
     * 
     * @author Diego Beraldin
     */
    function correctAnswer() {
    	var formRetrievePassword = document.getElementById("passwordretrieval");
    	element.removeChild(formRetrievePassword);
    	var message = document.createElement("p");
    	var text = document.createTextNode("Recupero password avvenuto correttamente." +
    			"Ti è stata inviata un'email contenente i dati richiesti.");
    	message.appendChild(text);
    	element.appendChild(message);
    	window.setTimeout(function(){ element.removeChild(message); }, 2000);
    }
    
    /**
     * In caso di inserimento della risposta non corretta alla domanda segreta
     * visualizza un messaggio di avvertimento all'utente per 2 secondi quindi lascia il
     * controllo al form di inserimento della risposta alla domanda segreta.
     * 
     * @author Diego Beraldin
     */
    function incorrectAnswer() {
    	var message = document.createElement("p");
    	var text = document.createTextNode("Dati non corretti. Inserire nuovamente la risposta.");
    	message.appendChild(text);
    	element.appendChild(message);
    	window.setTimeout(function(){ element.removeChild(message); }, 2000);
    }
    
    /**
     * Recupera la domanda segreta con una richiesta asincrona al server
     * 
     * @returns {String} la domanda segreta impostata dall'utente
     * @author Diego Beraldin
     */
    function getSecretQuestion() {
    	var userID = this.getUsername();
    	var question = "";
    	var request = new XMLHttpRequest();
    	request.onreadystatechange = function() {
    		if (this.readyState == 4 && this.status == 200) {
    			question = this.responseText;
    		}
    	};
    	request.open("POST", servletURL, true);
    	request.send("username=" + encodeURIComponent(userID) + "operation=3");
    	return question;
    }

/**********************************************************
                       METODI PUBBLICI
***********************************************************/
    /**
     * Costruisce il form per il recupero della password dell'utente
     * 
     * @param {String} username l'email dell'utente
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
    	var question = document.createTextNode(getSecretQuestion());
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
    		sendAnswer(username, inputAnswer.getAttribute("value"));
    	};
    	
    	formRetrievePassword.appendChild(labelQuestion);
    	formRetrievePassword.appendChild(inputAnswer);
    	return formRetrievePassword;
    };
    
    /**
     * Recupera lo username dall'interfaccia grafica utente
     * 
     * NOTA PER I VERIFICATORI
     * Richiede che il 'document' abbia al suo interno un elemento di
     * tipo '<input>' con 'id' uguale a 'username' contenente l'indirizzo
     * email dell'utente che intende autenticarsi al sistema
     * 
     * @author Diego Beraldin
     * @returns {String} lo username dell'utente
     */
    this.getUsername = function() {
    	return document.getElementById("username").value;;
    };
    
    /**
     * Recupera lo username dall'interfaccia grafica utente
     * 
     * NOTA PER I VERIFICATORI
     * Richiede che il 'document' abbia al suo interno un elemento di
     * tipo '<input>' con 'id' uguale a 'password' contenente la password
     * dell'utente che intende autenticarsi al sistema
     * 
     * @author Diego Beraldin
     * @returns {String} lo username dell'utente
     */
    this.getPassword = function() {
    	return document.getElementById("password").value;;
    };
    
    
    /**
     * Procedura che esegue il login inviando al server i dati di autenticazione
     *
     * @param {Object} un oggetto con proprietà 'username' e 'password'
     * @returns {String} la string di query che viene inviata alla servlet
     * @author Diego Beraldin
     */
    this.login = function(data) {
        if (!data.username || data.username == "" || !data.password || data.password == "") {
        	return "";
        }

        //invia la richiesta AJAX al server
        var request = new XMLHttpRequest();
        request.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {
                testCredentials(request.responseText);
            }
        };
        request.open("POST", this.servletURL, true);
        request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        var querystring = "username=" + encodeURIComponent(data.username) +
        				  "&password=" + encodeURIComponent(data.password) + "&operation=1";
        request.send(querystring);
        return querystring;
    };
    
    /**
     * Nasconde il form di autenticazione per lasciare spazio nella finestra a
     * altri elementi grafici come la schermata principale o il pannello di registrazione
     * 
     * @author Diego Beraldin
     */
    this.hide = function() {
    	if (element) {
    	    element.style.display = "none";
    	}
    };

    /**
     * Inizializzazione del pannello di login con la creazione di tutti i
     * widget grafici che sono contenuti al suo interno
     * 
     * NOTA PER I VERIFICATORI:
     * Richiede che nel 'document' sia presente un elemento '<div>' che
     * abbia come attributo 'id' il valore 'LoginPanel'
     *
     * @author Diego Beraldin
     */
    this.initialize = function() {
    	element.style.display = "block";
        //creazione dell'elemento form
        var loginForm = document.createElement('form');
        loginForm.setAttribute("name", "login");
        loginForm.setAttribute("action", "");
        loginForm.setAttribute("method", "");
        loginForm.setAttribute("accept-charset", "utf-8");

        //creazione dell'elemento <ul> contenuto nel form
        var ulData = document.createElement('ul');
        //TODO da spostare nel CSS
        ulData.style.listStyleType = "none";

        //creazione dell'item per lo username
        var liUserName = document.createElement('li');
        //label
        var labelUserName = document.createElement('label');
        labelUserName.setAttribute("for", "username");
        labelUserName.innerHTML = "Nome utente: ";
        //input
        var inputUserName = document.createElement('input');
        inputUserName.setAttribute("type", "email");
        inputUserName.setAttribute("id", "username");
        inputUserName.setAttribute("name", "username");
        inputUserName.setAttribute("placeholder", "yourname@email.com");
        inputUserName.setAttribute("required", "required");
        //costruisce il list item con la label e l'input
        liUserName.appendChild(labelUserName);
        liUserName.appendChild(inputUserName);

        //crea l'item per la password
        var liPassword = document.createElement('li');
        //label
        var labelPassword = document.createElement('label');
        labelPassword.setAttribute("for", "password");
        labelPassword.innerHTML = "Password: ";
        //input
        var inputPassword = document.createElement('input');
        inputPassword.setAttribute("type", "password");
        inputPassword.setAttribute("id", "password");
        inputPassword.setAttribute("name", "password");
        inputPassword.setAttribute("placeholder", "password");
        inputPassword.setAttribute("required", "required");
        //costruisce il list item con la label e l'input
        liPassword.appendChild(labelPassword);
        liPassword.appendChild(inputPassword);


        //pulsante di login
        var inputLogin = document.createElement('input');
        inputLogin.setAttribute("type", "submit");
        inputLogin.setAttribute("value", "Login");
        var self = this;
        inputLogin.onclick = function() {
        	var data = new Object();
        	data.username = self.getUsername();
        	data.password = self.getPassword();
        	self.login(data);
        };
        //pulsante di registrazione
        var inputRegister = document.createElement('input');
        inputRegister.setAttribute("type", "submit");
        inputRegister.setAttribute("value", "Registrati");
        inputRegister.onclick = function() {
        	self.hide();
            mediator.buildRegistrationUI();
        };
        //pulsante per recuperare la password
        var inputRetrievePassword = document.createElement("input");
        inputRetrievePassword.setAttribute("type", "submit");
        inputRetrievePassword.setAttribute("value", "Recupera password");
        inputRetrievePassword.onclick = function() {
        	var form = self.buildRetrievePasswordForm();
        	element.appendChild(form);
        };

        //creazione dell'item per i pulsanti
        var liButtons = document.createElement('li');
        liButtons.appendChild(inputLogin);
        liButtons.appendChild(inputRegister);
        liButtons.appendChild(inputRetrievePassword);

        //appende tutti gli elementi al form
        ulData.appendChild(liUserName);
        ulData.appendChild(liPassword);
        ulData.appendChild(liButtons);
        loginForm.appendChild(ulData);

        //appende il form al DOM della pagina
        element.appendChild(loginForm);
    };
}
