/**
 * Presenter incaricato di gestire il pannello che visualizza le informazioni del contatto
 *
 * @constructor
 * @this {ContactPanelPresenter}
 * @author Diego Beraldin
 * @author Riccardo tresoldi
 * @author Stefano Farronato
 * @author Elena Zecchinato
 */
function ContactPanelPresenter() {
    //elemento controllato da questo presenter
    this.element = document.getElementById("ContactPanel");

    /**
     * Inizializza il pannello che mostra le informazioni dei contatti
     * della rubrica, quando ne viene selezionato uno dal pannello
     * della rubrica
     *
     * @returns {HTMLDivElement} pannello 'ContactPanel' costruito
     * @author Elena Zecchinato
     */
    this.createPanel = function() {
        var element = document.createElement("div");
        element.setAttribute("id", "ContactPanel");

        var name = document.createElement('li');
        name.setAttribute("id", "contactName");

        var surname = document.createElement('li');
        surname.setAttribute("id", "contactSurname");

        var email = document.createElement('li');
        email.setAttribute("id", "contactEmail");

        var avatar = document.createElement('img');
        avatar.setAttribute("id", "contactAvatar");
        avatar.setAttribute("src", "");

        //pulsante per chiamata audio
        var callButton = document.createElement('button');
        callButton.type = "button";
        callButton.id = "callButton";
        callButton.appendChild(document.createTextNode("Chiama"));
        callButton.onclick = function() {
            //TODO inserire il codice per effettuare la chiamata con il contatto selezionato
        };

        //pulsante per chiamata audio/video
        var videoCallButton = document.createElement('button');
        videoCallButton.type = "button";
        videoCallButton.id = "videoCallButton";
        videoCallButton.appendChild(document.createTextNode("Video-chiama"));
        videoCallButton.onclick = function() {
            //TODO inserire il codice per effettuare la chiamata con il contatto selezionato
        };

        //pulsante per chat testuale
        var chatButton = document.createElement('button');
        chatButton.type = "button";
        chatButton.id = "chatButton";
        chatButton.appendChild(document.createTextNode("Avvia Chat Testuale"));
        chatButton.onclick = function() {
            //TODO inserire il codice per effettuare la chiamata con il contatto selezionato
        };

        //pulsante per agiungere utente alla rubrica
        var addToAddressBookButton = document.createElement('button');
        addToAddressBookButton.type = "button";
        addToAddressBookButton.id = "addToAddressBookButton";
        addToAddressBookButton.appendChild(document.createTextNode("Aggiungi in Rubrica"));
        addToAddressBookButton.onclick = function() {
            //TODO codice per aggiunere un utente alla rubrica
        };

        //pulsante per bloccare utente
        var blockButton = document.createElement('button');
        blockButton.type = "button";
        blockButton.id = "blockButton";
        blockButton.appendChild(document.createTextNode("Blocca"));
        blockButton.onclick = function() {
            //TODO codice per bloccare utente [se non presente prima agigungerlo alla rubrica]
        };

        //appendo i sottonodi alla lista dei dati dell'utente
        var ulData = document.createElement('ul');
        ulData.appendChild(name);
        ulData.appendChild(surname);
        ulData.appendChild(email);

        //apendo il sottoalbero al DOM
        element.appendChild(avatar);
        element.appendChild(ulData);
        element.appendChild(callButton);
        element.appendChild(videoCallButton);
        element.appendChild(chatButton);
        element.appendChild(addToAddressBookButton);
        element.appendChild(blockButton);

        return element;
    };

    /**
     * Visualizza un contatto nel pannello principale popolando il contenuto
     * dei <li> del pannello oppure impostando il percorso dell'immagine.
     * NOTA PER I VERIFICATORI:
     * Richiede la presenza di una lista ul con dei list item che abbiano
     * id rispettivametne 'contactName', 'contactSurname', 'contactEmail',
     * un elemento 'img' che abbia id 'contactAvatar' (di cui viene settato
     * l'attributo 'src')
     *
     * @param contact il concatto le cui informazioni devono essere visualizzates
     * @author Diego Beraldin
     * @author Riccardo Tresoldi
     */
    this.display = function(contact) {
        //FIXME: si può fare con un ciclo, se imposto una classe?
        document.getElementById("contactName").appendChild(createTextNode(contact.name));
        document.getElementById("contactSurname").appendChild(createTextNode(contact.surname));
        document.getElementById("contactEmail").appendChild(createTextNode(contact.mail));
        document.getElementById("contactAvatar").setAttribute("src", contact.image);

        //sistemare pulsanti

        //tolgo la possibilità di aggiungere un utente se già presente
        if (mediator.contactAlreadyPresent(contact))
            document.getElementById("addToAddressBookButton").style.display = "none";
    };

    /* TODO
     */
}
