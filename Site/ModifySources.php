<?php
	include_once('Script/Function.php');

	session_start();

	if(!isset($_SESSION['licensed']) || $_SESSION['licensed'] != 1)
	{
		header('refresh: 0; url = "index.php"');
		die();
	}
  if($_GET['flag']==1){
		//modifica fonte
		$modifica = "UPDATE Fonti SET ID=\"".$_GET['id']."\", Descrizione = \"".$_GET['descrizione']."\" WHERE ID=\"".$_GET['ID']."\"";
		if(!QueryDatabase($modifica)) {header('Location: DisplaySources.php?flag=error');}
    else {header('Location: DisplaySources.php?flag=done');}
	}
?>

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
				<div id = "PageBodyColumnLeft">&nbsp;</div>
				<div id = "PageBodyColumnRight">
					<h2>Fonti</h2>
					<blockquote>

        <?php
          $query="SELECT ID, Descrizione FROM Fonti WHERE ID=\"".$_GET['id']."\"";
          $source=mysql_fetch_assoc(QueryDatabase($query));
        ?>
        <form method="get" action="ModifySources.php">
							<ul>
							<li><span class="label">id:</span><input type="text" " name="id" value="<?php echo $source['ID']; ?>" /></li>
							<li><span class="label">descrizione:</span><textarea cols="90" rows="5" name="descrizione"><?php echo $source['Descrizione']; ?></textarea></li>
							</ul>
							<input type="hidden" name="ID" value="<?php echo $source['ID']; ?>" />
							<input type="hidden" name="flag" value="1">
							<input type="button" onclick="window.location.href='DisplaySources.php'" value="Indietro" />
							<input type="submit" value="Modifica" />
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
