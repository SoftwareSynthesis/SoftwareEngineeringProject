module(
		"AccontSettingsPanelPresenterTest",
		{
			setup : function() {
				communicationcenter = new Object();
				communicationcenter.my = {
					name : "Maria",
					surname : "Goretti",
					email : "margor@email.it",
					picturePath : "xx.png"
				};
				configurationFile = "/ModelManager/WebContent/Conf/servletlocationtest.xml";
				tester = new AccountSettingsPanelPresenter();
			},
			teardown : function() {
			}
		});

/*
 * Tale test controlla il buon funzionamento dell'inizializzazione
 * AccountSettingPanel controlla che l'albero sia stato costruito correttamente
 * e per i vari nodi controlla che il loro contenuto sia stato inserito
 * correttamente
 */
test("testCreatePanel()",
		function() {
			var i = 0;

			var element = tester.createPanel();
			var list = element.childNodes;

			equal(list.length, 3,
					"il numero di figli dell'elemento restituito e' 3");
			i++;

			equal(list[0].nodeName, "IMG",
					"il primo figlio dell'elemento e' un'immagine");
			i++;

			equal(list[1].nodeName, "UL",
					"il primo figlio dell'elemento e' una lista");
			i++;

			equal(list[2].nodeName, "BUTTON",
					"il primo figlio dell'elemento e' un bottone");
			i++;

			var items = list[1].childNodes;
			equal(items.length, 2, "i dati personali sono 2 elementi");
			i++;
			var name = items[0].innerHTML;
			equal(name, "Maria", "il nome è corretto");
			i++;
			var surname = items[1].innerHTML;
			equal(surname, "Goretti", "il cognome è corretto");
			i++;
//			var email = items[2].innerHTML;
//			equal(email, "margor@email.it", "l'email è corretta");
//			i++;

			// testo l'immagine
			var picture = list[0].getAttribute("src");
			equal(picture, "xx.png", "l'immagine ha il percorso corretto");
			i++;

			var text = list[2].innerHTML;
			equal(text, "Modifica dati", "il testo del bottone è corretto");
			i++;

			expect(i);
		});

test("testBuildQueryString()", function() {
	var data = {
		name : "enrico",
		surname : "botti",
		picturePath : "enrybot.png"
	};
	var string = tester.buildQueryString(data);
	equal(string, "name=enrico&surname=botti&picturePath=enrybot.png",
			"stringa di query costruita correttamente");
});

test("testhasSomethingChanged()", function() {
	var i = 0;
	
	var data = {
			name : "Maria",
			surname : "Goretti",
			picturePath : "xx.png"
		};
	
	var bool = tester.hasSomethingChanged(data);
	equal(bool, false, "rilevata correttamente mancanza di cambiamenti");
	i++;
	
	data.name = "Marta";
	bool = tester.hasSomethingChanged(data);
	equal(bool, true, "rilevato correttamente nome cambiato");
	i++;

	data.name = "Maria";
	data.surname = "Rossi";
	bool = tester.hasSomethingChanged(data);
	equal(bool, true, "rilevato correttamente cognome cambiato");
	i++;
	
	data.surname = "Goretti";
	data.picturePath = "yy.png";
	bool = tester.hasSomethingChanged(data);
	equal(bool, true, "rilevata correttamente immagine cambiato");
	i++;
	
	expect(i);
});

test("TestOnChangeButtonPressed", function() {
	// TODO da terminare
});
