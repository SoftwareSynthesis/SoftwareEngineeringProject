<?php
	include_once('Script/Function.php');

	session_start();

	if(!isset($_SESSION['licensed']) || $_SESSION['licensed'] != 1)
	{
		header('refresh: 0; url = "index.php"');
		die();
	}
?>
<html>
	<head>
		<title>Fonti</title>
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
					include_once('Layout/SecondaryMenu.php');
				?>
			</div>
			<div id = "PageBody">
				<div id = "PageBodyColumnLeft">
					<ul>
						<li><a href = "AddSources.php">INSERISCI NUOVA FONTE</a></li>
					</ul>
				</div>
				<div id = "PageBodyColumnRight">
					<h2>Fonti</h2>
					<blockquote>
						<?php
							if($_GET['flag']=="error"){echo "<p class=\"error\">Si &egrave; verificato un errore con la query. Riprova!</p>";}
							if($_GET['flag']=="done"){echo "<p class=\"info\">Query avvenuta con successo!</p>";}
						?>
						<h3>Presenti</h3>
						Segue un elenco degli attori:
					</blockquote>
					<table class = "Table">
					<?php
						$query = 'SELECT ID, Descrizione FROM Fonti ORDER BY ID';
						$optionMenu = array("modifica" => array("label" => "MODIFICA", "page" => "ModifySources.php?id="), "elimina" => array("label" => "ELIMINA", "page" => "DeleteSources.php?id="));
						DisplayTable($query, $optionMenu);
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
