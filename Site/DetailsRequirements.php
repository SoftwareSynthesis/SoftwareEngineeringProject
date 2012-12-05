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
		<title>Requisiti</title>
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
						<li><a href = "AddRequirements.php">INSERISCI NUOVO REQUISITO</a></li>
					</ul>
				</div>
				<div id = "PageBodyColumnRight">
					<h2>Requisiti</h2>
					<blockquote>
						<h3>Dettagli</h3>
					
					<?php
						$query = "SELECT r.ID, r.descrizione as rDesc, r.Requisito_padre, r.Tipologia, r.Priorita, rf.descrizione as rfDesc, f.ID as FID, f.descrizione as fDesc FROM ((Requisiti r join RequisitiFonti rf ON r.ID=rf.ID_requisito) join Fonti f ON rf.ID_fonte=f.ID) WHERE r.ID=\"".$_GET['id']."\"";
						$requisito=mysql_fetch_assoc(QueryDatabase($query));
					?>
					<ul>
						<li><span class="label">id:</span><?php echo $requisito['ID'];?></li>
						<li><span class="label">descrizione:</span><?php echo $requisito['rDesc'];?></li>
						<li><span class="label">requisito padre:</span><?php echo $requisito['Requisito_padre'];?></li>
						<li><span class="label">tipologia:</span><?php echo $requisito['Tipologia'];?></li>
						<li><span class="label">priorita:</span><?php echo $requisito['Priorita'];?></li>
						<li><span class="label">motivo:</span><?php echo $requisito['rfDesc'];?></li>
						<li><span class="label">fonte:</span><?php echo $requisito['FID'];?></li>
					</ul>
					</blockquote>
				</div>
			</div>
			<div id = "PageFooter">
				<?php include_once('Layout/Footer.php'); ?>
			</div>
		</div>
	</body>
</html>
