window.onload = function() {
    // VARIABILI GLOBALI per cui i programmatori meritano il taglio delle dita
    configurationFile = "Conf/servletlocation.xml";
    mediator = new PresenterMediator();
    communicationcenter = new CommunicationCenter();
    // crea l'interfaccia di autenticazione
    mediator.buildLoginUI();
};

window.onbeforeunload = function() {
    mediator.logout();
};

Object.size = function(obj) {
    var size = 0, key;
    for (key in obj) {
        if (obj.hasOwnProperty(key))
            size++;
    }
    return size;
}; 


//fatto da Stefano, eventualmente da sistemare (forse...)
var needToConfirm = true;
window.onbeforeunload = askConfirm;
window.onunload = unloadPage;
var isDelete = true;
function unloadPage(){
	if(isDelete){
		logout();
	}   
}
function askConfirm(){
	if (needToConfirm){
		return "Verra' effettuato automaticamente il logout.";
	} 
	else{
		isDelete = false;
	}   
}