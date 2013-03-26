module("MainPanelPresenter", {
	setup : function() {
		// stub di interfaccia grafica
		var element = document.createElement("div");
		element.id = "MainPanel";
		element.style.position = "absolute";
		element.style.left = "-999em";
		document.body.appendChild(element);
		
		tester = new MainPanelPresenter();
		
	},
	teardown : function() {
	}
});

test("testHide()", function() {
	tester.hide();
	equal(element.style.display, "none",
	"la proprietà display è stata settata correttamente");
});
