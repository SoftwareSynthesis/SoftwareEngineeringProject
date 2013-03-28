<?php

	/* simula un accesso avvenuto con successo al sistema restituendo
	 * un oggetto che contiene l'insieme dei dati di interesse al client
	 */
  	$user = array();
  	$user["name"] = "Paride";
  	$user["surname"] = "Bacco";
  	$user["picturePath"] = "img/contactImg/2.png";
  	$user["id"] = 2;
  	$user["email"] = "pariba@gmail.com";
  	
    echo json_encode($user);

?>