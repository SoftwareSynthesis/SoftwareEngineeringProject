/**
 * 
 */
module("Cesso", {
	setup : function() {
		// stub di communicationcenter
		communicationcenter = {};
		// brutti eventi cattivi (globali)
		showCommunicationPanel = new CustomEvent("showCommunicationPanel");
		loadedView = new CustomEvent("loadedView");
		removeRegistrationPanel = new CustomEvent("removeRegistrationPanel");
		removeLoginPanel = new CustomEvent("removeLoginPanel");
		removeAddressBookPanel = new CustomEvent("removeAddressBookPanel");
		removeMainPanel = new CustomEvent("removeMainPanel");
		removeToolsPanel = new CustomEvent("removeToolsPanel");
		showAddressBookPanel = new CustomEvent("showAddressBookPanel");
		showMainPanel = new CustomEvent("showMainPanel");
		showToolsPanel = new CustomEvent("showToolsPanel");
		removeAllPanel = new CustomEvent("removeAllPanel");
		// oggetto da testare
		cesso = new PresenterMediator();
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

// stub-ghost di MainPresenter
function MainPanelPresenter() {
	this.initialize = function(view) {
		var event = new CustomEvent("calledInitialize");
		event.view = view;
		document.dispatchEvent(event);
	};
	this.displayChildPanel = function(div) {
		document.dispatchEvent(new CustomEvent("calledDisplayChildPanel"));
	}
}

// stub-ghost di CallHistoryPresenter
function CallHistoryPanelPresenter() {
	this.display = function() {
		document.dispatchEvent(new CustomEvent("calledDisplay"));
	};
}

// stub-ghost di PhoneCallsRegistryPresenter
function PhoneCallsRegistryPresenter() {
	this.showView = function() {
		document.dispatchEvent(new CustomEvent("calledShowView"));
	};
}

// stub di ToolsPresenter
function ToolsPanelPresenter() {
}

// stub-ghost di richiesta AJAX
function XMLHttpRequest() {
	this.responsetype = "";
	this.open = function(a, b, c) {
		var event = new CustomEvent("calledOpen");
		event.method = a;
		event.path = b;
		document.dispatchEvent(event);
	};
	this.send = function() {
		document.dispatchEvent(new CustomEvent("calledSend"));
	}
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

	var result = cesso.createNameLabel(contact);
	equal(result, "indirizzo5@dominio.it");
	i++;

	contact.name = "Paolino";
	result = cesso.createNameLabel(contact);
	equal(result, "Paolino");
	i++;

	contact.surname = "Paperino";
	result = cesso.createNameLabel(contact);
	equal(result, "Paolino Paperino");
	i++;

	expect(i);
});

test("testGetAddressBookContacts()", function() {
	var result = cesso.getAddressBookContacts();
	equal(result, contacts);
});

test("testGetAddressBookGroups()", function() {
	var result = cesso.getAddressBookGroups();
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
	var result = cesso.getGroupsWhereContactsIs(contact);
	equal(result, groups[0]);
});

test("testContactAlreadyPresent()", function() {
	var contact = {};
	var bool = cesso.contactAlreadyPresent(contact);
	ok(bool);
});

test("testGetCommunicationPPMyVideo()", function() {
	var result = cesso.getCommunicationPPMyVideo();
	equal(result, video);
});

test("testGetCommunicationPPOtherVideo()", function() {
	var result = cesso.getCommunicationPPOtherVideo();
	equal(result, video);
});

test("testUpdateCommunicationPPUpdateStats", function() {
	var text = "";
	var bool = false;
	document.addEventListener("eventRaised", function() {
		bool = true;
	});

	cesso.communicationPPUpdateStats(text, bool);
	ok(bool);
});

test("testUpdateCommunicationPPUpdateTimer", function() {
	var text = "";
	var bool = false;
	document.addEventListener("eventRaised", function() {
		bool = true;
	});

	cesso.communicationPPUpdateTimer(text, bool);
	ok(bool);
});

test("testGetContactsById()", function() {
	var id = 1;
	var result = cesso.getContactById(id);
	equal(result, contacts[id]);
});

test("testOnLoadedFirstLevelView()", function() {
	var dummyView = document.createElement("div");
	var result;
	var event = new CustomEvent("loadedView");
	event.view = dummyView;
	event.presenter = "main";
	document.addEventListener("calledInitialize", function(evt) {
		result = evt.view;
	});

	document.dispatchEvent(event);
	equal(result, dummyView);
});

test("testOnLoadedSecondLevelView()", function() {
	var i = 0;
	var dummyView = document.createElement("div");
	var calledDisplay = false;
	var calledDisplayChildPanel = false;
	var event = new CustomEvent("loadedView");
	event.view = dummyView;
	event.presenter = "callHistory";
	document.addEventListener("calledDisplay", function() {
		calledDisplay = true;
	});
	document.addEventListener("calledDisplayChildPanel", function() {
		calledDisplayChildPanel = true;
	});

	document.dispatchEvent(event);
	ok(calledDisplay);
	i++;
	ok(calledDisplayChildPanel);
	i++;

	expect(i);
});

test("testOnLoadedThirdLevelView()", function() {
	var dummyView = document.createElement("div");
	var calledShowView = false;
	var event = new CustomEvent("loadedView");
	event.view = dummyView;
	event.presenter = "phoneCallsRegistry";
	document.addEventListener("calledShowView", function() {
		calledShowView = true;
	});

	document.dispatchEvent(event);
	ok(calledShowView);
});

test("testOnRemoveAllPanels()", function() {
	var i = 0;
	var boolArray = [ false, false, false, false, false ];
	document.addEventListener("removeLoginPanel", function() {
		boolArray[0] = true;
	});
	document.addEventListener("removeRegistrationPanel", function() {
		boolArray[1] = true;
	});
	document.addEventListener("removeAddressBookPanel", function() {
		boolArray[2] = true;
	});
	document.addEventListener("removeMainPanel", function() {
		boolArray[3] = true;
	});
	document.addEventListener("removeToolsPanel", function() {
		boolArray[4] = true;
	});

	document.dispatchEvent(new CustomEvent("removeAllPanel"));

	for ( var idx in boolArray) {
		ok(boolArray[i]);
		i++;
	}

	expect(i);
});

test("testOnShowUIPanels()", function() {
	var i = 0;
	var boolArray = [ false, false, false, false ];
	document.addEventListener("removeAllPanel", function() {
		boolArray[0] = true;
	});
	document.addEventListener("showAddressBookPanel", function() {
		boolArray[1] = true;
	});
	document.addEventListener("showMainPanel", function() {
		boolArray[2] = true;
	});
	document.addEventListener("showToolsPanel", function() {
		boolArray[3] = true;
	});

	document.dispatchEvent(new CustomEvent("showUIPanels"));

	for ( var idx in boolArray) {
		ok(boolArray[idx]);
		i++;
	}

	expect(i);
});

test("testGetView()", function() {
	var i = 0;
	var bool = false;
	var method = "";
	var path = "";
	document.addEventListener("calledSend", function() {
		bool = true;
	});
	document.addEventListener("calledOpen", function(evt) {
		method = evt.method;
		path = evt.path;
	});
	
	cesso.getView("main");
	
	ok(bool);
	i++;
	equal(method, "GET");
	i++;
	equal(path, "clientview/MainView.html");
	i++;
	
	expect(i);
});