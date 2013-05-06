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
    // elemento controllato da questo presenter
    var element;

    /***************************************************************************
     * METODI PRIVATI
     **************************************************************************/
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
            communicationcenter.connect();
            mediator.buildUI();
        } else {
            //funzione che setta la grafica per tornare l'errore nel login
            errorLogin();
        }
    }

    /**
     * Funzione che setta le textBox del loginPanelPresenter con la classe per
     * segnalare l'errore nel login e dunque il login non riuscito
     *
     * @author Riccardo Tresoldi
     */
    function errorLogin() {
        var user = document.getElementById("username");
        var pass = document.getElementById("password");
        user.className += " error";
        pass.className += " error";
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
        var text = document.createTextNode("Recupero password avvenuto correttamente." + "Ti è stata inviata un'email contenente i dati richiesti.");
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
        var text = document.createTextNode("Dati non corretti. Inserire nuovamente la risposta.");
        message.appendChild(text);
        element.appendChild(message);
        window.setTimeout(function() {
            element.removeChild(message);
        }, 2000);
    }

    /**
     * Recupera la domanda segreta con una richiesta asincrona al server
     *
     * @param {String}
     *            username username dell'utente di cui recuperare la domanda
     * @returns {String} la domanda segreta impostata dall'utente
     * @author Diego Beraldin
     */
    function getSecretQuestion(username) {
        var question = "";
        var request = new XMLHttpRequest();
        request.open("POST", commandURL, false);
        request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        request.send("operation=question&username=" + encodeURIComponent(username));
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
        request.open("POST", commandURL, false);
        request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        request.send("operation=answer&username=" + encodeURIComponent(username) + "&answer=" + encodeURIComponent(answer));
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
        // elimina il form se è già presente
        var oldForm = document.getElementById("passwordretrieval");
        if (oldForm != null) {
            element.removeChild(oldForm);
        }

        // costruisce il form con il valore ottenuto
        var formRetrievePassword = document.createElement("fieldset");
        formRetrievePassword.id = "passwordretrieval";
        // etichetta con la domanda
        var labelQuestion = document.createElement("label");
        labelQuestion.setAttribute("for", "inputanswer");
        // procura il testo della domanda segreta
        var name = this.getUsername();
        var text = getSecretQuestion(name);
        var question = document.createTextNode(text);
        labelQuestion.appendChild(question);
        // campo di immissione
        var inputAnswer = document.createElement("input");
        inputAnswer.type = "text";
        inputAnswer.id = "inputanswer";
        inputAnswer.name = "inputanswer";
        inputAnswer.setAttribute("placeholder", "risposta");
        inputAnswer.setAttribute("required", "required");
        // pulsante di conferma
        var submitButton = document.createElement("input");
        submitButton.setAttribute("type", "submit");
        submitButton.setAttribute("value", "OK");
        submitButton.onClick = function() {
            // Answer(username, inputAnswer.getAttribute("value"));
            if (this.hasAnsweredCorrectly(username, inputAnswer.getAttribute("value"))) {
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
            throw "indirizzo email non specificato";
        }
        var emailRegex = new RegExp("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,4}$", "i");
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
            throw "password non specificata";
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
        request.open("POST", commandURL, false);
        request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        var querystring = "operation=login&username=" + encodeURIComponent(data.username) + "&password=" + encodeURIComponent(data.password);
        request.send(querystring);
        testCredentials(request.responseText);
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
            document.body.removeChild(element);
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
    	// ottiene la propria vista
    	element = mediator.getView("LoginView");
    	
    	// attacca il pannello alla pagina
    	document.body.appendChild(element);
    	
    	// configura il comportamento della vista
    	var inputLogin = document.getElementById("inputLogin");
        inputLogin.onclick = function() {
            try {
                var data = new Object();
                data.username = self.getUsername();
                data.password = self.getPassword();
                self.login(data);
            } catch (err) {
                alert(err);
            }
        };
        
        var inputRegister = document.getElementById("inputRegister");
        inputRegister.onclick = function() {
            self.hide();
            mediator.buildRegistrationUI();
        };
        
        var inputRetrievePassword = document.getElementById("inputRetrievePassword");
        inputRetrievePassword.onclick = function() {
            try {
                var form = self.buildRetrievePasswordForm();
                element.appendChild(form);
            } catch (err) {
                alert(err);
            }
        };
    };
}