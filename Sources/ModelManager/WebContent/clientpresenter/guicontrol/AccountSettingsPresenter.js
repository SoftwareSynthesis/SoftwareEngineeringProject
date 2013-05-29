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
    var thisPresenter = this;
    var thisPanel;

    /***************************************************************************
     * METODI PUBBLICI
     **************************************************************************/
    /** PRESENTER
     * Verifica che sia effettivamente cambiato qualcosa rispetto a quanto
     * contenuto in communicationcenter.my nell'oggetto data che viene passato
     * come parametro
     *
     * @param {Object}
     *            data array contenente i dati raccolti dal form
     * @returns Boolean
     * @author Diego Beraldin
     */
    function hasSomethingChanged(data) {
        var my = communicationcenter.my;
        if (my.name != data.name || my.surname != data.surname || my.picturePath != data.picturePath) {
            return true;
        }
        return false;
    };
    
    /** PRESENTER 
	 * Verifica se i dati recuperati dal form hanno subito delle
	 * modifiche e, in caso positivo, invia i nuovi dati al server per
	 * aggiornare le informazioni memorizzate nel database.
	 * 
	 * @param data
	 *            i dati dell'utente raccolti dal form
	 * @returns Boolean
	 * @author Diego Beraldin
	 */
	this.sendUserData = function(data) {
		// verifica se è cambiato qualcosa e agisce di conseguenza
		if (hasSomethingChanged(data)) {
			// invia la richiesta al server
			var request = new XMLHttpRequest();
			var formData = new FormData();
			formData.append("operation", "accountSettings");
			if (data.name && data.name.length > 0) {
	            formData.append("name", encodeURIComponent(data.name));
	        }
	        if (data.surname && data.surname.length > 0) {
	            formData.append("surname", encodeURIComponent(data.surname));
	        }
	        if (data.picturePath && (data.picturePath != "")) {
	            formData.append("picturePath", data.picturePath);
	        }
			request.open("POST", commandURL, false);
			request.send(formData);

			// aggiorna il communicationcenter.my
			communicationcenter.my.name = data.name;
			communicationcenter.my.surname = data.surname;
			return JSON.parse(request.responseText);
		}
		return false;
	};

    /** VIEW
     * Gestisce la pressione del pulsante 'changeButton' che trasforma la lista
     * di elementi testuali in un form da compilare per modificare i propri
     * dati. Il form è completo di un pulsante submit che attiva
     * 'onSubmitChange'
     *
     * @author Diego Beraldin
     */
    this.onChangeButtonPressed = function() {
        thisPanel.innerHTML = "";
        var ulData = document.createElement("ul");
        ulData.style.listStyleType = "none";

        // list item per il nome
        var liName = document.createElement("li");
        var labelName = document.createElement("label");
        labelName.setAttribute("for", "name");
        labelName.appendChild(document.createTextNode("Nome:"));
        var inputName = document.createElement("input");
        inputName.id = "name";
        inputName.type = "text";
        inputName.setAttribute("name", "name");
        inputName.setAttribute("value", communicationcenter.my.name);
        liName.appendChild(labelName);
        liName.appendChild(inputName);

        // list item per il cognome
        var liSurname = document.createElement("li");
        var labelSurname = document.createElement("label");
        labelSurname.setAttribute("for", "surname");
        labelSurname.appendChild(document.createTextNode("Cognome:"));
        var inputSurname = document.createElement("input");
        inputSurname.id = "surname";
        inputSurname.type = "text";
        inputSurname.setAttribute("name", "surname");
        inputSurname.setAttribute("value", communicationcenter.my.surname);
        liSurname.appendChild(labelSurname);
        liSurname.appendChild(inputSurname);

        // list item per l'immagine
        var liPicture = document.createElement("li");
        var labelPicture = document.createElement("label");
        labelPicture.setAttribute("for", "picture");
        labelPicture.appendChild(document.createTextNode("Immagine:"));
        var inputPicture = document.createElement("input");
        inputPicture.setAttribute("type", "file");
        inputPicture.setAttribute("id", "picture");
        inputPicture.setAttribute("name", "picture");
        inputPicture.setAttribute("value", communicationcenter.my.picturePath);
        liPicture.appendChild(labelPicture);
        liPicture.appendChild(inputPicture);

        // aggiunge tutti i nodi alla lista
        ulData.appendChild(liName);
        ulData.appendChild(liSurname);
        // ulData.appendChild(liMail);
        ulData.appendChild(liPicture);

        // pulsante per processare i dati
        var submitButton = document.createElement("button");
        submitButton.type = "submit";
        submitButton.appendChild(document.createTextNode("Modifica"));
        submitButton.onclick = function() {
            // recupera i dati dal form e li memorizza in un oggetto
            var data = new Object();
            data.name = document.getElementById("name").value;
            data.surname = document.getElementById("surname").value;
            data.picture = document.getElementById("picture").value;
            thisPresenter.sendUserData(data);
    		document.dispatchEvent(showAccountSettingPanel);            
        };

        // aggiunge il tutto al sottoalbero del DOM
        var formData = document.createElement("form");
        formData.setAttribute("name", "formData");
        formData.setAttribute("action", "");
        formData.setAttibute("method", "POST");
        formData.setAttribute("accept-charset", "utf-8");
        formData.setAttribute("enctype", "multipart/form-data");
        formData.appendChild(ulData);
        formData.appendChild(submitButton);
        thisPanel.appendChild(formData);
    };

    /** VIEW
     * Inizializza il pannello costruendone i widget grafici interni e lo
	 * restituisce in modo che possa essere inserito all'interno del pannello
	 * principale
	 * 
	 * @author Elena Zecchinato
	 * @author Diego Beraldin
	 */
    this.display = function() {
        // salvo un riferimento alla vista
        var thisPanel = document.getElementById("AccountSettingsPanel");
        // configura la vista
        document.getElementById("name").appendChild(document.createTextNode(communicationcenter.my.name));
        document.getElementById("surname").appendChild(document.createTextNode(communicationcenter.my.surname));
        document.getElementById("picture").src = communicationcenter.my.picturePath;
        document.getElementById("changeButton").onclick = function() {
            thisPresenter.onChangeButtonPressed();
        };
    };

    /***************************************************************************
     * HANDLER EVENTI
     **************************************************************************/
    /** PRESENTER
     * Funzione per gestire l'evento in cui viene visualizzato il pannello delle
     * impostazioni dell'account
     * @version 2.0
     * @author Riccardo Tresoldi
     */
    function onShowAccountSettingPanel() {
        mediator.getView('accountSettings');
    }

    /***************************************************************************
     * LISTENER DEGLI EVENTI
     **************************************************************************/
    document.addEventListener("showAccountSettingPanel", onShowAccountSettingPanel);
}