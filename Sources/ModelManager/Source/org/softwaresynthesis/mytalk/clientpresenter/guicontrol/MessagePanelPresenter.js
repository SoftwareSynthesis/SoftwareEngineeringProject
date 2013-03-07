/**
 * Presenter incaricato di gestire i messaggi in segreteria
 *
 * @author Elena Zecchinato
 * @author Diego Beraldin
 */
function MessagePanelPresenter() {
    //elemento controllato da questo presenter
    this.element = document.getElementById("MessagePanel");
    //array di messaggi
    this.messages = new Array;
    //url della servlet da contattare per i messaggi
    this.urlServlet = "http://localhost:8080/MessageManager";

    /**
     * @author Riccardo Tresoldi
     */
    this.initialize = function() {
        //azzero il MessagePanel
        this.element.innerHTML = "";

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

        //appendo al this.element
        this.element.appendChild(video);
        this.element.appendChild(divMessageList);
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
        var ulList = this.element.getElementById("messageList");
        ulList.innerHTML = "";

        for (var message in this.messages) {
            //ciclo i messaggi e agiungo un <li> per ogni contatto
            //TODO togliere il commento seguente!
            //QUESTA È LA SINTASSI PER ARRAY ASSOCIATIVI MA ORA È UN ARRAY NORMALE: addListItem(ulList, this.contacts[contact]);
            this.addListItem(ulList, this.messages);
        }
    };

    /* TODO
     * -eliminazione di un messaggio
     */
}
