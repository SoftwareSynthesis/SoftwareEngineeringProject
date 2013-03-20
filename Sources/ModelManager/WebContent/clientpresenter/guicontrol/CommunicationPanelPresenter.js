/**
 * Presenter incaricato di gestire il pannello delle comunicazioni,
 * siano esse di natura testuale oppure di natura audio o audio/video
 *
 * @author Elena Zecchinato
 * @author Diego Beraldin
 */
function CommunicationPanelPresenter() {
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
        var closeButton = document.createElement('button');
        closeButton.type = "button";
        closeButton.id = "closeButton";
        closeButton.onclick = communicationcenter.endCall;

        //appendo i child al divCall
        divCall.appendChild(myVideo);
        divCall.appendChild(otherVideo);
        divCall.appendChild(closeButton);

        //creo gli elementi per la chat testuale
        //creo l'<ul> per le schede delle chat aperte
        var ulOpenChat = document.createElement('ul');
        ulOpenChat.id = "ulOpenChat";
        //TODO funzione per popolare l'<ul> delle chat con le chat aperte
        
        //creo il div per la visualizzazione della chat selezionata.
        var divContainerChat = document.createElement('div');
        divContainerChat.id = "divContainerChat";
        //TODO funzione per mostrare una chat particolare

        //appendo i child alla divChat
        divChat.appendChild(ulOpenChat);
        divChat.appendChild(divContainerChat);

        //apendo il sottoalbero al DOM
        element.appendChild(divCall);
        element.appendChild(divChat);

        return element;
    };
    
}
