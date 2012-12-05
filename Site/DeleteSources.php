<?php
	include_once('Script/Function.php');

	session_start();

	if(!isset($_SESSION['licensed']) || $_SESSION['licensed'] != 1)
	{
		header('refresh: 0; url = "index.php"');
		die();
	}
?>

<?php
	$query="DELETE FROM Fonti WHERE ID=\"".$_GET['id']."\"";
	if (QueryDatabase($query))
		{header('Location: DisplaySources.php?flag=done');}
	else
		{header('Location: DisplaySources.php?flag=error');}
?>