<?php
	include_once("Script/Funzioni.php");
	include_once("Setting/Global.php");
	global $file;
	crea_file($file);
	stampa_casi($file);
	stampa_requisiti($file);
	stampa_tracciamenti_requisiti($file);

	session_start();

	if(!isset($_SESSION['licensed']) || $_SESSION['licensed'] != 1)
	{
		header('refresh: 0; url = "index.php"');
		die();
	}
?>
<html>
	<head>
		<title>Stampa</title>
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
				<div id = "PageBodyColumnLeft">&nbsp;</div>
				<div id = "PageBodyColumnRight">
					<h2>File di Stampa</h2>
					<blockquote>
						</blockquote>
						<?php print '<a href="'.$file.'"> Scarica stampa </a>'; ?>
                                </div>
			</div>
			<div id = "PageFooter">
				<?php include_once('Layout/Footer.php'); ?>
			</div>
		</div>
	</body>
</html>
	