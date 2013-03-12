module ("Descrizione del modulo", {
    setup:
        function() {
					 test=new AccountSettingsPanelPresenter();
					}
teardown: function(){}
   
});


test("Test della funzione()",
    function () {
	var element=document.createElement("div");
	var x=test.createPanel(element);
	var list=x.childNodes;
	
	equal(list.length,3);
	
	
    });

