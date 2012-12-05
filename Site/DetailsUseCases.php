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
						<h3>Dettagli</h3>
						<?php
						$queryCaso = "SELECT c.Nome_caso, c.ID, c.Scopo, c.Pre, c.Post FROM Casi_uso c WHERE c.ID=\"".$_GET['id']."\" ORDER BY ID";
						$queryAttori = "SELECT ID_attore FROM AttoriCasi WHERE ID_caso=\"".$_GET['id']."\" ORDER BY ID_attore";
						$queryScenari = "SELECT s.Descrizione, s.Principale FROM (Scenari s JOIN Casi_uso c ON s.Caso_uso_associato=c.ID) WHERE c.ID=\"".$_GET['id']."\" ORDER BY s.Principale DESC";
						$queryRequisiti = "SELECT ID_requisito FROM RequisitiCasi WHERE ID_caso=\"".$_GET['id']."\" ORDER BY ID_requisito";
						
						$resultCaso = queryDatabase($queryCaso);
						$resultAttori = queryDatabase($queryAttori);
						$resultScenari = queryDatabase($queryScenari);
						$resultRequisiti = queryDatabase($queryRequisiti);
						
						
						$casoUso = mysql_fetch_assoc($resultCaso);	
						?>
						<ul>
						<li><span class="label">nome:</span><?php echo $casoUso['ID'] . " - " . $casoUso['Nome_caso']; ?></li>
						<li><span class="label">scopo:</span><?php echo $casoUso['Scopo']; ?></li>
						<li><span class="label">pre:</span><?php echo $casoUso['Pre']; ?></li>
						<li><span class="label">post:</span><?php echo $casoUso['Post']; ?></li>
						<li><span class="label">attori:</span>
							<ul>
								<?php
								while($attore = mysql_fetch_assoc($resultAttori)){
									echo"\n<li>".$attore['ID_attore']."</li>";
								}
								?>
							</ul>
						</li>
						<li><span class="label">scenari:</span>
							<ul>
								<?php
								while($scenario = mysql_fetch_assoc($resultScenari)){
									$principale = "alternativo:";
									if($scenario['Principale']==1){ $principale = "principale"; }
									echo"\n<li><span class=\"label\">".$principale."</span>".$scenario['Descrizione']."</li>";
								}
								?>
							</ul>
						</li>
						<li><span class="label">requisiti associati:</span>
							<ul>
								<?php
								while($requisito = mysql_fetch_assoc($resultRequisiti)){
									echo"\n<li>".$requisito['ID_requisito']."</li>";
								}
								?>
							</ul>
						</li>
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
