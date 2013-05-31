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
		var name = document.getElementById("MessagePanel").innerHTML;
		equal(name, "01", "id impostato correttamente");
		i++;
		expect(i);
	});