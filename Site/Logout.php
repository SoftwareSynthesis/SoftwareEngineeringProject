<?php
	session_start();

	$sessionName = session_name();
	session_destroy();

	if(isset($_COOKIE[$sessionName]))
		setcookie($sessionName, '', time() - 3600, '/');

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
					<h2>Disconnessione in corso...</h2>
					<blockquote>
						<p>Disconnessione dal sistema avvenuta con successo.</p>
						<p>Stai per essere rimandato alla home page, se il tuo browser non ha il reindirizzamento attivato<br/>premi <a href = "index.php">qui</a>.</p>
					</blockquote>
				</div>
			</div>
			<div id = "PageFooter">
				<?php include_once('Layout/Footer.php'); ?>
			</div>
		</div>
	</body>
</html>
