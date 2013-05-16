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
     * METODI PRIVATI
     **************************************************************************/
    /**
     * Funzione per gestire l'evento in cui viene visualizzato il pannello delle
     * impostazioni dell'account
     * @author Riccardo Tresoldi
     */
    function onShowAccountSettingPanel() {
        mediator.getView('accountSettings');
    }

    /***************************************************************************
     * METODI PUBBLICI
     **************************************************************************/
    /**
     * Verifica che sia effettivamente cambiato qualcosa rispetto a quanto
     * contenuto in communicationcenter.my nell'oggetto data che viene passato
     * come parametro
     *
     * @param {Object}
     *            data array contenente i dati raccolti dal form
     * @author Diego Beraldin
     */
    this.hasSomethingChanged = function(data) {
        var my = communicationcenter.my;
        // FIXME se cambiano anche domanda, risposta e password occorre
        // aggiungere qui i test
        if (my.name != data.name || my.surname != data.surname || my.picturePath != data.picturePath) {
            return true;
        }
        return false;
    };

    /**
     * Costruisce la stringa di query che deve essere inviata al server per la
     * modifica dei dati personali
     *
     * @param {Object}
     *            data oggetto che contiene nome, cognome e nuova immagine
     * @returns {String} la stringa che deve essere inviata alla servlet
     * @author Diego Beraldin
     */
    this.buildQueryString = function(data) {
        querystring = "";
        if (data.name && data.name.length) {
            querystring += "name=" + data.name;
        }
        if (data.surname && data.surname.length) {
            querystring += "&surname=" + data.surname;
        }
        if (data.picturePath && data.picturePath.length) {
            querystring += "&picturePath=" + data.picturePath;
        }
        return querystring;
    };

    /**
     * Gestisce la pressione del pulsante 'changeButton' che trasforma la lista
     * di elementi testuali in un form da compilare per modificare i propri
     * dati. Il form è completo di un pulsante submit che attiva
     * 'onSubmitChange'
     *
     * @author Diego Beraldin
     */
    this.onChangeButtonPressed = function() {
        var element = document.getElementById("AccountSettingsPanel");
        element.innerHTML = "";
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

        // FIXME se cambiano anche password, domanda segreta e risposta va
        // aggiunto qui

        // aggiunge tutti i nodi alla lista
        ulData.appendChild(liName);
        ulData.appendChild(liSurname);
        // ulData.appendChild(liMail);
        ulData.appendChild(liPicture);

        // pulsante per processare i dati
        var submitButton = document.createElement("button");
        submitButton.type = "submit";
        submitButton.appendChild(document.createTextNode("Modifica"));
        var self = this;
        submitButton.onclick = function() {
            // recupera i dati dal form e li memorizza in un oggetto
            var data = new Object();
            data.name = document.getElementById("name").getAttribute("value");
            data.surname = document.getElementById("surname").getAttribute("value");
            // data.mail =
            // document.getElementById("email").getAttribute("value");
            data.picture = document.getElementById("picture").getAttribute("value");

            // verifica se è cambiato qualcosa e agisce di conseguenza
            if (self.hasSomethingChanged(data)) {
                var request = new XMLHttpRequest();
                request.open("POST", commandURL, false);
                request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
                request.send("operation=accountSettings&" + self.buildQueryString(data));
                // FIXME aggiornare il communicationcenter.my
            }
            mediator.displayAccountSettingsPanel();
        };

        // aggiunge il tutto al sottoalbero del DOM
        var formData = document.createElement("form");
        formData.appendChild(ulData);
        formData.appendChild(submitButton);
        element.appendChild(formData);
    };

    /** VIEW
     * Inizializza il pannello costruendone i widget grafici interni e lo
     * restituisce in modo che possa essere inserito all'interno del pannello
     * principale
     *
     * @author Elena Zecchinato
     * @author Diego Beraldin
     */
    this.createPanel = function() {
        // salvo un riferimento alla vista
        var thisPanel = document.getElementById("AccountSettingsPanel");

        //XXX ????
        // configura la vista
        var nameNode = document.evaluate("//node()[@id='name']", element, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;
        nameNode.appendChild(document.createTextNode(communicationcenter.my.name));

        var surnameNode = document.evaluate("//node()[@id='surname']", element, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;
        surnameNode.appendChild(document.createTextNode(communicationcenter.my.surname));

        var pictureNode = document.evaluate("//node()[@id='picture']", element, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;
        pictureNode.src = communicationcenter.my.picturePath;

        var changeButton = document.evaluate("//node()[@id='changeButton']", element, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;
        changeButton.onclick = function() {
            self.onChangeButtonPressed();
        };

        // FIXME possono cambiare anche password, domanda segreta e risposta!?
    };

    /***************************************************************************
     * LISTNER DEGLI EVENTI
     **************************************************************************/
    document.addEventListener("showAccountSettingPanel", onShowAccountSettingPanel);
}