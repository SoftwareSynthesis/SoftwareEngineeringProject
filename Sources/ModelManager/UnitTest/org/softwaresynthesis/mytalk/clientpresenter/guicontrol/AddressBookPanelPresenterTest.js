module(
		"AddressBookPanelPresenterTest",
		{
			setup : function() {
				// stub di interfaccia grafica
				var element = document.createElement("div");
				element.id = "AddressBookPanel";
				element.style.position = "absolute";
				element.style.left = "-999em";
				document.body.appendChild(element);
				// imposta da dove leggere le servlet
				configurationFile = "/ModelManager/WebContent/Conf/servletlocationtest.xml";
				// oggetto da testare
				tester = new AddressBookPanelPresenter();
				mediator={displayContact: function(c){}};
			},
			teardown : function() {
			}
		});


	
/*


test("testDeleteGroup()", function(){

	var element = document.getElementById("AddressBookPanel");
	
	tester.set_groups({0:{name:"famiglia",id: 0, contacts:""}});
	
	var group=tester.get_groups();
	var famiglia=group[0];
	var amici={name:"amici",id: 1, contacts:""};
	var i=0;
	try{tester.deleteGroup(amici);ok(false,"errore non rilevato");i++;}catch(err){equal(err,"Il gruppo che stai cercando di eliminare non esiste.","il gruppo coi id 2 non e' presente");i++;}
	equal(tester.deleteGroup(famiglia),true,"");
	i++;
	
	expect(i);
});		
*/


/*ci sarebbe da testare che add group non aggiunga un gruppo che c'è gia...ma nn si può fare perchè nn si aggiunge nessun gruppo realmente*/


test("testInitialize()", function() {
			var i = 0;
			tester.initialize();
			
			var element = document.getElementById("AddressBookPanel");
			var list = element.childNodes;
			equal(list.length, 4,
					"il numero di figli dell'elemento restituito e' 4");
			i++;

			equal(list[0].nodeName, "DIV", "il primo figlio e' un div");
			i++;
			equal(list[1].nodeName, "DIV", "il secondo figlio e' un div");
			i++;
			equal(list[2].nodeName, "DIV", "il terzo figlio e' un div");
			i++;
			equal(list[3].nodeName, "DIV", "il quarto figlio e' un div");
			i++;

			equal(list[0].getAttribute("id"), "divFilter",
					"il primo div si chiama divFilter");
			i++;
			equal(list[1].getAttribute("id"), "divSort",
					"il secondo div si chiama divSort");
			i++;
			equal(list[2].getAttribute("id"), "divList",
					"il terzo div si chiama divList");
			i++;
			equal(list[3].getAttribute("id"), "divGroup",
					"il quarto div si chiama divGroup");
			i++;

			var Filter = list[0].childNodes;
			equal(Filter.length, 2, "il numero di figli di Filter e' 2");
			i++;

			equal(Filter[0].nodeName, "INPUT",
					"il primo figlio di divFilter e' un input");
			i++;
			equal(Filter[0].getAttribute("type"), "text",
					"il tipo dell'input e' text");
			i++;

			equal(Filter[1].nodeName, "INPUT",
					"il secondo figlio di divFilter e' un input");
			i++;
			equal(Filter[1].getAttribute("type"), "image",
					"il tipo dell'input e' button");
			i++;

			var Sort = list[1].childNodes;
			equal(Sort.length, 1, "il numero di figli di Sort e' 1");
			i++;
			equal(Sort[0].nodeName, "SELECT", "il figlio di Sort e' un select");
			i++;
			equal(Sort[0].getAttribute("id"), "selectSort",
					"il nome del select e' SelectSort");
			i++;

			var List = list[2].childNodes;
			equal(List.length, 1, "il numero di figli di List e' 1");
			i++;
			equal(List[0].nodeName, "UL", "il figlio di List e' una lista ul");
			i++;

			var Group = list[3].childNodes;
			equal(Group.length, 1, "il numero di figli di Group e' 1");
			i++;
			equal(Group[0].nodeName, "SELECT",
					"il figlio di Group e' un select");
			i++;
			equal(Group[0].getAttribute("id"), "selectGroup",
					"il nome del select e' SelectSort");
			i++;

			expect(i);
		});

