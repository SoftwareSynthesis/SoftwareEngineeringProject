<?
/* Variabile che contiene l'operazione da far effettuare al server */
$operation = $_REQUEST["operation"];
$result = array();

/* Suddivido i vari casi */
switch($operation) {
    case "login" :
        if ($_REQUEST["username"]=="pr@va.com" & $_REQUEST["password"]=="p") {
            $result["name"] = "Mario";
            $result["surname"] = "Rossi";
            $result["picturePath"] = "default.png";
            $result["id"] = 2;
            $result["email"] = "mario.rossi@gmail.com";
        } else {
            $result = null;
        }
        break;

    case "question" :
        $result = "Quale e' la risposta?";
        break;

    default :
        $result = null;
        break;
}

echo json_encode($result);
?>