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
if (! _$jscoverage['CallHistoryPanelPresenterTest.js']) {
  _$jscoverage['CallHistoryPanelPresenterTest.js'] = [];
  _$jscoverage['CallHistoryPanelPresenterTest.js'][1] = 0;
  _$jscoverage['CallHistoryPanelPresenterTest.js'][5] = 0;
  _$jscoverage['CallHistoryPanelPresenterTest.js'][11] = 0;
  _$jscoverage['CallHistoryPanelPresenterTest.js'][13] = 0;
  _$jscoverage['CallHistoryPanelPresenterTest.js'][16] = 0;
  _$jscoverage['CallHistoryPanelPresenterTest.js'][18] = 0;
  _$jscoverage['CallHistoryPanelPresenterTest.js'][20] = 0;
  _$jscoverage['CallHistoryPanelPresenterTest.js'][21] = 0;
  _$jscoverage['CallHistoryPanelPresenterTest.js'][24] = 0;
  _$jscoverage['CallHistoryPanelPresenterTest.js'][25] = 0;
  _$jscoverage['CallHistoryPanelPresenterTest.js'][27] = 0;
}
_$jscoverage['CallHistoryPanelPresenterTest.js'].source = ["module <span class=\"k\">(</span><span class=\"s\">\"CallHistoryPanelPresenterTest\"</span><span class=\"k\">,</span> <span class=\"k\">{</span>","    setup<span class=\"k\">:</span>","        <span class=\"k\">function</span><span class=\"k\">()</span> <span class=\"k\">{</span>","\t\t<span class=\"c\">//oggetto da testare</span>","\t\t\ttester <span class=\"k\">=</span> <span class=\"k\">new</span> CallHistoryPanelPresenter<span class=\"k\">();</span>\t<span class=\"k\">}</span><span class=\"k\">,</span>","\tteardown<span class=\"k\">:</span> ","\t\t<span class=\"k\">function</span><span class=\"k\">()</span><span class=\"k\">{}</span>","\t<span class=\"k\">}</span>","<span class=\"k\">);</span>","","test<span class=\"k\">(</span><span class=\"s\">\"testCreatePanel()\"</span><span class=\"k\">,</span>","    <span class=\"k\">function</span> <span class=\"k\">()</span> <span class=\"k\">{</span>","\t\t<span class=\"k\">var</span> i <span class=\"k\">=</span> <span class=\"s\">0</span><span class=\"k\">;</span>","\t\t","\t\t<span class=\"c\">// creo l'elemento 'CallHistoryPanel'</span>","\t\t<span class=\"k\">var</span> element <span class=\"k\">=</span> tester<span class=\"k\">.</span>createPanel<span class=\"k\">();</span>","\t\t<span class=\"c\">// estraggo la lista dei figli di questo pannello</span>","\t\t<span class=\"k\">var</span> list <span class=\"k\">=</span> element<span class=\"k\">.</span>childNodes<span class=\"k\">;</span>","\t\t<span class=\"c\">// controllo che abbia esattamente un figlio</span>","\t\tequal<span class=\"k\">(</span>list<span class=\"k\">.</span>length<span class=\"k\">,</span><span class=\"s\">1</span><span class=\"k\">,</span><span class=\"s\">\"il numero di figli dell'elemento restituito e' 1\"</span><span class=\"k\">);</span>","\t\ti<span class=\"k\">++;</span>","\t\t","\t\t<span class=\"c\">// controllo che il figlio figlio sia una lista ul</span>","\t\tequal<span class=\"k\">(</span>list<span class=\"k\">[</span><span class=\"s\">0</span><span class=\"k\">].</span>nodeName<span class=\"k\">,</span> <span class=\"s\">\"UL\"</span><span class=\"k\">,</span> <span class=\"s\">\"il primo figlio dell'elemento e' una lista\"</span><span class=\"k\">);</span>","\t\ti<span class=\"k\">++;</span>","\t","\t\texpect<span class=\"k\">(</span>i<span class=\"k\">);</span>","    <span class=\"k\">}</span><span class=\"k\">);</span>",""];
_$jscoverage['CallHistoryPanelPresenterTest.js'][1]++;
module("CallHistoryPanelPresenterTest", {setup: (function () {
  _$jscoverage['CallHistoryPanelPresenterTest.js'][5]++;
  tester = new CallHistoryPanelPresenter();
}), teardown: (function () {
})});
_$jscoverage['CallHistoryPanelPresenterTest.js'][11]++;
test("testCreatePanel()", (function () {
  _$jscoverage['CallHistoryPanelPresenterTest.js'][13]++;
  var i = 0;
  _$jscoverage['CallHistoryPanelPresenterTest.js'][16]++;
  var element = tester.createPanel();
  _$jscoverage['CallHistoryPanelPresenterTest.js'][18]++;
  var list = element.childNodes;
  _$jscoverage['CallHistoryPanelPresenterTest.js'][20]++;
  equal(list.length, 1, "il numero di figli dell'elemento restituito e' 1");
  _$jscoverage['CallHistoryPanelPresenterTest.js'][21]++;
  (i++);
  _$jscoverage['CallHistoryPanelPresenterTest.js'][24]++;
  equal(list[0].nodeName, "UL", "il primo figlio dell'elemento e' una lista");
  _$jscoverage['CallHistoryPanelPresenterTest.js'][25]++;
  (i++);
  _$jscoverage['CallHistoryPanelPresenterTest.js'][27]++;
  expect(i);
}));
