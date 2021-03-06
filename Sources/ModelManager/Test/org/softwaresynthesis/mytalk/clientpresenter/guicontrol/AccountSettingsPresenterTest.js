/**
 * Verifica di AccountSettingsPresenter
 * 
 * @version 2.0
 * @authot Elena Zecchinato
 * @author Diego Beraldin
 */
module(
		"AccountSettingsPresenterTest",
		{
			setup : function() {
				// calcola l'indirizzo di base
				host = window.location.protocol + "//" + window.location.host
						+ window.location.pathname;
				host = host.substr(0, host.length - 10);
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
				var element = document.getElementById("AccountSettingsPanel");
				if (element) {
					document.body.removeChild(element.parentElement);
				}
			}
		});

/**
 * Verifica il metodo sendUserData verificando che venga rilevata o non venga
 * rilevata la mancanza di cambiamenti nei dati dell'utente.
 * 
 * @version 2.0
 * @author Diego Beraldin
 */
test("testSendUserData()", function() {
	var i = 0;
	var data = {
		name : "Paperone",
		surname : "dePaperoni",
		picturePath : "xx.png"
	};

	var bool = tester.sendUserData(data);
	equal(bool, true);
	i++;

	data = communicationcenter.my;
	var bool = tester.sendUserData(data);
	equal(bool, false);
	i++;

	expect(i);
});

/**
 * Verifica che i dati dell'utente siano visualizzati correttamente.
 * 
 * @version 2.0
 * @author Diego Beraldin
 */
test("testDisplay()", function() {
	var i = 0;
	var event = new CustomEvent("showAccountSettingPanel");
	document.dispatchEvent(event);

	tester.display();

	var element = document.getElementById("AccountSettingsPanel");
	equal(element.children.length, 4);
	i++;

	element = element.children[0];
	equal(element.nodeName, "DIV");
	i++;
	equal(element.children.length, 1);
	i++;
	element = element.children[0];
	equal(element.nodeName, "H1");
	i++;
	equal(element.innerHTML.trim(), "DATI PERSONALI");
	i++;
	element = element.parentElement.parentElement.children[1];
	equal(element.nodeName, "IMG");
	i++;
	equal(element.id, "picture");
	i++;
	equal(element.src, host + "img/contactImg/Default.png");
	i++;
	element = element.parentElement.children[2];
	equal(element.nodeName, "UL");
	i++;
	equal(element.children.length, 2);
	i++;
	element = element.children[0];
	equal(element.nodeName, "LI");
	i++;
	equal(element.id, "name");
	i++;
	equal(element.innerHTML.trim(), "Paolino");
	i++;
	element = element.parentElement.children[1];
	equal(element.nodeName, "LI");
	i++;
	equal(element.id, "surname");
	i++;
	equal(element.innerHTML.trim(), "Paperino");
	i++;
	element = element.parentElement.parentElement.children[3];
	equal(element.nodeName, "BUTTON");
	i++;
	equal(element.id, "changeButton");
	i++;
	equal(element.innerHTML.trim(), "Modifica dati");
	i++;

	expect(i);
});

/**
 * Verifica che sia costruito correttamente il form per la modifica dei dati
 * 
 * @version 2.0
 * @author Diego Beraldin
 */
test("testOnChangeButtonPressed()", function() {
	var i = 0;
	var event = new CustomEvent("showAccountSettingPanel");
	document.dispatchEvent(event);
	tester.display();
	var button = document.getElementById("changeButton");
	var event = new MouseEvent("click");
	button.dispatchEvent(event);

	var element = document.getElementById("formData");
	equal(element.nodeName, "FORM");
	i++;
	equal(element.enctype, "multipart/form-data");
	i++;
	equal(element.children.length, 2);
	i++;
	element = element.children[0];
	equal(element.nodeName, "UL");
	i++;
	equal(element.children.length, 3);
	i++;
	element = element.nextSibling;
	equal(element.nodeName, "BUTTON");
	i++;

	expect(i);
});