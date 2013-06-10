/**
 * Verifica della classe CallHistoryPresenter
 * 
 * @version 2.0
 * @author Diego Beraldin
 */
module(
		"CallHistoryPanelPresenterTest",
		{
			setup : function() {
				// configura il percorso del ControllerManager
				commandURL = "http://localhost/ModelManager/WebContent/Conf/controllerManagerStub.php";
				// stub di mediator
				mediator = {
					getView : function(someString) {
						var viewRequest = new XMLHttpRequest();
						viewRequest.open("POST",
								"clientview/CallHistoryView.html", false);
						viewRequest.send();
						var div = document.createElement("div");
						div.innerHTML = viewRequest.responseText;
						if (document.getElementById("CallHistoryPanel") == null) {
							document.body.appendChild(div);
							div.style.display = "none";
						}
					},

					createNameLabel : function() {
						return "pippo";
					},

					getContactById : function(something) {
						return new Object();
					},
					contactAlreadyPresent : function(contact) {
						if (contact.id !=  10) {
							return true;
						}
						return false;
					}
				};
				// oggetto da testare
				tester = new CallHistoryPanelPresenter();
			},
			teardown : function() {
				var element = document.getElementById("CallHistoryPanel");
				if (element) {
					document.body.removeChild(element.parentElement);
				}
			}
		});

/**
 * Verifica che la lista delle chiamate sia visualizzata correttamente
 * 
 * @version 2.0
 * @author Diego Beraldin
 */
test("testDisplay()",
		function() {
			var i = 0;

			var event = new CustomEvent("showCallHistoryPanel");
			document.dispatchEvent(event);

			tester.display();

			var list = document.getElementById("ulHistory");

			var children = list.childNodes;
			equal(children.length, 3, "numero di figli corretto");
			i++;

			var item = children[0];

			equal(item.nodeName, "LI", "tipo corretto");
			i++;

			equal(item.innerHTML, "pippo Wed May 22 10:42:02 CEST 2013",
					"contenuto corretto");
			i++;
			equal(item.className, "outcome",
					"chiamata in uscita mostrata correttamente");
			i++;

			item = children[1];
			equal(item.nodeName, "LI", "tipo corretto");
			i++;
			equal(item.className, "income",
					"chiamata in uscita mostrata correttamente");
			i++;

			item = children[2];
			equal(item.nodeName, "LI", "tipo corretto");
			i++;
			equal(item.className, "income",
					"chiamata in uscita mostrata correttamente");
			i++;

			expect(i);
		});

//TODO manca da testare quello con la ricerca