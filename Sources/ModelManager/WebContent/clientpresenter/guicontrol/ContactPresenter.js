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

    /***************************************************************************
     * METODI PRIVATI
     **************************************************************************/
    /**
     * Funzione che controlla se il contatto passato come parametro è bloccato o
     * no sistemando la vista in modo da lasciare consistente la visualizzazione
     * del contatto con lo stato dello stesso
     *
     * @author Riccardo Tresoldi
     * @param {Object}
     *            contact Contatto da controllare
     */
    function adjustBlockButtonDisplay(contact) {
        if (contact.blocked == true)/*FIXME avvisare il puffo del server [dovrebbe funzionare... controllare]*/
        {
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
            var label = document.createElement("span");
            var img = document.createElement("img");
            img.className = "deleteGroupButton";
            img.src = "img/close.png";
            img.onclick = function() {
                mediator.onContactRemovedFromGroup(contact, groups[group]);
            }
            label.appendChild(document.createTextNode(groups[group].name));
            label.appendChild(img);
            label.className = "groupLabel";
            div.appendChild(label);
            // TODO gestire hover con CSS
        }
    }

    /***************************************************************************
     * METODI PUBBLICI
     **************************************************************************/
    /**
     * Inizializza il pannello che mostra le informazioni dei contatti della
     * rubrica, quando ne viene selezionato uno dal pannello della rubrica
     *
     * @returns {HTMLDivElement} pannello 'ContactPanel' costruito
     * @author Elena Zecchinato
     */
    this.createPanel = function() {
        // ottiene un riferiment alla vista
    	var element = mediator.getView("ContactView");
    	
       // configura la vista
       // TODO sbaglio o manca qualcosa?
        return element;
    };

    /**
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
    this.display = function(contact) {
        document.getElementById("contactName").appendChild(document.createTextNode(contact.name));
        document.getElementById("contactSurname").appendChild(document.createTextNode(contact.surname));
        document.getElementById("contactEmail").appendChild(document.createTextNode(contact.email));
        document.getElementById("contactAvatar").src = contact.picturePath;

        // recupero i bottoni per associargli i metodi
        var addToAddressBookButton = document.getElementById("addToAddressBookButton");
        var blockButton = document.getElementById("blockButton");
        var unlockButton = document.getElementById("unlockButton");
        var chatButton = document.getElementById("chatButton");
        var videoCallButton = document.getElementById("videoCallButton");
        var callButton = document.getElementById("callButton");
        var removeFromAddressBookButton = document.getElementById("removeFromAddressBookButton");

        adjustBlockButtonDisplay(contact);

        // popolo le label dei gruppi al div groupsDiv
        buildGroupsDiv(contact);

        // associo gli eventi onClick ai bottoni
        addToAddressBookButton.onclick = function() {
            mediator.onContactAdded(contact);
        };
        
        removeFromAddressBookButton.onclick = function() {
        	mediator.onContactRemoved(contact);
        };

        blockButton.onclick = function() {
            mediator.onBlockContact(contact);
        };

        unlockButton.onclick = function() {
            mediator.onUnlockContact(contact);
        };

        chatButton.onclick = function() {
            mediator.onChatStarted(contact);
        };

        videoCallButton.onclick = function() {
            mediator.onCall(contact, false);
        };

        callButton.onclick = function() {
            mediator.onCall(contact, true);
        };

        // tolgo la possibilità di aggiungere un utente se già presente
        if (mediator.contactAlreadyPresent(contact)) {
            document.getElementById("addToAddressBookButton").style.display = "none";
        }
    };

    // TODO fare funzuone che popola groupsDiv
}