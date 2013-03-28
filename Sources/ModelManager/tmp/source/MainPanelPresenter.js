/**
 * Presenter incaricato di gestire il pannello principale
 * 
 * @constructor
 * @this {MainPanelPresenter}
 * @author Elena Zecchinato
 * @author Diego Beraldin
 */
function MainPanelPresenter() {
	/***************************************************************************
	 * VARIABILI PRIVATE
	 **************************************************************************/
	// elemento controllato da questo presenter
	var element = document.getElementById("MainPanel");

	/***************************************************************************
	 * METODI PUBBICI
	 **************************************************************************/
	/**
	 * Costruisce il pannello principale dell'applicazione che occupa il posto
	 * centrale della finestra
	 * 
	 * @author Elena Zecchinato
	 */
	this.initialize = function() {
		element.style.display = "block";
		element.innerHTML = "";
	};

	/**
	 * Visualizza un elemento interno al pannello principale
	 * 
	 * @param {HTMLDivElement}
	 *            node nodo che deve essere visualizzato all'interno del
	 *            pannello principale
	 * @author Diego Beraldin
	 */
	this.displayChildPanel = function(node) {
		element.innerHTML = "";
		element.appendChild(node);
	};

	/**
	 * Rende invisibile il pannello principale
	 * 
	 * @author Diego Beraldin
	 */
	this.hide = function() {
		element.style.display = "none";
	};
}
