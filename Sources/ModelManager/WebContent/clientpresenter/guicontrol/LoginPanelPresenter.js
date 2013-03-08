/**
 * Presenter incaricato di gestire il pannello di login
 *
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
        //creazione dell'elemento form
        var loginForm = document.createElement('form');
        loginForm.setAttribute("name", "login");
        loginForm.setAttribute("action", "");
        loginForm.setAttribute("method", "");
        loginForm.setAttribute("accept-charset", "utf-8");

        //creazione dell'elemento <ul> contenuto nel form
        var ulData = document.createElement('ul');
        ulData.style.listStyleType = "none";

        //creazione dell'item per lo username
        var liUserName = document.createElement('li');
        //label
        var labelUserName = document.createElement('label');
        labelUserName.setAttribute("for", "username");
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
        inputRegister.setAttribute("type", "submit");
        inputRegister.setAttribute("value", "Registrati");
        inputRegister.onclick = this.hidePanel;
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

        //appende il form al DOM della pagin
        this.element.appendChild(loginForm);
    };

    /**
     * Testa quanto ricevuto dal server e, in caso di login avvenuto correttamente
     * reindirizza il browser nella pagina finale dopo aver salvato i dati dell'utente
     *
     * @author Diego Beraldin
     */
    this.testCredentials = function(data) {
        // 'communicationcenter' deve essere una variabile globale
        var user = JSON.parse(data);
        if (user != null) {
            communicationcenter.my = user;
            //FIXME questo è solo per i test, in realtà se il login ha successo occorre fare altro
            window.location = "success.html";
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

        //invia la richiesta AJAX al server
        var request = new XMLHttpRequest();
        var that = this;
        request.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {
                that.testCredentials(request.responseText);
            }
        };
        request.open("POST", this.servletURL, true);
        request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        request.send("username=" + username + "&password=" + password + "&operation=1");
    };
    
    /**
     * Nasconde il form di autenticazione e richiama il presenter del pannello di
     * registrazione affinché visualizzi il nuovo form sulla finestra del browser
     * 
     * @author Diego Beraldin
     */
    this.hidePanel = function() {
    	this.element.style.display = "none";
        //TODO 'registrationpp' deve essere una variabile globale
    	registrationpp.initialize();
    };
}
