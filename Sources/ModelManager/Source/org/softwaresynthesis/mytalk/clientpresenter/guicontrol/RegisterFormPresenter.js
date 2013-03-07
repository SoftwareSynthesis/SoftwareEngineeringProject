/**
 * Presenter incaricato di gestire la form di registrazione
 * 
 * @author Stefano Farronato
 */
function RegisterFormPresenter() {
  //url della servlet che deve gestire il login
  this.servletURL = "localhost:8080/RegisterManager";
  //elemento controllato da questo presenter
  this.element = document.getElementById("RegisterForm");
  
  /**
   * Inizializzazione dellla form di registrazione con la creazione di tutti i
   * widget grafici che sono contenuti al suo interno
   * 
   * @author Stefano Farronato
   */
  this.initialize = function() {
    //creazione dell'elemento form
    var registerForm = document.createElement('form');
    registerForm.setAttribute("name", "register");
    registerForm.setAttribute("action", "");
    registerForm.setAttribute("method", "");
    registerForm.setAttribute("accept-charset", "utf-8");
    
    //creazione dell'elemento <ul> contenuto nel form
    var ulData = document.createElement('ul');
	ulData.style.listStyleType="none";
    
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
    inputPassword.setAttribute("type", "email");
    inputPassword.setAttribute("id", "password");
    inputPassword.setAttribute("name", "username");
    inputPassword.setAttribute("placeholder", "password");
    inputPassword.setAttribute("required", "required");
    //costruisce il list item con la label e l'input
    liPassword.appendChild(labelPassword);
    liPassword.appendChild(inputPassword);

	//creazione dell'item per la domanda segreta
    var liSecretQ = document.createElement('li');
    //label
    var labelSecretQ = document.createElement('label');
    labelSecretQ.setAttribute("for", "SecretQ");
    //input
    var inputSecretQs = document.createElement('input');
    inputSecretQ.setAttribute("type", "string");
    inputSecretQ.setAttribute("id", "question");
    inputSecretQ.setAttribute("name", "Domanda segreta");
    inputSecretQ.setAttribute("placeholder", "colore del gatto");
    inputSecretQ.setAttribute("required", "required");
    //costruisce il list item con la label e l'input
    liUserName.appendChild(labelSecretQ);
    liUserName.appendChild(inputSecretQ);
	
	//creazione dell'item per la risposta alla domanda segreta
    var liAnswerSQ = document.createElement('li');
    //label
    var labelAnswerSQ = document.createElement('label');
    labelAnswerSQ.setAttribute("for", "username");
    //input
    var inputAnswerSQ = document.createElement('input');
    inputAnswerSQ.setAttribute("type", "");
    inputAnswerSQ.setAttribute("id", "Question");
    inputAnswerSQ.setAttribute("name", "Risposta");
    inputAnswerSQ.setAttribute("placeholder", "blallo");
    inputAnswerSQ.setAttribute("required", "required");
    //costruisce il list item con la label e l'input
    liUserName.appendChild(labelAnswerSQ);
    liUserName.appendChild(inputAnswerSQ);


	//creazione dell'item per il nome
    var liFirstName = document.createElement('li');
    //label
    var labelFirstName = document.createElement('label');
    labelFirsName.setAttribute("for", "firstname");
    //input
    var inputFirstName = document.createElement('input');
    inputFirstName.setAttribute("type", "");
    inputFirstName.setAttribute("id", "name");
    inputFirstName.setAttribute("name", "Nome");
    inputFirstName.setAttribute("placeholder", "il tuo nome");
    //costruisce il list item con la label e l'input
    liUserName.appendChild(labelFirstName);
    liUserName.appendChild(inputFirstName);

	//creazione dell'item per il cognome
    var liFirstName = document.createElement('li');
    //label
    var labelFirstName = document.createElement('label');
    labelLastName.setAttribute("for", "lastname");
    //input
    var inputLastName = document.createElement('input');
    inputLastName.setAttribute("type", "");
    inputLastName.setAttribute("id", "lastname");
    inputLastName.setAttribute("name", "Cognome");
    inputLastName.setAttribute("placeholder", "il tuo cognome");
    //costruisce il list item con la label e l'input
    liUserName.appendChild(labelLastName);
    liUserName.appendChild(inputLastName);

    //creazione dell'item per il pulsante di registrazione
    var inputRegister = document.createElement('input');
    inputRegister.setAttribute("value", "Registrati");
    liButtons.appendChild(inputRegister);
    
    //appende tutti gli elementi al form
    ulData.appendChild(liUserName);
    ulData.appendChild(liPassword);
	ulData.appendChild(liSecretQ);
	ulData.appendChild(liAnswerSQ);
	ulData.appendChild(liSecretQ);
	ulData.appendChild(liFirstName);
	ulData.appendChild(liLastName);
	ulData.appendChild(liButtons);
    loginForm.appendChild(ulData);
    
    //appende il form al DOM della pagin
    this.element.appendChild(registerForm);
  };
  
  /**
   * Testa quanto ricevuto dal server e, in caso di registrazione avvenuta correttamente
   * reindirizza il browser nella pagina finale dopo aver salvato i dati dell'utente
   * 
   * @author Stefano Farronato
   */
  this.testCredentials = function(data) {
    //salva i dati ricevuti dal server in una variabile globale
    user = JSON.parse(data);
    if (user != null) {
      //TODO decidere cosa fare quando la registrazione ha successo
      //TODO settare i campi
      window.location = "success.html";
    }
  };
  
  /**
   * Procedura che esegue il login prelevando le credenziali inserite nel
   * form e inviando al server i dati di autenticazione
   * 
   * @author Stefano Farronato
   */
  this.login = function() {
    //recupera le credenziali dall'interfaccia grafica
    var username = document.getElementById("usermail").value;
    var password = document.getElementById("password").value;
    var question = document.getElementById("question").value;
    var answer = document.getElementById("answer").value;
    var firstname = document.getElementById("firtname").value;
    var lastname = document.getElementById("lastname").value;
    
    //invia la richiesta AJAX al server    
    var request = new XMLHttpRequest();
    var that = this;
    request.onreadystatechange = function() {
      if (this.readyState == 4 && this.status == 200) {
        that.testCredentials(request.responseText);
      }
    };
    request.open("POST", this.servletURL, true);
    request.setRequestHeader("Content-type","application/x-www-form-urlencoded");
    request.send("username=" + username + "&password=" + password + "&question"+ question + "&answer" +answer + "&firstname" + firsname + "&lastname" + lastname );
  };
}
