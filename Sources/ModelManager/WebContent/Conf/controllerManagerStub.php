<?
/* Variabile che contiene l'operazione da far effettuare al server */
$operation = $_REQUEST["operation"];
$result = array();

/* Suddivido i vari casi */
switch($operation) {
    case "login" :
        $result["name"] = "Mario";
        $result["surname"] = "Rossi";
        $result["picturePath"] = "default.png";
        $result["id"] = 2;
        $result["email"] = "mario.rossi@gmail.com";
        break;

    case "question" :
        $result = "Quale e' la risposta?";
        break;

    default :
        $result = false;
        break;
}

echo json_encode($result);
?>