<?php

    // simula l'invio di un messaggio di avvenuto recupero della password
    $answer = isset($_POST["answer"])? $_POST["answer"] : "";
    
    if ($answer == "tricolore") {
      echo "true";
    } else {
      echo "false";
    }

?>
