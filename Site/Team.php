<?php
	session_start();

	include_once('Script/Function.php');
?>
<html>
	<head>
		<title>Il team di sviluppo</title>
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
					<h2>I membri del team</h2>
					<blockquote>
						<p>Il team di sviluppo si &egrave; formato nell'ottobre 2012 al fine di prendere in appalto uno dei<br/>capitolati commissionati da un committente e proposti dal docente.</p>
						<p>Il team &egrave; attualmente composto da sette componenti, tutti in regola con i requisiti minimi<br/>richiesti, e tutti altamente motivati al raggiungimento dell'obiettivo prefissato.</p>
					</blockquote>
					<?php
						$query = 'SELECT Matricola, Cognome, Nome FROM Membri ORDER BY Cognome';
						$members = QueryDatabase($query);
						echo '<table class = "Table">';
						echo '<tr>';
						echo '<th>Matricola</th>';
						echo '<th>Cognome</th>';
						echo '<th>Nome</th>';
						echo '<th>Opzioni</th>';
						echo '</tr>';
						while($row = mysql_fetch_assoc($members))
						{
							echo '<tr>';
							echo '<td>' . $row['Matricola'] . '</td>';
							echo '<td>' . $row['Cognome'] . '</td>';
							echo '<td>' . $row['Nome'] . '</td>';
							echo '<td><a href = "Contacts.php?member=' . $row['Matricola'] . '">CONTATTA</a></td>';
							echo '</tr>';
						}
						echo '</table>';
					?>
				</div>
			</div>
			<div id = "PageFooter">
				<?php include_once('Layout/Footer.php'); ?>
			</div>
		</div>
	</body>
</html>
