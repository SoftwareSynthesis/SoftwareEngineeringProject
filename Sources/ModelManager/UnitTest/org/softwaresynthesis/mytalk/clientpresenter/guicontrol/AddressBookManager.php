<?php

$operation = isset($_POST["operation"]) ? $_POST["operation"] : "";

// non prendo mai l'id dell'utente perché nel server ce le abbiamo in $_SESSION

if ($operation == 0) {
	// scarica la rubrica come array associativo
	
	$addressbook = array(
	1 => array("name" => "Flavia",
	             "surname" => "Bacco"),
	0 => array("name" => "Laura")
	);
	
	echo json_encode($addressbook);

} elseif ($operation == 1) {

} elseif ($operation == 2) {

} elseif ($operation == 3) {

} elseif ($operation == 4) {

} elseif ($operation == 5) {

} elseif ($operation == 6) {

} elseif ($operation == 7) {

} elseif ($operation == 8) {

} elseif ($operation == 9) {

} elseif ($operation == 10) {

} elseif ($operation == 11) {

} elseif ($operation == 12) {

}

?>