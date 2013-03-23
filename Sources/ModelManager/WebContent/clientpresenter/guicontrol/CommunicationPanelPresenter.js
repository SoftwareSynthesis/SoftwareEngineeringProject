/**
 * Presenter incaricato di gestire il pannello delle comunicazioni, siano esse
 * di natura testuale oppure di natura audio o audio/video
 *
 * @author Elena Zecchinato
 * @author Diego Beraldin
 */
function CommunicationPanelPresenter() {
    /***************************************************************************
     * VARIABILI PRIVATE
     **************************************************************************/
    // array associativo di tutte le chat che ho aperte in un dato momento
    // gli elementi memorizzati in questo array sono {HTMLDivElements}
    var chatElements = new Object();

    /***************************************************************************
     * METODI PRIVATI
     **************************************************************************/
    /**
     * Crea la stringa di testo che deve comparire come titolo della tab che
     * contiene la lista delle chat testuali attive in un determinato momento
     *
     * @see AddressBookPanelPresenter#addListItem()
     *
     * @returns {String} il testo che deve costituire il titolo del list item
     * @author Diego Beraldin
     */
    function createLabel(user) {
        var title = "";
        if (user.name != null) {
            title += user.name;
        }
        if (user.surname != null) {
            if (title != "") {
                title += " ";
            }
            title += user.surname;
        }
        if (title == "") {
            title += user.email;
        }
        return title;
    }

    /**
     * Crea un elemento di lista adatto ad essere visualizzato dentro
     * 'ulOpenchat' all'interno di questo presenter
     *
     * @param {Object} user utente con cui è avviata la chat
     * @returns {HTMLLiElement} list item che contiene il nome del contatto con
     * cui è attiva la comunicazione testuale e che, se cliccato, determina la
     * visualizzazione all'interno del divChat come secondo figlio
     * @author Diego Beraldin
     */
    function createChatItem(user) {
        var item = document.createElement("li");
        item.id = user.id;
        item.appendChild(document.createTextNode(createLabel(user)));
        var self = this;
        item.onclick = function() {
            self.displayChat(user);
        };
        return item;
    }

    /**
     * Crea un HTMLDivElement che contiene la porzione di codice HTML che deve
     * essere visualizzata nel momento in cui si fa click sul corrispondente
     * list item della 'ulOpenChat'.
     *
     * @param {Object} user utente con cui è stata avviata la chat
     * @returns {HTMLDivElement} elemento che contiene l'area di testo, il campo
     * di immissione testuale e il pulsante di invio del testo
     * @author Diego Beraldin
     */
    function createChatElement(user) {
        var element = document.createElement("div");
        element.id = "divContainerChat";
        var form = document.createElement("form");
        form.id = user.id;
        // crea l'area di testo
        var textArea = document.createElement("textarea");
        textArea.id = "chatText";
        form.appendChild(textArea);
        // crea il campo per l'immissioen del testo
        var input = document.createElement("input");
        input.id = "text";
        input.name = "text";
        form.appendChild(input);

        // crea il pulsante
        var sendButton = document.createElement("button");
        sendButton.appendChild(document.createTextNode("Invia"));

        // aggancia la funzione di callback al pulsante
        sendButton.onclick = function() {
            var text = input.value;
            textArea.value += (text + "\n");
            communicationcenter.send(user, text);
        };
        form.appendChild(sendButton);

        element.appendChild(form);
        return element;
    }

    /***************************************************************************
     * METODI PUBBLICI
     **************************************************************************/
    /**
     * Visualizza una determinata chat nel 'divContainerChat'
     *
     * PRE: Esiste nel documento un <div> con id uguale a 'divChat' che contiene
     * come figlio un altro <div> con id uguale a 'divContainerChat'. Inoltre,
     * chatElements contiene un elemento chat indicizzato con l'id dell'utente
     * passato come parametro alla funzione
     *
     * POST: il <div> interno ('divContainerChat') è stato rimpiazzato da un
     * nuovo
     * elemento che corrisponde alla chat con l'utente passato come parametro
     *
     * @param {Object} user utente con cui è attiva la chat che si vuole
     * visualizzare
     * @author Diego Beraldin
     */
    this.displayChat = function(user) {
        var divChat = document.getElementById("divChat");
        var container = document.getElementById("divContainerChat");
        divChat.removeChild(container);
        divChat.appendChild(chatElements[user.id]);
    };

    /**
     * Aggiunge una chat fra quelle controllate da questo presenter, sia nella
     * lista degli elementi che nel liste item contenuto nella lista di tutte le
     * chat aperte
     *
     * PRE: il presenter ha la proprietà chatElements e la vista ha una lista
     * che ha come id 'ulOpenChat'
     *
     * POST: a chatElements è stato aggiunto un nuovo elemento che contiene i
     * dati relativi alla nuova chat testuale, e alla lista è stato attiunto un
     * list item che contiene il nome del contatto con cui è stata instaurata la
     * chat testuale.
     *
     * @param {Object} user utente con cui è stata avviata la chat che deve
     * essere aggiunta fra quelle controllate da questo presenter
     * @author Diego Beraldin
     */
    this.addChat = function(user) {
        var element = createChatElement(user);
        chatElements[user.id] = element;
        var item = createChatItem(user);
        var ulOpenChat = document.getElementById("ulOpenChat");
        ulOpenChat.appendChild(item);
    };

    /**
     * Rimuove una chat dovunque sia riferita all'interno di questo
     * CommunicationPanelPresenter (nella lista e negli elementi)
     *
     * PRE: esiste nella lista 'ulChat' un
     * <li> che riferisce la chat passata come parametro ed esiste in
     * chatElements un <div> che contiene il testo di quella chat
     *
     * POST: sono stati rimossi sua l'elemento <div> da chatElements sia il
     * <li> associato alla chat dalla lista delle chiamate
     *
     * @param {Object} user utente con cui è avviata la chat da rimuovere
     * @returns {Boolean}
     * @author Diego Beraldin
     */
    this.removeChat = function(user) {
        delete chatElements[user.id];
        var ulOpenChat = document.getElementById("ulOpenChat");
        var liChat = ulOpenChat.getElementById(user.id);
        ulOpenChat.removeChild(liChat);

        // testa se era visualizzata proprio quella chat e in tal caso la
        // rimuove dalla vista
        var divContainerChat = document.getElementById("divContainerChat");
        var form = divContainerChat.childNodes[0];
        if (form.id == user.id) {
            var divChat = document.getElementById("divChat");
            divChat.removeChild(divContainerChat);
        }
    };

    /**
     * Aggiunge una stringa all'interno dell'area di testo che è associata alla
     * chat con l'utente passato come parametro
     *
     * PRE: si assume che già esista in chatElements un oggetto HTMLDivElement
     * indicizzato con l'id dell'utente passato come parametro
     *
     * POST: al ritorno della funzione, la textArea contenuta in questo <div>
     * contiene il nuovo testo passato come parametro
     *
     * @param {Object} utente con cui è avviata la chat
     * @param {String} text testo da appendere al termine della textarea
     * @author Diego Beraldin
     */
    this.appendToChat = function(user, text) {
        var divContainerChat = chatElements[user.id];
        var textArea = divContainerChat.getElementById("chatText");
        textArea.value += ("io:" + text + "\n");
    };

    /**
     * Inizializza il pannello costruendone i widget grafici interni e lo
     * restituisce in modo che possa essere inserito all'interno del pannello
     * principale
     *
     * POST: l'elemento <div> restituito ha la struttura del CommunicationPanel
     *
     * @returns {HTMLDivElement} il 'CommunicationPanel'
     * @author Elena Zecchinato
     * @author Riccardo Tresoldi
     */
    this.createPanel = function() {
        var element = document.createElement('div');
        element.setAttribute("id", "CommunicationPanel");

        // azzero il div
        element.innerHTML = "";

        // creo div contenente la chiamata vera e propria
        var divCall = document.createElement('div');
        divCall.id = "divCall";
        // creo div contenente le chat testuali
        var divChat = document.createElement('div');
        divChat.id = "divChat";

        // creo gli elementi video per la videochat
        var myVideo = document.createElement('video');
        myVideo.id = "myVideo";
        myVideo.setAttribute("autoplay", "autoplay");
        var otherVideo = document.createElement('video');
        otherVideo.id = "otherVideo";
        otherVideo.setAttribute("autoplay", "autoplay");

        // creo div per visualizzare le statistiche della chiamata
        statDiv = document.createElement("div");
        statDiv.id = "statDiv";
        var statSpan = document.createElement("span");
        statSpan.id = "statSpan";
        var timerSpan = document.createElement("span");
        timerSpan.id = "timerSpan";
        statDiv.appendChild(statSpan);
        statDiv.appendChild(timerSpan);

        // creo i bottoni per per la gestione della chiamata
        var closeButton = document.createElement('button');
        closeButton.type = "button";
        closeButton.id = "closeButton";
        closeButton.onclick = communicationcenter.endCall;

        // appendo i child al divCall
        divCall.appendChild(myVideo);
        divCall.appendChild(otherVideo);
        divCall.appendChild(statDiv);
        divCall.appendChild(closeButton);

        // creo gli elementi per la chat testuale
        // creo l'<ul> per le schede delle chat aperte
        var ulOpenChat = document.createElement('ul');
        ulOpenChat.id = "ulOpenChat";

        // creo il div per la visualizzazione della chat selezionata.
        // var divContainerChat = document.createElement('div');
        // divContainerChat.id = "divContainerChat";

        // appendo la lista al <div> della chat
        divChat.appendChild(ulOpenChat);
        // divChat.appendChild(divContainerChat);

        // apendo il sottoalbero al DOM
        element.appendChild(divCall);
        element.appendChild(divChat);

        return element;
    };

    /**
     * Funzione per scrivere sulla label che visualizza il tempo della chiamata
     *
     * @author Riccardo Tresoldi
     * @param {String} text testo da visualizzare
     */
    this.updateTimer = function(text) {
        statDiv.childNodes[1].textContent = text;
    };

    /**
     * Funzione per scrivere sulla label che visualizza il tempo della chiamata
     *
     * @author Riccardo Tresoldi
     * @param {String} text testo da visualizzare
     */
    this.updateStarts = function(text) {
        statDiv.childNodes[0].textContent = text;
        ;
    };
}
