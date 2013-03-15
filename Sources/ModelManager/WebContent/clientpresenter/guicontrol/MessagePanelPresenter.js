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
     * nel pannello della segreteria telefonica
     * 
     * TODO questa funzione è da completare
     * 
     * @param {HTMLUListElement} list
     * @param {Object} message
     */
    
    /**
     * Elimina un messaggio dalla segreteria
     * 
     * @param {Number} idMessage id del messaggio che deve essere cancellato
     */
    function deleteMessage(idMessage) {
    	//TODO da finire
    }
    
    function addListItem(list, message) {
    	//TODO da finire questa cosa
    }
    
    /**
     * Rende lo stato di un messaggio "letto" oppure "non letto"
     *
     * @author Riccardo Tresoldi
     * @param idMessage il messaggio che deve essere modificato
     * @param valueToSet [true: "letto"] oppure [false: "non letto"]
     * @throws {String} un errore se il non è stato possibile cambiare lo stato del messaggio
     */
    function setAsRead(idMessage, valueToSet) {
        var request = new XMLHttpRequest();
        request.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {
                //ricevo un JSON contenente l'esito del
                outcome = JSON.parse(request.responseText);
            }
        };
        request.open("POST", urlServlet, "true");
        request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        request.send("idUser=" + communicationcenter.my.id + "&idMessage=" + idMessage + "&read=" + valueToSet);
        if (!outcome) {
        	throw "Impossibile impostare lo stato del messaggio a " + (valueToSet? " letto " : " non letto") + "!";
        }
    };
    
    /**
     * Ottiene i messaggi di segreteria dal server
     *
     * @author Riccardo Tresoldi
     */
    function getMessages() {
        var request = new XMLHttpRequest();
        //il server ritorna i messaggi di segreteria dal server:
        /*
        [
        {"id": "1", "src": "/messages/1.ogg", "mittente": "Tres", "tipo": "audio", "stato": "letto"},
        {"id": "2", "src": "/messages/2.ogg", "mittente": "Mene", "tipo": "video", "stato": "non_letto"}
        ]
        */
        request.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {
                messages = JSON.parse(request.responseText);
            }
        };
        request.open("POST", urlServlet, "true");
        request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        request.send("id=" + connectionmanager.my.id);
    };
    
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
     * @param {HTMLDivElement} il pannello che deve diventare la segreteria telefonica
     * @returns {HTMLDivElement} pannello contenente la segreteria telefonica
     * @author Riccardo Tresoldi
     */
    this.createPanel = function(element) {
    	var element = document.createElement("div");
    	element.setAttribute("id", "MessagePanel");
        // creo elemento <video>, <audio> e <img> (nel caso non ci sia video)
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
        this.setup(messageList);
        //appendo al this.element
        element.appendChild(video);
        element.appendChild(divMessageList);
        return element;
    };
    
    /**
     * Costruisce la lista dei messaggi in segreteria e la appende all'elemento in input
     *
     * @param {HTMLUlElement} list lista a cui aggiungere i messaggi
     * @author Riccardo Tresoldi
     */
    this.setup = function(list) {
        getMessages();
        for (var message in messages) {
            // ciclo i messaggi e aggiungo un <li> per ogni contatto
            this.addListItem(list, message);
        }
    };
}
