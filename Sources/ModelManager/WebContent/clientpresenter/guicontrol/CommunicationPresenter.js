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
    // memorizza il pannello nella versione precedente
    var thisPresenter = this;
    var thisPanel;

    // array associativo di tutte le chat che ho aperte in un dato momento
    // gli elementi memorizzati in questo array sono {HTMLDivElements}
    var chatElements = new Object();
    var intervalRing = null;
    var audio = {
        income : new Audio("incomeRingtone.wav"),
        outcome : new Audio("outcomeRingtone.wav")
    };

    /***************************************************************************
     * METODI PRIVATI
     **************************************************************************/
    /** VIEW
     * Crea un elemento di lista adatto ad essere visualizzato dentro
     * 'ulOpenchat' all'interno di questo presenter
     *
     * @param {Object}
     *            user utente con cui è avviata la chat
     * @returns {HTMLLiElement} list item che contiene il nome del contatto con
     *          cui è attiva la comunicazione testuale e che, se cliccato,
     *          determina la visualizzazione all'interno del divChat come
     *          secondo figlio
     * @author Diego Beraldin
     */
    function createChatItem(user) {
        var item = document.createElement("li");
        item.id = "chat-" + user.id;
        item.appendChild(document.createTextNode(mediator.createNameLabel(user)));
        var closeChatButton = document.createElement("img");
        closeChatButton.src = "img/deleteContactImg.png";
        closeChatButton.onclick = function() {
            self.removeChat(user);
            mediator.addOrRemoveCommunicationToTools();
        };
        item.onclick = function() {
            thisPresenter.displayChat(user);
        };
        return item;
    }

    /** VIEW
     * Crea un HTMLDivElement che contiene la porzione di codice HTML che deve
     * essere visualizzata nel momento in cui si fa click sul corrispondente
     * list item della 'ulOpenChat'.
     *
     * @param {Object}
     *            user utente con cui è stata avviata la chat
     * @returns {HTMLDivElement} elemento che contiene l'area di testo, il campo
     *          di immissione testuale e il pulsante di invio del testo
     * @author Diego Beraldin
     */
    function createChatElement(user) {
        var element = document.createElement("div");
        element.id = "divContainerChat";
        var form = document.createElement("form");
        form.id = "chatForm-" + user.id;
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
            sendMessage.contact = user;
            sendMessage.messageText = text;
            document.dispatchEvent(sendMessage);
        };
        form.appendChild(sendButton);

        element.appendChild(form);
        return element;
    }

    /***************************************************************************
     * METODI PUBBLICI
     **************************************************************************/
    /** VIEW
     * Visualizza una determinata chat nel 'divContainerChat'
     *
     * @param {Object}
     *            user utente con cui è attiva la chat che si vuole visualizzare
     * @author Diego Beraldin
     */
    this.displayChat = function(user) {
        var divChat = document.getElementById("divChat");
        var container = document.getElementById("divContainerChat");
        if (divChat != null && container != null) {
            divChat.removeChild(container);
        }
        divChat.appendChild(chatElements[user.id]);
    };

    /** VIEW
     * Aggiunge una chat fra quelle controllate da questo presenter, sia nella
     * lista degli elementi che nel liste item contenuto nella lista di tutte le
     * chat aperte
     *
     * @param {Object}
     *            user utente con cui è stata avviata la chat che deve essere
     *            aggiunta fra quelle controllate da questo presenter
     * @author Diego Beraldin
     */
    // Nella documentazione è onChatAdded
    this.addChat = function(user) {
        var element = createChatElement(user);
        chatElements[user.id] = element;
        var item = createChatItem(user);
        var ulOpenChat = document.getElementById("ulOpenChat");
        ulOpenChat.appendChild(item);
    };

    /** VIEW
     * Rimuove una chat dovunque sia riferita all'interno di questo
     * CommunicationPanelPresenter (nella lista e negli elementi)
     *
     * @param {Object}
     *            user utente con cui è avviata la chat da rimuovere
     * @returns {Boolean}
     * @author Diego Beraldin
     */
    // Nella documentazione è onChatRemoved
    this.removeChat = function(user) {
        delete chatElements[user.id];
        var ulOpenChat = document.getElementById("ulOpenChat");
        var liChat = ulOpenChat.getElementById("chat-" + user.id);
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

    /** VIEW
     * Se presente il pannello è già stato creato allora verrà richiamato,
     * altrimenti verrà
     * @author Riccardo Tresoldi
     */
    this.display = function() {
        if (!thisPanel) {
            thisPanel = document.getElementById("CommunicationPanel");
        }

        // configura la vista
        var closeButton = document.getElementById("closeButton");
        closeButton.onclick = function() {
            communicationcenter.endCall();
            mediator.addOrRemoveCommunicationToTools();
        };
    };

    /** VIEW
     * Funzione per scrivere sulla label che visualizza il tempo della chiamata
     *
     * @author Riccardo Tresoldi
     * @param {String}
     *            text testo da visualizzare
     */
    // Nella documentazione è onUpdateTimer
    this.updateTimer = function(text) {
        var statDiv = document.getElementById("statDiv");
        statDiv.children[1].textContent = "Tempo chiamata: " + text;
    };

    /** VIEW
     * Funzione per scrivere sulla label che visualizza il tempo della chiamata
     *
     * @author Riccardo Tresoldi
     * @param {String}
     *            text testo da visualizzare
     * @param {Boolean}
     *            isRecevedData rappresenta un flag booleano che determina se i
     *            dati sono ricenuti o inviati
     */
    // Nella documentazione è onUpdateStats
    this.updateStats = function(text, isRecevedData) {
        var statDiv = document.getElementById("statDiv");
        if (isRecevedData) {
            statDiv.children[0].children[0].textContent = "Dati ricevuti: " + text;
        } else {
            statDiv.children[0].children[1].textContent = "Dati inviati: " + text;

        }
    };

    /** PRESENTER
     * Funzione che restituisce l'elemento video myVideo dove viene visualizzato
     * lo steaming video estratto dalla propria webcam
     *
     * @author Riccardo Tresoldi
     * @return {HTMLVideoElement} elemento video
     */
    this.getMyVideo = function() {
        return document.getElementById("myVideo");
    };

    /** PRESENTER
     * Funzione che restituisce l'elemento video otherVideo dove viene
     * visualizzato lo steaming video della persona chiamata
     *
     * @author Riccardo Tresoldi
     * @return {HTMLVideoElement} elemento video
     */
    this.getOtherVideo = function() {
        return document.getElementById("otherVideo");
    };

    /***************************************************************************
     * HANDLER DEGLI EVENTI
     **************************************************************************/
    /** PRESENTER
     * Funzione per gestire l'evento in cui viene visualizzato il pannello della
     * comunicazione contenente i video e le chat
     * @author Riccardo Tresoldi
     */
    function onShowCommunicationPanel() {
        if (!thisPanel) {
            mediator.getView('communication');
        } else {
            // TODO da aggiungere il comando di visualizzazione del
            // CommunicationPanel al MainPresenter perche non lo fa più
            // onLoadedView del cesso
            thisPresenter.display();
        }
        if (!document.getElementById("CallFunction"))
            document.dispatchEvent(showReturnToCommunicationPanelButton);
    }

    /** VIEW
     * Gestore dell'evento per l'aggiunta una stringa all'interno dell'area di
     * testo che è associata alla chat con l'utente passato come parametro
     *
     * @author Diego Beraldin
     * @author Riccardo Tresoldi
     * @param {Object} user utente con cui è avviata la chat
     * @param {String} message testo da appendere al termine della textarea
     * @param {Boolean} amISender rappresenta un flag che determina chi è che ha
     * inviato il messaggio
     */
    // nella documentazione è onAppendMessage
    function onAppendMessageToChat(user, message, amISender) {
        var divContainerChat = chatElements[user.id];
        var textArea = document.evaluate("//node()[@id='chatText']", divContainerChat, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;
        var sender = null;
        if (amISender)
            sender = "io";
        else
            sender = user.name;
        textArea.value += (sender + ":\t" + message + "\n");
    }

    /**
     * Gestione suoneria chiamata ON
     * @version 2.0
     * @author Riccardo Tresoldi
     * @param {String} event evento che richiede la suoneria
     */
    function onStartRinging(event) {
        if (!intervalRing) {
            intervalRing = setInterval(function() {
                audio[event].play();
            }, 1000);
        }
    }

    /**
     * Gestione suoneria chiamata OFF
     * @version 2.0
     * @author Riccardo Tresoldi
     * @param {String} event evento che richiede la suoneria
     */
    function onStopRinging() {
        if (intervalRing) {
            clearInterval(intervalRing);
            intervalRing = null;
        }
    }

    /***************************************************************************
     * LISTENER DEGLI EVENTI
     **************************************************************************/
    document.addEventListener("showCommunicationPanel", onShowCommunicationPanel);
    document.addEventListener("appendMessageToChat", function(evt) {
        onAppendMessageToChat(evt.user, evt.message, evt.IAmSender);
    })
    document.addEventListener("startRinging", function(evt){
        onStartRinging(evt.evento);
    });
    document.addEventListener("stopRinging", onStopRinging);
}
