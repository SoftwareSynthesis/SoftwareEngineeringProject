<?php
	echo '<ul>';
	echo '<li><a href = "index.php">HOME PAGE</a></li';
	echo '><li><a href = "Team.php">IL TEAM</a></li';
	echo '><li><a href = "Project.php">IL PROGETTO</a></li';
	echo '><li><a href = "Contacts.php">CONTATTI</a></li';
	if(isset($_SESSION['licensed']) && $_SESSION['licensed'] == 1)
		echo '><li><a href = "Logout.php">ESCI</a></li>';
	else
		echo '><li><a href = "Login.php">ACCEDI</a></li>';
	echo '</ul>';
?>
