<?php
	// questa deve ancora essere fatta
	$addressbook = array(
	  1 => array(
	         "name" => "Flavia",
	         "surname" => "Bacco",
	         "email" => "flabacco@gmail.com",
	         "id" => 1,
	         "picturePath" => "x.png",
	         "state" => "available",
	         "blocked" => false
	       ),
	  0 => array(
	         "name" => "Flavia",
	         "surname" => "Pausini",
	         "email" => "flapau@gmail.com",
	         "id" => "0",
	         "picturePath" => "y.png",
	         "state" => "occupied",
	         "blocked" => false
	       ),
	   2 => array(
	   	     "name" => "Flavia",
	         "surname" => "Vento",
	         "email" => "flavento@gmail.com",
	         "id" => "2",
	         "picturePath" => "z.png",
	         "state" => "offline",
	         "blocked" => false
	   )
	);
	
	echo json_encode($addressbook);
?>
