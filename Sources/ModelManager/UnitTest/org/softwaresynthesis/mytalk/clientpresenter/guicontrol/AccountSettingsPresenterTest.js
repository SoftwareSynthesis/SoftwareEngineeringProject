module(
		"AccountSettingsPresenterTest",
		{
			setup : function() {
				// stub di communicationcenter
				communicationcenter = new Object();
				communicationcenter.my = {
					name : "Paolino",
					surname : "Paperino",
					email : "indirizzo5@dominio.it",
					picturePath : "img/contactImg/Default.png"
				};
				// configura il percorso del ControllerManager
				commandURL = "http://localhost/ModelManager/WebContent/Conf/controllerManagerStub.php";
				// oggetto da testare
				tester = new AccountSettingsPanelPresenter();
			},
			teardown : function() {
			}
		});
		
	test("testSendUserData()", function() {
	var i = 0;
	
	var data = {
			name : "Paperone",
			surname : "dePaperoni",
			picturePath : "xx.png"
		};
	
	var bool = tester.sendUserData(data);
	equal(bool, true, "operazione andata a buon fine");
	i++;
	
	data = communicationcenter.my;
	var bool = tester.sendUserData(data);
	equal(bool, false, "rilevato correttamente che non e' cambiato nulla");
	i++;
	
	expect(i);
});
		
		
/*
 * Tale test controlla il buon funzionamento dell'inizializzazione
 * AccountSettingPanel controlla che l'albero sia stato costruito correttamente
 * e per i vari nodi controlla che il loro contenuto sia stato inserito
 * correttamente
 */
 
 
 /*//COMMENTO PERCHE SONO MORTI I CREATE PANEL
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
*/


//è MORTO ANCHE QUESTO
/*
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


*/



/*
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
*/

/*
test("testOnChangeButtonPressed", function() {
	var i = 0;
	//stub di interfaccia grafica
	var element = document.createElement("div");
	element.id = "AccountSettingsPanel";
	document.body.appendChild(element);
	
	tester.onChangeButtonPressed();
	var form = element.childNodes[0];
	
	equal(form.nodeName, "FORM", "elemento form aggiunto correttamente");
	i++;
	
	var ul = form.childNodes[0];
	equal(ul.nodeName, "UL", "lista dei contatti aggiunta correttamente");
	i++;
	
	var list = ul.childNodes;
	equal(list.length, 3, "i dati che possono essere modificati sono tre");
	i++;
	
	var liName = list[0];
	var liSurname = list[1];
	var liPicture = list[2];
	
	var label = liName.childNodes[0];
	var input = liName.childNodes[1];
	
	equal(liName.childNodes.length, 2, "numero di figli corretto");
	i++;
	equal(input.nodeName, "INPUT", "tipo dell'elemento corretto");
	i++;
	equal(label.getAttribute("for"), input.id, "label impostata correttamente");
	i++;
	equal(label.textContent, "Nome:", "contenuto della label impostato correttamente");
	i++;
	equal(input.value, communicationcenter.my.name, "valore corretto dell'input");
	i++;
	
	
	label = liSurname.childNodes[0];
	input = liSurname.childNodes[1];
	
	equal(liSurname.childNodes.length, 2, "numero di figli corretto");
	i++;
	equal(input.nodeName, "INPUT", "tipo dell'elemento corretto");
	i++;
	equal(label.getAttribute("for"), input.id, "label impostata correttamente");
	i++;
	equal(label.textContent, "Cognome:", "contenuto della label impostato correttamente");
	i++;
	equal(input.value, communicationcenter.my.surname, "valore corretto dell'input");
	i++;
	
	label = liPicture.childNodes[0];
	input = liPicture.childNodes[1];
	
	equal(liPicture.childNodes.length, 2, "numero di figli corretto");
	i++;
	equal(input.nodeName, "INPUT", "tipo dell'elemento corretto");
	i++;
	equal(label.getAttribute("for"), input.id, "label impostata correttamente");
	i++;
	equal(label.textContent, "Immagine:", "contenuto della label impostato correttamente");
	i++;
	
	var button = form.childNodes[1];
	equal(button.nodeName, "BUTTON", "il pulsante ha il tipo corretto");
	i++;
	
	document.body.removeChild(element);
	
	
	
	expect(i);
});

test("testDisplay", function() {
	var i = 0;
	//stub di interfaccia grafica
	document.dispatchEvent(showAccountSettingPanel);
	
	
	tester.display();
	
	expect(i);
});

*/





