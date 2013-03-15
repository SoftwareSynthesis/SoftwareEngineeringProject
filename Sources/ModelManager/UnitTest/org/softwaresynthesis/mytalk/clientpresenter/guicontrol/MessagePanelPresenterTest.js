module ("MessagePanelPresenterTest", {
    setup:
        function() {
			communicationcenter = new Object();
			communicationcenter.my = {id:"01"};
			test = new MessagePanelPresenter("MessageManager.php");								},
	teardown: 
		function(){}
	}
);


test("createPanelTest()",
    function () {
		var i = 0;
		// creo l'elemento 'MessagePanel'
		var element = test.createPanel();
		// estraggo la lista dei figli di questo pannello
	var list = element.childNodes;
		// controllo che abbia esattamente due figli
		equal(list.length,2,"il numero di figli dell'elemento restituito e' 2");
		i++;
		
		// controllo che il primo figlio sia il video
		equal(list[0].nodeName, "VIDEO", "il primo figlio dell'elemento e' un video");
		i++;
		
		// controllo che il secondo figlio sia un div
		equal(list[1].nodeName, "DIV", "il primo figlio dell'elemento e' un div");
		i++;
		

	
		// estraggo la lista dei figli di video
		var items = list[0].childNodes;
		equal(items.length, 1, "il video ha un figlio");
		i++;
		//controllo che il figlio di video sia source
		equal(items[0].nodeName, "SOURCE", "il figlio di video e' source ");
		i++;
		
		
		// estraggo la lista dei figli del di div
	var items_uno = list[1].childNodes;
	equal(items_uno.length, 1, "il div ha un figlio");
	i++;
		//controllo che il figlio di div sia ul
	equal(items_uno[0].nodeName, "UL", "il figlio di video e' ul ");
		i++;
		
		
		
		expect(i);
    });
