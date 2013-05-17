/**
 * Presenter incaricato di gestire la form di registrazione
 *
 * @constructor
 * @this {RegisterPanelPresenter}
 * @author Stefano Farronato
 */
function RegisterPanelPresenter() {
    /***************************************************************************
     * VARIABILI PRIVATE
     **************************************************************************/
    // elemento controllato da questo presenter
    var thisPresenter = this;
    var thisPanel;

    /***************************************************************************
     * METODI PRIVATI
     **************************************************************************/
    /**
     * Funzione per gestire l'evento in cui viene visualizzato il pannello di
     * registrazione
     * @author Riccardo Tresoldi
     */
    function onShowRegistrationPanel() {
        // TODO elimina eventuale GUI già visualizzata
        document.dispatchEvent(removeAllPanel);
        mediator.getView('register');
    }

    /**
     * Funzione per gestire l'evento in cui viene rimosso il pannello di
     * registrazione
     * @author Riccardo Tresoldi
     */
    function onRemoveRegistrationPanel() {
        thisPresenter.destroy();
    }

    /***************************************************************************
     * METODI PUBBLICI
     **************************************************************************/
    /** VIEW
     * Distruttore del pannello
     * @author Riccardo Tresoldi
     */
    this.destroy = function() {
        var thisPanelParent = thisPanel.parentElement.parentElement;
        thisPanelParent.removeChild(thisPanel.parentElement);
    };

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
        request.open("POST", commandURL, false);
        request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        var querystring = "operation=register&username=" + encodeURIComponent(userData.username) + "&password=" + encodeURIComponent(userData.password) + "&question=" + encodeURIComponent(userData.question) + "&answer=" + encodeURIComponent(userData.answer);
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
     * @author Diego Beraldin
     */
    this.initialize = function(view) {
        // attacca il pannello alla pagina
        var dummyDiv = document.createElement("div");
        document.body.insertBefore(dummyDiv, document.getElementsByTagName("footer")[0]);
        dummyDiv.innerHTML = view.outerHTML;

        //salvo un riferimento all'elemento DOM appena creato
        thisPanel = document.getElementById("RegisterPanel");

        // configura il comportamento della vista
        var inputLogin = document.getElementById("inputLogin");
        inputLogin.onclick = function() {
            document.dispatchEvent(showLoginPanel);
        };

        // pulsante di registrazione
        var inputRegister = document.getElementById("inputRegister");
        inputRegister.onclick = function() {
            var data = new Object();
            try {
                data.username = thisPresenter.getUsername();
                data.password = thisPresenter.getPassword();
                data.question = thisPresenter.getQuestion();
                data.answer = thisPresenter.getAnswer();
                data.name = thisPresenter.getName();
                data.surname = thisPresenter.getSurname();
                data.picturePath = thisPresenter.getPicturePath();
                thisPresenter.register(data);
            } catch (err) {
                alert(err);
            }
        };
    };

    /**
     * Nasconde il form di registrazione per lasciare spazio alla schermata
     * principale dell'applicativo (che deve essere costruita dal
     * PresenterMediator)
     *
     * @author Diego Beraldin
     */
    this.hide = function() {
        if (thisPanel) {
            thisPanel.style.display = "none";
        }
    };

    /***************************************************************************
     * LISTNER DEGLI EVENTI
     **************************************************************************/
    document.addEventListener("showRegistrationPanel", function(evt) {
        onShowRegistrationPanel();
    });
    document.addEventListener("removeRegistrationPanel", function(evt) {
        onRemoveRegistrationPanel();
    });
}
