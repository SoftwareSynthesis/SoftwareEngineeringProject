<?php
	session_start();
?>
<html>
	<head>
		<title>Revisione di Progettazione</title>
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
					<h3>Materiale</h3>
					<ul>
						<li><a href = "">Documentazione</a></li>
						<li><a href = "">Presentazione</a></li>
					</ul>
				</div>
				<div id = "PageBodyColumnRight">
					<h2>Revisione di Progettazione</h2>
					<blockquote>
						<p>La consegna della documentazione per poter accedere alla gara d'appalto &egrave; da consegnarsi<br/>entro il giorno...</p>
					</blockquote>
					<h2>Evento associato</h2>
					<blockquote>
						<p>In questa sezione verr&agrave; descritto l'evento.</p>
						<p>Di seguito &egrave possibile osservare il risultato dell'evento. Tra parentesi, nell'intestazione,<br/> &egrave; indicato il peso in centesimi.</p>
					</blockquote>
					<table class = "Table">
						<tr>
							<th>Tecnico Metodologica (0.67)</th>
							<th>Presentazione (0.33)</th>
							<th>Totale</th>
						</tr>
						<tr>
							<td>0.00</td>
							<td>0.00</td>
							<td>0.00</td>
						</tr>
					</table>
					<blockquote>
						Da <a href = "">qui</a> &egrave; possibile scaricare il dettaglio della valutazione effettutata dal docente.</p>
					</blockquote>
				</div>
			</div>
			<div id = "PageFooter">
				<?php include_once('Layout/Footer.php'); ?>
			</div>
		</div>
	</body>
</html>
