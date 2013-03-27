/**
 * Presenter incaricato di gestire il pannello delle impostazioni dell'utente
 * 
 * @constructor
 * @this {AccountSettingsPanelPresenter}
 * @param {String}
 *            url URL della servlet con cui il presenter deve comunicare
 * @author Elena Zecchinato
 * @author Diego Beraldin
 */
function AccountSettingsPanelPresenter(url) {
	/***************************************************************************
	 * VARIABILI PRIVATE
	 **************************************************************************/
	// url della servlet con cui il presenter deve comunicare
	var servlets = new Array();
	getServletURLs();

	/***************************************************************************
	 * METODI PRIVATI
	 **************************************************************************/
	/**
	 * Configura gli indirizzi delle servlet con cui il presenter deve
	 * comunicare per svolgere le proprie operazioni
	 * 
	 * @author Diego Beraldin
	 */
	function getServletURLs() {
		var configurationRequest = new XMLHttpRequest();
		configurationRequest.open("POST", configurationFile, false);
		configurationRequest.send();
		var XMLDocument = configurationRequest.responseXML;
		var baseURL = XMLDocument.getElementsByTagName("baseURL")[0].childNodes[0].data;
		var name = XMLDocument.getElementById("accountsettings").childNodes[0].data;
		servlets.push(baseURL + name);
	}

	/**
	 * Verifica che sia effettivamente cambiato qualcosa rispetto a quanto
	 * contenuto in communicationcenter.my nell'oggetto data che viene passato
	 * come paramtro
	 * 
	 * @param {Object}
	 *            data array contenente i dati raccolti dal form
	 * @author Diego Beraldin
	 */
	function hasSomethingChanged(data) {
		var my = communicationcenter.my;
		// FIXME se cambiano anche domanda, risposta e password occorre
		// aggiungere qui i test
		if (my.name != data.name || my.surname != data.surname
				|| my.picture != data.picture) {
			return true;
		}
		return false;
	}

	/**
	 * Gestisce la pressione del pulsante 'changeButton' che trasforma la lista
	 * di elementi testuali in un form da compilare per modificare i propri
	 * dati. Il form è completo di un pulsante submit che attiva
	 * 'onSubmitChange'
	 * 
	 * @author Diego Beraldin
	 */
	function onChangeButtonPressed() {
		var element = document.getElementById("AccountSettingsPanel");
		var ulData = document.createElement("ul");
		// list item per il nome
		var liName = document.createElement("li");
		var labelName = document.createElement("label");
		labelName.setAttribute("for", "name");
		var inputName = document.createElement("input");
		inputName.setAttribute("id", "name");
		inputName.setAttribute("name", "name");
		inputName.setAttribute("value", communicationcenter.my.name);
		liName.appendChild(labelName);
		liName.appendChild(inputName);

		// list item per il cognome
		var liSurname = document.createElement("li");
		var labelSurname = document.createElement("label");
		labelSurname.setAttribute("for", "surname");
		var inputSurname = document.createElement("input");
		inputSurname.setAttribute("id", "surname");
		inputSurname.setAttribute("name", "surname");
		inputSurname.setAttribute("value", communicationcenter.my.surname);
		liName.appendChild(labelSurname);
		liName.appendChild(inputSurname);

		// list item per l'email
		var liMail = document.createElement("li");
		var labelMail = document.createElement("label");
		labelMail.setAttribute("for", "email");
		var inputMail = document.createElement("input");
		inputMail.setAttribute("id", "email");
		inputMail.setAttribute("name", "email");
		inputMail.setAttribute("value", communicationcenter.my.email);
		liName.appendChild(labelMail);
		liName.appendChild(inputMail);

		// list item per l'immagine
		var liPicture = document.getElementById("li");
		var labelPicture = document.createElement("label");
		labelPicture.setAttribute("for", "picture");
		var inputPicture = document.createElement("input");
		inputPicture.setAttribute("type", "file");
		inputPicture.setAttribute("id", "picture");
		inputPicture.setAttribute("name", "picture");
		inputPicture.setAttribute("value", communicationcenter.my.picturePath);
		liName.appendChild(labelPicture);
		liName.appendChild(inputPicture);

		// FIXME se cambiano anche password, domanda segreta e risposta va
		// aggiunto qui

		// aggiunge tutti i nodi alla lista
		ulData.appendChild(liName);
		ulData.appendChild(liSurname);
		ulData.appendChild(liMail);
		ulData.appendChild(liPicture);

		// pulsante per processare i dati
		var submitButton = document.createElement("input");
		submitButton.setAttribute("type", "submit");
		submitButton.setAttribute("value", "OK");
		// TODO da testare il comportamento quando viene premuto il pulsante
		submitButton.onclick = onSubmitChange;

		// aggiunge il tutto al sottoalbero del DOM
		var formData = document.createElement("form");
		formData.appendChild(ulData);
		formData.appendChild(submitButton);
		element.appendChild(formData);
	}

	/**
	 * Costruisce la stringa di cueri che deve essere spedita alla servlet per
	 * portare a termine la richiesta di cambiamento dei dati personali
	 * 
	 * @param {Object}
	 *            data i dati che sono stati raccolti dal form
	 * @returns {String} la stringa da inviare alla servlet
	 * @author Diego Beraldin
	 */
	function buildQueryString(data) {
		querystring = "";
		for ( var key in data) {
			querystring += key + "=" + encodeURIComponent(data.key) + "&";
		}
		return querystring;
	}

	/**
	 * Gestisce il cambiamento dei dati da parte dell'utente contattando, se
	 * necessario la servlet incaricata di aggiornare il contenuto del database
	 * sul server
	 * 
	 * @author Diego Beraldin
	 */
	function onSubmitChange() {
		// recupera i dati dal form e li memorizza in un oggetto
		var element = document.getElementById("AccountSettingsPanel");
		var data = new Object();
		data.name = document.getElementById("name").getAttribute("value");
		data.surname = document.getElementById("surname").getAttribute("value");
		data.mail = document.getElementById("email").getAttribute("value");
		data.picture = document.getElementById("picture").getAttribute("value");

		// verifica se è cambiato qualcosa e agisce di conseguenza
		if (this.hasSomethingChanged(data)) {
			var request = new XMLHttpRequest();
			request.onreadystatechange = function() {
				if (this.readystate == 4 && this.status == 200) {
					element.innerHTML = "";
					initialize();
				}
			};
			request.open("POST", servletURL, "true");
			request.setRequestHeader("Content-type",
					"application/x-www-form-urlencoded");
			request.send(buildQueryString(data));
		}
	}

	/***************************************************************************
	 * METODI PUBBLICI
	 **************************************************************************/
	/**
	 * Inizializza il pannello costruendone i widget grafici interni e lo
	 * restituisce in modo che possa essere inserito all'interno del pannello
	 * principale
	 * 
	 * @returns {HTMLDivElement} pannello delle impostazioni inizializzato
	 * @author Elena Zecchinato
	 * @author Diego Beraldin
	 */
	this.createPanel = function() {
		var element = document.createElement("div");
		element.setAttribute("id", "AccountSettingsPanel");
		/*
		 * Tutti gli elementi qui mostrati sono recuperati tramite il
		 * riferimento al communicationcenter e la proprietà my in esso
		 * contenuta, dove sono memorizzati i dati relativi all'utente.
		 */
		var nameNode = document.createElement('li');
		nameNode.setAttribute("id", "name");
		nameNode.appendChild(document
				.createTextNode(communicationcenter.my.name));

		var surnameNode = document.createElement('li');
		nameNode.setAttribute("id", "surname");
		surnameNode.appendChild(document
				.createTextNode(communicationcenter.my.surname));

		var mailNode = document.createElement('li');
		mailNode.setAttribute("id", "mail");
		mailNode.appendChild(document
				.createTextNode(communicationcenter.my.email));

		var pictureNode = document.createElement('img');
		pictureNode.setAttribute("id", "picture");
		pictureNode.setAttribute("src", communicationcenter.my.picturePath);

		var changeButton = document.createElement('button');
		changeButton.setAttribute("type", "button");
		changeButton.appendChild(document.createTextNode("Modifica dati"));
		// TODO da testare il funzionamento quando viene premuto il pulsante
		changeButton.onclick = onChangeButtonPressed;

		// FIXME possono cambiare anche password, domanda segreta e risposta!?

		// appende i sottonodi ai nodi principali
		var ulData = document.createElement('ul');
		ulData.style.listStyleType = "none";
		ulData.appendChild(nameNode);
		ulData.appendChild(surnameNode);
		ulData.appendChild(mailNode);

		// appende il sottoalbero al DOM
		element.appendChild(pictureNode);
		element.appendChild(ulData);
		element.appendChild(changeButton);
		return element;
	};
}
