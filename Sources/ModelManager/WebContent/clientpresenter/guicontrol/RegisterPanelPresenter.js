/**
 * Presenter incaricato di gestire la form di registrazione
 *
 * @constructor
 * @this {RegisterPanelPresenter}
 * @param {String}
 *            url URL della servlet con cui il presenter deve comunicare
 * @author Stefano Farronato
 */
function RegisterPanelPresenter(url) {
    /***************************************************************************
     * VARIABILI PRIVATE
     **************************************************************************/
    // elemento controllato da questo presenter
    var element = document.getElementById("RegisterPanel");
    // URL della servlet con cui questo presenter
    var servlets = new Array();
    // costruisce l'array delle servlet
    getServletURLs();

    /***************************************************************************
     * METODI PRIVATI
     **************************************************************************/
    /**
     * Configura gli URL delle servlet da interrogare leggendoli dal file di
     * configurazione
     *
     * @author Diego Beraldin
     */
    function getServletURLs() {
        var configurationRequest = new XMLHttpRequest();
        configurationRequest.open("POST", configurationFile, false);
        configurationRequest.send();
        var XMLDocument = configurationRequest.responseXML;
        var baseURL = XMLDocument.getElementsByTagName("baseURL")[0].childNodes[0].data;
        var name = (XMLDocument.getElementById("registration").childNodes[0].data);
        servlets.push(baseURL + name);
    }

    /***************************************************************************
     * METODI PUBBLICI
     **************************************************************************/
    /**
     * Estrae dal form il valore del cognome del nuovo utente
     *
     * @returns {String} il cognome dell'utente
     * @author Diego Beraldin
     */
    this.getSurname = function() {
        var surname = document.getElementById("lastname").value;
        return surname;
    };

    /**
     * Estrae dal form il valore del nome del nuovo utente
     *
     * @returns {String} il nome dell'utente
     * @author Diego Beraldin
     */
    this.getName = function() {
        var name = document.getElementById("firstname").value;
        return name;
    };

    /**
     * Estrae dal form la risposta alla domanda segreta associata al nuovo
     * utente
     *
     * @throws {String}
     *             messaggio di errore in caso di dati mancanti
     * @returns {String} la risposta alla domanda
     * @author Diego Beraldin
     */
    this.getAnswer = function() {
        var answer = document.getElementById("answer").value;
        if (!answer || answer.length == 0) {
            throw "risposta alla domanda segreta non specificata";
        }
        return answer;
    };

    /**
     * Estrae dal form la domanda segreta associata al nuovo utente
     *
     * @throws {String}
     *             messaggio di errore in caso di dati mancanti
     * @returns {String} la domanda segreta
     * @author Diego Beraldin
     */
    this.getQuestion = function() {
        var question = document.getElementById("question").value;
        if (!question || question.length == 0) {
            throw "domanda segreta non specificata";
        }
        return question;
    };

    /**
     * Estrae dal form la password associata al nuovo utente
     *
     * @throws {String}
     *             messaggio d'errore in caso di dati mancanti
     * @returns {String} la password
     * @author Diego Beraldin
     */
    this.getPassword = function() {
        var password = document.getElementById("password").value;
        if (!password || password.length == 0) {
            throw "password non specificata";
        }
        return password;
    };

    /**
     * Estrae dal form lo username del nuovo utente
     *
     * @throws {String}
     *             messaggio d'errore in caso di dati mancanti
     * @returns {String} lo username
     * @author Diego Beraldin
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
     * Estrae dal form il percorso dell'immagine del nuovo profilo utente
     *
     * @returns {String} il percorso locale del file da caricare
     * @author Diego Beraldin
     */
    this.getPicturePath = function() {
        var picturePath = document.getElementById("picture").value;
        return picturePath;
    };

    /**
     * Invia i dati ricevuti alla servlet per la creazione di un nuovo account
     * utente
     *
     * NOTE PER I VERIFICATORI Richiede la presenza di communicationcenter.my
     * globale e al termine di questo metodo se le operazioni sono andate a buon
     * fine my deve essere configurato con i dati di un utente valido Richiede
     * un oggetto mediator globale con una proprietà buildUI() che deve essere
     * simulata
     *
     * @param {Object}
     *            userData array associativo contenente i dati dell'utente
     *            recuperati dal form
     * @returns {String} la stringa di query che viene inviata al server
     * @author Diego Beraldin
     */
    this.register = function(userData) {
        var request = new XMLHttpRequest();
        // invia una richiesta SINCRONA al server (terzo parametro 'false')
        request.open("POST", servlets[0], false);
        request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        var querystring = "username=" + encodeURIComponent(userData.username) + "&password=" + encodeURIComponent(userData.password) + "&question=" + encodeURIComponent(userData.question) + "&answer=" + encodeURIComponent(userData.answer);
        if (userData.name && userData.name.length > 0) {
            querystring += ("&name=" + encodeURIComponent(userData.name));
        }
        if (userData.surname && userData.surname.length > 0) {
            querystring += ("&surname=" + encodeURIComponent(userData.surname));
        }
        if (userData.picturePath && userData.picturePath.length > 0) {
            querystring += ("&picturePath=" + encodeURIComponent(userData.picturePath));
        }
        request.send(querystring);
        var user = JSON.parse(request.responseText);
        if (user != null) {
            communicationcenter.my = user;
            mediator.buildUI();
        }
        return querystring;
    };

    /**
     * Inizializzazione dellla form di registrazione con la creazione di tutti i
     * widget grafici che sono contenuti al suo interno
     *
     * @author Stefano Farronato
     */
    this.initialize = function() {
        element.innerHTML = "";

        element.style.display = "block";
        // creazione dell'elemento form
        var registerForm = document.createElement('form');
        registerForm.setAttribute("name", "register");
        registerForm.setAttribute("action", "");
        registerForm.setAttribute("method", "");
        registerForm.setAttribute("accept-charset", "utf-8");

        // creazione dell'elemento <ul> contenuto nel form
        var ulData = document.createElement('ul');

        // creazione dell'item per lo username
        var liUserName = document.createElement('li');
        // label
        var labelUserName = document.createElement('label');
        labelUserName.setAttribute("for", "username");
        labelUserName.innerHTML = "Indirizzo email: ";
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

        // creazione dell'item per la domanda segreta
        var liSecretQ = document.createElement('li');
        // label
        var labelSecretQ = document.createElement('label');
        labelSecretQ.setAttribute("for", "question");
        labelSecretQ.innerHTML = "Domanda segreta: ";
        // input
        var inputSecretQ = document.createElement('input');
        inputSecretQ.setAttribute("type", "text");
        inputSecretQ.setAttribute("id", "question");
        inputSecretQ.setAttribute("name", "question");
        inputSecretQ.setAttribute("placeholder", "il colore del mio gatto");
        inputSecretQ.setAttribute("required", "required");
        // costruisce il list item con la label e l'input
        liSecretQ.appendChild(labelSecretQ);
        liSecretQ.appendChild(inputSecretQ);

        // creazione dell'item per la risposta alla domanda segreta
        var liAnswerSQ = document.createElement('li');
        // label
        var labelAnswerSQ = document.createElement('label');
        labelAnswerSQ.setAttribute("for", "answer");
        labelAnswerSQ.innerHTML = "Risposta: ";
        // input
        var inputAnswerSQ = document.createElement('input');
        inputAnswerSQ.setAttribute("type", "text");
        inputAnswerSQ.setAttribute("id", "answer");
        inputAnswerSQ.setAttribute("name", "answer");
        inputAnswerSQ.setAttribute("placeholder", "blallo");
        inputAnswerSQ.setAttribute("required", "required");
        // costruisce il list item con la label e l'input
        liAnswerSQ.appendChild(labelAnswerSQ);
        liAnswerSQ.appendChild(inputAnswerSQ);

        // creazione dell'item per il nome
        var liFirstName = document.createElement('li');
        // label
        var labelFirstName = document.createElement('label');
        labelFirstName.setAttribute("for", "firstname");
        labelFirstName.innerHTML = "Nome: ";
        // input
        var inputFirstName = document.createElement('input');
        inputFirstName.setAttribute("type", "text");
        inputFirstName.setAttribute("id", "firstname");
        inputFirstName.setAttribute("name", "firstname");
        inputFirstName.setAttribute("placeholder", "il tuo nome");
        // costruisce il list item con la label e l'input
        liFirstName.appendChild(labelFirstName);
        liFirstName.appendChild(inputFirstName);

        // creazione dell'item per il cognome
        var liLastName = document.createElement('li');
        // label
        var labelLastName = document.createElement('label');
        labelLastName.setAttribute("for", "lastname");
        labelLastName.innerHTML = "Cognome: ";
        // input
        var inputLastName = document.createElement('input');
        inputLastName.setAttribute("type", "text");
        inputLastName.setAttribute("id", "lastname");
        inputLastName.setAttribute("name", "lastname");
        inputLastName.setAttribute("placeholder", "il tuo cognome");
        // costruisce il list item con la label e l'input
        liLastName.appendChild(labelLastName);
        liLastName.appendChild(inputLastName);

        // crazione dell'item per l'immagine
        var liPicture = document.createElement("li");
        // label
        var labelPicture = document.createElement("label");
        labelPicture.setAttribute("for", "picture");
        labelPicture.innerHTML = "Immagine del profilo: ";
        // input
        var inputPicture = document.createElement("input");
        inputPicture.setAttribute("type", "file");
        inputPicture.setAttribute("name", "picture");
        inputPicture.setAttribute("id", "picture");
        // costruisce il list item con label e input
        liPicture.appendChild(labelPicture);
        liPicture.appendChild(inputPicture);

        // pulsante di ritorno alla pagina di login
        var inputLogin = document.createElement('button');
        inputLogin.type="submit";
        inputLogin.appendChild(document.createTextNode("Indietro"));
        inputLogin.onclick = function() {
            //FIXME crea errore di duplicazione LoginUI se già premuti in
            // precedenza
            self.hide();
            mediator.buildLoginUI();
        };

        // pulsante di registrazione
        var inputRegister = document.createElement('button');
        inputRegister.type= "submit";
        inputRegister.appendChild(document.createTextNode("Registrati"));
        var self = this;
        inputRegister.onclick = function() {
            var data = new Object();
            try {
                data.username = self.getUsername();
                data.password = self.getPassword();
                data.question = self.getQuetion();
                data.answer = self.getAnswer();
                data.name = self.getName();
                data.surname = self.getSurname();
                data.picturePath = self.getPicturePath();
                self.register(data);
            } catch (err) {
                alert(err);
            }
        };
        // creazione dell'item per i pulsante
        var divButtons = document.createElement('div');
        divButtons.appendChild(inputLogin);
       divButtons.appendChild(inputRegister);

        // appende tutti gli elementi al form
        ulData.appendChild(liUserName);
        ulData.appendChild(liPassword);
        ulData.appendChild(liSecretQ);
        ulData.appendChild(liAnswerSQ);
        ulData.appendChild(liFirstName);
        ulData.appendChild(liLastName);
        ulData.appendChild(liPicture);
        registerForm.appendChild(ulData);
        registerForm.appendChild(divButtons);

        // appende il form al DOM della pagin
        element.appendChild(registerForm);
    };

    /**
     * Nasconde il form di registrazione per lasciare spazio alla schermata
     * principale dell'applicativo (che deve essere costruita dal
     * PresenterMediator)
     *
     * @author Diego Beraldin
     */
    this.hide = function() {
        element.style.display = "none";
    };
}
