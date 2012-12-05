<?php
	include_once('Script/Function.php');

	session_start();

	if(!isset($_SESSION['licensed']) || $_SESSION['licensed'] != 1)
	{
		header('refresh: 0; url = "index.php"');
		die();
	}
	
	if($_GET['flag']==1){
		
		$insertRequisiti = "INSERT INTO Requisiti(ID, Descrizione, Requisito_padre, Tipologia, Priorita) VALUES (\"".$_GET['id']."\", \"".$_GET['descrizione']."\"";
		if($_GET['padre']=="") {$insertRequisiti = $insertRequisiti . ", \"".$_GET['id']."\"";}
		else {$insertRequisiti = $insertRequisiti . ", \"".$_GET['padre']."\"";}
		$insertRequisiti = $insertRequisiti . ", \"".$_GET['tipologia']."\", \"".$_GET['priorita']."\")";
		if(!QueryDatabase($insertRequisiti)) {header('Location: AddRequirements.php?flag=error');}
		
		$insertRequisitiFonti="INSERT INTO RequisitiFonti(ID_fonte, ID_requisito, Descrizione) VALUES (\"".$_GET['fonte']."\", \"".$_GET['id']."\", \"".$_GET['motivo']."\")";
		if (!QueryDatabase($insertRequisitiFonti)){
			//Elimino il requisito inserito prima con $insertRequisiti 
			$delete="DELETE FROM Requisiti WHERE ID=\"".$_GET['id']."\"";
			QueryDatabase($delete);
			header('Location:AddRequirements.php?flag=error');
		}else{
			header('Location:AddRequirements.php?flag=done');
		}
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
				<div id = "PageBodyColumnLeft">&nbsp;</div>
				<div id = "PageBodyColumnRight">
					<h2>Requisiti</h2>
					<blockquote>
						<?php if($_GET['flag']=="error"){echo "<p class=\"error\">Si &egrave; verificato un errore con l'inserimento dei dati. Riprova!</p>";}?>
						<?php if($_GET['flag']=="done"){echo"<p class=\"info\">Inserimento avvenuto con successo!</p>";} ?>
						<h3>Inserimento</h3>
						<?php
						$Requisiti = 'SELECT ID FROM Requisiti ORDER BY ID';
						$Fonti = 'SELECT ID, Descrizione FROM Fonti ORDER BY ID';
						?>
						<form method="get" action="AddRequirements.php?flag=1">
							<ul>
								<li><span class="label">id:</span><input type="text" name="id"></li>
								<li><span class="label">descrizione:</span><textarea cols="90" rows="5" name="descrizione"></textarea></li>
								<li><span class="label">requisito padre:</span><?php DisplaySelect($Requisiti, "padre", true);?></li>
								<li><span class="label">tipologia:</span>
								<select name="tipologia">
									<option value="F">Funzionale</option>
									<option value="Q">Qualit&agrave;</option>
									<option value="P">Prestazionale</option>
									<option value="V">Vincolo</option>
								</select>
								</li>
								<li><span class="label">priorita:</span>
									<select name="priorita">
										<option value="O">Obbligatorio</option>
										<option value="D">Desiderabile</option>
										<option value="F">Facoltativo</option>
									</select>
								</li>
								<input type="hidden" name="flag" value="1" />
								<li><span class="label">fonte:</span><?php DisplaySelect($Fonti, "fonte");?></li>
								<li><span class="label">motivo:</span><br /><textarea cols="90" rows="5" name="motivo"></textarea></li>
							</ul>
							<input type="button" onclick="window.location.href='DisplayRequirements.php'" value="Indietro" />
							<input type="submit" value="Inserisci"/>
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
