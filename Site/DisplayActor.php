<?php
	include_once('Script/Function.php');

	session_start();

	if(!isset($_SESSION['licensed']) || $_SESSION['licensed'] != 1)
		header('refresh: 0; url = "index.php"');
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
				<?php include_once('Layout/MainMenu.php'); ?>
			</div>
			<div id = "PageBody">
				<div id = "PageBodyColumnLeft">
					<?php include_once('Layout/SecondaryMenu.php'); ?>
				</div>
				<div id = "PageBodyColumnRight">
					<h2>Elenco attori</h2>
					<blockquote>
						Segue un elenco degli attori:
					</blockquote>
					<table class = "Table">
					<?php
						$query = 'SELECT ID, Descrizione FROM Attori ORDER BY ID';
						DisplayTable($query, 'DetailActor.php');
					?>
					</table>
				</div>
			</div>
			<div id = "PageFooter">
				<?php include_once('Layout/Footer.php'); ?>
			</div>
		</div>
	</body>
</html>
