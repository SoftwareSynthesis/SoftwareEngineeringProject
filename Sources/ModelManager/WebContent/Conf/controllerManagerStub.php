<?
/* Variabile che contiene l'operazione da far effettuare al server */
$operation = $_REQUEST["operation"];
$result = array();

/* Suddivido i vari casi */
switch($operation) {
    // operazioni login utente
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

    case "register" :
        break;

    case "logout" :
        break;

    case "answer" :
        break;

    // operazioni rubrica utente
    case "getContacts" :
        $result = array(1 => array("name" => "Andrea", "surname" => "Rizzi", "email" => "a.rizzi@gmail.com", "id" => "1", "picturePath" => "img/contactImg/Default.png", "state" => "available", "blocked" => false), 2 => array("name" => "Stefano", "surname" => "", "email" => "s.farro@gmail.com", "id" => "2", "picturePath" => "img/contactImg/Default.png", "state" => "offline", "blocked" => true), 3 => array("name" => "", "surname" => "Beraldin", "email" => "d.beraldin@gmail.com", "id" => "3", "picturePath" => "img/contactImg/Default.png", "state" => "available", "blocked" => true), 4 => array("name" => "", "surname" => "", "email" => "skivo.marco@gmail.com", "id" => "4", "picturePath" => "img/contactImg/Default.png", "state" => "available", "blocked" => false), 5 => array("name" => "Andrea", "surname" => "Mene", "email" => "ma@gmail.com", "id" => "5", "picturePath" => "img/contactImg/Default.png", "state" => "occupied", "blocked" => false), );
        break;

    case "getGroups" :
        $result = array(1 => array("name" => "amici", "id" => "1", "contacts" => array(2, 3, 4, 5)), 2 => array("name" => "mona", "id" => "2", "contacts" => array(1)), 3 => array("name" => "addrBookEntry", "id" => "3", "contacts" => array(1, 2, 3, 4, 5)));
        break;

    case "addContact" :
        break;

    case "blockContact" :
        break;

    case "addGroup" :
        break;

    case "deleteGroup" :
        break;

    case "addInGroup" :
        break;

    case "deleteFromGroup" :
        break;

    case "unblockContact" :
        break;

    case "deleteContact" :
        break;

    case "accountSettings" :
        break;

    // operazioni lista chiamate
    case "addCall" :
        break;

    case "getCalls" :
		$result = array(
			array("id" => 1, "start" => "Wed May 22 10:42:02 CEST 2013", "caller" => true), 
			array("id" => 3, "start" => "Tue May 21 17:42:02 CEST 2013", "caller" => false), 
			array("id" => 2, "start" => "Mon May 20 22:42:02 CEST 2013", "caller" => false)
		); 
		      break;

    // operazioni segreteria telefonica
    case "getMessages" :
        break;

    case "addMessage" :
        break;

    case "deleteMessage" :
        break;

    case "updateMessage" :
        break;

    // operazioni ricerca utenti del sistema
    case "search" :
        $result = array(10 => array("name" => "Marco", "surname" => "Pegoraro", "email" => "p.m@gmail.com", "id" => "10", "picturePath" => "img/contactImg/Default.png", "blocked" => false), 11 => array("name" => "Elia", "surname" => "", "Cavallaro" => "e.a@gmail.com", "id" => "11", "picturePath" => "img/contactImg/Default.png", "blocked" => false), 12 => array("name" => "", "surname" => "Garavello", "email" => "cobra@gmail.com", "id" => "12", "picturePath" => "img/contactImg/Default.png", "blocked" => false), 13 => array("name" => "", "surname" => "", "email" => "skivo.marco@gmail.com", "id" => "13", "picturePath" => "img/contactImg/Default.png", "blocked" => false), 14 => array("name" => "Giulia", "surname" => "", "email" => "giulia@gmail.com", "id" => "14", "picturePath" => "img/contactImg/Default.png", "blocked" => false), );
        break;

    // ritorno null nel caso non sia gestita la operation
    default :
        $result = null;
        break;
}

echo json_encode($result);
?>
