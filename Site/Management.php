<?php
	session_start();

	if(!isset($_SESSION['licensed']) || $_SESSION['licensed'] != 1)
	{
		header('refresh: 0; url = "index.php"');
		die();
	}
?>
<html>
	<head>
		<title>Gestione del progetto</title>
		<link rel = "stylesheet" type = "text/css" media = "screen" href = "CSS/Screen.css"/>
	</head>
	<body>
		<div id = "PageContainer">
			<div id = "PageHeader">
				<?php include_once('Layout/Header.php'); ?>
			</div>
			<div id = "MainMenu">
				<?php include_once('Layout/MainMenu.php'); ?>
			</div>
			<div id = "PageBody">
				<div id = "PageBodyColumnLeft">
					<?php include_once('Layout/SecondaryMenu.php'); ?>
				</div>
				<div id = "PageBodyColumnRight">
					<h2>Gestione del progetto</h2>
					<blockquote>
						<p>In questa sezione &egrave; possibile amministrare e gestire le informazioni inerenti al progetto.</p>
					</blockquote>
				</div>
			</div>
			<div id = "PageFooter">
				<?php include_once('Layout/Footer.php'); ?>
			</div>
		</div>
	</body>
</html>
