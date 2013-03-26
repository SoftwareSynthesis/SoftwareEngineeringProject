module("MainPanelPresenter", {
	setup : function() {
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
