/**
 * Presenter incaricato di gestire il pannello delle impostazioni dell'utente
 * 
 * @author Elena Zecchinato
 * @author Diego Beraldin
 */
function AccountSettingsPanelPresenter() {
    //elemento controllato da questo presenter
    this.element = document.getElementById("AccountSettingsPanel");

    /**
     * Inizializza il pannello costruendone i widget grafici interni
     * 
     * @author Elena Zecchinato
     */
    this.initialize = function() {
        var ulData = document.createElement('ul');

        var name = document.createElement('li');
        name.innerHTML = communicationcenter.my.name;
        
        var surname = document.createElement('li');
        surname.innerHTML = communicationcenter.my.surname;
        
        var mail = document.createElement('li');
        mail.innerHTML = communicationcenter.my.email;

        var picure = document.createElement('img');
        picture.setAttribute("src", communicationcenter.my.picture);

        var change = document.createElement('button');
        change.setAttribute("type", "button");
        //TODO il bottone 'change' che cosa dovrebbe fare?

        //appende i sottonodi ai nodi principali
        ulData.appendChild(name);
        ulData.appendChild(surname);
        ulData.appendChild(mail);

        //appende il sottoalbero al DOM
        this.element.appendChild(picture);
        this.element.appendChild(ulData);
        this.element.appendChild(change);
    };
}
