module ("CommunicationPanelPresenter", {
    setup:
        function() {
			tester = new CommunicationPanelPresenter();
			},
	teardown: 
		function(){}
	}
);

test("testCreatePanel()",
    function () {
		var i = 0;
		
		// creo l'elemento 'CommunicationPanel'
		var ele = tester.createPanel();
		// estraggo la lista dei figli di questo pannello
		var list = ele.childNodes;
		// controllo che abbia esattamente due figli
		equal(list.length,2,"il numero di figli dell'elemento restituito e' 2");
		i++;
		// controllo che il primo figlio sia il div delle call
		equal(list[0].nodeName, "DIV", "il primo figlio dell'elemento e' un div");
		i++;
		
		// controllo che il secondo figlio sia il div della Chat
		equal(list[1].nodeName, "DIV", "il primo figlio dell'elemenento e' una lista");
		i++;
		
		
		var divCall =list[0].getAttribute("id");
		equal(divCall, "divCall", "l'id del div è divCall");
		i++;
		
		var divChat =list[1].getAttribute("id");
		equal(divChat, "divChat", "l'd del div è divChat");
		i++;
		
		expect(i);
    });

