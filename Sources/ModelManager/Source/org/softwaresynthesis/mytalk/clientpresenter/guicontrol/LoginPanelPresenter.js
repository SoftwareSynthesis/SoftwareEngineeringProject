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
  //
  this.successURL = "";
  
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
