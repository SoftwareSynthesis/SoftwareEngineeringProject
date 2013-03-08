/**
 * Presenter incaricato di gestire la form di registrazione
 *
 * @author Stefano Farronato
 */
function RegisterPanelPresenter() {
    //url della servlet che deve gestire la registrazione
    this.servletURL = "localhost:8080/RegisterManager";
    //elemento controllato da questo presenter
    this.element = document.getElementById("RegisterPanel");

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
        inputPassword.setAttribute("type", "email");
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
        //input
        var inputSecretQ = document.createElement('input');
        inputSecretQ.setAttribute("type", "text");
        inputSecretQ.setAttribute("id", "question");
        inputSecretQ.setAttribute("name", "question");
        inputSecretQ.setAttribute("placeholder", "colore del gatto");
        inputSecretQ.setAttribute("required", "required");
        //costruisce il list item con la label e l'input
        liSecretQ.appendChild(labelSecretQ);
        liSecretQ.appendChild(inputSecretQ);

        //creazione dell'item per la risposta alla domanda segreta
        var liAnswerSQ = document.createElement('li');
        //label
        var labelAnswerSQ = document.createElement('label');
        labelAnswerSQ.setAttribute("for", "answer");
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
        //input
        var inputLastName = document.createElement('input');
        inputLastName.setAttribute("type", "text");
        inputLastName.setAttribute("id", "lastname");
        inputLastName.setAttribute("name", "lastname");
        inputLastName.setAttribute("placeholder", "il tuo cognome");
        //costruisce il list item con la label e l'input
        liLastName.appendChild(labelLastName);
        liLastName.appendChild(inputLastName);

        //pulsante di registrazione
        var inputRegister = document.createElement('input');
        inputRegister.setAttribute("type", "submit");
        inputRegister.setAttribute("value", "Registrati");
        var liButtons=document.createElement('li');
		liButtons.appendChild(inputRegister);

        //appende tutti gli elementi al form
        ulData.appendChild(liUserName);
        ulData.appendChild(liPassword);
        ulData.appendChild(liAnswerSQ);
        ulData.appendChild(liSecretQ);
        ulData.appendChild(liFirstName);
        ulData.appendChild(liLastName);
        ulData.appendChild(liButtons);
        registerForm.appendChild(ulData);

        //appende il form al DOM della pagin
        this.element.appendChild(registerForm);
    };
}
