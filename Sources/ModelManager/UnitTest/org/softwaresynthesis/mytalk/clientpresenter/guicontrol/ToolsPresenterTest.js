module("ToolsPanelPresenter", {
	setup : function() {
		tester = new ToolsPanelPresenter();
	},
	teardown : function() {
	}
});

test("testHide()", function() {
	tester.hide();
	equal(element.style.display, "none",
	"la proprietà display è stata settata correttamente");
});