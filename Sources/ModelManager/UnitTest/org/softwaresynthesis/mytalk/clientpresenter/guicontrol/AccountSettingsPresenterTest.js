/**
 * Verifica di AccountSettingsPresenter
 */
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
				// stub di mediator
				mediator = {
					getView : function(someString) {
						var viewRequest = new XMLHttpRequest();
						viewRequest.open("POST",
								"clientview/AccountSettingsView.html", false);
						viewRequest.send();
						var div = document.createElement("div");
						div.innerHTML = viewRequest.responseText;
						if (document.getElementById("AccountSettingsPanel") == null) {
							document.body.appendChild(div);
							div.style.display = "none";
						}
					}
				};
				// oggetto da testare
				tester = new AccountSettingsPanelPresenter();
			},
			teardown : function() {
			}
		});

/**
 * Verifica il metodo sendUserData verificando che venga rilevata o non venga
 * rilevata la mancanza di cambiamenti nei dati dell'utente.
 */
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

/**
 * Verifica che i dati dell'utente siano visualizzati correttamente.
 */
test(
		"testDisplay()",
		function() {
			var i = 0;

			var event = new CustomEvent("showAccountSettingPanel");
			document.dispatchEvent(event);

			tester.display();

			var name = document.getElementById("name").innerHTML;
			equal(name, "Paolino", "nome impostato correttamente");
			i++;

			var surname = document.getElementById("surname").innerHTML;
			equal(surname, "Paperino", "cognome impostato correttamente");
			i++;

			var picture = document.getElementById("picture");
			equal(
					picture.src,
					"http://localhost/ModelManager/UnitTest/org/softwaresynthesis/mytalk/img/contactImg/Default.png",
					"percorso immagine impostato correttamente");
			i++;

			var button = document.getElementById("changeButton");
			equal(button.nodeName, "BUTTON", "tipo corretto dell'elemento");
			i++;

			expect(i);
		});