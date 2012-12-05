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
					<h3>Revisioni</h3>
					<ul>
						<li><a href = "RequirementsRevision.php">Revisione dei Requisiti (RR)</a></li>
						<li><a href = "DesignRevision.php">Revisione di Progettazione (RP)</a></li>
						<li><a href = "QualificationRevision.php">Revisione di Qualifica (RQ)</a></li>
						<li><a href = "AcceptanceRevision.php">Revisione di Accettazione (RA)</a></li>
					</ul>
				</div>
				<div id = "PageBodyColumnRight">
					<h2>Il progetto</h2>
					<blockquote>
						<p>In questa pagina verr&agrave; illustrato il capitolato d'appalto scelto dai membri del team, si potr&agrave;<br/> seguire lo stato del progetto visionando i documenti che saranno redatti nel corso dello<br/>sviluppo.<br/>Per qualsiasi informazione potrete contattarci e sar&agrave; nostra premura rispondervi al pi&ugrave;<br/>presto.</p>
					</blockquote>
					<h2>Il capitolato d'appalto</h2>
					<blockquote>
						<p>I capitolati di appalto saranno resi pubblici il 28 Novembre 2012 dopo la presentazione da<br/>parte del docente del corso.</p>
					</blocquote>
				</div>
			</div>
			<div id = "PageFooter">
				<?php include_once('Layout/Footer.php'); ?>
			</div>
		</div>
	</body>
</html>
