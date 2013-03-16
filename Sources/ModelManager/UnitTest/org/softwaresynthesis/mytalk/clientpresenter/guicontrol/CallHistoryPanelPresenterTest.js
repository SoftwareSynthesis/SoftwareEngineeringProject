module ("CallHistoryPanelPresenterTest", {
    setup:
        function() {
		//oggetto da testare
			tester = new CallHistoryPanelPresenter();	},
	teardown: 
		function(){}
	}
);

test("testCreatePanel()",
    function () {
		var i = 0;
		
		// creo l'elemento 'CallHistoryPanel'
		var element = tester.createPanel();
		// estraggo la lista dei figli di questo pannello
		var list = element.childNodes;
		// controllo che abbia esattamente un figlio
		equal(list.length,1,"il numero di figli dell'elemento restituito e' 1");
		i++;
		
		// controllo che il figlio figlio sia una lista ul
		equal(list[0].nodeName, "UL", "il primo figlio dell'elemento e' una lista");
		i++;
	
		expect(i);
    });

