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
    var element = null;
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
        item.id = user.id;
        item.appendChild(document.createTextNode(createLabel(user)));
        var closeChatButton = document.createElement("img");
        // FIXME add image src!
        closeChatButton.src = "";
        var self = this;
        closeChatButton.onclick = function() {
            self.removeChat(user);
            mediator.addOrRemoveCommunicationToTools();
        };
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

    /**
     * Funzione che nasconde il messaggio che avvisa l'arrivo della chiamata
     *
     * @author Riccardo Tresoldi
     */
    function removeAnswerBox() {
        //estraggo gli elementi da rimuovere
        overlay = document.getElementById('overlayAnswerBox');
        answerBox = document.getElementById('answerBox');
        //setto la visibilità a 'none'
        overlay.style.display = "none";
        answerBox.style.display = "none";
        //elimino dal DOM gli elementi
        document.removeChild(overlay);
        document.removeChild(answerBox);
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
     * nuovo elemento che corrisponde alla chat con l'utente passato come
     * parametro
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
     * @param {Object}
     *            user utente con cui è stata avviata la chat che deve essere
     *            aggiunta fra quelle controllate da questo presenter
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
     * @param {Object}
     *            user utente con cui è avviata la chat da rimuovere
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
     * @param {Object}
     *            utente con cui è avviata la chat
     * @param {String}
     *            text testo da appendere al termine della textarea
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
        // FIXME preventivo
        if (element == null) {
            element = document.createElement('div');
            element.setAttribute("id", "CommunicationPanel");

            // azzero il div
            element.innerHTML = "";

            var divHeader = document.createElement("div");
            divHeader.className = "panelHeader";
            var header = document.createElement("h1");
            header.appendChild(document.createTextNode("pannello di comunicazione"));
            divHeader.appendChild(header);
            element.appendChild(divHeader);

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
            myVideo.setAttribute("muted", "muted");
            // attribute poster per immagine di caricamento
            var otherVideo = document.createElement('video');
            otherVideo.id = "otherVideo";
            otherVideo.setAttribute("autoplay", "autoplay");

            // creo div per visualizzare le statistiche della chiamata
            statDiv = document.createElement("div");
            statDiv.id = "statDiv";
            var statSpan = document.createElement("span");
            statSpan.id = "statSpan";
            var statReceved = document.createElement("span");
            statReceved.id = "statReceved";
            var statSend = document.createElement("span");
            statSend.id = "statSend";
            statSpan.appendChild(statReceved);
            statSpan.appendChild(statSend);
            var timerSpan = document.createElement("span");
            timerSpan.id = "timerSpan";
            statDiv.appendChild(statSpan);
            statDiv.appendChild(timerSpan);

            // creo i bottoni per per la gestione della chiamata
            var closeButton = document.createElement('button');
            closeButton.type = "button";
            closeButton.appendChild(document.createTextNode("Termina"));
            closeButton.id = "closeButton";
            closeButton.onclick = function() {
                communicationcenter.endCall();
                mediator.addOrRemoveCommunicationToTools();
            };

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
        }
        return element;
    };

    /**
     * Funzione per scrivere sulla label che visualizza il tempo della chiamata
     *
     * @author Riccardo Tresoldi
     * @param {String}
     *            text testo da visualizzare
     */
    this.updateTimer = function(text) {
        var statDiv = document.getElementById("statDiv");
        statDiv.childNodes[1].textContent = "Tempo chiamata: " + text;
    };

    /**
     * Funzione per scrivere sulla label che visualizza il tempo della chiamata
     *
     * @author Riccardo Tresoldi
     * @param {String}
     *            text testo da visualizzare
     * @param {Boolean}
     *            isRecevedData rappresenta un flag booleano che determina se i
     *            dati sono ricenuti o inviati
     */
    this.updateStats = function(text, isRecevedData) {
        var statDiv = document.getElementById("statDiv");
        if (isRecevedData) {
            statDiv.childNodes[0].childNodes[0].textContent = "Dati ricevuti: " + text;
        } else {
            statDiv.childNodes[0].childNodes[1].textContent = "Dati inviati: " + text;

        }
    };

    /**
     * Funzione che restituisce l'elemento video myVideo dove viene visualizzato
     * lo steaming video estratto dalla propria webcam
     *
     * @author Riccardo Tresoldi
     * @return {HTMLVideoElement} elemento video
     */
    this.getMyVideo = function() {
        return document.getElementById("myVideo");
    };

    /**
     * Funzione che restituisce l'elemento video otherVideo dove viene
     * visualizzato lo steaming video della persona chiamata
     *
     * @author Riccardo Tresoldi
     * @return {HTMLVideoElement} elemento video
     */
    this.getOtherVideo = function() {
        return document.getElementById("otherVideo");
    };

    /**
     * Funzione che mostra il messaggio che avvisa l'arrivo della chiamata
     *
     * @author Riccardo Tresoldi
     * @param {Object} caller contatto che rappresenta il chiamante
     */
    this.showAnswerBox = function(caller) {
        var body = document.getElementsByTagName("body").item(0);
        //creo il div di sfondo
        var overlay = document.createElement("div");
        overlay.id = "overlayAnswerBox";
        //creo il div con la richiesta di risposta
        var answerBox = document.createElement("div");
        answerBox.id = "answerBox";
        //creo gli elementi della finestra di risposta
        //label generica
        var labelAnswerBox = document.createElement("span");
        labelAnswerBox.appendChild(document.createTextNode("Chiamata in arrivo da:"));
        labelAnswerBox.id = "labelAnswerBox";
        //label con nome del chiamante
        var nameCallerAnswerBox = document.createElement("span");
        nameCallerAnswerBox.appendChild(document.createTextNode(mediator.createNameLabel(caller)));
        nameCallerAnswerBox.id = "nameCallerAnswerBox";
        //pulsante per rifiutare la chiamata
        var refuseCallButton = document.createElement("button");
        refuseCallButton.appendChild(document.createTextNode("Rifiuta"));
        refuseCallButton.type = "button";
        refuseCallButton.id = "refuseCallButton";
        refuseCallButton.onclick = function() {
            removeAnswerBox();
            communicationcenter.refuseCall();
        };
        //pulsante per accettare la chiamata
        var acceptCallButton = document.createElement("button");
        acceptCallButton.appendChild(document.createTextNode("Accetta"));
        acceptCallButton.type = "button";
        acceptCallButton.id = "acceptCallButton";
        acceptCallButton.onclick = function() {
            removeAnswerBox();
            communicationcenter.acceptCall();
        };
        //appendo tutti gli elementi al div
        answerBox.appendChild(labelAnswerBox);
        answerBox.appendChild(nameCallerAnswerBox);
        answerBox.appendChild(refuseCallButton);
        answerBox.appendChild(acceptCallButton);
        //appendo i div appena creati al body
        body.appendChild(overlay);
        body.appendChild(answerBox);
    };

    /**
     * Funzione che fa partire una suoneria
     *
     * @author Riccardo tresoldi
     * @param {String} evt evento che richiede la suoneria
     */
    this.startRinging = function(evt) {
        if (!intervalRing) {
            intervalRing = setInterval(function() {
                audio[evt].play();
            }, 1000);
        }
    };

    /**
     * Funzione che fa partire una suoneria
     *
     * @author Riccardo tresoldi
     */
    this.stopRinging = function() {
        if (intervalRing) {
            clearInterval(intervalRing);
            intervalRing = null;
        }

    };
}
