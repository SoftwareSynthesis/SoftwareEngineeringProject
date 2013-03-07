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
    var loginform = document.createElement('form');
    loginForm.setAttribute("name", "login");
    loginForm.setAttribute("action", "");
    loginForm.setAttribute("method", "");
    loginForm.setAttribute("accept-charset", "utf-8");
    
    //creazione dell'elemento <ul> contenuto nel form
    var ulData = document.createElement('ul');
    
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
    inputUserName.setAttribute("required");//FIXME potenziale problema perché richiede che ci siano due stringhe
    //costruisce il list item con la label e l'input
    liUserName.appendChild(labelUserName);
    liUserName.appendChild(inputUserName);
    
    //crea l'item per la password
    var liPassword = documente.createElement('li');
    //label
    var labelPassword = document.createElement('label');
    labelPassword.setAttribute("for", "password");
    //input
    var inputPassword = document.createElement('input');
    inputPassword.setAttribute("type", "email");
    inputPassword.setAttribute("id", "password");
    inputPassword.setAttribute("name", "username");
    inputPassword.setAttribute("placeholder", "password");
    inputPassword.setAttribute("required");//FIXME potenziale problema perché richiede che ci siano due stringhe
    //costruisce il list item con la label e l'input
    liPassword.appendChild(labelPassword);
    liPasswrod.appendChild(inputPassword);
    
    //creazione dell'item per i pulsanti
    var liButtons = document.createElement('li');
    var inputLogin = document.createElement('input');
    inputLogin.setAttribute("type", "submit");
    inputLogin.setAttribute("value", "Login");
    var inputRegister = document.createElement('input');
    inputRegister.setAttribute("type", "submit");
    inputRegister.setAttribute("value", "Registrati");
    liButtons.appendChild(inputLogin);
    liButtons.appendChild(inputRegister);
    
    //appende tutti gli elementi al form
    ulData.appendChild(liUserName);
    ulData.appendChild(liPassword);
    ulData.appendChild(liButtons);
    loginForm.appendChild(ulData);
    
    //appende il form al DOM della pagin
    this.element.appendChild(loginform);
  };
  
  /**
   * Testa quanto ricevuto dal server e, in caso di login avvenuto correttamente
   * reindirizza il browser nella pagina finale dopo aver salvato i dati dell'utente
   * 
   * @author Diego Beraldin
   */
  this.testCredentials = function(data) {
    //salva i dati ricevuti dal server in una variabile globale
    user = JSON.parse(data);
    if (user != null) {
      //TODO decidere cosa fare quando il login ha successo
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
    var username = document.getElementById("usermail").value;
    var password = document.getElementById("password").value;
    
    //invia la richiesta AJAX al server    
    var request = new XMLHttpRequest();
    var that = this;
    request.onreadystatechange = function() {
      if (this.readyState == 4 && this.status == 200) {
        that.testCredentials(request.responseText);
      }
    };
    request.open("POST", servletURL, true);
    request.setRequestHeader("Content-type","application/x-www-form-urlencoded");
    request.send("username=" + username + "&password=" + password);
  };
}
