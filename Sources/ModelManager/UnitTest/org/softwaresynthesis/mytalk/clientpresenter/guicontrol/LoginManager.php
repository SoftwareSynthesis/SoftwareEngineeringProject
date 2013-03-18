<?php

  $operation = isset($_POST["operation"])? $_POST["operation"] : "";
  $username = isset($_POST["username"])? $_POST["username"]: "";
  $password = isset($_POST["password"])? $_POST["password"]: "";
  
  if ($operation == 1) {
    echo "";
  } elseif ($operation == 2) {
    $user = array();
    $user["username"] = $username;
    $user["id"] = 0;
    if (isset($_POST["name"])) {
      $user["name"] = $_POST["name"];
    }
    if (isset($_POST["surname"])) {
      $user["surname"] = $_POST["surname"];
    }
    if (isset($_POST["picturePath"])) {
      $user["picturePath"] = "http://softwaresynthesis.org/pictures/" . $_POST["picturePath"];
    }
    echo json_encode($user, JSON_PRETTY_PRINT);
  } elseif ($operation == 3) {
  	echo "Come si chiama la mia gatta?";
  }

?>