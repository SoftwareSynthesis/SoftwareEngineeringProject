<?
/* Variabile che contiene l'operazione da far effettuare al server */
$operation = $_REQUEST["operation"];
$result = array();

/* Suddivido i vari casi */
switch($operation) {
    case "login" :
        if ($_REQUEST["username"] == "pr@va.com" & $_REQUEST["password"] == "p") {
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

    case "getContacts" :
        $result = array(1 => array("name" => "Andrea", "surname" => "Rizzi", "email" => "a.rizzi@gmail.com", "id" => "1", "picturePath" => "img/contactImg/Default.png", "state" => "available", "blocked" => false), 2 => array("name" => "Stefano", "surname" => "", "email" => "s.farro@gmail.com", "id" => "2", "picturePath" => "img/contactImg/Default.png", "state" => "offline", "blocked" => true), 3 => array("name" => "", "surname" => "Beraldin", "email" => "d.beraldin@gmail.com", "id" => "3", "picturePath" => "img/contactImg/Default.png", "state" => "available", "blocked" => true), 4 => array("name" => "", "surname" => "", "email" => "skivo.marco@gmail.com", "id" => "4", "picturePath" => "img/contactImg/Default.png", "state" => "available", "blocked" => false), 5 => array("name" => "Andrea", "surname" => "Mene", "email" => "ma@gmail.com", "id" => "5", "picturePath" => "img/contactImg/Default.png", "state" => "occupied", "blocked" => false), );
        break;

    case "getGroups" :
        $result = array(1 => array("name" => "amici", "id" => "1", "contacts" => array(2, 3, 4, 5)), 2 => array("name" => "mona", "id" => "2", "contacts" => array(1)), 3 => array("name" => "addrBookEntry", "id" => "3", "contacts" => array(1, 2, 3, 4, 5)));
        break;

    default :
        $result = null;
        break;
}

echo json_encode($result);
?>