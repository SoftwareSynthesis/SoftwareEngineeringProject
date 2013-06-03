/**
 * 
 */
module("PresenterMediator", {
	setup : function() {
		// stub di communicationcenter
		communicationcenter = {};
		// brutti eventi cattivi (globali)
		showCommunicationPanel = new CustomEvent("showCommunicationPanel");
		// oggetto da testare
		mediator = new PresenterMediator();
	},
	teardown : function() {
	}
});

// dati di test
var video = document.createElement("video");
var contacts = {
	1 : {
		id : 1,
		name : "Paolino",
		surname : "Paperino",
		email : "indirizzo5@dominio.it",
		blocked : false,
		state : "available",
		picturePath : "img/contactImg/Default.png"
	},
	2 : {
		id : 2,
		name : "Gastone",
		surname : "Paperone",
		email : "indirizzo4@dominio.it",
		blocked : false,
		state : "offline",
		picturePath : "img/contactImg/Default.png"
	}
};

var groups = {
	1 : {
		name : "amici",
		id : 1,
		contacts : [ 1, 2 ]
	},
	0 : {
		name : "famiglia",
		id : 0,
		contacts : [ 1 ]
	}
};

// stub di CommunicationPresenter
function CommunicationPanelPresenter() {
	this.updateStats = function() {
		document.dispatchEvent(new CustomEvent("eventRaised"));
	};
	this.getMyVideo = function() {
		return video;
	};
	this.getOtherVideo = function() {
		return video;
	};
	this.updateTimer = function() {
		document.dispatchEvent(new CustomEvent("eventRaised"));
	};
}

// stub di AddressBookPresenter
function AddressBookPanelPresenter() {
	this.getContacts = function() {
		return contacts;
	};
	this.getGroups = function() {
		return groups;
	};
	this.getGroupsWhereContactsIs = function(contact) {
		return groups[0];
	};
	this.contactAlreadyPresent = function(contact) {
		return true;
	};
	this.getContact = function(id) {
		return contacts[id];
	};
}

/**
 * Verifica la corretta costruzione della stringa che rappresenta un contatto
 * della rubrica nell'interfaccia grafica
 * 
 * @version 2.0
 * @author Diego Beraldin
 */
test("testCreateNameLabel()", function() {
	var i = 0;
	var contact = {
		name : "null",
		surname : "null",
		email : "indirizzo5@dominio.it"
	};

	var result = mediator.createNameLabel(contact);
	equal(result, "indirizzo5@dominio.it");
	i++;

	contact.name = "Paolino";
	result = mediator.createNameLabel(contact);
	equal(result, "Paolino");
	i++;

	contact.surname = "Paperino";
	result = mediator.createNameLabel(contact);
	equal(result, "Paolino Paperino");
	i++;

	expect(i);
});

test("testGetAddressBookContacts()", function() {
	var result = mediator.getAddressBookContacts();
	equal(result, contacts);
});

test("testGetAddressBookGroups()", function() {
	var result = mediator.getAddressBookGroups();
	equal(result, groups);
});

test("testGetGroupsWhereContactsIs()", function() {
	var contact = {
		id : 2,
		name : "Gastone",
		surname : "Paperone",
		email : "indirizzo4@dominio.it",
		blocked : false,
		state : "offline",
		picturePath : "img/contactImg/Default.png"
	};
	var result = mediator.getGroupsWhereContactsIs(contact);
	equal(result, groups[0]);
});

test("testContactAlreadyPresent()", function() {
	var contact = {};
	var bool = mediator.contactAlreadyPresent(contact);
	ok(bool);
});

test("testGetCommunicationPPMyVideo()", function() {
	var result = mediator.getCommunicationPPMyVideo();
	equal(result, video);
});

test("testGetCommunicationPPOtherVideo()", function() {
	var result = mediator.getCommunicationPPOtherVideo();
	equal(result, video);
});

test("testUpdateCommunicationPPUpdateStats", function() {
	var text = "";
	var bool = false;
	document.addEventListener("eventRaised", function() {
		bool = true;
	});

	mediator.communicationPPUpdateStats(text, bool);
	ok(bool);
});

test("testUpdateCommunicationPPUpdateTimer", function() {
	var text = "";
	var bool = false;
	document.addEventListener("eventRaised", function() {
		bool = true;
	});

	mediator.communicationPPUpdateTimer(text, bool);
	ok(bool);
});

test("testGetContactsById()", function() {
	var id = 1;
	var result = mediator.getContactById(id);
	equal(result, contacts[id]);
});

test("testGetView()", function() {
	
});