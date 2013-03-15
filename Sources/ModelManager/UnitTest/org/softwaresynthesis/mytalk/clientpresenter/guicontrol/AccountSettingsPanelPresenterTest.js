module ("AccontSettingsPanelPresenterTest", {
    setup:
        function() {
			communicationcenter = new Object();
			communicationcenter.my = {name:"Maria", surname:"Goretti", email:"margor@email.it", picturePath:"xx.png"};
			test = new AccountSettingsPanelPresenter();								},
	teardown: 
		function(){}
	}
);

/* dentro setup le variabili DEVONO esssere GLOBALI*/

/*Tale test controlla il buon funzionamento dell'inizializzazione AccountSettingPanel
controlla che l'albero sia stato costruito correttamente e per i vari nodi controlla che il loro contenuto sia stato inserito correttamente*/
test("createPanelTest()",
    function () {
		var i = 0;
		
		// creo l'elemento 'AccountSettingsPanel'
		var element = test.createPanel();
		// estraggo la lista dei figli di questo pannello
		var list = element.childNodes;
		// controllo che abbia esattamente tre figli
		equal(list.length,3,"il numero di figli dell'elemento restituito e' 3");
		i++;
		
		// controllo che il primo figlio sia la mia immagine personale
		equal(list[0].nodeName, "IMG", "il primo figlio dell'elemento e' un'immagine");
		i++;
		
		// controllo che il secondo figlio sia una lista
		equal(list[1].nodeName, "UL", "il primo figlio dell'elemento e' una lista");
		i++;
		
		// controllo che il terzo elemento sia un bottone
		equal(list[2].nodeName, "BUTTON", "il primo figlio dell'elemento e' un bottone");
		i++;
		
		// estraggo la lista dei dati personali e testo tutti i suoi nodi
		var items = list[1].childNodes;
		equal(items.length, 3, "i dati personali sono 4 elementi");
		i++;
		var name = items[0].innerHTML;// estraggo il <li>Maria</li>
		equal(name, "Maria", "il nome è corretto");
		i++;
		var surname = items[1].innerHTML;
		equal(surname, "Goretti", "il cognome è corretto");
		i++;
		var email = items[2].innerHTML;
		equal(email, "margor@email.it", "l'email è corretta");
		i++;
		
		// testo l'immagine
		var picture = list[0].getAttribute("src");
		equal(picture, "xx.png", "l'immagine ha il percorso corretto");
		i++;
		
		// testo il contenuto del bottone
		var text = list[2].innerHTML;
		equal(text, "Modifica dati", "il testo del bottone è corretto");
		i++;
		expect(i);
    });

