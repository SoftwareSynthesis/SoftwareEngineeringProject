/**
 * Presenter incaricato di gestire il pannello di login
 *
 * @constructor
 * @this {LoginPanelPresenter}
 * @author Diego Beraldin
 */
function LoginPanelPresenter() {
/**********************************************************
                     VARIABILI PRIVATE
***********************************************************/
    // url della servlet che deve gestire il login
    var servletURL = "localhost:8080/LoginManager";
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
    };
    
    /**
     * Invia la risposta alla domanda segreta al server
     * 
     * @param {String} username nome utente associato alla domanda segreta
     * @param {String} answer risposta alla domanda segreta
     * @author Diego Beraldin
     */
    function sendAnswer(username, answer) {
    	var request = XMLHttpRequest();
    	request.open("POST", servletURL, true);
    	request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    	request.send("username=" + encodeURIComponent(username) + "&answer=" + encodeURIComponent(answer) + "&operation=4");
    	/* FIXME questo va contro 10.000 diagrammi di attività, bisognerebbe attendere la risposta 
    	 * dal server e differenziare il comportamento del client a seconda che i dati siano corretti o errati!
    	 */
		var form = document.getElementById("passwordretrieval");
		var message = document.createElement("p");
		message.innerHTML = "La richiesta &egrave; stata inoltrata, " +
				"se hai inserito la risposta corretta riceverai via email la password.";
		element.removeChild(form);
		element.appendChild(message);
    }
    
    /**
     * Attiva la proocedura del recupero passoword per l'utente
     * 
     * @param {String} username l'email dell'utente
     * @author Diego Beraldin
     */
    function retrievePassword(username) {
    	//invia la richiesta AJAX al server per ottenere la domanda segreta
    	var question = "";
    	var request = new XMLHttpRequest();
    	request.onreadystatechange = function() {
    		if (this.readyState == 4 && this.status == 200) {
    			question = request.responseText;
    		}
    	};
    	request.open("POST", servletURL, "true");
    	request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    	request.send("username=" + encodeURIComponent(username) + "&operation=3");
    	
    	// costruisce il form con il valore ottenuto
    	var formRetrievePassword = document.createElement("fieldset");
    	formRetrievePassword.setAttribute("id", "passwordretrieval");
    	// etichetta con la domanda
    	var labelQuestion = document.createElement("label");
    	labelQuestion.setAttribute("for", "inputanswer");
    	labelQuestion.innerHTML = question;
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
    	element.appendChild(formRetrievePassword);
    }
    
    /**
     * Procedura che esegue il login prelevando le credenziali inserite nel
     * form e inviando al server i dati di autenticazione
     *
     * @author Diego Beraldin
     */
    function login() {
        //recupera le credenziali dall'interfaccia grafica
        var username = document.getElementById("username").value;
        var password = document.getElementById("password").value;
        
        if (!username || username == "" || !password || password == "") {
        	return;
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
        request.send("username=" + encodeURIComponent(username) + "&password=" + encodeURIComponent(password) + "&operation=1");
    };
    
/**********************************************************
                       METODI PUBBLICI
***********************************************************/
    /**
     * Nasconde il form di autenticazione per lasciare spazio nella finestra a
     * altri elementi grafici come la schermata principale o il pannello di registrazione
     * 
     * @author Diego Beraldin
     */
    this.hide = function() {
    	element.style.display = "none";
    };

    /**
     * Inizializzazione del pannello di login con la creazione di tutti i
     * widget grafici che sono contenuti al suo interno
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
        inputLogin.onclick = login;
        //pulsante di registrazione
        var inputRegister = document.createElement('input');
        inputRegister.setAttribute("type", "submit");
        inputRegister.setAttribute("value", "Registrati");
        var self = this;
        inputRegister.onclick = function() {
        	self.hide();
            //'registrationpp' deve essere una variabile globale
        	registrationpp.initialize();
        };
        //pulsante per recuperare la password
        var inputRetrievePassword = document.createElement("input");
        inputRetrievePassword.setAttribute("type", "submit");
        inputRetrievePassword.setAttribute("value", "Recupera password");
        inputRetrievePassword.onclick = function() {
        	retrievePassword(inputUserName.getAttribute("value"));
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