test("testSetup()", function() {
	var i = 0;
	tester.setup();
	var list = document.getElementById("AddressBookList").childNodes;
	// controllo che abbia scaricato tutti contatti dal server
	equal(list.length, 2, "numero corretto di contatti nella rubrica");
	i++;

	equal(list[0].nodeName, "LI",
			"il primo figlio dell'elemento e' un figlio della lista");
	i++;

	var laura = list[1].childNodes;

	equal(laura.length, 3, "ci sono tre figli");
	i++;

	equal(laura[0].nodeName, "IMG",
			"il primo figlio dell'elemento e' un immagine(profilo)");
	i++;

	equal(laura[1].nodeName, "#text",
			"il primo figlio dell'elemento e' un nodo testo");
	i++;

	equal(laura[2].nodeName, "IMG",
			"il primo figlio dell'elemento e' un immagine(stato)");
	i++;

	equal(laura[0].getAttribute("src"), "x.png",
			"l'immagine del primo contatto e' corretta");
	i++;

	equal(laura[1].data, "Flavia Bacco", "il nome e congnome sono corretti");
	i++;

	equal(laura[2].getAttribute("src"), "img/stateavailable.png",
			"lo stato del primo contatto e' corretto");
	i++;

	var flavia = list[0].childNodes;

	equal(flavia.length, 3, "ci sono tre figli");
	i++;

	equal(flavia[0].nodeName, "IMG",
			"il primo figlio dell'elemento e' un immagine(profilo)");
	i++;

	equal(flavia[1].nodeName, "#text",
			"il primo figlio dell'elemento e' un nodo testo");
	i++;

	equal(flavia[2].nodeName, "IMG",
			"il primo figlio dell'elemento e' un immagine(stato)");
	i++;

	equal(flavia[0].getAttribute("src"), "y.png",
			"l'immagine del secondo contatto e' corretta");
	i++;

	equal(flavia[1].data, "Laura Pausini", "il nome e congnome sono corretti");
	i++;

	equal(flavia[2].getAttribute("src"), "img/stateoffline.png",
			"lo stato del secondo contatto e' corretto");
	i++;

	expect(i);
});



test("testHide()", function() {
	tester.hide();
	var element = document.getElementById("AddressBookPanel");
	equal(element.style.display, "none",
			"il pannello viene nascosto correttamente");
});



/*
test("testAddContact()", function(){

	tester.set_contacts({0:{name:"Laura",surname: "Pausini", email:"laupau@gmail.com", id:"0"},1:{name:"Enrico",surname: "Botti", email:"enribot@gmail.com", id: "1"}});
	
	
	var flavia={name:"Flavia", surname:"Bacco", email:"flaba@gmail.com",id: "4", picturePath:"yy.png", state: "offline",
	blocked: "false"};
	var contact=tester.get_contacts();
	var laura=contact[0];
	
	var i=0;
	try{tester.addContact(laura);ok(false,"errore non rilevato");i++;}catch(err){equal(err,"Contatto già presente nella rubrica.","aggiunta di flavia non funziona perche e' gia presente in rubrica!");i++;}
	
	
	equal(tester.addContact(flavia),true,"flavia e' stata aggiunta correttamente");
	i++;
		
	expect(i);
});		

*/


