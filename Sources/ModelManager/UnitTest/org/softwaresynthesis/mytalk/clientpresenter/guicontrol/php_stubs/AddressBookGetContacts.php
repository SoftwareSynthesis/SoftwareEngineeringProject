<?php
 	// scarica la rubrica come array associativo
	
	$addressbook = array(
	  1 => array(
	         "name" => "Flavia",
	         "surname" => "Bacco",
	         "email" => "flabacco@gmail.com",
	         "id" => "1",
	         "picturePath" => "img/contactImg/1.png",
	         "state" => "available",
	         "blocked" => false
	       ),
	  0 => array(
	         "name" => "Laura",
	         "surname" => "Pausini",
	         "email" => "laupau@gmail.com",
	         "id" => "0",
	         "picturePath" => "img/contactImg/Default.png",
	         "state" => "offline",
	         "blocked" => true
	       )
	);
	
	echo json_encode($addressbook);
?>