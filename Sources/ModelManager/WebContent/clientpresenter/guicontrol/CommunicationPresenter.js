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
        income : new Audio("ringtones/incomeRingtone.wav"),
        outcome : new Audio("ringtones/outcomeRingtone.wav")
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
        var textItem = document.createElement("span");
        textItem.appendChild(document.createTextNode(mediator.createNameLabel(user)));
        item.appendChild(textItem);
        var closeChatButton = document.createElement("img");
        closeChatButton.src = "img/deleteContactImg.png";
        closeChatButton.onclick = function() {
            thisPresenter.removeChat(user);
        };
        textItem.onclick = function() {
            thisPresenter.displayChat(user);
        }; 
        item.appendChild(closeChatButton);
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
        textArea.readOnly = true;
        form.appendChild(textArea);
        // crea il campo per l'immissioen del testo
        var input = document.createElement("input");
        input.id = "text";
        input.type = "text";
        input.name = "text";
        form.appendChild(input);
        input.onkeyup = function() {
            if (this.value != "") {
                document.getElementById("sendButton").disabled = false;
            } else {
                document.getElementById("sendButton").disabled = true;
            }
        }
        // crea il pulsante
        var sendButton = document.createElement("button");
        sendButton.id = "sendButton";
        sendButton.disabled = true;
        sendButton.appendChild(document.createTextNode("Invia"));

        // aggancia la funzione di callback al pulsante
        sendButton.onclick = function(evt) {
            evt.preventDefault();
            var text = input.value;
            sendMessage.contact = user;
            sendMessage.messageText = text;
            document.dispatchEvent(sendMessage);
            input.value = "";
            this.disabled = true;
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
    // NB nella documentazione questo è un metodo privato
    this.displayChat = function(user) {
        if (user) {
            var divChat = document.getElementById("divChat");
            var container = document.getElementById("divContainerChat");
            if (divChat != null && container != null) {
                divChat.removeChild(container);
            }
            divChat.appendChild(chatElements[user.id]);
        }
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
        if (!chatElements[user.id]) {
            // non è presente nessuna chat con questo utente. la creo
            var element = createChatElement(user);
            chatElements[user.id] = element;
            var item = createChatItem(user);
            var ulOpenChat = document.getElementById("ulOpenChat");
            ulOpenChat.appendChild(item);
            thisPanel = document.getElementById("CommunicationPanel");
        }
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
        var liChat = document.getElementById("chat-" + user.id);
        ulOpenChat.removeChild(liChat);

        // testa se era visualizzata proprio quella chat e in tal caso la
        // rimuove dalla vista
        var divContainerChat = document.getElementById("divContainerChat");
        var form = divContainerChat.children[0];
        if (form.id == ("chatForm-" + user.id)) {
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
        } else {
            showGeneralPanel.panel = thisPanel.outerHTML;
            document.dispatchEvent(showGeneralPanel);
        }
        // configura la vista
        var closeButton = document.getElementById("closeButton");
        closeButton.onclick = function() {
            communicationcenter.endCall();
        };
        // mostro le chat
        thisPresenter.displayChat();
        // inizializzo i callback delle chat
        var chatsList = document.getElementById("ulOpenChat");
        if (chatsList && chatsList.children.length != 0){
            for (c in chatsList.children) {
                if(c == "length"){
                    break;
                }
                var item = chatsList.children[c];
                var userId = item.id.split("-")[1];
                var user = mediator.getContactById(userId);
                // setto l'evento onclick per la visualizzazione dela chat
                item.children[0].onclick = function() {
                    var currentUser = user;
                    thisPresenter.displayChat(currentUser);
                };
                // setto l'evento onclick per la chiusura della chat
                item.children[1].onclick = function() {
                    var currentUser = user;
                    thisPresenter.removeChat(currentUser);
                };
            }
        }
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
        var statDiv = document.getElementById("timerSpan");
        statDiv.textContent = "Tempo chiamata: " + text;
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
        var statDiv = document.getElementById("statSpan");
        if (isRecevedData) {
            statDiv.children[0].textContent = "Dati ricevuti: " + text;
        } else {
            statDiv.children[1].textContent = "Dati inviati: " + text;

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
    function onAppendMessageToChat(user, message, amISender) {
        var divContainerChat = chatElements[user.id];
        var textArea = document.evaluate("//node()[@id='chatText']", document.body, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;
        var sender = null;
        if (amISender)
            sender = "io";
        else
            sender = user.name;
        textArea.value += (sender + ":\t" + message + "\n");
    }

    /** PRESENTER
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

    /** PRESENTER
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

    /** PRESENTER
     * Gestione evento visualizzazione chat
     * @version 2.0
     * @author Riccardo Tresoldi
     */
    function onChatStarted(user) {
        document.dispatchEvent(showCommunicationPanel);
        thisPresenter.addChat(user);
        thisPresenter.displayChat(user);
    }

    /** VIEW
     * Gestione dell'azzeramento dell'oggetto che contiene le chat
     * @version 2.0
     * @author Riccardo Tresoldi
     */
    function onResetChatsObject() {
        chatElements = new Object();
    }

    /***************************************************************************
     * LISTENER DEGLI EVENTI
     **************************************************************************/
    document.addEventListener("showCommunicationPanel", onShowCommunicationPanel);
    document.addEventListener("appendMessageToChat", function(evt) {
        onAppendMessageToChat(evt.user, evt.text, evt.IAmSender);
    })
    document.addEventListener("startRinging", function(evt) {
        onStartRinging(evt.evento);
    });
    document.addEventListener("stopRinging", onStopRinging);
    document.addEventListener("chatStarted", function(evt) {
        onChatStarted(evt.user);
    });
    document.addEventListener("resetChatsObject", onResetChatsObject);
}
