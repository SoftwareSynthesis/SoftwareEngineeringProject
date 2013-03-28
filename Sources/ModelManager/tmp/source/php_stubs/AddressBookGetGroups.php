<?php
 	// scarica l'insieme dei gruppi come array associativo
	
	$groups = array(
	  1 => array(
	       "name" => "amici",
	       "id" => "1",
	       "contacts" => array(1, 0)
	     ),
	  0 => array(
	         "name" => "famiglia",
	         "id" => "0",
	         "contacts" => array(0)
	       )
	);
	
	echo json_encode($groups);
?>