/**
 */
function AccountSettingsPanelPresenter(mediator) {
    //FIXME please! Questo ci è stato vietato da ricCARDINo
    this.mediator = mediator;
    //elemento controllato da questo presenter
    this.element = document.getElementById("AccountSettingsPanel");

    /**
     * TODO: da riguardare
     * @author Elena Zecchinato
     */
    this.initialize = function() {
        var ulDati = document.createElement('ul');

        var nome = document.createElement('li');
        var cognome = document.createElement('li');
        var mail = document.createElement('li');

        var immagine = document.createElement('img');
        immagine.setAttribute("src");

        var cambia = document.createElement('button');
        chiama.setAttribute("type", "button");

        //appendo i sottonodi ai nodi principali
        ulDati.appendChild(nome);
        ulDati.appendChild(cognome);
        ulDati.appendChild(mail);

        //apendo il sottoalbero al DOM
        this.element.appendChild(immagine);
        this.element.appendChild(ulDati);
        this.element.appendChild(cambia);
    };
}
