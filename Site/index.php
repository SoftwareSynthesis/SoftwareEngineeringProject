<?php
	session_start();
?>
<html>
	<head>
		<title>Software Synthesis Team</title>
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
					if(isset($_SESSION['licensed']) && $_SESSION['licensed'] == 1)
						include_once('Layout/SecondaryMenu.php');
				?>
			</div>
			<div id = "PageBody">
				<div id = "PageBodyColumnLeft">
					&nbsp;
				</div>
				<div id = "PageBodyColumnRight">
					<h2>Benvenuti in Software Synthesis</h2>
					<blockquote>
						<p>Questo &egrave; il sito ufficiale del team di sviluppo che si &egrave; formato per <b>analizzare</b>, <b>apprendere</b><br/>e <b>mettere in pratica</b> i concetti chiave dell'<b>Ingegneria del Software</b>. Per fare ci&ograve; il gruppo si<br/>incaricher&agrave;, entro la fine di Novembre 2012, di prendere a carico uno dei capitolati<br/>proposti dal docente del corso.</p>
						<p>L'<b>obbiettivo</b> che il team si propone &egrave; di sviluppare un prodotto software <b>seguendo</b> ed<br/><b>applicando</b> al meglio quanto appreso durante le lezioni dell'intero semestre e rimanendo<br/>nei tempi di consegna concordati con il committente.</p>
						<p>Potrete seguire, con noi, i passi che consentono lo sviluppo di un progetto software<br/>per capire il modo con cui risolviamo problematiche nell'ambito software. Potrete <a href = "Contacts.php">contattarci</a><br/> in qualsiasi momento per avere delucidazioni sullo stato di avanzamento del progetto.</p>
					</blockquote>
				</div>
			</div>
			<div id = "PageFooter">
				<?php include_once('Layout/Footer.php'); ?>
			</div>
		</div>
	</body>
</html>
