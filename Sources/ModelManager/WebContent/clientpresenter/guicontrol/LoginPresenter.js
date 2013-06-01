/**
 * Presenter incaricato di gestire il pannello di login
 *
 * @constructor
 * @this {LoginPanelPresenter}
 * @author Diego Beraldin
 * @author Riccardo Tresoldi
 */
function LoginPanelPresenter() {
    /***************************************************************************
     * VARIABILI PRIVATE
     **************************************************************************/
    var thisPresenter = this;
    var thisPanel;

    /***************************************************************************
     * METODI PRIVATI
     **************************************************************************/
    /** PRESENTER
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
        if (user) {
            login.user = user;
            document.dispatchEvent(login);
        } else {
            //funzione che setta la grafica per tornare l'errore nel login
            errorLogin();
        }
    }

    /** VIEW
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

    /** VIEW
     * Rimuove il form di recupero della password, visualizza un messaggio di
     * conferma per 2 secondi quindi rimuove anche questo e lascia il controllo
     * al form di login.
     *
     * @author Diego Beraldin
     */
    function correctAnswer() {
        var formRetrievePassword = document.getElementById("passwordretrieval");
        if (formRetrievePassword) {
	        thisPanel.removeChild(formRetrievePassword);
		}
        var message = document.createElement("p");
        var text = document.createTextNode("Recupero password avvenuto correttamente." + "Ti è stata inviata un'email contenente i dati richiesti.");
        message.appendChild(text);
        thisPanel.appendChild(message);
        window.setTimeout(function() {
            thisPanel.removeChild(message);
        }, 2000);
    }

    /** VIEW
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
        thisPanel.appendChild(message);
        window.setTimeout(function() {
            thisPanel.removeChild(message);
        }, 2000);
    }

    /** PRESENTER
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

    /** PRESENTER
     * Testa se l'utente ha dato la risposta corretta alla domanda segreta
     *
     * @param {String}
     *            username nome utente associato alla domanda segreta
     * @param {String}
     *            answer risposta alla domanda segreta
     * @returns {Boolean} true se la risposta è corretta, false altrimenti
     * @author Diego Beraldin
     */
    function hasAnsweredCorrectly(username, answer) {
        var request = new XMLHttpRequest();
        request.open("POST", commandURL, false);
        request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        request.send("operation=answer&username=" + username + "&answer=" + answer);
        return JSON.parse(request.responseText);
    }

    /***************************************************************************
     * METODI PUBBLICI
     **************************************************************************/
    /** VIEW
     * Distruttore del pannello
     * @author Riccardo Tresoldi
     */
    this.destroy = function() {
        if (thisPanel) {
            var thisPanelParent = thisPanel.parentElement.parentElement;
            thisPanelParent.removeChild(thisPanel.parentElement);
            thisPanel = null;
        }
    };

    /** VIEW
     * Costruisce il form per il recupero della password dell'utente
     *
     * @returns {HTMLFormElement} il form per il recupero della password
     * @author Diego Beraldin
     */
    function buildRetrievePasswordForm() {
        // elimina il form se è già presente
        var oldForm = document.getElementById("passwordretrieval");
        if (oldForm != null) {
            thisPanel.removeChild(oldForm);
        }
        // costruisce il form con il valore ottenuto
        var formRetrievePassword = document.createElement("fieldset");
        formRetrievePassword.id = "passwordretrieval";
        // etichetta con la domanda
        var labelQuestion = document.createElement("label");
        labelQuestion.setAttribute("for", "inputanswer");
        // procura il testo della domanda segreta
        var name = thisPresenter.getUsername();
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
        var submitButton = document.createElement("button");
        submitButton.type = "submit";
        submitButton.appendChild(document.createTextNode("Richiedi Password"));
        submitButton.onclick = function() {
            // Answer(username, inputAnswer.getAttribute("value"));
            if (hasAnsweredCorrectly(thisPresenter.getUsername(), inputAnswer.value)) {
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

    /** PRESENTER
     * Recupera lo username dall'interfaccia grafica utente
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

    /** PRESENTER
     * Recupera lo username dall'interfaccia grafica utente
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

    /** PRESENTER
     * Procedura che esegue il login inviando al server i dati di autenticazione
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

    /** VIEW
     * Inizializzazione del pannello di login con la creazione di tutti i widget
     * grafici che sono contenuti al suo interno
     *
     * @author Diego Beraldin
     */
    this.initialize = function(view) {
        // attacca il pannello alla pagina
        var dummyDiv = document.createElement("div");
        document.body.insertBefore(dummyDiv, document.getElementsByTagName("footer")[0]);
        dummyDiv.innerHTML = view.outerHTML;

        //salvo un riferimento all'elemento DOM appena creato
        thisPanel = document.getElementById("LoginPanel");

        // configura il comportamento della vista
        var inputLogin = document.getElementById("inputLogin");
        inputLogin.onclick = function() {
            try {
                var data = new Object();
                data.username = thisPresenter.getUsername();
                data.password = thisPresenter.getPassword();
                thisPresenter.login(data);
            } catch (err) {
                alert(err);
            }
        };

        var inputRegister = document.getElementById("inputRegister");
        inputRegister.onclick = function() {
            document.dispatchEvent(showRegistrationPanel);
        };

        var inputRetrievePassword = document.getElementById("inputRetrievePassword");
        inputRetrievePassword.onclick = function() {
            try {
                var form = buildRetrievePasswordForm();
                thisPanel.appendChild(form);
            } catch (err) {
                alert(err);
            }
        };
    };

    /***************************************************************************
     * HANDLER DEGLI EVENTI
     **************************************************************************/
    /** PRESENTER
     * Funzione per gestire l'evento in cui viene visualizzato il pannello di
     * registrazione
     * 
     * @author Riccardo Tresoldi
     */
    function onShowLoginPanel() {
        // controllo che non sia già visualizzato
        if (!thisPanel) {
            // elimina eventuale GUI già visualizzata
            document.dispatchEvent(removeAllPanel);
            mediator.getView('login');
        }
    }

    /** PRESENTER
     * Funzione per gestire l'evento in cui viene rimosso il pannello di
     * registrazione
     * 
     * @author Riccardo Tresoldi
     */
    function onRemoveLoginPanel() {
        thisPresenter.destroy();
    }

    /** PRESENTER
     * Funzione per effettuare il vero e proprio login visualizzando anche la GUI
     * 
     * @author Riccardo Tresoldi
     */
    function onLogin(user) {
        // 'communicationcenter' deve essere una variabile globale
        communicationcenter.my = user;
        communicationcenter.connect();
        document.dispatchEvent(showUIPanels);
    }

    /***************************************************************************
     * LISTENER DEGLI EVENTI
     **************************************************************************/
    document.addEventListener("showLoginPanel", onShowLoginPanel);
    document.addEventListener("removeLoginPanel", onRemoveLoginPanel);
    document.addEventListener("login", function(evt) {
        onLogin(evt.user);
    });
}
