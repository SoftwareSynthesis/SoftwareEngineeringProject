module ("ContactPanelPresenterTest", {
  setup:
       function() {
			test = new ContactPanelPresenter();								},
	teardown: 
		function(){}
	});

test("createPanelTest()",
    function () {
		var i = 0;
		
		// creo l'elemento 'AccountSettingsPanel'
		var element = test.createPanel();
		// estraggo la lista dei figli di questo pannello
		var list = element.childNodes;
		// controllo che abbia esattamente quattro figli
		equal(list.length,5,"il numero di figli dell'elemento restituito e' 5");
		i++;
		
		// controllo che il primo figlio sia la mia immagine personale
		equal(list[0].nodeName, "IMG", "il primo figlio dell'elemento e' un'immagine");
		i++;
		
		// controllo che il secondo figlio sia una lista
		equal(list[1].nodeName, "UL", "il secondo figlio dell'elemento e' una lista");
		i++;
	
		// controllo che il terzo elemento sia un bottone
		equal(list[2].nodeName, "BUTTON", "il terzo figlio dell'elemento e' un bottone");
			i++;
			
		// controllo che il quarto elemento sia un bottone
		equal(list[3].nodeName, "BUTTON", "il quarto figlio dell'elemento e' un bottone");
			i++;
		// controllo che il quinto elemento sia un bottone
		equal(list[4].nodeName, "BUTTON", "il quinto figlio dell'elemento e' un bottone");
			i++;
		
	
		expect(i);
    });