/*
test("testRemoveContact()", function(){
	tester.set_contacts({0:{name:"Laura",surname: "Pausini", email:"laupau@gmail.com", id:"0"},1:{name:"Enrico",surname: "Botti", email:"enribot@gmail.com", id: "1"}});

	var i=0;
	var contact=tester.get_contacts();
	var laura=contact[0];
	var antonio=contact[1];

	var flavia={name:"Flavia", surname:"Bacco", email:"flaba@gmail.com",id: "4", picturePath:"yy.png", state: "offline",
	blocked: "false"};
	
	
	try{tester.removeContact(flavia);ok(false,"errore non rilevato");i++;}catch(err){equal(err,"Non puoi eliminare un contatto non presente in rubrica.","rimozione di flavia non possibile perchè non esite!");i++;}
	
	equal(tester.removeContact(laura),true,"rimozione di laura andata a buon fine");
	i++;
	equal(tester.removeContact(antonio),true,"rimozione di laura andata a buon fine");
	i++;
	
	expect(i);
	//NON VA BENE...RESTITUISCE TRUE MA NN LO TOGLIE// visualizzo l'esito della richiesta. Se esito positivo refresh della
		// rubrica...NN SO SE POSSO CONTTOLLAROL
	contact=tester.get_contacts();
	
});		






test("testapplyFilterByString()", function(){

	tester.set_contacts({0:{name:"Laura",surname: "Pausini", email:"laupau@gmail.com"},1:{name:"Serena",surname: "Pausini", email:"serpau@gmail.com"}});
	
	
	var i=0;
	
	//controllo che funzioni il filtro per un contatto esistente tramite il nome
	var cerca_nome="Laura";
	var ele=tester.applyFilterByString(cerca_nome);
	equal(ele.length,1,"Trova un solo contatto che ha nome Laura");
	i++;
	equal(ele[0],"0","Il contatto Laura ha id 0");
	i++;
	
	//controllo che funzioni il filtro per un contatto esistente tramite il cognome
	var cerca_cognome="Pausini";
	ele=tester.applyFilterByString(cerca_cognome);
	equal(ele.length,2,"Trova due contatti avente cogome Pausini");
	i++;
	equal(ele[0],"0","il primo contatto Pausini ha id 0");
	i++;
	equal(ele[1],"1","il secondo contatto Pausini ha id 1");
	i++;
	
	
	//controllo che funzioni il filtro per un contatto esistente tramite il cognome
	var cerca_mail="laupau@gmail.com"
	ele=tester.applyFilterByString(cerca_mail);
	equal(ele.length,1,"Trova un solo contatto che ha email laupau@gmail.com");
	i++;
	equal(ele[0],"0","il contatto che ha email laupau@gmail.com ha id 0");
	i++;
	
	//controllo che la funzione filtro non trovi un nome non presente
	var cerca_nome_non_presente="Flavia";
	ele=tester.applyFilterByString(cerca_nome_non_presente);
	notEqual(ele.length,1,"Non trova nessun contatto con nome Flavia");
	i++;
	
	
	expect(i);
});		




/*
test("testaddGroup()", function(){

	var element = document.getElementById("AddressBookPanel");
	
	var i=0;
	var amici={nome: "amici", id: "50", contacts: "array(0)"};
	try{tester.addGroup("amici");ok(false,"errore non rilevato");i++;}catch(err){equal(err,"Il gruppo che stai cercando di inserire non esiste.","rilevato errore");i++;}
	
	
	
	expect(i);
});		

*/


/*

test("testBlockUser()", function(){
	var i=0;
	var element=document.getElementById("AddressBookPanel");
	//var laura={name:"Laura", surname:"Pausini", email:"laupau@gmail.com",id:"12",picturePath:"xx.png",state:"offline", blocked:"false"};
	tester.set_contacts({0:{name:"Laura", surname:"Pausini", email:"laupau@gmail.com",id:"0",picturePath:"xx.png",state:"offline", blocked:true},1:{name:"Flavia", surname:"Bacco", email:"flaba@gmail.com",id:"1",picturePath:"xx.png",state:"offline", blocked:false}});
	
	var contact=tester.get_contacts();
	var laura=contact[0];
	var flavia=contact[1];

	
	try{tester.blockUser(laura);ok(false,"errore non rilevato");i++;}catch(err){equal(err,"Contatto già bloccato.","blocco di Laura non possibile...già bloccato!");i++;}
	
	
	
	
	equal(tester.blockUser(flavia),true,"verificato blocco attivato");
	i++;
	expect(i);
});
*/

/*

test("testUnlockUser()", function(){	
	var i=0;
	var element=document.getElementById("AddressBookPanel");
	//var laura={name:"Laura", surname:"Pausini", email:"laupau@gmail.com",id:"12",picturePath:"xx.png",state:"offline", blocked:"false"};
	tester.set_contacts({0:{name:"Laura", surname:"Pausini", email:"laupau@gmail.com",id:"0",picturePath:"xx.png",state:"offline", blocked:true},1:{name:"Flavia", surname:"Bacco", email:"flaba@gmail.com",id:"1",picturePath:"xx.png",state:"offline", blocked:false}});
	
	var contact=tester.get_contacts();
	var laura=contact[0];
	var flavia=contact[1];

	try{tester.unlockUser(flavia);ok(false,"errore non rilevato");i++;}catch(err){equal(err,"Contatto già sbloccato.","sblocco di Laura non possibile...già sbloccato!");i++;}
	
	
	
	equal(tester.unlockUser(laura),true,"blocco avvenuto con successo");
	i++;
	expect(i);
});*/