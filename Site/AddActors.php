<?php
	include_once('Script/Function.php');

	session_start();

	if(!isset($_SESSION['licensed']) || $_SESSION['licensed'] != 1)
	{
		header('refresh: 0; url = "index.php"');
		die();
	}
	
	if($_GET['flag']==1){
		//inserimento attore
		$insertQueryAttore = "INSERT INTO Attori(ID, Descrizione) VALUES (\"".$_GET['id']."\", \"".$_GET['nome']."\")";
		if(!QueryDatabase($insertQueryAttore)) {header('Location: AddActors.php?flag=error');} else {header('Location: AddActors.php?flag=done');}
	}
?>
<html>
	<head>
		<title>Attori</title>
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
					<h2>Attori</h2>
					<blockquote>
						<?php if($_GET['flag']=="error"){echo"<p class=\"error\">Si &egrave; verificato un errore con l'inserimento dei dati. Riprova!</p>";} ?>
						<?php if($_GET['flag']=="done"){echo"<p class=\"info\">Inserimento avvenuto con successo!</p>";} ?>
						<h3>Inserimento</h3>
						<form method="get" action="AddActors.php">
							<ul>
							<li><span class="label">id:</span><input type="text" name="id" /></li>
							<li><span class="label">descrizione:</span><textarea cols="90" rows="5" name="nome"></textarea></li>
							</ul>
							<input type="hidden" name="flag" value="1">
							<input type="button" onclick="window.location.href='DisplayActors.php'" value="Indietro" />
							<input type="submit" value="Inserisci" />
						</form>
					</blockquote>
				</div>
			</div>
			<div id = "PageFooter">
				<?php include_once('Layout/Footer.php'); ?>
			</div>
		</div>
	</body>
</html>
