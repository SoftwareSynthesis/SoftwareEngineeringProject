window.onload = function() {
	// VARIABILI GLOBALI per cui i programmatori meritano il taglio delle dita
	configurationFile = "Conf/servletlocationintegrationtest.xml";
	mediator = new PresenterMediator();
	communicationcenter = new CommunicationCenter();
	// crea l'interfaccia di autenticazione
	mediator.buildLoginUI();
};