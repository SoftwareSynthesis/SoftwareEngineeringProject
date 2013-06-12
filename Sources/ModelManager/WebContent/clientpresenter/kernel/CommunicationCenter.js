/**
 * Classe logica che gestisce la parte della comunicazione lato Client
 *
 * @author Riccardo Tresoldi
 * @author Marco Schivo
 */
function CommunicationCenter() {
    /**********************************************************
     VARIABILI PUBBLICHE
     ***********************************************************/
    var thisMonolith = this;
    //oggetto che contiene i dati dell'utente
    this.my = new Object();

    /**********************************************************
     VARIABILI PRIVATE
     ***********************************************************/
    //self=this per utilizzo nei meotdi
    var self = this;
    // dichiaro globale la websocket per lo scambio di dati con la servlet
    var websocket, pc;
    // variabili per la pacchettizzazione del signal in message.type=2
    var remoteDescriptionPacket = new Array();
    var sdpPacket = new Array();
    // variabili per statistiche
    var timer, statCollector;

    /**********************************************************
     METODI PRIVATI
     ***********************************************************/

    /**
     * Funzione per formattare i bytes ricevuti.
     *
     * @author Marco Schivo
     * @param {Number} bytes rappresenta il numero dei bytes da formattare
     * @return {Number} i bytes formattati correttamente
     */
    function formatBytes(bytes) {
        if (bytes <= 1048576) {
            var kb = bytes / 1024;
            kb = Math.round(kb);
            kb += " KB";
            return kb;
        } else {
            var mb = bytes / 1048576;
            var cifre = mb.toString();
            var cifreArray = cifre.split(".");
            if (cifreArray[0].length >= 1) {
                return (cifre.substring(0, 4)) + " MB";
            }
            if (cifreArray[0].length >= 2) {
                return (cifre.substring(0, 5)) + " MB";
            }
            if (cifreArray[0].length >= 3) {
                return (cifre.substring(0, 6)) + " MB";
            }
        }
    }

    /**
     * Funzione per formattare nel formato "hh:mm:ss" il tempo della chiamata.
     *
     * @author Marco Schivo
     * @param {Number} tempo rappresenta il tempo espresso in secondi da
     * formattare
     * @return {Number} il tempo nel format standard hh:mm:ss
     */
    function formatTime(tempo) {
        var ore = parseInt((tempo) / 3600);
        var minuti = parseInt((tempo - (ore * 3600)) / 60);
        var secondi = tempo - (ore * 3600) - (minuti * 60);
        if (ore < 10)
            ore = "0" + ore;
        if (minuti < 10)
            minuti = "0" + minuti;
        if (secondi < 10)
            secondi = "0" + secondi;

        var time = ore + ":" + minuti + ":" + secondi;
        return time;
    }

    /**
     * Funzione per fermare il timer della chiamata.
     *
     * @author Marco Schivo
     */
    function stopTimer() {
        clearInterval(timer);
        time = 0;
    }

    /**
     * Funzione per fermare la ricezione delle statistiche
     *
     * @author Marco Schivo
     */
    function stopStat() {
        clearInterval(statCollector);
    }

    /**
     * Funzione per l'estrazione dei dati dal oggetto delle statistiche
     *
     * @author Marco Schivo
     */
    function dumpStats(obj) {
        var statsString;
        if (obj.names) {
            names = obj.names();
            for (var i = 0; i < names.length; ++i) {
                if (names[i] == "bytesReceived") {
                    mediator.communicationPPUpdateStats(formatBytes(obj.stat(names[i])), true);
                }
                if (names[i] == "bytesSent") {
                    mediator.communicationPPUpdateStats(formatBytes(obj.stat(names[i])), false);
                }
            }
        }
    }

    /**
     * Imposto la mia descrizione e la invio all'altro peer
     *
     * @author Marco Schivo
     */
    function gotDescription(desc) {
        pc.setLocalDescription(desc);
        var json = JSON.stringify(desc);
        var ar = new Array("2", idOther, json);
        websocket.send(JSON.stringify(ar));
    }

    /**********************************************************
     METODI PUBBLICI
     ***********************************************************/
    /**
     * Funzione per la connessione del client al server utilizzando una WebSoket.
     * Questa funzione gestisce anche la ricezione di dati da parte del server
     *
     * @author Marco Schivo
     */
    this.connect = function() {
        var self = this;
        websocket = new WebSocket(urlChannelServlet);
        //event handle per gestire l'apertura della socket
        websocket.onopen = function(evt) {
            //creo l'array da passare alla servlet per la connessione e l'invio
            var ar = new Array("1", self.my.id);
            websocket.send(JSON.stringify(ar));
            //informo gli utenti della mia rubrica che sono online
            changeMyState.state = "available";
            document.dispatchEvent(changeMyState);
        };

        //event handle per gestire la chiusura della socket
        websocket.onclose = function(evt) {
        };

        //event handle per gestire l'arrivo di un messaggio da parte della socket
        websocket.onmessage = function(evt) {
            //split del messaggio ricevuto e estrazione del tipo di messaggio
            var str = evt.data.split("|");
            var onlyAudio = false;
            var type = str[0];
            //controllo che tipo di messaggio ho ricevuto
            /*{ 3 : ottengo id della persona che mi sta chiamando,
             *  2 : quando inizia la chiamata,
             *  5 : cambio stato altri utenti
             *  6 : notifica rifiuto chiamata
             *  7 : ricezione messaggio chat
             *} */
            if (type == "3") {
                idOther = str[1];
                onlyAudio = str[2];
            } else if (type == "2") {
                var signal = JSON.parse(str[1]);
                if (pc == null) {
                    if ((signal.sdp) == null) {
                        sdpPacket.push(signal);
                    } else {
                        remoteDescriptionPacket.push(signal);
                    }
                    // chiamo il gestore della chiamata in arrivo
                    showPhoneIncomeCallAlertPanel.caller = mediator.getContactById(idOther);
                    showPhoneIncomeCallAlertPanel.onlyAudio = onlyAudio;
                    document.dispatchEvent(showPhoneIncomeCallAlertPanel);
                } else {
                    if ((signal.sdp) == null) {
                        pc.addIceCandidate(new RTCIceCandidate(signal));
                    } else {
                        document.dispatchEvent(stopRinging);
                        pc.setRemoteDescription(new RTCSessionDescription(signal));
                    }
                }
            } else if (type == "5") {
                changeAddressBooksContactState.idUserChange = str[1];
                changeAddressBooksContactState.statusUserChange = str[2];
                document.dispatchEvent(changeAddressBooksContactState);
            } else if (type == "6") {
                // notifica rifiuto chiamata
                document.dispatchEvent(rejectedCall);
            } else if (type == "7") {
                // ricezione messaggio in chat
                var user = mediator.getContactById(str[1]);
                chatStarted.user = user;
                document.dispatchEvent(chatStarted);
                appendMessageToChat.user = user;
                appendMessageToChat.text = str[2];
                appendMessageToChat.IAmSender = false;
                document.dispatchEvent(appendMessageToChat);
            }
        };
        //event handle per gestire gli errori avvenuti della socket
        websocket.onerror = function(evt) {
            //nel caso la servlet restituisse un errore
            alert("È avvenuto un problema nel server che tuttavia potrebbe non compromettere la chiamata se già avviata.");
        };
    };

    /**
     * Funzione per la disconnessione del client dal server.
     *
     * @author Marco Schivo
     */
    this.disconnect = function() {
        //azzero la variabile my
        //creo array per inviare la rischiesta di disconnessione e lo invio
        var ar = new Array("4");
        websocket.send(JSON.stringify(ar));
        websocket.close();
        my = new Object();
        websocket = null;
        pc = null;
    };

    /**
     * Funzione per la vera e propria chiamata attraverso webRTC
     *
     * @author Marco Schivo
     *
     * @param {Boolean} isCaller rappresenta il flag che determina se chi invoca
     * la funzione è il chiamante o il chiamato
     * @param {Object} contact rappresenta il contatto da contattare
     * @param {Boolean} onlyAudio true se si vole fare una chiamata solo audio
     */
    this.call = function(isCaller, contact, onlyAudio) {
		if (isCaller) {
			var addToCallsHistory = new XMLHttpRequest();
			addToCallsHistory.open("POST", commandURL, false);
			addToCallsHistory.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
			addToCallsHistory.send("operation=addCall&contactId=" + contact.id);
		}

        //invio l'avviso di cambio stato in occupato
        changeMyState.state = "occupied";
        document.dispatchEvent(changeMyState);

        //creo l'oggetto di configurazione della RTCPeerConnection con
        // l'indirizzo del server STUN
        var configuration = {
            "iceServers" : [{
                "url" : "stun:stun.l.google.com:19302"
            }]
        };

        pc = new webkitRTCPeerConnection(configuration);

        //invio tutti gli ICECandidate agli altri peer
        pc.onicecandidate = function(evt) {
            var json = JSON.stringify(evt.candidate);
            var ar = new Array("2", idOther, json);
            websocket.send(JSON.stringify(ar));
        };

        //quando arriva un remoteStream lo visualizo nell corrispettivo elemento
        // <video>
        pc.onaddstream = function(evt) {
            mediator.getCommunicationPPOtherVideo().src = URL.createObjectURL(evt.stream);
            //avvio il timer della chiamata
            var time = 0;
            timer = setInterval(function() {
                time++;
                var now = formatTime(time);
                //richiamo un metodo di CommunicationPanelPresenter per
                // visualizzare il tempo.
                mediator.communicationPPUpdateTimer(now);
            }, 1000);
            //Visualizzazione statistiche chiamata
            statCollector = setInterval(function() {
                if (pc && pc.getRemoteStreams()[0]) {
                    if (pc.getStats) {
                        pc.getStats(function(stats) {
                            var statsString = '';
                            var results = stats.result();
                            for (var i = 0; i < results.length; ++i) {
                                var res = results[i];
                                if (res.local) {
                                    dumpStats(res.local);
                                }
                            }
                        });
                    } else {
                        mediator.communicationPPUpdateStats("Browser not supported", false);
                        mediator.communicationPPUpdateStats("Browser not supported", true);
                    }
                }
            }, 1000);
        };

        //quando il remoteStream viene tolto lo eliminio dal mio client
        pc.onremovestream = function() {
			if (localStream != null){
				changeMyState.state = "available";
				document.dispatchEvent(changeMyState);
				localStream.stop();
				pc.removeStream(localStream);
				stopTimer();
				stopStat();
				mediator.getCommunicationPPMyVideo().src = "";
				mediator.getCommunicationPPOtherVideo().src = "";
				pc.close();
				pc = null;
				localStream = null;
			}
        };

        //prende lo stream video locale, lo visualizza sul corrispetivo <video> e
        // lo invia agli altri peer
        if (onlyAudio == true) {
            navigator.webkitGetUserMedia({
                "audio" : true,
                "video" : false
            }, function(stream) {
                mediator.getCommunicationPPMyVideo().src = URL.createObjectURL(stream);
                localStream = stream;
                pc.addStream(stream);

                if (isCaller == true) {
                    idOther = contact.id;
                    var ar = new Array("3", idOther, onlyAudio);
                    websocket.send(JSON.stringify(ar));
                    pc.createOffer(gotDescription);
                } else
                    pc.createAnswer(gotDescription);
            });
        } else {
            navigator.webkitGetUserMedia({
                "audio" : true,
                "video" : true
            }, function(stream) {
                mediator.getCommunicationPPMyVideo().src = URL.createObjectURL(stream);
                localStream = stream;
                pc.addStream(stream);

                if (isCaller == true) {
                    idOther = contact.id;
                    var ar = new Array("3", idOther, onlyAudio);
                    websocket.send(JSON.stringify(ar));
                    pc.createOffer(gotDescription);
                } else
                    pc.createAnswer(gotDescription);
            });
        }
    };

    /**
     * Funzione per la chiusura della chiamata tra due client.
     *
     * @author Marco Schivo
     */
    this.endCall = function() {
        // imposto il mio stato a disponibile
        changeMyState.state = "available";
        document.dispatchEvent(changeMyState);
        // rimuovo lo stream dalla connesione
        pc.removeStream(localStream);
        // blocco il localstream
        localStream.stop();
        pc.createOffer(gotDescription);
        stopTimer();
        stopStat();
        mediator.getCommunicationPPMyVideo().src = "";
        mediator.getCommunicationPPOtherVideo().src = "";
        //aspetto un secondo che pc finisca di comunicare la nuova offerta
        setTimeout(function() {
            pc.close();
            pc = null;
			localStream = null;
        }, 1000);
    };

    /**
     * Verifica la presenza di una eventuale connessione WebSocket aperta con il
     * server
     *
     * @author Riccardo Tresoldi
     */
    // XXX condannato a morte - impiccato al campanile
    this.isPCDefined = function() {
        return (pc != null && pc != undefined);
    };

    /***************************************************************************
     * HANDLER EVENTI
     **************************************************************************/
    /**
     * Gestione dell'evento che deve notificare agli utenti nella rubrica del mio
     * cambio di stato
     * @version 2.0
     * @author Riccardo Tresoldi
     * @param {String} state stringa contenente lo stato da impostare
     */
    function onChangeMyState(state) {
        if ((state == "available") || (state == "occupied") || (state == "offline")) {
            var message = new Array("5", state);
            if (websocket)
                websocket.send(JSON.stringify(message));
            else
                alert("Errore di connessione al server: websocket non esistente!");
        } else {
            alert("Errore: Impostazione stato non conosciuto.\nNon può essere impostato lo stato " + state);
        }
    }

    /**
     * Gesione dell'evento per l'invio di un messaggio di chat
     * @version 2.0
     * @author Riccardo Tresoldi
     * @param {Object} contact il contatto a cui inviare il messaggio
     * @param {String} messageText il testo del messaggio da inviare
     */
    function onSendMessage(contact, messageText) {
        var messageRequest = new Array("7", contact.id, messageText);
        if (websocket)
            websocket.send(JSON.stringify(messageRequest));
        // scateno l'evento per visualizzare il messaggio nel pannello della chat
        appendMessageToChat.user = contact;
        appendMessageToChat.text = messageText;
        appendMessageToChat.IAmSender = true;
        document.dispatchEvent(appendMessageToChat);
    }

    /**
     * Funzione per effettuare la vera e propria chiamata con un contatto
     * @version 2.0
     * @author Riccardo Tresoldi
     * @param {Object} contact il contatto da chiamare
     * @param {Boolean} onlyAudio true se si vuole effettuare una chiamata solo
     * audio
     */
    function onCall(contact, onlyAudio) {
        document.dispatchEvent(showCommunicationPanel);
        thisMonolith.call(true, contact, onlyAudio);
        startRinging.evento = "outcome";
        document.dispatchEvent(startRinging);
    };

    /**
     * Gestione evento chiamata rifiutata
     * @version 2.0
     * @author Riccardo Tresoldi
     */
    function onRejectedCall() {
        // imposto il mio stato a disponibile
        changeMyState.state = "available";
        document.dispatchEvent(changeMyState);
        // fermo la suoneria
        document.dispatchEvent(stopRinging);
        // fermo gli stream video e menate varie
        localStream.stop();
        mediator.getCommunicationPPMyVideo().src = "";
        mediator.getCommunicationPPOtherVideo().src = "";
        // azzero la variabile della chiamata
        pc.close();
        pc = null;
        // mostro il contatto della persona che ha rifiutato la chiamata
        document.dispatchEvent(showContactPanel);
    }

    /**
     * Gestione evento acettare chiamata
     * @version 2.0
     * @author Riccardo Tresoldi
     */
    function onAcceptCall(contact, onlyAudio) {
        document.dispatchEvent(showCommunicationPanel);
        thisMonolith.call(false, contact, onlyAudio);
        for (var i in remoteDescriptionPacket) {
            pc.setRemoteDescription(new RTCSessionDescription(remoteDescriptionPacket[i]));
        }
        for (var i in sdpPacket) {
            pc.addIceCandidate(new RTCIceCandidate(sdpPacket[i]));
        }
    }

    /**
     * Gestione evento rifiutare chiamata
     * @version 2.0
     * @author Riccardo Tresoldi
     */
    function onRejectCall(caller) {
        var message = new Array("6", caller.id);
        if (websocket)
            websocket.send(JSON.stringify(message));
    }

    /***************************************************************************
     * LISTENER DEGLI EVENTI
     **************************************************************************/
    document.addEventListener("changeMyState", function(evt) {
        onChangeMyState(evt.state);
    });
    document.addEventListener("sendMessage", function(evt) {
        onSendMessage(evt.contact, evt.messageText);
    });
    document.addEventListener("call", function(evt) {
        onCall(evt.contact, evt.onlyAudio);
    });
    document.addEventListener("rejectedCall", function(evt) {
        onRejectedCall();
    });
    document.addEventListener("acceptCall", function(evt) {
        onAcceptCall(evt.contact, evt.onlyAudio);
    });
    document.addEventListener("rejectCall", function(evt) {
        onRejectCall(evt.caller);
    });
}