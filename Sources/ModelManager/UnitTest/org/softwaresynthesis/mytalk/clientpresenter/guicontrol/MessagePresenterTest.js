module(
		"MessagePanelPresenterTest",
		{
			setup : function() {
					// stub di communicationcenter
				communicationcenter = new Object();
				communicationcenter.my = { id:"01"};
				commandURL = "http://localhost:8888/ModelManager/WebContent/Conf/controllerManagerStub.php";
				// stub di mediator
				mediator = {
					getView : function(someString) {
						var viewRequest = new XMLHttpRequest();
						viewRequest.open("POST",
								"clientview/MessageView.html", false);
						viewRequest.send();
						var div = document.createElement("div");
						div.innerHTML = viewRequest.responseText;
						if (document.getElementById("MessagePanel") == null) {
							document.body.appendChild(div);
							div.style.display = "none";
						}
					},
					
					getContactById: function(someString){
						return "01";
					},
					createNameLabel: function(someString){
						return "pippo";
					}
				};
				// oggetto da testare
				tester = new MessagePanelPresenter();
			},
			teardown : function() {
			}
		});
		
test("testDisplay()",
	function() {
		var i = 0;
		var event = new CustomEvent("showMessagePanel");
		document.dispatchEvent(event);
		tester.display();
		var name = document.getElementById("MessagePanel");
		equal(name.childNodes.length, 7 , "il numero di figli e' corretto");
		i++;
		var list = name.childNodes;
		equal(name.nodeName,"DIV" , "id impostato correttamente");
		i++;
		equal(list[1].nodeName,"DIV","il secondo figlio e' un campo div");
		i++;
		equal(list[2].nodeName,"#text","il terzo figlio e' un campo testuale");
		i++;
		equal(list[3].nodeName,"VIDEO","il quarto figlio e' il campo video");
		i++;
		equal(list[4].nodeName,"#text","il quinto figlio e' un campo testule");
		i++;
		equal(list[5].nodeName,"DIV","il sesto figlio e' un campo DIV");
		i++;
		equal(list[6].nodeName,"#text","il settimo figlio e' un campo testule");
		i++;
		var items = list[3].childNodes;
		equal(items.length, 3, "il video ha un figlio");
		i++;
		equal(items[0].length, 3, "figlio correttamente inizializzato");
		i++;
		equal(items[2].length, 2, "figlio correttamente inizializzato");
		i++;
		expect(i);
	});