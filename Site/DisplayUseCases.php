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
		<title>Casi d'Uso</title>
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
						<li><a href = "AddUseCases.php">INSERISCI NUOVO CASO D'USO</a></li>
					</ul>
				</div>
				<div id = "PageBodyColumnRight">
					<h2>Casi d'Uso</h2>
					<blockquote>
						<h3>Presenti</h3>
						Segue un elenco dei casi d'uso:
					</blockquote>
					<table class = "Table">
					<?php
						$query = 'SELECT ID, Nome_caso FROM Casi_uso ORDER BY ID';
						$optionMenu = array("dettagli" => array("label" => "DETTAGLI", "page" => "DetailsUseCases.php?id="));
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
</body>
</html>
