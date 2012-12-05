<?php
	include_once('Script/Function.php');

	session_start();

	if(!isset($_SESSION['licensed']) || $_SESSION['licensed'] != 1)
	{
		header('refresh: 0; url = "index.php"');
		die();
	}
	
	if($_GET['flag']==1){
		//inserimento caso d'uso
		$insertQueryCaso = "INSERT INTO Casi_uso(ID, Scopo, Pre, Post, Nome_caso) VALUES (\"".$_GET['id']."\", \"".$_GET['scopo']."\", \"".$_GET['pre']."\", \"".$_GET['post']."\", \"".$_GET['nome']."\")";
		if(!queryDatabase($insertQueryCaso)) {header('Location: AddUseCases.php?flag=error');}
		
		//inserimento attori
		$insertQueryActors = "INSERT INTO AttoriCasi(ID_attore, ID_caso) VALUES ";
		$prima = true;
		foreach($_GET as $key => $value){
			if(preg_match('|^(attore)[0-9]{1,3}$|', $key)){
				if(!$prima){$insertQueryActors = $insertQueryActors.", ";}
				$prima = false;
				$insertQueryActors = $insertQueryActors . "(\"" . $value . "\", \"" . $_GET['id'] . "\")";
			}
		}
		if(!queryDatabase($insertQueryActors)) {header('Location: AddUseCases.php?flag=error');}

		//inserimento in RequisitiCasi
		$insertQueryRequisitiCasi = "INSERT INTO RequisitiCasi(ID_requisito, ID_caso) VALUES ";
		$prima = true;
		foreach($_GET as $key => $value){
			if(preg_match('|^(requisito)[0-9]{1,3}$|', $key)){
				if(!$prima){$insertQueryRequisitiCasi = $insertQueryRequisitiCasi.", ";}
				$prima = false;
				$insertQueryRequisitiCasi = $insertQueryRequisitiCasi . "(\"" . $value . "\", \"" . $_GET['id'] . "\")";
			}
		}
		if(!queryDatabase($insertQueryRequisitiCasi)) {header('Location: AddUseCases.php?flag=error');}
		
		//inserimento scenari
		$insertQueryScenari = "INSERT INTO Scenari(Descrizione, Caso_uso_associato, Principale) VALUES (\"".$_GET['scenarioPrincipale']."\", \"".$_GET['id']."\", 1)";
		foreach($_GET as $key => $value){
			if(preg_match('|^(al)[0-9]{1,3}$|', $key) and $value!=""){
				$insertQueryScenari = $insertQueryScenari . ", (\"" . $value . "\", \"" . $_GET['id'] . "\", 0)";
			}
		}
		if(!queryDatabase($insertQueryScenari)) {header('Location: AddUseCases.php?flag=error');} else {header('Location: AddUseCases.php?flag=done');}
	}	
?>
<html>
	<head>
		<title>Casi d'Uso</title>
		<link rel = "stylesheet" type = "text/css" media = "screen" href = "CSS/Screen.css"/>
		
		<script type="text/javascript">
		var numeroScenarioAlternativo = 1;
		
		function add(num){
			<!--Creo <li>-->
			var li=document.createElement("li");
			
			<!--Creo <span>-->
			var span=document.createElement("span");
			span.setAttribute("class", "label");
			var label=document.createTextNode("Alternativo #"+num);
			span.appendChild(label);
			
			<!--Creo <br />-->
			var br=document.createElement("br");
			
			<!--Creo <textarea>-->
			var textarea=document.createElement("textarea");
			textarea.setAttribute("name", "al"+num);
			textarea.setAttribute("cols", "90");
			textarea.setAttribute("rows", "5");

			<!--Appendo tutti i nodi ad <li>-->
			li.appendChild(span);
			li.appendChild(br);
			li.appendChild(textarea);
			
			<!--Appenado <li> alla lista nel DOM-->
			document.getElementById("listaScenari").appendChild(li);
		}
		</script>
		
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
					<?php
					$Attori = "SELECT ID, Descrizione FROM Attori ORDER BY ID";
					$Requisiti = "SELECT ID FROM Requisiti ORDER BY ID";
					?>
					<h2>Casi d'Uso</h2>
					<blockquote>
						<?php if($_GET['flag']=="error"){echo"<p class=\"error\">Si &egrave; verificato un errore con l'inserimento dei dati. Riprova!</p>";} ?>
						<?php if($_GET['flag']=="done"){echo"<p class=\"info\">Inserimento avvenuto con successo!</p>";} ?>
						<h3>Inserimento</h3>
						<form method="get" action="AddUseCases.php">
							<ul>
							<li><span class="label">id:</span><input type="text" name="id" /></li>
							<li><span class="label">nome:</span><input type="text" name="nome" /></li>
							<li><span class="label">scopo:</span><br /><textarea name="scopo" cols="90" rows="5"></textarea></li>
							<li><span class="label">pre:</span><br /><textarea name="pre" cols="90" rows="5"></textarea></li>
							<li><span class="label">post:</span><br /><textarea name="post" cols="90" rows="5"></textarea></li>
							<li><span class="label">attore:</span><?php DisplayCheck($Attori, "attore"); ?></li>
							<li><span class="label">scenari:</span><br />
								<ul id="listaScenari">
									<li><span class="label">principale:</span><br /><textarea name="scenarioPrincipale" cols="90" rows="5"></textarea></li>
								</ul>
								<input type="button" name="addScenario" value="aggiungi scenari alternativi" onclick="add(numeroScenarioAlternativo); numeroScenarioAlternativo++;" />
							</li>
							<li><span class="label">requisiti associati:</span><?php DisplayCheck($Requisiti, "requisito"); ?></li>
							</ul>
							<input type="hidden" name="flag" value="1" />
							<input type="button" onclick="window.location.href='DisplayUseCases.php'" value="Indietro" />
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
