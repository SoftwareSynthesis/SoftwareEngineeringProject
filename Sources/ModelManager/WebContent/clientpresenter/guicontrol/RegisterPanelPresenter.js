/**
 * Presenter incaricato di gestire la form di registrazione
 *
 * @constructor
 * @this {RegisterPanelPresenter}
 * @author Stefano Farronato
 */
function RegisterPanelPresenter() {
    //url della servlet che deve gestire la registrazione
    this.servletURL = "localhost:8080/LoginManager";
    //elemento controllato da questo presenter
    this.element = document.getElementById("RegisterPanel");

    /**
     * Inizializzazione dellla form di registrazione con la creazione di tutti i
     * widget grafici che sono contenuti al suo interno
     *
     * @author Stefano Farronato
     */
    this.initialize = function() {
    	this.element.style.display = "block";
        //creazione dell'elemento form
        var registerForm = document.createElement('form');
        registerForm.setAttribute("name", "register");
        registerForm.setAttribute("action", "");
        registerForm.setAttribute("method", "");
        registerForm.setAttribute("accept-charset", "utf-8");

        //creazione dell'elemento <ul> contenuto nel form
        var ulData = document.createElement('ul');
        ulData.style.listStyleType = "none";

        //creazione dell'item per lo username
        var liUserName = document.createElement('li');
        //label
        var labelUserName = document.createElement('label');
        labelUserName.setAttribute("for", "username");
        labelUserName.innerHTML = "Indirizzo email: ";
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

        //creazione dell'item per la domanda segreta
        var liSecretQ = document.createElement('li');
        //label
        var labelSecretQ = document.createElement('label');
        labelSecretQ.setAttribute("for", "question");
        labelSecretQ.innerHTML = "Domanda segreta: ";
        //input
        var inputSecretQ = document.createElement('input');
        inputSecretQ.setAttribute("type", "text");
        inputSecretQ.setAttribute("id", "question");
        inputSecretQ.setAttribute("name", "question");
        inputSecretQ.setAttribute("placeholder", "il colore del mio gatto");
        inputSecretQ.setAttribute("required", "required");
        //costruisce il list item con la label e l'input
        liSecretQ.appendChild(labelSecretQ);
        liSecretQ.appendChild(inputSecretQ);

        //creazione dell'item per la risposta alla domanda segreta
        var liAnswerSQ = document.createElement('li');
        //label
        var labelAnswerSQ = document.createElement('label');
        labelAnswerSQ.setAttribute("for", "answer");
        labelAnswerSQ.innerHTML = "Risposta: ";
        //input
        var inputAnswerSQ = document.createElement('input');
        inputAnswerSQ.setAttribute("type", "text");
        inputAnswerSQ.setAttribute("id", "answer");
        inputAnswerSQ.setAttribute("name", "answer");
        inputAnswerSQ.setAttribute("placeholder", "blallo");
        inputAnswerSQ.setAttribute("required", "required");
        //costruisce il list item con la label e l'input
        liAnswerSQ.appendChild(labelAnswerSQ);
        liAnswerSQ.appendChild(inputAnswerSQ);

        //creazione dell'item per il nome
        var liFirstName = document.createElement('li');
        //label
        var labelFirstName = document.createElement('label');
        labelFirstName.setAttribute("for", "firtname");
        labelFirstName.innerHTML = "Nome: ";
        //input
        var inputFirstName = document.createElement('input');
        inputFirstName.setAttribute("type", "text");
        inputFirstName.setAttribute("id", "firstname");
        inputFirstName.setAttribute("name", "firstname");
        inputFirstName.setAttribute("placeholder", "il tuo nome");
        //costruisce il list item con la label e l'input
        liFirstName.appendChild(labelFirstName);
        liFirstName.appendChild(inputFirstName);

        //creazione dell'item per il cognome
        var liLastName = document.createElement('li');
        //label
        var labelLastName = document.createElement('label');
        labelLastName.setAttribute("for", "lastname");
        labelLastName.innerHTML = "Cognome: ";
        //input
        var inputLastName = document.createElement('input');
        inputLastName.setAttribute("type", "text");
        inputLastName.setAttribute("id", "lastname");
        inputLastName.setAttribute("name", "lastname");
        inputLastName.setAttribute("placeholder", "il tuo cognome");
        //costruisce il list item con la label e l'input
        liLastName.appendChild(labelLastName);
        liLastName.appendChild(inputLastName);
        
        //TODO manca l'immagine, se deve essere caricata qui

        //pulsante di registrazione
        var inputRegister = document.createElement('input');
        inputRegister.setAttribute("type", "submit");
        inputRegister.setAttribute("value", "Registrati");
        var self = this;
        inputRegister.onclick = function() {
        	self.register();
        };
        var liButtons=document.createElement('li');
		liButtons.appendChild(inputRegister);

        //appende tutti gli elementi al form
        ulData.appendChild(liUserName);
        ulData.appendChild(liPassword);
        ulData.appendChild(liSecretQ);
        ulData.appendChild(liAnswerSQ);
        ulData.appendChild(liFirstName);
        ulData.appendChild(liLastName);
        ulData.appendChild(liButtons);
        registerForm.appendChild(ulData);

        //appende il form al DOM della pagin
        this.element.appendChild(registerForm);
    };
    
    /**
     * Invia i dati ricevuti alla servlet per la creazione di un nuovo utente
     * 
     * @author Diego Beraldin
     */
    this.register = function() {
    	//recupera i dati obbligatori dal form
    	var data = new Array();
    	data["username"] = document.getElementById("username").value;
    	data["password"] = document.getElementById("password").value;
    	data["question"]= document.getElementById("question").value;
    	data["answer"] = document.getElementById("answer").value;
    	//verifica la presenza dei dati (e salta il resto se non sono presenti)
    	var ok = true;
    	for (var key in data) {
    		if (!data[key] || data[key] == "") {
    			ok = false;
    		}
    	}
    	if (!ok) {
    		return;
    	}
    	
    	//costruisce la stringa di cueri con i dati obbligatori
    	var querystring = "";
    	for (var key in data) {
    		querystring = querystring + key + "=" + data[key] + "&";
    	}
    	//elimina il carattere '&' finale non necessario
    	querystring = querystring.substring(0, querystring.length-1);
    	
    	//recupera i dati facoltativi e li accoda alla stringa di cueri
    	var name = document.getElementById("firstname").value;
    	var surname = document.getElementById("lastname").value;
    	if (name  && name.length) {
    		querystring += "&name=" + name;
    	}
    	if (surname && surname.length) {
    		querystring += "&surname0" + surname;
    	}
    	//imposta l'operazione che la servlet deve fare (2 = registrazione nuovo utente)
    	querystring += "&operation=2";
    	
    	//invia la richiesta AJAX al server
    	var request = new XMLHttpRequest();
    	var self = this;
    	request.onreadystatechange = function() {
    		if (this.readyState == 4 && this.status == 200) {
    			//la servlet deve restituire l'utente appena creato
    			var user = JSON.parse(this.responseText);
    			if (user != null) {
    				communicationcenter.my = user;
    				self.hide();
    				mediator.buildUI();
    			}
    		}
    	};
    	request.open("POST", this.servletURL, true);
    	request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    	request.send(querystring);
    };
    
    /**
     * Nasconde il form di registrazione per lasciare spazio alla schermata principale
     * dell'applicativo (che deve essere costruita dal PresenterMediator)
     * 
     * @author Diego Beraldin
     */
    this.hide = function() {
    	this.element.style.display = "none";
    };
}
