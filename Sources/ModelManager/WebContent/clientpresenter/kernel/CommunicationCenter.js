/**
 * Classe logica che gestisce la parte della comunicazione lato Client
 *
 * @author Diego Beraldin
 * @author Riccardo Tresoldi
 */
function CommunicationCenter() {
    /**********************************************************
     VARIABILI PUBBLICHE
     ***********************************************************/
    //TODO Contiene i dati della videochiamata
    this.videoComunication
    //E' un array di HTMLTextAreaElement
    this.openChat = new Array();

    /**********************************************************
     VARIABILI PRIVATE
     ***********************************************************/
    //TODO sistemare discorso della servlet in modo che se la prende dal mediator
    var urlServlet = "http://localhost:8080/MyTalk/ChannelServlet";
    //oggetto che contiene i dati dell'utente
    this.my = new Object();
    //dichiaro i due elementi <video>
    var myVideo, otherVideo;
    //dichiaro globale la websocket per lo scambio di dati con la servlet
    var websocket;

    //codice che dovrebbe rendere usabile webRTC da ogni browser che lo supporti DA TESTARE
    /*
    navigator.getUserMedia = navigator.getUserMedia || navigator.webkitGetUserMedia || navigator.mozGetUserMedia || navigator.msGetUserMedia;
    RTCPeerConnection = RTCPeerConnection || webkitRTCPeerConnection || mozRTCPeerConnection || msRTCPeerConnection;
    */

    /**********************************************************
     METODI PRIVATI
     ***********************************************************/
    /**
     * Funzione per formattare i bytes ricevuti.
     *
     * @author Marco Schivo
     */
    function formatBytes(bytes) {
        if (bytes <= 1048576) {
            var kb = bytes / 1024;
            kb = Math.round(kb);
            kb += " KB/s";
            return kb;
        } else {
            var mb = bytes / 1048576;
            var cifre = mb.toString();
            var cifreArray = cifre.split(".");
            if (cifreArray[0].length >= 1) {
                return (cifre.substring(0, 4)) + " MB/s";
            }
            if (cifreArray[0].length >= 2) {
                return (cifre.substring(0, 5)) + " MB/s";
            }
            if (cifreArray[0].length >= 3) {
                return (cifre.substring(0, 6)) + " MB/s";
            }
        }
    }

    /**
     * Funzione per formattare nel formato "hh:mm:ss" il tempo della chiamata.
     *
     * @author Marco Schivo
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
                    //FIXME da correggere il dove visualizzare le stats (possibile interazione con mediator->communicationPP)
                    document.getElementById("byteReceived").value = formatBytes(obj.stat(names[i]));
                }
                if (names[i] == "bytesSent") {
                    //FIXME da correggere il dove visualizzare le stats (possibile interazione con mediator->communicationPP)
                    document.getElementById("byteSent").value = formatBytes(obj.stat(names[i]));
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
        /*TODO Sistemare la funzione in modo che se si disconnette senza
         * rischiesta si riconnetta automaticamente
         */
        websocket = new WebSocket(urlServlet);
        //event handle per gestire l'apertura della socket
        websocket.onopen = function(evt) {
            //creo l'array da passare alla servlet per la connessione e l'invio
            var ar = new Array("1", my.id);
            websocket.send(JSON.stringify(ar));

            //eventuale segnale di avvenuta connessione con la servlet [per
            // esempio spia verde o rossa]
        };
        //event handle per gestire la chiusura della socket
        websocket.onclose = function(evt) {
            //disconnessione dalla servlet
        };
        //event handle per gestire l'arrivo di un messaggio da parte della socket
        websocket.onmessage = function(evt) {
            //split del messaggio ricevuto e estrazione del tipo di messaggio
            var str = evt.data.split("|");
            var type = str[0];
            //controllo che tipo di messaggio ho ricevuto
            /*{ 3 : ottengo id della persona che mi sta chiamando,
             *  2 : quando inizia la chiamata,
             *  5 : cambio stato altri utenti
             *} */
            if (type == "3") {
                idOther = str[1];
            } else if (type == "2") {
                var signal = JSON.parse(str[1]);
                if (pc == null)
                    call(false);
                if ((signal.sdp) == null) {
                    pc.addIceCandidate(new RTCIceCandidate(signal));
                } else {
                    pc.setRemoteDescription(new RTCSessionDescription(signal));
                }
            } else if (type == "5") {
                var idUserChange = JSON.parse(str[1]);
                var statusUserChange = JSON.parse(str[2]);
                //può avere tre stati [available | occupied]
                //TODO modificare classe <li> dell'utente.
            }
        };
        //event handle per gestire gli errori avvenuti della socket
        websocket.onerror = function(evt) {
            //nel caso la servlet restituisse un errore
            alert("È avvenuto un problema nel serve che tuttavia potrebbe non compromettere la chiamata se già avviata.");
        };
    };

    //FUNZIONE connect() ORIGINALE
    /*function connect() {websocket = new
    * WebSocket("ws://localhost:8080/channel/ChannelServlet");websocket.onopen
    * = function(evt) {var id=
    * document.getElementById("id").value;idutente=id;var ar= new Array("1",
    * idutente);websocket.send(JSON.stringify(ar));var text=
    * document.createTextNode("Connesso con id " +
    * idutente);document.getElementById("label").innerHTML='';document.getElementById("label").appendChild(text);};websocket.onclose
    * = function(evt) {var text=
    * document.createTextNode("Disconnesso");document.getElementById("label").innerHTML='';document.getElementById("label").appendChild(text);};websocket.onmessage
    * = function (evt) {var str= evt.data.split("|");var type=
    * str[0];if(type=="3"){idOther= str[1];}else if(type=="2"){var signal=
    * JSON.parse(str[1]);if (pc==null)call(false);if
    * ((signal.sdp)==null){pc.addIceCandidate(new
    * RTCIceCandidate(signal));}else{pc.setRemoteDescription(new
    * RTCSessionDescription(signal));}}};websocket.onerror = function(evt)
    * {alert("ERRORE");};}*/

    /**
     * Funzione per la disconnessione del client dal server.
     *
     * @author Marco Schivo
     */
    this.disconnect = function() {
        //creo array per inviare la rischiesta di disconnessione e lo invio
        var ar = new Array("4", my.id);
        websocket.send(JSON.stringify(ar));
        websocket.close();
    };

    /**
     * Funzione per la vera e propria chiamata attraverso webRTC
     *
     * @author Marco Schivo
     */
    this.call = function(isCaller) {
        //creo l'oggetto di configurazione della RTCPeerConnection con
        // l'indirizzo del server STUN
        var configuration = {
            "iceServers" : [{
                "url" : "stun:stun.l.google.com:19302"
            }]
        };
        //FIXME da togliere il prefisso webkit quando saremo certi che funzioni
        // [RTCPeerConnection = webkitRTCPeerConnection]
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
            otherVideo.src = URL.createObjectURL(evt.stream);
            //avvio il timer della chiamata
            timer = setInterval(function() {
                time++;
                var now = formatTime(time);
                //TODO contattare mediator per modificare lo span del timer
                document.getElementById("timer").value = now;
            }, 1000);
        };

        //quando il remoteStream viene tolto lo eliminio dal mio client
        pc.onremovestream = function() {
            localStream.stop();
            stopTimer();
            stopStat();
            myVideo.src = "";
            otherVideo.src = "";
            pc.close();
            pc = null;
        };

        //prende lo stream video locale, lo visualizza sul corrispetivo <video> e
        // lo invia agli altri peer
        navigator.webkitGetUserMedia({
            "audio" : true,
            "video" : true
        }, function(stream) {
            //TODO sistemare il fatto che accede al myVideo... forse dovrebbe
            // essre il mediator o simile ad avvisare il ComunicationPP
            myVideo.src = URL.createObjectURL(stream);
            localStream = stream;
            pc.addStream(stream);

            if (isCaller == true) {
                //FIXME sistemare l'id dell'altro utente!!!!
                idOther = document.getElementById("idutente").value;
                var ar = new Array("3", idOther);
                websocket.send(JSON.stringify(ar));
                pc.createOffer(gotDescription);
            } else
                pc.createAnswer(gotDescription);
        });

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
                    //TODO gestire l'errore possibile lancio di eccezione
                }
            }
        }, 1000);
    };

    /**
     * Funzione per la chiusura della chiamata tra due client.
     *
     * @author Marco Schivo
     */
    this.endCall = function() {
        pc.removeStream(localStream);
        localStream.stop();
        pc.createOffer(gotDescription);
        stopTimer();
        myVideo.src = "";
        otherVideo.src = "";
        //aspetto un secondo che pc finisca di comunicare la nuova offerta
        setTimeout(function() {
            pc.close();
            pc = null;
        }, 1000);
    };

    //FIXME da sistemare la funzione associata all'event heandler onbeforeunload
    /*window.onbeforeunload = function() {
    if (pc != null) {
    setTimeout(function() {
    endCall();
    }, 3000);
    }
    disconnect();
    }*/
}
