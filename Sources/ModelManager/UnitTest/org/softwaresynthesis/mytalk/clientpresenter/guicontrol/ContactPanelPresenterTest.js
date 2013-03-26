module("ContactPanelPresenterTest", {
	setup : function() {
		communicationcenter = new Object();
		tester = new ContactPanelPresenter();
	},
	teardown : function() {
	}
});

test("testCreatePanel()",
		function() {
			var i = 0;

			// creo l'elemento 'ContactPanel'
			var element = tester.createPanel();
			// estraggo la lista dei figli di questo pannello
			var list = element.childNodes;

			equal(list.length, 10,
					"il numero di figli dell'elemento restituito e' corretto");
			i++;

			equal(list[0].nodeName, "DIV", "il primo figlio e' un div");
			i++;

			equal(list[1].nodeName, "IMG",
					"il secondo figlio dell'elemento e' un'immagine");
			i++;

			equal(list[2].nodeName, "UL",
					"il terzo figlio dell'elemento e' una lista");
			i++;

			equal(list[3].nodeName, "BUTTON",
					"il quarto figlio dell'elemento e' un bottone");
			i++;

			equal(list[4].nodeName, "BUTTON",
					"il quinto figlio dell'elemento e' un bottone");
			i++;

			equal(list[5].nodeName, "BUTTON",
					"il sesto figlio dell'elemento e' un bottone");
			i++;

			equal(list[6].nodeName, "BUTTON",
					"il settimo figlio dell'elemento e' un bottone");
			i++;

			equal(list[7].nodeName, "BUTTON",
					"il ottavo figlio dell'elemento e' un bottone");
			i++;

			equal(list[8].nodeName, "BUTTON",
					"il nono figlio dell'elemento e' un bottone");
			i++;

			equal(list[9].nodeName, "DIV",
					"il decimo figlio dell'elemento e' un div");
			i++;

			expect(i);
		});
