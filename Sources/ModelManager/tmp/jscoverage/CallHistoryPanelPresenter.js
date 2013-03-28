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
if (! _$jscoverage['CallHistoryPanelPresenter.js']) {
  _$jscoverage['CallHistoryPanelPresenter.js'] = [];
  _$jscoverage['CallHistoryPanelPresenter.js'][9] = 0;
  _$jscoverage['CallHistoryPanelPresenter.js'][14] = 0;
  _$jscoverage['CallHistoryPanelPresenter.js'][16] = 0;
  _$jscoverage['CallHistoryPanelPresenter.js'][18] = 0;
  _$jscoverage['CallHistoryPanelPresenter.js'][29] = 0;
  _$jscoverage['CallHistoryPanelPresenter.js'][30] = 0;
  _$jscoverage['CallHistoryPanelPresenter.js'][31] = 0;
  _$jscoverage['CallHistoryPanelPresenter.js'][32] = 0;
  _$jscoverage['CallHistoryPanelPresenter.js'][33] = 0;
  _$jscoverage['CallHistoryPanelPresenter.js'][34] = 0;
  _$jscoverage['CallHistoryPanelPresenter.js'][35] = 0;
  _$jscoverage['CallHistoryPanelPresenter.js'][36] = 0;
  _$jscoverage['CallHistoryPanelPresenter.js'][46] = 0;
  _$jscoverage['CallHistoryPanelPresenter.js'][47] = 0;
  _$jscoverage['CallHistoryPanelPresenter.js'][48] = 0;
  _$jscoverage['CallHistoryPanelPresenter.js'][49] = 0;
  _$jscoverage['CallHistoryPanelPresenter.js'][50] = 0;
  _$jscoverage['CallHistoryPanelPresenter.js'][68] = 0;
  _$jscoverage['CallHistoryPanelPresenter.js'][69] = 0;
  _$jscoverage['CallHistoryPanelPresenter.js'][70] = 0;
  _$jscoverage['CallHistoryPanelPresenter.js'][72] = 0;
  _$jscoverage['CallHistoryPanelPresenter.js'][74] = 0;
  _$jscoverage['CallHistoryPanelPresenter.js'][75] = 0;
  _$jscoverage['CallHistoryPanelPresenter.js'][89] = 0;
  _$jscoverage['CallHistoryPanelPresenter.js'][90] = 0;
  _$jscoverage['CallHistoryPanelPresenter.js'][91] = 0;
  _$jscoverage['CallHistoryPanelPresenter.js'][93] = 0;
  _$jscoverage['CallHistoryPanelPresenter.js'][94] = 0;
  _$jscoverage['CallHistoryPanelPresenter.js'][95] = 0;
  _$jscoverage['CallHistoryPanelPresenter.js'][96] = 0;
  _$jscoverage['CallHistoryPanelPresenter.js'][105] = 0;
  _$jscoverage['CallHistoryPanelPresenter.js'][106] = 0;
  _$jscoverage['CallHistoryPanelPresenter.js'][107] = 0;
  _$jscoverage['CallHistoryPanelPresenter.js'][108] = 0;
}
_$jscoverage['CallHistoryPanelPresenter.js'].source = ["<span class=\"c\">/**</span>","<span class=\"c\"> * Presenter incaricato di gestire lo storico delle chiamate</span>","<span class=\"c\"> * </span>","<span class=\"c\"> * @param {String}</span>","<span class=\"c\"> *            url URL della servlet con cui il presenter deve comunicare</span>","<span class=\"c\"> * @author Elena Zecchinato</span>","<span class=\"c\"> * @author Diego Beraldin</span>","<span class=\"c\"> */</span>","<span class=\"k\">function</span> CallHistoryPanelPresenter<span class=\"k\">(</span>url<span class=\"k\">)</span> <span class=\"k\">{</span>","\t<span class=\"c\">/***************************************************************************</span>","<span class=\"c\">\t * VARIABILI PRIVATE</span>","<span class=\"c\">\t **************************************************************************/</span>","\t<span class=\"c\">// URL delle servlet con cui deve relazionarsi questo presenter</span>","\t<span class=\"k\">var</span> servlets <span class=\"k\">=</span> <span class=\"k\">new</span> Array<span class=\"k\">();</span>","\t<span class=\"c\">// configura gli indirizzi delle servlet</span>","\tgetServletURLs<span class=\"k\">();</span>","\t<span class=\"c\">// array (enumerativo) delle chiamate scaricate dal server</span>","\t<span class=\"k\">var</span> calls <span class=\"k\">=</span> <span class=\"k\">new</span> Array<span class=\"k\">();</span>","","\t<span class=\"c\">/***************************************************************************</span>","<span class=\"c\">\t * METODI PRIVATI</span>","<span class=\"c\">\t **************************************************************************/</span>","\t<span class=\"c\">/**</span>","<span class=\"c\">\t * Configura gli indirizzi delle servlet con cui il presenter ha l'esigenza</span>","<span class=\"c\">\t * di comunicare per svolgere le proprie operazioni</span>","<span class=\"c\">\t * </span>","<span class=\"c\">\t * @author Diego Beraldin</span>","<span class=\"c\">\t */</span>","\t<span class=\"k\">function</span> getServletURLs<span class=\"k\">()</span> <span class=\"k\">{</span>","\t\t<span class=\"k\">var</span> configurationRequest <span class=\"k\">=</span> <span class=\"k\">new</span> XMLHttpRequest<span class=\"k\">();</span>","\t\tconfigurationRequest<span class=\"k\">.</span>open<span class=\"k\">(</span><span class=\"s\">\"POST\"</span><span class=\"k\">,</span> configurationFile<span class=\"k\">,</span> <span class=\"k\">false</span><span class=\"k\">);</span>","\t\tconfigurationRequest<span class=\"k\">.</span>send<span class=\"k\">();</span>","\t\t<span class=\"k\">var</span> XMLDocument <span class=\"k\">=</span> configurationRequest<span class=\"k\">.</span>responseXML<span class=\"k\">;</span>","\t\t<span class=\"k\">var</span> baseURL <span class=\"k\">=</span> XMLDocument<span class=\"k\">.</span>getElementsByTagName<span class=\"k\">(</span><span class=\"s\">\"baseURL\"</span><span class=\"k\">)[</span><span class=\"s\">0</span><span class=\"k\">].</span>childNodes<span class=\"k\">[</span><span class=\"s\">0</span><span class=\"k\">].</span>data<span class=\"k\">;</span>","\t\t<span class=\"k\">var</span> name <span class=\"k\">=</span> XMLDocument<span class=\"k\">.</span>getElementById<span class=\"k\">(</span><span class=\"s\">\"getcalls\"</span><span class=\"k\">).</span>childNodes<span class=\"k\">[</span><span class=\"s\">0</span><span class=\"k\">].</span>data<span class=\"k\">;</span>","\t\tservlets<span class=\"k\">.</span>push<span class=\"k\">(</span>baseURL <span class=\"k\">+</span> name<span class=\"k\">);</span>","\t<span class=\"k\">}</span>","","\t<span class=\"c\">/**</span>","<span class=\"c\">\t * Scarica la lista delle chiamate che sono state effettuate dall'utente</span>","<span class=\"c\">\t * associato a questo client</span>","<span class=\"c\">\t * </span>","<span class=\"c\">\t * @returns {Object} la lista delle chiamate ottenuta dal server</span>","<span class=\"c\">\t * @author Diego Beraldin</span>","<span class=\"c\">\t */</span>","\t<span class=\"k\">function</span> getCalls<span class=\"k\">()</span> <span class=\"k\">{</span>","\t\t<span class=\"k\">var</span> request <span class=\"k\">=</span> <span class=\"k\">new</span> XMLHttpRequest<span class=\"k\">();</span>","\t\trequest<span class=\"k\">.</span>open<span class=\"k\">(</span><span class=\"s\">\"POST\"</span><span class=\"k\">,</span> servlets<span class=\"k\">[</span><span class=\"s\">0</span><span class=\"k\">],</span> <span class=\"k\">false</span><span class=\"k\">);</span>","\t\trequest<span class=\"k\">.</span>send<span class=\"k\">();</span>","\t\t<span class=\"k\">return</span> JSON<span class=\"k\">.</span>parse<span class=\"k\">(</span>request<span class=\"k\">.</span>responseText<span class=\"k\">);</span>","\t<span class=\"k\">}</span>","","\t<span class=\"c\">/**</span>","<span class=\"c\">\t * Aggiunge alla lista delle chiamate visualizzata nel CallHistoryPanel una</span>","<span class=\"c\">\t * nuova voce che corrirsponde alla chiamata impostata come parametro. Ogni</span>","<span class=\"c\">\t * voce della lista contiente i dati relativi alla chiamata e non consente</span>","<span class=\"c\">\t * di effettuare alcuna azione, ragion per cui non presenta associato nessun</span>","<span class=\"c\">\t * gestore di eventi (es. onclick).</span>","<span class=\"c\">\t * </span>","<span class=\"c\">\t * @param {Object}</span>","<span class=\"c\">\t *            call oggetto che corrisponde a 'JSCall' e rappresenta una</span>","<span class=\"c\">\t *            chiamata nello storico delle chiamate di un utente. Le</span>","<span class=\"c\">\t *            chiamate sono caratterizzate dall'altro utente coinvolto</span>","<span class=\"c\">\t *            (user) l'orario di inizio (startdate) e l'orario di fine</span>","<span class=\"c\">\t *            (enddate)</span>","<span class=\"c\">\t * @author Diego Beraldin</span>","<span class=\"c\">\t */</span>","\t<span class=\"k\">function</span> addListItem<span class=\"k\">(</span>call<span class=\"k\">)</span> <span class=\"k\">{</span>","\t\t<span class=\"k\">var</span> list <span class=\"k\">=</span> document<span class=\"k\">.</span>getElementById<span class=\"k\">(</span><span class=\"s\">\"ulHistory\"</span><span class=\"k\">);</span>","\t\t<span class=\"k\">var</span> item <span class=\"k\">=</span> document<span class=\"k\">.</span>createElement<span class=\"k\">(</span><span class=\"s\">\"li\"</span><span class=\"k\">);</span>","\t\t<span class=\"c\">// TODO probabilmente da modificare in futuro</span>","\t\t<span class=\"k\">var</span> text <span class=\"k\">=</span> document<span class=\"k\">.</span>createTextNode<span class=\"k\">(</span>call<span class=\"k\">.</span>user <span class=\"k\">+</span> <span class=\"s\">\" \"</span> <span class=\"k\">+</span> call<span class=\"k\">.</span>startdate","\t\t\t\t<span class=\"k\">+</span> <span class=\"s\">\" \"</span> <span class=\"k\">+</span> call<span class=\"k\">.</span>enddate<span class=\"k\">);</span>","\t\titem<span class=\"k\">.</span>appendChild<span class=\"k\">(</span>text<span class=\"k\">);</span>","\t\tlist<span class=\"k\">.</span>appendChild<span class=\"k\">(</span>item<span class=\"k\">);</span>","\t<span class=\"k\">}</span>","","\t<span class=\"c\">/***************************************************************************</span>","<span class=\"c\">\t * METODI PUBBLICI</span>","<span class=\"c\">\t **************************************************************************/</span>","\t<span class=\"c\">/**</span>","<span class=\"c\">\t * Crea il pannello dello storico delle chiamate che deve essere</span>","<span class=\"c\">\t * visualizzato all'interno del MainPanel come elemento figlio</span>","<span class=\"c\">\t * </span>","<span class=\"c\">\t * @returns {HTMLDivElement} il pannello dello storico delle chiamate</span>","<span class=\"c\">\t *          inizializzato</span>","<span class=\"c\">\t * @author Elena Zecchinato</span>","<span class=\"c\">\t */</span>","\t<span class=\"k\">this</span><span class=\"k\">.</span>createPanel <span class=\"k\">=</span> <span class=\"k\">function</span><span class=\"k\">()</span> <span class=\"k\">{</span>","\t\t<span class=\"k\">var</span> element <span class=\"k\">=</span> document<span class=\"k\">.</span>createElement<span class=\"k\">(</span><span class=\"s\">\"div\"</span><span class=\"k\">);</span>","\t\telement<span class=\"k\">.</span>setAttribute<span class=\"k\">(</span><span class=\"s\">\"id\"</span><span class=\"k\">,</span> <span class=\"s\">\"CallHistoryPanel\"</span><span class=\"k\">);</span>","\t\t<span class=\"c\">// creo contenuto di CallHistory</span>","\t\t<span class=\"k\">var</span> ulHistory <span class=\"k\">=</span> document<span class=\"k\">.</span>createElement<span class=\"k\">(</span><span class=\"s\">'ul'</span><span class=\"k\">);</span>","\t\tulHistory<span class=\"k\">.</span>id <span class=\"k\">=</span> <span class=\"s\">\"ulHistory\"</span><span class=\"k\">;</span>","\t\telement<span class=\"k\">.</span>appendChild<span class=\"k\">(</span>ulHistory<span class=\"k\">);</span>","\t\t<span class=\"k\">return</span> element<span class=\"k\">;</span>","\t<span class=\"k\">}</span><span class=\"k\">;</span>","","\t<span class=\"c\">/**</span>","<span class=\"c\">\t * Scarica la lista delle chiamate dal server, quindi aggiunge alla lista</span>","<span class=\"c\">\t * delle chiamate contenuta all'interno del CallHistoryPanel</span>","<span class=\"c\">\t * </span>","<span class=\"c\">\t * @author Diego Beraldin</span>","<span class=\"c\">\t */</span>","\t<span class=\"k\">this</span><span class=\"k\">.</span>setup <span class=\"k\">=</span> <span class=\"k\">function</span><span class=\"k\">()</span> <span class=\"k\">{</span>","\t\tcalls <span class=\"k\">=</span> getCalls<span class=\"k\">();</span>","\t\t<span class=\"k\">for</span> <span class=\"k\">(</span> <span class=\"k\">var</span> call <span class=\"k\">in</span> calls<span class=\"k\">)</span> <span class=\"k\">{</span>","\t\t\taddListItem<span class=\"k\">(</span>call<span class=\"k\">);</span>","\t\t<span class=\"k\">}</span>","\t<span class=\"k\">}</span><span class=\"k\">;</span>","<span class=\"k\">}</span>"];
_$jscoverage['CallHistoryPanelPresenter.js'][9]++;
function CallHistoryPanelPresenter(url) {
  _$jscoverage['CallHistoryPanelPresenter.js'][14]++;
  var servlets = new Array();
  _$jscoverage['CallHistoryPanelPresenter.js'][16]++;
  getServletURLs();
  _$jscoverage['CallHistoryPanelPresenter.js'][18]++;
  var calls = new Array();
  _$jscoverage['CallHistoryPanelPresenter.js'][29]++;
  function getServletURLs() {
    _$jscoverage['CallHistoryPanelPresenter.js'][30]++;
    var configurationRequest = new XMLHttpRequest();
    _$jscoverage['CallHistoryPanelPresenter.js'][31]++;
    configurationRequest.open("POST", configurationFile, false);
    _$jscoverage['CallHistoryPanelPresenter.js'][32]++;
    configurationRequest.send();
    _$jscoverage['CallHistoryPanelPresenter.js'][33]++;
    var XMLDocument = configurationRequest.responseXML;
    _$jscoverage['CallHistoryPanelPresenter.js'][34]++;
    var baseURL = XMLDocument.getElementsByTagName("baseURL")[0].childNodes[0].data;
    _$jscoverage['CallHistoryPanelPresenter.js'][35]++;
    var name = XMLDocument.getElementById("getcalls").childNodes[0].data;
    _$jscoverage['CallHistoryPanelPresenter.js'][36]++;
    servlets.push((baseURL + name));
}
  _$jscoverage['CallHistoryPanelPresenter.js'][46]++;
  function getCalls() {
    _$jscoverage['CallHistoryPanelPresenter.js'][47]++;
    var request = new XMLHttpRequest();
    _$jscoverage['CallHistoryPanelPresenter.js'][48]++;
    request.open("POST", servlets[0], false);
    _$jscoverage['CallHistoryPanelPresenter.js'][49]++;
    request.send();
    _$jscoverage['CallHistoryPanelPresenter.js'][50]++;
    return JSON.parse(request.responseText);
}
  _$jscoverage['CallHistoryPanelPresenter.js'][68]++;
  function addListItem(call) {
    _$jscoverage['CallHistoryPanelPresenter.js'][69]++;
    var list = document.getElementById("ulHistory");
    _$jscoverage['CallHistoryPanelPresenter.js'][70]++;
    var item = document.createElement("li");
    _$jscoverage['CallHistoryPanelPresenter.js'][72]++;
    var text = document.createTextNode((call.user + " " + call.startdate + " " + call.enddate));
    _$jscoverage['CallHistoryPanelPresenter.js'][74]++;
    item.appendChild(text);
    _$jscoverage['CallHistoryPanelPresenter.js'][75]++;
    list.appendChild(item);
}
  _$jscoverage['CallHistoryPanelPresenter.js'][89]++;
  this.createPanel = (function () {
  _$jscoverage['CallHistoryPanelPresenter.js'][90]++;
  var element = document.createElement("div");
  _$jscoverage['CallHistoryPanelPresenter.js'][91]++;
  element.setAttribute("id", "CallHistoryPanel");
  _$jscoverage['CallHistoryPanelPresenter.js'][93]++;
  var ulHistory = document.createElement("ul");
  _$jscoverage['CallHistoryPanelPresenter.js'][94]++;
  ulHistory.id = "ulHistory";
  _$jscoverage['CallHistoryPanelPresenter.js'][95]++;
  element.appendChild(ulHistory);
  _$jscoverage['CallHistoryPanelPresenter.js'][96]++;
  return element;
});
  _$jscoverage['CallHistoryPanelPresenter.js'][105]++;
  this.setup = (function () {
  _$jscoverage['CallHistoryPanelPresenter.js'][106]++;
  calls = getCalls();
  _$jscoverage['CallHistoryPanelPresenter.js'][107]++;
  for (var call in calls) {
    _$jscoverage['CallHistoryPanelPresenter.js'][108]++;
    addListItem(call);
}
});
}
