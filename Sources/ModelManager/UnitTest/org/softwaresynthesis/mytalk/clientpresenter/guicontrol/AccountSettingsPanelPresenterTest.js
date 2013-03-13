module ("Test di AccontSettingsPanelPresenter", {
    setup:
        function() {
			communicationcenter=new Object();
			communicationcenter.my={name:"Maria",surname:"Goretti",email:"margor@email.it",picturePath:"xx.png"};
			test=new AccountSettingsPanelPresenter();
			element=document.createElement("div");				
									},
	teardown: 
		function(){}
	}
);

/* dentro setup le variabili DEVONO esssere GLOBALI*/

test("Test della funzione CreatePanel()",
    function () {
		expect(1);
		var x=test.createPanel(element);
		var list=x.childNodes;
	
		equal(list.length,3,"lunghezza è 3");
	
    });

