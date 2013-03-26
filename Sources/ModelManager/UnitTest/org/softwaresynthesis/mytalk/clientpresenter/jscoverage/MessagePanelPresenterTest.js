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
if (! _$jscoverage['MessagePanelPresenterTest.js']) {
  _$jscoverage['MessagePanelPresenterTest.js'] = [];
  _$jscoverage['MessagePanelPresenterTest.js'][1] = 0;
  _$jscoverage['MessagePanelPresenterTest.js'][4] = 0;
  _$jscoverage['MessagePanelPresenterTest.js'][5] = 0;
  _$jscoverage['MessagePanelPresenterTest.js'][6] = 0;
  _$jscoverage['MessagePanelPresenterTest.js'][13] = 0;
  _$jscoverage['MessagePanelPresenterTest.js'][15] = 0;
  _$jscoverage['MessagePanelPresenterTest.js'][17] = 0;
  _$jscoverage['MessagePanelPresenterTest.js'][19] = 0;
  _$jscoverage['MessagePanelPresenterTest.js'][21] = 0;
  _$jscoverage['MessagePanelPresenterTest.js'][22] = 0;
  _$jscoverage['MessagePanelPresenterTest.js'][25] = 0;
  _$jscoverage['MessagePanelPresenterTest.js'][26] = 0;
  _$jscoverage['MessagePanelPresenterTest.js'][29] = 0;
  _$jscoverage['MessagePanelPresenterTest.js'][30] = 0;
  _$jscoverage['MessagePanelPresenterTest.js'][35] = 0;
  _$jscoverage['MessagePanelPresenterTest.js'][36] = 0;
  _$jscoverage['MessagePanelPresenterTest.js'][37] = 0;
  _$jscoverage['MessagePanelPresenterTest.js'][39] = 0;
  _$jscoverage['MessagePanelPresenterTest.js'][40] = 0;
  _$jscoverage['MessagePanelPresenterTest.js'][44] = 0;
  _$jscoverage['MessagePanelPresenterTest.js'][45] = 0;
  _$jscoverage['MessagePanelPresenterTest.js'][46] = 0;
  _$jscoverage['MessagePanelPresenterTest.js'][48] = 0;
  _$jscoverage['MessagePanelPresenterTest.js'][49] = 0;
  _$jscoverage['MessagePanelPresenterTest.js'][53] = 0;
}
_$jscoverage['MessagePanelPresenterTest.js'].source = ["module <span class=\"k\">(</span><span class=\"s\">\"MessagePanelPresenterTest\"</span><span class=\"k\">,</span> <span class=\"k\">{</span>","    setup<span class=\"k\">:</span>","        <span class=\"k\">function</span><span class=\"k\">()</span> <span class=\"k\">{</span>","\t\t\tcommunicationcenter <span class=\"k\">=</span> <span class=\"k\">new</span> Object<span class=\"k\">();</span>","\t\t\tcommunicationcenter<span class=\"k\">.</span>my <span class=\"k\">=</span> <span class=\"k\">{</span>id<span class=\"k\">:</span><span class=\"s\">\"01\"</span><span class=\"k\">}</span><span class=\"k\">;</span>","\t\t\ttest <span class=\"k\">=</span> <span class=\"k\">new</span> MessagePanelPresenter<span class=\"k\">(</span><span class=\"s\">\"MessageManager.php\"</span><span class=\"k\">);</span>\t\t\t\t\t\t\t\t<span class=\"k\">}</span><span class=\"k\">,</span>","\tteardown<span class=\"k\">:</span> ","\t\t<span class=\"k\">function</span><span class=\"k\">()</span><span class=\"k\">{}</span>","\t<span class=\"k\">}</span>","<span class=\"k\">);</span>","","","test<span class=\"k\">(</span><span class=\"s\">\"createPanelTest()\"</span><span class=\"k\">,</span>","    <span class=\"k\">function</span> <span class=\"k\">()</span> <span class=\"k\">{</span>","\t\t<span class=\"k\">var</span> i <span class=\"k\">=</span> <span class=\"s\">0</span><span class=\"k\">;</span>","\t\t<span class=\"c\">// creo l'elemento 'MessagePanel'</span>","\t\t<span class=\"k\">var</span> element <span class=\"k\">=</span> test<span class=\"k\">.</span>createPanel<span class=\"k\">();</span>","\t\t<span class=\"c\">// estraggo la lista dei figli di questo pannello</span>","\t<span class=\"k\">var</span> list <span class=\"k\">=</span> element<span class=\"k\">.</span>childNodes<span class=\"k\">;</span>","\t\t<span class=\"c\">// controllo che abbia esattamente due figli</span>","\t\tequal<span class=\"k\">(</span>list<span class=\"k\">.</span>length<span class=\"k\">,</span><span class=\"s\">2</span><span class=\"k\">,</span><span class=\"s\">\"il numero di figli dell'elemento restituito e' 2\"</span><span class=\"k\">);</span>","\t\ti<span class=\"k\">++;</span>","\t\t","\t\t<span class=\"c\">// controllo che il primo figlio sia il video</span>","\t\tequal<span class=\"k\">(</span>list<span class=\"k\">[</span><span class=\"s\">0</span><span class=\"k\">].</span>nodeName<span class=\"k\">,</span> <span class=\"s\">\"VIDEO\"</span><span class=\"k\">,</span> <span class=\"s\">\"il primo figlio dell'elemento e' un video\"</span><span class=\"k\">);</span>","\t\ti<span class=\"k\">++;</span>","\t\t","\t\t<span class=\"c\">// controllo che il secondo figlio sia un div</span>","\t\tequal<span class=\"k\">(</span>list<span class=\"k\">[</span><span class=\"s\">1</span><span class=\"k\">].</span>nodeName<span class=\"k\">,</span> <span class=\"s\">\"DIV\"</span><span class=\"k\">,</span> <span class=\"s\">\"il primo figlio dell'elemento e' un div\"</span><span class=\"k\">);</span>","\t\ti<span class=\"k\">++;</span>","\t\t","","\t","\t\t<span class=\"c\">// estraggo la lista dei figli di video</span>","\t\t<span class=\"k\">var</span> items <span class=\"k\">=</span> list<span class=\"k\">[</span><span class=\"s\">0</span><span class=\"k\">].</span>childNodes<span class=\"k\">;</span>","\t\tequal<span class=\"k\">(</span>items<span class=\"k\">.</span>length<span class=\"k\">,</span> <span class=\"s\">1</span><span class=\"k\">,</span> <span class=\"s\">\"il video ha un figlio\"</span><span class=\"k\">);</span>","\t\ti<span class=\"k\">++;</span>","\t\t<span class=\"c\">//controllo che il figlio di video sia source</span>","\t\tequal<span class=\"k\">(</span>items<span class=\"k\">[</span><span class=\"s\">0</span><span class=\"k\">].</span>nodeName<span class=\"k\">,</span> <span class=\"s\">\"SOURCE\"</span><span class=\"k\">,</span> <span class=\"s\">\"il figlio di video e' source \"</span><span class=\"k\">);</span>","\t\ti<span class=\"k\">++;</span>","\t\t","\t\t","\t\t<span class=\"c\">// estraggo la lista dei figli del di div</span>","\t<span class=\"k\">var</span> items_uno <span class=\"k\">=</span> list<span class=\"k\">[</span><span class=\"s\">1</span><span class=\"k\">].</span>childNodes<span class=\"k\">;</span>","\tequal<span class=\"k\">(</span>items_uno<span class=\"k\">.</span>length<span class=\"k\">,</span> <span class=\"s\">1</span><span class=\"k\">,</span> <span class=\"s\">\"il div ha un figlio\"</span><span class=\"k\">);</span>","\ti<span class=\"k\">++;</span>","\t\t<span class=\"c\">//controllo che il figlio di div sia ul</span>","\tequal<span class=\"k\">(</span>items_uno<span class=\"k\">[</span><span class=\"s\">0</span><span class=\"k\">].</span>nodeName<span class=\"k\">,</span> <span class=\"s\">\"UL\"</span><span class=\"k\">,</span> <span class=\"s\">\"il figlio di video e' ul \"</span><span class=\"k\">);</span>","\t\ti<span class=\"k\">++;</span>","\t\t","\t\t","\t\t","\t\texpect<span class=\"k\">(</span>i<span class=\"k\">);</span>","    <span class=\"k\">}</span><span class=\"k\">);</span>",""];
_$jscoverage['MessagePanelPresenterTest.js'][1]++;
module("MessagePanelPresenterTest", {setup: (function () {
  _$jscoverage['MessagePanelPresenterTest.js'][4]++;
  communicationcenter = new Object();
  _$jscoverage['MessagePanelPresenterTest.js'][5]++;
  communicationcenter.my = {id: "01"};
  _$jscoverage['MessagePanelPresenterTest.js'][6]++;
  test = new MessagePanelPresenter("MessageManager.php");
}), teardown: (function () {
})});
_$jscoverage['MessagePanelPresenterTest.js'][13]++;
test("createPanelTest()", (function () {
  _$jscoverage['MessagePanelPresenterTest.js'][15]++;
  var i = 0;
  _$jscoverage['MessagePanelPresenterTest.js'][17]++;
  var element = test.createPanel();
  _$jscoverage['MessagePanelPresenterTest.js'][19]++;
  var list = element.childNodes;
  _$jscoverage['MessagePanelPresenterTest.js'][21]++;
  equal(list.length, 2, "il numero di figli dell'elemento restituito e' 2");
  _$jscoverage['MessagePanelPresenterTest.js'][22]++;
  (i++);
  _$jscoverage['MessagePanelPresenterTest.js'][25]++;
  equal(list[0].nodeName, "VIDEO", "il primo figlio dell'elemento e' un video");
  _$jscoverage['MessagePanelPresenterTest.js'][26]++;
  (i++);
  _$jscoverage['MessagePanelPresenterTest.js'][29]++;
  equal(list[1].nodeName, "DIV", "il primo figlio dell'elemento e' un div");
  _$jscoverage['MessagePanelPresenterTest.js'][30]++;
  (i++);
  _$jscoverage['MessagePanelPresenterTest.js'][35]++;
  var items = list[0].childNodes;
  _$jscoverage['MessagePanelPresenterTest.js'][36]++;
  equal(items.length, 1, "il video ha un figlio");
  _$jscoverage['MessagePanelPresenterTest.js'][37]++;
  (i++);
  _$jscoverage['MessagePanelPresenterTest.js'][39]++;
  equal(items[0].nodeName, "SOURCE", "il figlio di video e' source ");
  _$jscoverage['MessagePanelPresenterTest.js'][40]++;
  (i++);
  _$jscoverage['MessagePanelPresenterTest.js'][44]++;
  var items_uno = list[1].childNodes;
  _$jscoverage['MessagePanelPresenterTest.js'][45]++;
  equal(items_uno.length, 1, "il div ha un figlio");
  _$jscoverage['MessagePanelPresenterTest.js'][46]++;
  (i++);
  _$jscoverage['MessagePanelPresenterTest.js'][48]++;
  equal(items_uno[0].nodeName, "UL", "il figlio di video e' ul ");
  _$jscoverage['MessagePanelPresenterTest.js'][49]++;
  (i++);
  _$jscoverage['MessagePanelPresenterTest.js'][53]++;
  expect(i);
}));