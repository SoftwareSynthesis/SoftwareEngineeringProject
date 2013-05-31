/**
 * Verifica della classe CallHistoryPresenter
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
					
					createNameLabel: function() {
						return "pippo";
					},
					
					getContactById: function(something) {
						return new Object();
					}
				};
				// oggetto da testare
				tester = new CallHistoryPanelPresenter();
			},
			teardown : function() {
			}
		});

/**
 * Verifica che la lista delle chiamate sia visualizzata correttamente
 */
test("testDisplay()", function() {
	var i = 0;
	
	var event = new CustomEvent("showCallHistoryPanel");
	document.dispatchEvent(event);
	
	tester.display();
	
	var list = document.getElementById("ulHistory");
	
	var children= list.childNodes;
	equal(children.length, 3, "numero di figli corretto");
	i++;
	
	var item = children[0];
	
	equal(item.nodeName, "LI", "tipo corretto");
	i++;
	
	equal(item.innerHTML, "pippo Wed May 22 10:42:02 CEST 2013", "contenuto corretto");
	i++;
	equal(item.className, "outcome", "chiamata in uscita mostrata correttamente");
	i++;
	
	item = children[1];
	equal(item.nodeName, "LI", "tipo corretto");
	i++;
	equal(item.className, "income", "chiamata in uscita mostrata correttamente");
	i++;
	
	item = children[2];
	equal(item.nodeName, "LI", "tipo corretto");
	i++;
	equal(item.className, "income", "chiamata in uscita mostrata correttamente");
	i++;
	
	expect(i);
});