<?php

	/* simula un accesso avvenuto con successo al sistema restituendo
	 * un oggetto che contiene l'insieme dei dati di interesse al client
	 */
  	$user = array();
  	$user["name"] = "Laura";
  	$user["surname"] = "Pausini";
  	$user["picturePath"] = "xxx.png";
  	$user["id"] = 0;
  	
    echo json_encode($user, JSON_PRETTY_PRINT);

?>