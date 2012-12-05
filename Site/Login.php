<?php
	include_once('Script/Function.php');
	
	session_start();

	if(isset($_POST['verify']))
	{
		$query = 'SELECT ID FROM Membri WHERE Username = \'' . $_POST['username'] . '\' AND Password = PASSWORD(\'' . $_POST['password'] . '\');';
		$confirm = QueryDatabase($query);
		if(mysql_num_rows($confirm) == 1)
		{
			$_SESSION['licensed'] = 1;
			header('refresh: 0; url = "index.php"');
		}
	}
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
					<h2>Accesso all'area riservata</h2>
					<blockquote>
						<p>Accesso riservato ai membri del team.</p>
						<form name = "Login" action = "Login.php" method = "POST">
							<table>
								<tr>
									<td>Username</td>
									<td><input type = "text" name = "username"/></td>
								</tr>
								<tr>
									<td>Password</td>
									<td><input type = "password" name = "password"/></td>
								</tr>
								<tr>
									<td>&nbsp;</td>
									<td><input type = "submit" name = "verify" value = "Accedi"/></td>
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
