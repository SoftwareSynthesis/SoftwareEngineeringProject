<?php
	session_start();

	header('refresh: 5; url = "index.php"');
?>
<html>
	<head>
		<title>Software Synthesis Team</title>
		<link rel = "stylesheet" type = "text/css" media = "screen" href = "CSS/Screen.css"/>
	</head>
	<body>
		<div id = "PageContainer">
			<div id = "PageHeader">
				<?php include_once('Layout/Header.php'); ?>
			</div>
			<div id = "MainMenu">
				<?php
					include_once('Layout/MainMenu.php');
					if(isset($_SESSION['licensed']) && $_SESSION['licensed'] == 1)
						include_once('Layout/SecondaryMenu.php');
				?>
			</div>
			<div id = "PageBody">
				<div id = "PageBodyColumnLeft">
					&nbsp;
				</div>
				<div id = "PageBodyColumnRight">
					<h2>Messaggio inviato correttamente</h2>
					<blockquote>
						<p>Il suo messaggio &egrave; stato spedito correttamente. Verrai rimandato, entro pochi secondi, alla home page.<br/>Se il tuo browser non ha il reindirizzamento automatico attivo premi <a href = "index.php">qui</a>.</p>
					</blockuote>
				</div>
			</div>
			<div id = "PageFooter">
				<?php include_once('Layout/Footer.php'); ?>
			</div>
		</div>
	</body>
</html>
