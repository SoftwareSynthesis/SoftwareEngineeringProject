module("Test di LoginPanelPresenter", {
	setup: function() {
		var tester = new LoginPanelPresenter();
	},
	teardown: function() {}
});


function testTestCredentials() {
	var json = "{\"name\":\"maria\", \"surname\":\"goretti\"}";
	tester.testCredentials(json);
}

test("", testTestCredentials);