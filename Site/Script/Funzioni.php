<?php 
	include_once("Global.php");

	function crea_file($file)
	{
		//crea un file, se esiste lo sovrascrive
		if(file_exists($file))
		{
			unlink($file);
		}
		$file = fopen($file, 'a+');
		fclose($file);
	}

	function scrivi($file, $text)
	{
		$file = fopen($file, 'a+');
		fputs($file, utf8_encode($text));
		//fputs($file, "\n");
		fclose($file);
	}

	function stampa_casi($file)
	{
		$casi_uso = "SELECT * FROM Casi_uso ORDER BY ID";
		$result = throw_query($casi_uso);
		scrivi($file, "\section{Diagrammi dei casi d'uso}\n\n");
		while($row = mysql_fetch_assoc($result))
		{
			scrivi($file, "\subsection{".$row["ID"].": ".$row["Nome_caso"]."}");
			//-------------------------INIZIO LA STAMPA DEL SINGOLO CASO D'USO------------------------------
			scrivi($file, "\n\begin{figure}[h!]\n\centering\n\includegraphics[width=.8\\textwidth]{".$row["ID"]."}\n\caption{".$row["ID"].": ".$row["Nome_caso"]."}\label{".$row["ID"]."}\n\end{figure}");
			scrivi($file, "\n\begin{description}\n\item{\scshape\bfseries Attori principali:}");
			//STAMPA ATTORI PRINCIPALI: creo una nuova query che mi fa ottenere gli attori
			$attori = "SELECT * FROM Attori JOIN AttoriCasi ON(ID = ID_attore) Where AttoriCasi.ID_caso = '".$row["ID"]."'";
			stampa_attori($file, $attori);
			scrivi($file, "\n");
			scrivi($file, "\item{\scshape\bfseries Scopo e descrizione:} ".$row["Scopo"]."\n");
			scrivi($file, "\item{\scshape\bfseries Precondizione:} ".$row["Pre"]."\n");
			scrivi($file, "\item{\scshape\bfseries Postcondizione:} ".$row["Post"]."\n");
			//STAMPA SCENARIO PRINCIPALE
			$principale = "SELECT * FROM Scenari WHERE Caso_uso_associato = '".$row["ID"]."' AND Principale = 1";
			$result_scenario_principale = throw_query($principale);
			$scenario_principale = mysql_fetch_assoc($result_scenario_principale);
			scrivi($file, "\item{\scshape\bfseries Illustrazione scenario principale:} ".$scenario_principale["Descrizione"]."\n");
			//STAMPA SCENARI SECONDARI
			$secondari = "SELECT * FROM Scenari WHERE Caso_uso_associato = '".$row["ID"]."' AND Principale != 1";
			stampa_scenari_secondari($file, $secondari);
			//-----------------------------------CASO D'USO TERMINATO----------------------------------------
			scrivi($file, "\end{description}\n\n");
		}
	}

	function stampa_attori($file, $attori)
	{
		$result_attori = throw_query($attori);
		$count = mysql_num_rows($result_attori);
		$index = 1;
		if($count >= 1)
		{
			while($row_attori = mysql_fetch_assoc($result_attori))
			{	
				if($count != $index)
					scrivi($file, "".$row_attori["ID"].", ");
				else
					scrivi($file, "".$row_attori["ID"].".");
				$index = $index + 1;
			}
		}
	}

	function stampa_scenari_secondari($file, $scenari)
	{
		$result_scenario_secondari = throw_query($scenari);
		$count = mysql_num_rows($result_scenario_secondari);
		if($count >= 1)
		{
			while($row_scenari_secondari = mysql_fetch_assoc($result_scenario_secondari))
			{
				scrivi($file, "\item{\scshape\bfseries Illustrazione scenario alternativo:} ".$row_scenari_secondari["Descrizione"]."\n");
			}
		}
	}

	function stampa_requisiti($file)
	{
		scrivi($file, "\section{Requisiti}\n\n\begin{tabularx}{\\textwidth}{lXl}\n");
		scrivi($file, "\\toprule Codice & Requisito & Fonte\\\\\n");
		scrivi($file, "\midrule\n");
		$requisiti = "SELECT ID, Requisiti.Descrizione, ID_fonte FROM Requisiti LEFT JOIN RequisitiFonti ON (Requisiti.ID = ID_requisito)";
		$result_requisiti = throw_query($requisiti);
		while($row_requisiti = mysql_fetch_assoc($result_requisiti))
		{
			scrivi($file, "".$row_requisiti["ID"]." & ".$row_requisiti["Descrizione"]." & ".$row_requisiti["ID_fonte"]." \\\\\n");
		}
		scrivi($file, "\bottomrule\n");
		scrivi($file, "\end{tabularx}\n");
	}
	
	function stampa_tracciamenti_requisiti($file)
	{
		scrivi($file, "\section{Tracciamento dei requisiti}\n\n\begin{tabularx}{\\textwidth}{lXl}\n");
		scrivi($file, "\\toprule Codice UC & Nome UC  & Requisito\\\\\n");
		scrivi($file, "\midrule\n");
		$requisiti_casi = "SELECT * FROM RequisitiCasi LEFT JOIN Casi_uso ON (ID_caso = ID)";
		$result_tracciamenti = throw_query($requisiti_casi);
		while($row_tracciamenti = mysql_fetch_assoc($result_tracciamenti))
		{
			scrivi($file, "".$row_tracciamenti["ID_caso"]." & ".$row_tracciamenti["Nome_caso"]." & ".$row_tracciamenti["ID_requisito"]." \\\\\n");
		}
		scrivi($file, "\bottomrule\n");
		scrivi($file, "\end{tabularx}\n");
	}
	
	function throw_query($query, $close = true)
	{
		global $nome_server;
		global $user_name;
		global $password;
		global $nome_db;
		$connessione = mysql_connect($nome_server, $user_name, $password) or die('CONNESSIONE FALLITA');
		mysql_select_db($nome_db, $connessione) or die(mysql_error($connessione));
		$risultato = mysql_query($query, $connessione) or die(mysql_error($connessione));
		if($close == true)
			mysql_close($connessione);
		return $risultato;
	}
?>
