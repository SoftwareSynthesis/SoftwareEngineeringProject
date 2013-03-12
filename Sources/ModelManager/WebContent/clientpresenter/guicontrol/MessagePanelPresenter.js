/**
 * Presenter incaricato di gestire i messaggi in segreteria
 *
 * @author Elena Zecchinato
 * @author Diego Beraldin
 */
function MessagePanelPresenter() {
	/**********************************************************
    VARIABILI PRIVATE
    ***********************************************************/
    // elemento controllato da questo presenter
	// non serve più perché è creato al volo in createPanel()
    //var element = document.getElementById("MessagePanel");
    //array di messaggi
    var messages = new Array();
    //url della servlet da contattare per i messaggi
    var urlServlet = "http://localhost:8080/MessageManager";
    
    /**********************************************************
    METODI PRIVATI
    ***********************************************************/
    /**
     * Aggiunge un messaggio a una lista per creare l'elenco della 
     * segreteria telefonica
     * 
     * TODO eliminerei il paramtro lista perché in questo metodo privato
     * è scontato che si tratta della lista interna al '<div>' contenuto
     * nel pannello della segreteria telefonica.
     * TODO questa funzione è da completare
     * 
     * @param {HTMLUListElement} list
     * @param {Object} message
     */
    function addListItem(list, message) {
    	
    }
    
	/**********************************************************
    METODI PUBBLICI
    ***********************************************************/
    /**
     * Costruisce il pannello della segreteria telefonica, che deve essere visualizzato
     * all'interno del MainPanel dell'applicazione quando è selezionata la funzione corsispondente
     * dal pannello degli strumenti.
     * Il MessagePanel è costituito da un elemento video seguito da un 'div' che a sua volta
     * contiene una lista di messaggi.
     * 
     * @returns {HTMLDivElement} il pannello contenente la segreteria telefonica
     * @author Riccardo Tresoldi
     */
    this.createPanel = function() {
        var element = document.createElement("div");
        element.setAttribute("id", "MessagePanel");

        //creo elemento <video>, <audio> e <img>(nel caso non ci sia video)
        var video = document.createElement("video");
        video.setAttribute("id", "messageVideo");
        video.setAttribute("controls", "controls");
        var source = document.createElement("source");
        source.setAttribute("src", "");
        source.setAttribute("type", "");
        video.appendChild(source);

        //creo la lista dei messaggi
        var divMessageList = document.createElement("div");
        divMessageList.setAttribute("id", "divMessage");
        var messageList = document.createElement("ul");
        messageList.setAttribute("id", "messageList");
        divMessageList.appendChild(messageList);
        //FIXME non dovremmo aggiungere i '<li>' a questa lista?

        //appendo al this.element
        element.appendChild(video);
        element.appendChild(divMessageList);
        return element;
    };

    /**
     * Rende lo stato di un messaggio "letto" oppure "non letto"
     *
     * @author Riccardo Tresoldi
     * @param idMessage il messaggio che deve essere modificato
     * @param valueToSet [true: "letto"] oppure [false: "non letto"]
     */
    this.setAsRead = function(idMessage, valueToSet) {
        var request = new XMLHttpRequest();
        request.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {
                //ricevo un JSON contenente l'esito del
                outcome = JSON.parse(requesto.responseText);
            }
        };
        request.open("POST", urlServlet, "true");
        request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        //se c'è bisogno di passare piu parametri, agganciarli con &
        //idUser deve essere dichiarata variabile globale
        request.send("idUser=" + idUser + "&idMessage=" + idMessage + "&read=" + valueToSet);
    };

    /**
     * Ottenere i messaggi di segreteria dal server
     *
     * @author Riccardo Tresoldi
     */
    this.getMessages = function() {
        var request = new XMLHttpRequest();
        //il server ritorna i messaggi di segreteria dal server:
        /*
        [
        {"id": "1", "src": "/messages/1.ogg", "mittente": "Tres", "tipo": "audio", "stato": "letto"},
        {"id": "2", "src": "/messages/2.ogg", "mittente": "Mene", "tipo": "video", "stato": "non_letto"}
        ]
        */
        //var that = this;
        request.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {
                //ricevo un JSON contenente tutti i contatti della mia rubrica e li inserisco nell'array dichiarato globalmente "AddresssBookList"
                messages = JSON.parse(request.responseText);
                // probabile problema: risolvere con that.contacts
            }
        };

        request.open("POST", urlServlet, "true");
        request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        //se c'è bisogno di passare piu parametri, agganciarli con &
        //idUser deve essere dichiarata variabile globale
        request.send("id=" + idUser);
    };

    /**
     * Settare la lista dei messaggi
     *
     * @author Riccardo Tresoldi
     */
    this.setup = function() {
        //estraggo l'<ul> della lista messaggi e lo inizializzo
        var ulList = element.getElementById("messageList");
        ulList.innerHTML = "";

        for (var message in this.messages) {
            //ciclo i messaggi e aggiungo un <li> per ogni contatto
            this.addListItem(ulList, message);
        }
    };

    /* TODO
     * -eliminazione di un messaggio
     */
}
