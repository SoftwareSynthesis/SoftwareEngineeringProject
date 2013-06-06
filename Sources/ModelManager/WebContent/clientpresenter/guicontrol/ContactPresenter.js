/**
 * Presenter incaricato di gestire il pannello che visualizza le informazioni
 * del contatto
 *
 * @constructor
 * @this {ContactPanelPresenter}
 * @author Diego Beraldin
 * @author Riccardo tresoldi
 * @author Stefano Farronato
 * @author Elena Zecchinato
 */
function ContactPanelPresenter() {
    /***************************************************************************
     * VARIABILI PRIVATE
     **************************************************************************/
    var currentContact = null;
    var thisPresenter = this;
    var thisPanel;

    /***************************************************************************
     * METODI PRIVATI
     **************************************************************************/
    /** VIEW
     * Funzione che controlla se il contatto passato come parametro è bloccato o
     * no sistemando la vista in modo da lasciare consistente la visualizzazione
     * del contatto con lo stato dello stesso
     *
     * @author Riccardo Tresoldi
     * @param {Object}
     *            contact Contatto da controllare
     */
    function adjustBlockButtonDisplay(contact) {
        if (contact.blocked == true) {
            document.getElementById("displayBlockedDiv").style.display = "block";
            document.getElementById("blockButton").style.display = "none";
            document.getElementById("unlockButton").style.display = "inline";
        } else {
            document.getElementById("displayBlockedDiv").style.display = "none";
            document.getElementById("blockButton").style.display = "inline";
            document.getElementById("unlockButton").style.display = "none";
        }
    }

    /**
     * Tres te l'eri dimenticata questa!
     *
     * @author Diego Beraldin
     * @param {Object}
     * 			contact Contatto da controllare
     */
    function adjustAddressBookButtons(contact) {
        if (mediator.contactAlreadyPresent(contact)) {
            document.getElementById("addToAddressBookButton").style.display = "none";
            document.getElementById("removeFromAddressBookButton").style.display = "inline";
        } else {
            document.getElementById("addToAddressBookButton").style.display = "inline";
            document.getElementById("removeFromAddressBookButton").style.display = "none";
        }
    }

    /** VIEW
     * Funzione che sistema la grafica della visualizzazione del contatto in base
     * allo stato del contatto stesso, visualizzando i giusti pulsanti
     *
     * @author Riccardo Tresoldi
     * @param {Object} contact oggetto che rappresenta il contatto
     */
    function adjustGUIOnContactState(contact) {
        var chatButton = document.getElementById("chatButton");
        var videoCallButton = document.getElementById("videoCallButton");
        var callButton = document.getElementById("callButton");
        var messageButton = document.getElementById("messageButton");
        if (chatButton && videoCallButton && callButton && messageButton) {
            switch(contact.state) {
                case "available":
                    //visualizzo solo pulsanti per chiamata
                    messageButton.style.display = "none";
                    chatButton.style.display = "inline";
                    callButton.style.display = "inline";
                    videoCallButton.style.display = "inline";
                    break;
                default:
                    //visualizzo solo pulsanti per messaggio segreteria
                    messageButton.style.display = "inline";
                    chatButton.style.display = "none";
                    callButton.style.display = "none";
                    videoCallButton.style.display = "none";
                    break;
            }
        }
    }

    /** VIEW
     * Funzione che popola il div groupsDiv che contiene le label dei gruppi
     *
     * @author Riccardo Tresoldi
     * @param {Object}
     *            contact contatto di cui ricavare i gruppi di appartenenza
     */
    function buildGroupsDiv(contact) {
        // estraggo il div da popolare
        var div = document.getElementById("groupsDiv");
        // estraggo la lista dei gruppi a cui appartiene l'utente visualizzato
        var groups = mediator.getGroupsWhereContactsIs(contact);
        // ciclo la lista e creo le "label"
        for (var group in groups) {
            if (groups[group].name != "addrBookEntry") {
                var label = document.createElement("span");
                var img = document.createElement("img");
                img.className = "deleteGroupButton";
                img.src = "img/close.png";
                img.onclick = function() {
                    removeContactFromGroup.contact = contact;
                    removeContactFromGroup.group = groups[group];
                    document.dispatchEvent(removeContactFromGroup);
                    showContactPanel.contact = currentContact;
                    document.dispatchEvent(showContactPanel);
                }
                label.appendChild(document.createTextNode(groups[group].name));
                label.appendChild(img);
                label.className = "groupLabel";
                div.appendChild(label);
                // TODO gestire hover con CSS
            }
        }
    }

    /***************************************************************************
     * METODI PUBBLICI
     **************************************************************************/
    /** VIEW
     * Visualizza un contatto nel pannello principale popolando il contenuto dei
     * <li> del pannello oppure impostando il percorso dell'immagine. NOTA PER I
     * VERIFICATORI: Richiede la presenza di una lista ul con dei list item che
     * abbiano id rispettivametne 'contactName', 'contactSurname',
     * 'contactEmail', un elemento 'img' che abbia id 'contactAvatar' (di cui
     * viene settato l'attributo 'src')
     *
     * @param contact
     *            il concatto le cui informazioni devono essere visualizzates
     * @author Diego Beraldin
     * @author Riccardo Tresoldi
     */
    this.display = function() {
        if (currentContact == null) {
            alert("Contatto da visualizzare non impostato!");
            return;
        }

        thisPanel = document.getElementById("ContactPanel");

        document.getElementById("contactName").appendChild(document.createTextNode("Nome: " + currentContact.name));
        document.getElementById("contactSurname").appendChild(document.createTextNode("Cognome: " + currentContact.surname));
        document.getElementById("contactEmail").appendChild(document.createTextNode("Email: " + currentContact.email));
        document.getElementById("contactAvatar").src = currentContact.picturePath;

        // recupero i bottoni per associargli i metodi
        var addToAddressBookButton = document.getElementById("addToAddressBookButton");
        var blockButton = document.getElementById("blockButton");
        var unlockButton = document.getElementById("unlockButton");
        var chatButton = document.getElementById("chatButton");
        var videoCallButton = document.getElementById("videoCallButton");
        var callButton = document.getElementById("callButton");
        var removeFromAddressBookButton = document.getElementById("removeFromAddressBookButton");
        var messageButton = document.getElementById("messageButton");

        adjustBlockButtonDisplay(currentContact);
        adjustGUIOnContactState(currentContact);
        adjustAddressBookButtons(currentContact);

        // popolo le label dei gruppi al div groupsDiv
        buildGroupsDiv(currentContact);

        // associo gli eventi onClick ai bottoni
        addToAddressBookButton.onclick = function() {
            addContactToAddressBook.contact = currentContact;
            document.dispatchEvent(addContactToAddressBook);
        };

        removeFromAddressBookButton.onclick = function() {
            removeContactFromAddressBook.contact = currentContact;
            document.dispatchEvent(removeContactFromAddressBook);
        };

        blockButton.onclick = function() {
            blockContact.contact = currentContact;
            document.dispatchEvent(blockContact);
            currentContact.blocked = true;
            showContactPanel.contact = currentContact;
            document.dispatchEvent(showContactPanel);
        };

        unlockButton.onclick = function() {
            unlockContact.contact = currentContact;
            document.dispatchEvent(unlockContact);
            currentContact.blocked = false;
            showContactPanel.contact = currentContact;
            document.dispatchEvent(showContactPanel);
        };

        chatButton.onclick = function() {
            mediator.onChatStarted(currentContact);
        };

        videoCallButton.onclick = function() {
            call.contact = currentContact;
            call.onlyAudio = false;
            document.dispatchEvent(call);
        };

        callButton.onclick = function() {
            call.contact = currentContact;
            call.onlyAudio = true;
            document.dispatchEvent(call);
        };

        messageButton.onclick = function() {
            showPhoneCallMessagePanel.reciver = currentContact;
            document.dispatchEvent(showPhoneCallMessagePanel);
        };

        // tolgo la possibilità di aggiungere un utente se già presente
        /*if (mediator.contactAlreadyPresent(currentContact)) {
         document.getElementById("addToAddressBookButton").style.display =
         "none";
         }*/
    };

    /**************************************************************************
     * HANDLER DEGLI EVENTI
     **************************************************************************/
    /** PRESENTER
     * Funzione per gestire l'evento in cui viene visualizzato il pannello del
     * contatto
     * @author Riccardo Tresoldi
     */
    function onShowContactPanel(contact) {
        currentContact = contact;
        mediator.getView('contact');
    }

    /** PRESENTER
     * Funzione per gestire l'evento in cui viene rimosso il pannello del
     * contatto
     * @author Riccardo Tresoldi
     */
    function onRemoveContactPanel() {
        currentContact = null;
    }

    /***************************************************************************
     * LISTENER DEGLI EVENTI
     **************************************************************************/
    document.addEventListener("changeAddressBooksContactState", function(evt) {
        if (currentContact && currentContact.id == evt.idUserChange)
            adjustGUIOnContactState(currentContact);
    });
    document.addEventListener("showContactPanel", function(evt) {
        onShowContactPanel(evt.contact);
    });
    document.addEventListener("removeContactPanel", onRemoveContactPanel);
}