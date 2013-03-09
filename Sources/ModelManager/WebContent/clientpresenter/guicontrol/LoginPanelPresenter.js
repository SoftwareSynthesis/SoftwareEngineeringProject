/**
 * Presenter incaricato di gestire il pannello di login
 *
 * @constructor
 * @this {LoginPanelPresenter}
 * @author Diego Beraldin
 */
function LoginPanelPresenter() {
    //url della servlet che deve gestire il login
    this.servletURL = "localhost:8080/LoginManager";
    //elemento controllato da questo presenter
    this.element = document.getElementById("LoginPanel");

    /**
     * Inizializzazione del pannello di login con la creazione di tutti i
     * widget grafici che sono contenuti al suo interno
     *
     * @author Diego Beraldin
     */
    this.initialize = function() {
    	this.element.style.display = "block";
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
        inputLogin.onclick = this.login;
        //pulsante di registrazione
        var inputRegister = document.createElement('input');
        inputRegister.setAttribute("type", "button");
        inputRegister.setAttribute("value", "Registrati");
        // non c'è altro verso di impostare un metodo come callback
        var self = this;
        inputRegister.onclick = function() {
        	self.hide();
            //'registrationpp' deve essere una variabile globale
        	registrationpp.initialize();
        };
        //FIXME manca il pulsante per recuperare la password

        //creazione dell'item per i pulsanti
        var liButtons = document.createElement('li');
        liButtons.appendChild(inputLogin);
        liButtons.appendChild(inputRegister);

        //appende tutti gli elementi al form
        ulData.appendChild(liUserName);
        ulData.appendChild(liPassword);
        ulData.appendChild(liButtons);
        loginForm.appendChild(ulData);

        //appende il form al DOM della pagina
        this.element.appendChild(loginForm);
    };

    /**
     * Testa quanto ricevuto dal server e, in caso di login avvenuto correttamente
     * reindirizza il browser nella pagina finale dopo aver salvato i dati dell'utente
     *
     * @param {String} data la stringa restituita dalla servlet a seguito del login
     * @author Diego Beraldin
     */
    this.testCredentials = function(data) {
        // 'communicationcenter' deve essere una variabile globale
        var user = JSON.parse(data);
        if (user != null) {
            communicationcenter.my = user;
            this.hide();
            mediator.buildUI();
        }
    };

    /**
     * Procedura che esegue il login prelevando le credenziali inserite nel
     * form e inviando al server i dati di autenticazione
     *
     * @author Diego Beraldin
     */
    this.login = function() {
        //recupera le credenziali dall'interfaccia grafica
        var username = document.getElementById("username").value;
        var password = document.getElementById("password").value;
        
        if (!username || username == "" || !password || password == "") {
        	return;
        }

        //invia la richiesta AJAX al server
        var request = new XMLHttpRequest();
        var self = this;
        request.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {
                self.testCredentials(request.responseText);
            }
        };
        request.open("POST", this.servletURL, true);
        request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        request.send("username=" + encodeURIComponent(username) + "&password=" + encodeURIComponent(password) + "&operation=1");
    };
    
    /**
     * Nasconde il form di autenticazione per lasciare spazio nella finestra a
     * altri elementi grafici come la schermata principale o il pannello di registrazione
     * 
     * @author Diego Beraldin
     */
    this.hide = function() {
    	this.element.style.display = "none";
    };
}
