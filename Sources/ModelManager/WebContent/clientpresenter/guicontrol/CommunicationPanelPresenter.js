/**
 * Presenter incaricato di gestire il pannello delle comunicazioni,
 * siano esse di natura testuale oppure di natura audio o audio/video
 *
 * @author Elena Zecchinato
 * @author Diego Beraldin
 */
function CommunicationPanelPresenter(mediator) {
    /**********************************************************
     METODI PUBBLICI
     ***********************************************************/
    /**
     * Inizializza il pannello costruendone i widget grafici interni e lo
     * restituisce in modo che possa essere inserito all'interno del pannello principale
     *
     * @returns {HTMLDivElement} il 'CommunicationPanel'
     * @author Elena Zecchinato
     * @author Riccardo Tresoldi
     */
    this.createPanel = function() {
        var element = document.createElement("div");
        element.setAttribute("id", "CommunicationPanel");

        //azzero il div
        element.innerHTML = "";

        //creo div contenente la chiamata vera e propria
        var divCall = document.createElement('div');
        divCall.id = "divCall";
        //creo div contenente le chat testuali
        var divChat = document.createElement('div');
        divChat.id = "divChat";

        //creo gli elementi video per la videochat
        var myVideo = document.createElement('video');
        myVideo.id = "myVideo";
        myVideo.setAttribute("autoplay", "autoplay");
        var otherVideo = document.createElement('video');
        otherVideo.id = "otherVideo";
        otherVideo.setAttribute("autoplay", "autoplay");
        //creo i bottoni per per la gestione della chiamata
        var closeButton = document.createElement('input');
        closeButton.type = "button";
        closeButton.id = "closeButton";
        var muteButton = document.createElement('input');
        muteButton.type = "button";
        muteButton.id = "muteButton";
        closeButton.onclick = function() {
            //TODO creare la funzione per la chiusura della chiamata
        };
        muteButton.onclick = function() {
            //TODO creo la funzione per mettere in mute e togliere dal mute la chiamata
        };

        //appendo i child al divCall
        divCall.appendChild(myVideo);
        divCall.appendChild(otherVideo);
        divCall.appendChild(closeButton);
        divCall.appendChild(muteButton);
        
        //creo gli elementi per la chat testuale
        //TODO decidere come strutturare la chat testuale

        //apendo il sottoalbero al DOM
        element.appendChild(divCall);
        element.appendChild(divChat);

        return element;
    };
}
