module ("Test di ContactPanelPresenter", {
    setup:
        function() {
			test = new ContactPanelPresenter();								},
	teardown: 
		function(){}
	}
);

test("Test della funzione createPanel()",
    function () {
		var i = 0;
		
		// creo l'elemento 'ContactPanel'
		var element = test.createPanel();
		// estraggo la lista dei figli di questo pannello
		var list = element.childNodes;
		// controllo che abbia esattamente cinque figli
		equal(list.length,5,"il numero di figli dell'elemento restituito e' 5");
		i++;
		
		// controllo che il primo figlio sia un'immagine
		equal(list[0].nodeName, "IMG", "il primo figlio dell'elemento e' un'immagine");
		i++;
		
		// controllo che il secondo figlio sia una lista
		equal(list[1].nodeName, "UL", "il primo figlio dell'elemento e' una lista");
		i++;
		
		// controllo che il terzo figlio sia un buttone
		equal(list[2].nodeName, "BUTTON", "il primo figlio dell'elemento e' una lista");
		i++;
		
		// controllo che il figlio sia un'bottone
		equal(list[3].nodeName, "BUTTON", "il primo figlio dell'elemento e' una lista");
		i++;
		
		
		// estraggo la lista dei dati personali e testo tutti i suoi nodi
		var items = list[1].childNodes;
		equal(items.length, 3, "i dati personali sono 3 elementi");
		i++;
		
		//controllo che il primo elemnto sia name
		var nome = items[0].getAttribute("id");
		equal(nome, "contactName", "l'id del primo elemento della lista e' contactName");
		i++;
		
		//controllo che il secondo elemento sia surname
		var cognome = items[1].getAttribute("id");
		equal(cognome, "contactSurname", "l'id del secondo elemento della lista è contactSurname");
		i++;
		
		//controllo che il terzo elemento sia email
		var email = items[2].getAttribute("id");
		equal(email, "contactEmail", "l'id del terzo elemento della lista è contactEmail");
		i++;
		
		expect(i);
    });

