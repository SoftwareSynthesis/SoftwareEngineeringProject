<?php
	include_once('Script/Function.php');

	session_start();

	if(!isset($_SESSION['licensed']) || $_SESSION['licensed'] != 1)
	{
		header('refresh: 0; url = "index.php"');
		die();
	}
  if($_GET['flag']==1){
		$req_padre=$_GET['padre'];
		if (empty($req_padre)){$req_padre=$_GET['RID'];}
		//modifica requisito
		$modifica = "UPDATE Requisiti SET Descrizione = \"".$_GET['descrizione']."\", Requisito_padre = \"".$req_padre."\", Tipologia = \"".$_GET['tipologia']."\", Priorita = \"".$_GET['priorita']."\" WHERE ID=\"".$_GET['RID']."\"";
		if(!QueryDatabase($modifica))
			{header('Location: DisplayRequirements.php?flag=error');}
		else{
			$modifica2="UPDATE RequisitiFonti SET Descrizione = \"".$_GET['motivo']."\", ID_fonte= \"".$_GET['fonte']."\" WHERE ID_requisito=\"".$_GET['RID']."\"";
			if(!QueryDatabase($modifica2))
				{header('Location: DisplayRequirements.php?flag=error');}
			else
				{header('Location: DisplayRequirements.php?flag=done');}
		}
	}
?>

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

        <?php
          $query = "SELECT r.ID as RID, r.descrizione as rDesc, r.Requisito_padre, r.Tipologia, r.Priorita, rf.descrizione as rfDesc, f.ID as FID, f.Descrizione as fDesc FROM ((Requisiti r join RequisitiFonti rf ON r.ID=rf.ID_requisito) join Fonti f ON rf.ID_fonte=f.ID) WHERE r.ID=\"".$_GET['id']."\"";
			$requisito=mysql_fetch_assoc(QueryDatabase($query));
			$Requisiti = 'SELECT ID FROM Requisiti ORDER BY ID';
			$Fonti = 'SELECT ID, Descrizione FROM Fonti ORDER BY ID';
        ?>
        <form method="get" action="ModifyRequirements.php">
							<ul>
								<li><span class="label">ID:</span><?php echo $_GET['id']; ?></li>
								<li><span class="label">descrizione:</span><textarea cols="90" rows="5" name="motivo"><?php echo $requisito['rDesc']; ?></textarea></li>
								<li><span class="label">requisito padre:</span><?php DisplaySelect($Requisiti, "padre", true);?><span class="error">requisito padre precedente: <span class="info"><?php echo $requisito['Requisito_padre']; ?></span></span></li>
								<li><span class="label">tipologia:</span>
								  <select name="tipologia">
									<option value="F">Funzionale</option>
									<option value="Q">Qualit&agrave;</option>
									<option value="P">Prestazionale</option>
									<option value="V">Vincolo</option>
								  </select>
								<span class="error">tipologia precedente: <span class="info"><?php echo $requisito['Tipologia']; ?></span></span>
								</li>

								<li><span class="label">priorita:</span>
									<select name="priorita">
										<option value="O">Obbligatorio</option>
										<option value="D">Desiderabile</option>
										<option value="F">Facoltativo</option>
									</select>
								<span class="error">priorit&agrave; precedente: <span class="info"><?php echo $requisito['Priorita']; ?></span></span>
								</li>

								<input type="hidden" name="flag" value="1" />
								<input type="hidden" name="RID" value="<?php echo $requisito['RID']; ?>" />

								<li><span class="label">fonte:</span><?php DisplaySelect($Fonti, "fonte");?><span class="error">fonte precedente: <span class="info"><?php echo $requisito['FID']; ?></span></span></li>
								<li><span class="label">motivo:</span><br /><textarea cols="90" rows="5" name="motivo"><?php echo $requisito['rfDesc']; ?></textarea></li>
							</ul>
							<input type="button" onclick="window.location.href='DisplayRequirements.php'" value="Indietro" />
							<input type="submit" value="Modifica"/>
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
