/* automatically generated by JSCoverage - do not edit */
try {
  if (typeof top === 'object' && top !== null && typeof top.opener === 'object' && top.opener !== null) {
    // this is a browser window that was opened from another window

    if (! top.opener._$jscoverage) {
      top.opener._$jscoverage = {};
    }
  }
}
catch (e) {}

try {
  if (typeof top === 'object' && top !== null) {
    // this is a browser window

    try {
      if (typeof top.opener === 'object' && top.opener !== null && top.opener._$jscoverage) {
        top._$jscoverage = top.opener._$jscoverage;
      }
    }
    catch (e) {}

    if (! top._$jscoverage) {
      top._$jscoverage = {};
    }
  }
}
catch (e) {}

try {
  if (typeof top === 'object' && top !== null && top._$jscoverage) {
    _$jscoverage = top._$jscoverage;
  }
}
catch (e) {}
if (typeof _$jscoverage !== 'object') {
  _$jscoverage = {};
}
if (! _$jscoverage['ToolsPanelPresenterTest.js']) {
  _$jscoverage['ToolsPanelPresenterTest.js'] = [];
  _$jscoverage['ToolsPanelPresenterTest.js'][1] = 0;
  _$jscoverage['ToolsPanelPresenterTest.js'][3] = 0;
  _$jscoverage['ToolsPanelPresenterTest.js'][9] = 0;
  _$jscoverage['ToolsPanelPresenterTest.js'][10] = 0;
  _$jscoverage['ToolsPanelPresenterTest.js'][11] = 0;
}
_$jscoverage['ToolsPanelPresenterTest.js'].source = ["module<span class=\"k\">(</span><span class=\"s\">\"ToolsPanelPresenter\"</span><span class=\"k\">,</span> <span class=\"k\">{</span>","\tsetup <span class=\"k\">:</span> <span class=\"k\">function</span><span class=\"k\">()</span> <span class=\"k\">{</span>","\t\ttester <span class=\"k\">=</span> <span class=\"k\">new</span> ToolsPanelPresenter<span class=\"k\">();</span>","\t<span class=\"k\">}</span><span class=\"k\">,</span>","\tteardown <span class=\"k\">:</span> <span class=\"k\">function</span><span class=\"k\">()</span> <span class=\"k\">{</span>","\t<span class=\"k\">}</span>","<span class=\"k\">}</span><span class=\"k\">);</span>","","test<span class=\"k\">(</span><span class=\"s\">\"testHide()\"</span><span class=\"k\">,</span> <span class=\"k\">function</span><span class=\"k\">()</span> <span class=\"k\">{</span>","\ttester<span class=\"k\">.</span>hide<span class=\"k\">();</span>","\tequal<span class=\"k\">(</span>element<span class=\"k\">.</span>style<span class=\"k\">.</span>display<span class=\"k\">,</span> <span class=\"s\">\"none\"</span><span class=\"k\">,</span>","\t<span class=\"s\">\"la propriet&#195;&#160; display &#195;&#168; stata settata correttamente\"</span><span class=\"k\">);</span>","<span class=\"k\">}</span><span class=\"k\">);</span>"];
_$jscoverage['ToolsPanelPresenterTest.js'][1]++;
module("ToolsPanelPresenter", {setup: (function () {
  _$jscoverage['ToolsPanelPresenterTest.js'][3]++;
  tester = new ToolsPanelPresenter();
}), teardown: (function () {
})});
_$jscoverage['ToolsPanelPresenterTest.js'][9]++;
test("testHide()", (function () {
  _$jscoverage['ToolsPanelPresenterTest.js'][10]++;
  tester.hide();
  _$jscoverage['ToolsPanelPresenterTest.js'][11]++;
  equal(element.style.display, "none", "la propriet\u00c3\u00a0 display \u00c3\u00a8 stata settata correttamente");
}));
