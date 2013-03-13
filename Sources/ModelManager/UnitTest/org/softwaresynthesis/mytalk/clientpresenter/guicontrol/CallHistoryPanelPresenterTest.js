module ("Test di CallHistoryPanelPresenter", {
    setup:
        function() {
			test = new HistoryPanelPresenter();								},
	teardown: 
		function(){}
	}
);

test("Test della funzione createPanel()",
    function () {
		var i = 0;
		
		// creo l'elemento 'CallHistoryPanel'
		var element = test.createPanel();
		// estraggo la lista dei figli di questo pannello
		var list = element.childNodes;
		// controllo che abbia esattamente tre figli
		equal(list.length,3,"il numero di figli dell'elemento restituito e' 3");
		i++;
		
	/*	// controllo che il primo figlio sia la mia immagine personale
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
		i++;*/
		expect(i);
    });

