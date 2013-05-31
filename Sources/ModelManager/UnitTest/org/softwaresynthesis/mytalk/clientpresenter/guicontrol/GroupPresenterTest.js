module("GroupPresenter", {
	setup : function() {
	
	
		
	mediator = {
	
		getAddressBookContacts: function(){ 
			return {
				0 : {
					name : "Laura",
					surname : "Pausini",
					email : "laupau@gmail.com",
					id : "0",
					picturePath : "xx.png",
					state : "offline",
					blocked : true},
					
				1 : {
					name : "Flavia",
					surname : "Bacco",
					email : "flaba@gmail.com",
					id : "1",
					picturePath : "zz.png",
					state : "offline",
					blocked : false},
					
				2 : {
					name : "Antonio",
					surname : "Rossi",
					email : "antros@gmail.com",
					id : "2",
					picturePath : "yy.png",
					state : "offline",
					blocked : false}
	}; 
	
	},
	
	getAddressBookGroups: function(){
	
	return {
	
	1:	{name : "amici",
		id : "1",
		contacts : [1]},
		
	2:	{name : "famiglia",
		id : "2",
		contacts : [1]}
		
	};
},


	getView : function(someString) {
		var viewRequest = new XMLHttpRequest();
		viewRequest.open("POST",
				"clientview/GroupView.html", false);
		viewRequest.send();
		var div = document.createElement("div");
		div.innerHTML = viewRequest.responseText;
		if (document.getElementById("GroupPanel") == null) {
			document.body.appendChild(div);
			div.style.display = "none";
		}
	},
	
	
	
	createNameLabel: function(){return "pippo";}

	

}
	
	tester = new GroupPanelPresenter();	
	},
	teardown : function() {
	}
});





	test("testSelectCandidates()", function() {

	var event= new CustomEvent("showGroupPanel");
	document.dispatchEvent(event);
	tester.display();

	
	
	var i = 0;
	
	var amici= mediator.getAddressBookGroups()[1];
	var prova=tester.selectCandidates(amici);
	
	
	equal(Object.keys(prova).length,2,"Gli utenti restituiti che quindi non fanno parte del gruppo sono due.");
	i++;
	
	equal(prova["0"].id,0,"Il primo utente non appartenente al gruppo ha id=0");
	i++;
	equal(prova["0"].name,"Laura","Il primo utente non appartenente al gruppo ha nome = Laura");
	i++;
	equal(prova["0"].surname,"Pausini","Il primo utente non appartenente al gruppo ha cognome = Pausini");
	i++;
	equal(prova["0"].state,"offline","Il primo utente non appartenente al gruppo ha stato = offline");
	i++;
	equal(prova["0"].blocked,true,"Il primo utente non appartenente al gruppo è bloccato");
	i++;
	equal(prova["0"].picturePath,"xx.png","Il primo utente non appartenente al gruppo ha immagine = xx.png");
	i++;
	

	
	
	equal(prova["2"].id,2,"Il secondo utente non appartenente al gruppo ha id=2");
	i++;
	equal(prova["2"].name,"Antonio","Il secondo utente non appartenente al gruppo ha nome = Antonio");
	i++;
	equal(prova["2"].surname,"Rossi","Il secondo utente non appartenente al gruppo ha cognome = Rossi");
	i++;
	equal(prova["2"].state,"offline","Il secondo utente non appartenente al gruppo ha stato = offline");
	i++;
	equal(prova["2"].blocked,false,"Il secondo utente non appartenente al gruppo non è bloccato");
	i++;
	equal(prova["2"].picturePath,"yy.png","Il secondo utente non appartenente al gruppo ha immagine = yy.png");
	i++;
	
	
	expect(i);
});








test("test()", function() {

	
	var i = 0;
	
	expect(i);
});
	






	
	test("testDisplay()", function() {

	var event= new CustomEvent("showGroupPanel");
	document.dispatchEvent(event);
	
	var i = 0;
	
	var prova=tester.display();
	
	equal(prova,"","non so");
	i++;
	
	expect(i);
});
	
	
	