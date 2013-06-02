/**
 * Verifica della classe PresenterMediator
 * 
 * @version 2.0
 * @author Stefano Farronato
 */
module("PresenterMediator", { //Da sistemare
	setup : function() {
		// calcola l'indirizzo base
		host = window.location.protocol + "//" + window.location.host
				+ window.location.pathname;
		host = host.substr(0, host.length - 10);
		// stub di mediator
		mediator = {
			getView : function(someString) {
				
				}
			},
		tester = new PresenterMediator();
	},
	teardown : function() {
		
		}
	}
});


test("NamelabelTest()"), function(){ //devo creare i dati mediante AccountSettingPanel?
	var i=0;
/*	
	var user = new Object();
    user.name = "paperino";
    user.surname = "Depaperoni";

*/
	expect(i);
};


test("AddressBookContactTest()"), function(){
	var i=0;
	
	expect(i);
}


test("AddressBookGroupsTest()"), function(){
	var i=0;
	
	expect(i);
}


test("whereContactsTest()"), function(){
	var i=0;
	
	expect(i);
}


test("ContactAlreadyTest()"), function(){
	var i=0;
	
	expect(i);
}


test("ChatTest()"), function(){ //non si sa se esiste ancora (?)
	var i=0;
	
	expect(i);
}


test("CallTest()"), function(){
	var i=0;
	
	expect(i);
}


test("ComunicationPPVideoTest()"), function(){
	var i=0;
	
	expect(i);
}


test("ComunicationPPOtherVideoTest()"), function(){
	var i=0;
	
	expect(i);
}

test("ComunicationStatsTest()"), function(){
	var i=0;
	
	expect(i);
}

test("ComunicationTimerTest()"), function(){
	var i=0;
	
	expect(i);
}

test("RemoveComunicationToolsTest()"), function(){
	var i=0;
	
	expect(i);
}

test("ContactByIdTest()"), function(){
	var i=0;
	
	expect(i);
}

test("IncomeCallTest()"), function(){
	var i=0;
	
	expect(i);
}

test("RingTest()"), function(){
	var i=0;
	
	expect(i);
}

test("stopRingTest()"), function(){
	var i=0;
	
	expect(i);
}
test("PopupTest()"), function(){
	var i=0;
	
	expect(i);
}
test("RemoveAnswerTest()"), function(){
	var i=0;
	
	expect(i);
}
test("viewTest()"), function(){
	var i=0;
	
	expect(i);
}