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
	// elemento controllato da questo presenter
	this.element = document.getElementById("ContactPanel");

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
		if (contact.blocked) {
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
		for ( var group in groups) {
			var label = document.createElement("span");
			var img = document.createElement("img");
			img.className = "deleteGroupButton";
			// TODO
			img.src = "";
			label.appendChild(document.createTextNode(groups[group].name));
			label.appendChild(img);
			label.className = "groupLabel";
			div.appendChild(label);
			// gestire hover con CSS
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
		var element = document.createElement("div");
		element.setAttribute("id", "ContactPanel");

		var displayBlockedDiv = document.createElement("div");
		displayBlockedDiv.id = "displayBlockedDiv";
		displayBlockedDiv.appendChild(document
				.createTextNode("Contatto Bloccato"));
		displayBlockedDiv.style.display = "none";

		var name = document.createElement('li');
		name.setAttribute("id", "contactName");

		var surname = document.createElement('li');
		surname.setAttribute("id", "contactSurname");

		var email = document.createElement('li');
		email.setAttribute("id", "contactEmail");

		var avatar = document.createElement('img');
		avatar.setAttribute("id", "contactAvatar");
		avatar.setAttribute("src", "");

		var groupsDiv = document.createElement("div");
		groupsDiv.id = "groupsDiv";

		// pulsante per chiamata audio
		var callButton = document.createElement('button');
		callButton.type = "button";
		callButton.id = "callButton";
		callButton.appendChild(document.createTextNode("Chiama"));

		// pulsante per chiamata audio/video
		var videoCallButton = document.createElement('button');
		videoCallButton.type = "button";
		videoCallButton.id = "videoCallButton";
		videoCallButton.appendChild(document.createTextNode("Video-chiama"));

		// pulsante per chat testuale
		var chatButton = document.createElement('button');
		chatButton.type = "button";
		chatButton.id = "chatButton";
		chatButton.appendChild(document.createTextNode("Avvia Chat Testuale"));

		// pulsante per agiungere utente alla rubrica
		var addToAddressBookButton = document.createElement('button');
		addToAddressBookButton.type = "button";
		addToAddressBookButton.id = "addToAddressBookButton";
		addToAddressBookButton.appendChild(document
				.createTextNode("Aggiungi in Rubrica"));

		// pulsante per bloccare l'utente
		var blockButton = document.createElement('button');
		blockButton.type = "button";
		blockButton.id = "blockButton";
		blockButton.style.display = "none";
		blockButton.appendChild(document.createTextNode("Blocca"));

		// pulsante per sbloccare l'utente
		var unlockButton = document.createElement('button');
		unlockButton.type = "button";
		unlockButton.id = "unlockButton";
		unlockButton.style.display = "none";
		unlockButton.appendChild(document.createTextNode("Sblocca"));

		// appendo i sottonodi alla lista dei dati dell'utente
		var ulData = document.createElement('ul');
		ulData.appendChild(name);
		ulData.appendChild(surname);
		ulData.appendChild(email);

		// apendo il sottoalbero al DOM
		element.appendChild(displayBlockedDiv);
		element.appendChild(avatar);
		element.appendChild(ulData);
		element.appendChild(callButton);
		element.appendChild(videoCallButton);
		element.appendChild(chatButton);
		element.appendChild(addToAddressBookButton);
		element.appendChild(blockButton);
		element.appendChild(unlockButton);
		element.appendChild(groupsDiv);

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
		// FIXME: si può fare con un ciclo, se imposto una classe?
		document.getElementById("contactName").appendChild(
				document.createTextNode(contact.name));
		document.getElementById("contactSurname").appendChild(
				document.createTextNode(contact.surname));
		document.getElementById("contactEmail").appendChild(
				document.createTextNode(contact.email));
		document.getElementById("contactAvatar").src = contact.image;

		// recupero i bottoni per associargli i metodi
		var addToAddressBookButton = document
				.getElementById("addToAddressBookButton");
		var blockButton = document.getElementById("blockButton");
		var unlockButton = document.getElementById("unlockButton");
		var chatButton = document.getElementById("chatButton");
		var videoCallButton = document.getElementById("videoCallButton");
		var callButton = document.getElementById("callButton");

		var self = this;

		adjustBlockButtonDisplay(contact);

		// popolo le label dei gruppi al div groupsDiv
		buildGroupsDiv(contact);

		// associo gli eventi onClick ai bottoni
		addToAddressBookButton.onclick = function() {
			mediator.onContactAdded(contact.id);
		};

		blockButton.onclick = function() {
			mediator.onBlockContact(contact);
		};

		unlockButton.onclick = function() {
			mediator.onUnlockContact(contact);
		};

		chatButton.onclick = function() {
			self.hide();
			mediator.onChatStarted(contact);
		};

		videoCallButton.onclick = function() {
			// TODO inserire il codice per effettuare la chiamata con il
			// contatto
			// selezionato
			mediator.onCall(contact, false);
		};

		callButton.onclick = function() {
			// TODO inserire il codice per effettuare la chiamata con il
			// contatto
			// selezionato
			mediator.onCall(contact, true);
		};

		// tolgo la possibilità di aggiungere un utente se già presente
		if (mediator.contactAlreadyPresent(contact)) {
			document.getElementById("addToAddressBookButton").style.display = "none";
		}
	};

	// TODO fare funzuone che popola groupsDiv
}