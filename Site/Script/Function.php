<?php
	include_once('Setting/Setting.php');

	/*
		Funzione per interrogare il database
	*/
	function QueryDatabase($query, $closeOnTerm = true)
	{
		global $mysqlServer;
		global $mysqlUsername;
		global $mysqlPassword;
		global $mysqlDbName;

		$connection = mysql_connect($mysqlServer, $mysqlUsername, $mysqlPassword) or die('Errore durante un tentativo di connessione al server MySql');
		mysql_select_db($mysqlDbName, $connection) or die(mysql_error($connection));
		$result = mysql_query($query, $connection) or die(mysql_error($connection));
		if($closeOnTerm == true)
			mysql_close($connection);
		return $result;
	}

	function GeneratesTableHeader($resultSet, $hasOption)
	{
		$index = 0;
		echo '<tr>';
		for($i = 0; $i < mysql_num_fields($resultSet); $i = $i + 1)
		{
			echo '<th>';
			echo mysql_field_name($resultSet, $i);
			echo "</th>";
		}
		if($hasOption)
			echo "<th class=\"option\">Opzioni</th>";
		echo "</tr>";
	}
	
	function PopulateTable($resultSet, $optionMenu)
	{
		$primayKey = '';
		$index = 0;
		while($row = mysql_fetch_assoc($resultSet))
		{
			$primaryKey = $row['ID'];
			$index = 0;
			echo '<tr>';
			foreach($row as $key => $value)
			{
				echo '<td>';
				echo $value;
				echo '</td>';
			}
			echo '<td>';
			foreach($optionMenu as $key => $option)
			{
				echo '<a href = "' . $option['page'] . $primaryKey . '">' . $option['label'] . '</a>';
				if($index < count($optionMenu) - 1)
					echo ' --- ';
				$index = $index + 1;
			}
			echo '</td>';
			echo '</tr>';
		}
	}

	function DisplayTable($query, $optionMenu, $closeOnTerm = true)
	{
		$resultSet = QueryDatabase($query, $closeOnTerm);
		if(mysql_num_rows($resultSet) > 0 && mysql_num_fields($resultSet))
		{
			if(count($optionMenu) > 0)
				GeneratesTableHeader($resultSet, true);
			else
				GeneratesTableHeader($resultSet, false);
			PopulateTable($resultSet, $optionMenu);
			return true;
		}
		else
			return false;
	}
	
	function DisplaySelect($query, $selectName, $noneOption = false, $closeOnTerm = true)
	{
		$resultSet = QueryDatabase($query, $closeOnTerm);
		if(mysql_num_rows($resultSet) > 0)
		{
			echo "<select name=\"".$selectName."\">";
			if($noneOption) echo "<option value=\"\">NULL</option>";
			while($row = mysql_fetch_array($resultSet))
			{
				echo "<option value=\"".$row[0]."\">".$row[0]."</option>";
			}
			echo "</select>";
			return true;
		}
		else
			return false;
	}
	
	function DisplayCheck($query, $checkName, $noneOption = false, $closeOnTerm = true)
	{
		$resultSet = QueryDatabase($query, $closeOnTerm);
		if(mysql_num_rows($resultSet) > 0)
		{
			echo"<ul>";
			$count=1;
			if($noneOption)
			{
				echo "<li><input type=\"checkbox\" name=\"".$checkName.$count."\" value=\"\" /><label for=\"".$checkName.$count."\">NULL</label></li>";
				$count = $count+1;
			}
			while($row = mysql_fetch_array($resultSet))
			{
				echo "<li><input type=\"checkbox\" id=\"".$checkName.$count."\" name=\"".$checkName.$count."\" value=\"".$row[0]."\" /><label for=\"".$checkName.$count."\">".$row[0]."</label></li>";
				$count = $count+1;
			}
			echo"</ul>";
			return $count;
		}
		else
			return false;
	}
?>
