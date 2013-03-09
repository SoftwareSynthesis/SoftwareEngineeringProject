/**
 * Presenter incaricato di gestire il pannello delle impostazioni dell'utente
 * 
 * @constructor
 * @this {AccountSettingsPanelPresenter}
 * @author Elena Zecchinato
 * @author Diego Beraldin
 */
function AccountSettingsPanelPresenter() {
/**********************************************************
                      VARIABILI PRIVATE
***********************************************************/
    //elemento controllato da questo presenter
    var element = document.getElementById("AccountSettingsPanel");
    //url della servlet con cui il presenter deve comunicare
    var servletURL = "http://localhost:8080/AccountManager";

/**********************************************************
                      METODI PRIVATI
***********************************************************/
    /**
     * Verifica che sia effettivamente cambiato qualcosa rispetto a quanto contenuto
     * in communicationcenter.my nell'oggetto data che viene passato come paramtro
     * 
     * @author Diego Beraldin
     */
    function hasSomethingChanged(data) {
    	var my = communicationcenter.my;
    	if (my.name != data.name || my.surname != data.surname || my.picture != data.picture) {
    		return true;
    	}
    	return false;
    }
    
    /**
     * Gestisce la pressione del pulsante 'changeButton' che trasforma la lista di
     * elementi testuali in un form da compilare per modificare i propri dati.
     * Il form è completo di un pulsante submit che attiva 'onSubmitChange'
     * 
     * @author Diego Beraldin
     */
    function onChangeButtonPressed() {
    	//TODO da terminare!
    }
    
    /**
     * Gestisce il cambiamento dei dati da parte dell'utente contattando, se necessario
     * la servlet incaricata di aggiornare il contenuto del database sul server
     * 
     * @author Diego Beraldin
     */
    function onSubmitChange() {
    	// recupera i dati dal form e li memorizza in un oggetto
    	var data = new Object();
    	data.name = document.getElementById("name").innerHTML;
    	data.surname = document.getElementById("surname").innerHTML;
    	data.mail = document.getElementById("mail").innerHTML;
    	// cosa fare con l'immagine del profilo
    	data.picture = "";
    	
    	// verifica se è cambiato qualcosa e agisce di conseguenza
    	if (this.hasSomethingChanged(data)) {
    		//TODO contattare la servlet con una richiesta AJAX
    	}
    }
    
/**********************************************************
                      METODI PUBBLICI
***********************************************************/
    /**
     * Inizializza il pannello costruendone i widget grafici interni
     * 
     * @author Elena Zecchinato
     */
    this.initialize = function() {  
    	element.style.display = "block";
        /* Tutti gli elementi qui mostrati sono recuperati tramite
         * il riferimento al communicationcenter e la proprietà my in
         * esso contenuta, dove sono memorizzati i dati relativi all'utente.
         */
        var nameNode = document.createElement('li');
        nameNode.setAttribute("id", "name");
        nameNode.innerHTML = communicationcenter.my.name;
        
        var surnameNode = document.createElement('li');
        nameNode.setAttribute("id", "surname");
        surnameNode.innerHTML = communicationcenter.my.surname;
        
        var mailNode = document.createElement('li');
        mailNode.setAttribute("id", "mail");
        mailNode.innerHTML = communicationcenter.my.mail;

        var pictureNode = document.createElement('img');
        pictureNode.setAttribute("id", "picture");
        pictureNode.setAttribute("src", communicationcenter.my.picturePath);

        var changeButton = document.createElement('button');
        changeButton.setAttribute("type", "button");
        changeButton.innerHTML = "Modifica dati";
        changeButton.onclick = onChangeButtonPressed;
        
        //appende i sottonodi ai nodi principali
        var ulData = document.createElement('ul');
        ulData.style.listStyleType = "none";
        ulData.appendChild(nameNode);
        ulData.appendChild(surnameNode);
        ulData.appendChild(mailNode);

        //appende il sottoalbero al DOM
        element.appendChild(pictureNode);
        element.appendChild(ulData);
        element.appendChild(changeButton);
    };
    
    /**
     * Rende invisibile il pannello delle impostazioni
     * 
     * @author Diego Beraldin
     */
    this.hide = function() {
    	element.style.display = "none";
    };
}
