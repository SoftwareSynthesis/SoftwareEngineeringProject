<?php
	session_start();

	include_once('Script/Function.php');

	if(isset($_POST['send']))
	{
		$error = '';
		$recipient = (isset($_POST['recipient'])) ? trim($_POST['recipient']) : '';
		$sender = (isset($_POST['sender']) && preg_match('|^[\w\-]+(\.[\w\-]+)*@[\w\-]+(\.[\w\-]+)+$|', $_POST['sender'])) ? trim($_POST['sender']) : '';
		$subject = (isset($_POST['subject']) && !empty($_POST['subject'])) ? $_POST['subject'] : '';
		$message = (isset($_POST['messageText']) && !empty($_POST['messageText'])) ? $_POST['messageText'] : '';

		if(!empty($sender))
		{
			if(!empty($recipient))
			{
				if(!empty($subject))
				{
					if(!empty($message))
					{
						mail($recipient, $subject, $message, $sender);
						header('refresh: 0; url = "Confirm.php"');
					}
					else
						$error = 'Inserire il testo del messaggio per poter inviare correttamente la mail';
				}
				else
					$error = 'Inserire il l\'oggetto per poter inviare correttamente la mail';
			}
			else
				$error = 'Selezionare un destinatario valido a cui spedira la mail';
		}
		else
			$error = 'Inserire un indirizzo mail, per il mittente, valido';
	}
?>
<html>
	<head>
		<title>Contattaci</title>
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
					<?php
						if(isset($error))
							echo '<p class = "Error">' . $error . '</p>';
					?>
					<h2>Contatti</h2>
					<blockquote>
						<p>In questa sezione potrai contattarci per maggiori informazioni, sar&agrave; nostra premura<br/>risponderLe al pi&ugrave; presto.</p>
						<form name = "Contacts" action = "Contacts.php" method = "POST">
							<table>
								<tr>
									<td>Mittente</td>
									<td><input type = "text" name = "sender"/></td>
								</tr>
								<tr>
									<td>Destinatario</td>
									<?php
										$query = "SELECT Matricola, Cognome, Nome, EMail FROM Membri ORDER BY Cognome";
										$members = QueryDatabase($query);
										$option = '';
										$find = false;
										echo '<td>';
										echo '<select name = "recipient">';
										while($row = mysql_fetch_assoc($members))
										{
											$option = '<option value = "'. $row['EMail'] . '"';
											if(isset($_GET['member']) && $_GET['member'] == $row['Matricola'])
											{
											$option = $option . ' selected = "selected"';
												$find = true;
											}
											$option = $option . '>' . $row['Cognome'] . ' ' . $row['Nome'] . '</option>';
											echo $option;
											$option = '';
										}
										$option = '<option value = "info@softwaresynthesis.org"';
										if(!$find)
										$option = $option . ' selected = "selected"';
										$option = $option . '>Software Synthesis Team</option>';
										echo $option;
										echo '</select>';
										echo '</td>';
									?>
								</tr>
								<tr>
									<td>Oggetto della richiesta</td>
									<td><input type = "text" name = "subject"/></td>
								</tr>
								<tr>
									<td>Testo del messaggio</td>
									<td><textarea name = "messageText" rows = "20" cols = "60"></textarea></td>
								</tr>
								<tr>
									<td>&nbsp;</td>
									<td>
										<input type = "reset" value = "Pulisci campi della form"/>
										<input type = "submit" value = "Invia e-mail" name = "send"/>
									</td>
								</tr>
							</table>
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
