<?php
	include "koneksi.php";
	
	$Filename =  $_POST['Filename'];
	$Fileconten  = $_FILES['Fileconten']['name'];
	$Photoname  = $_FILES['photo']['name'];
	$date =  $_POST['date'];
	
	
	
	if ($_FILES['photo']['type'] == "image/gif" || $_FILES['photo']['type'] == "image/jpeg" || $_FILES['photo']['type'] == "image/x-png") 
	{
      move_uploaded_file ($_FILES['photo']['tmp_name'],"image/".$_FILES['photo']['name']);
	  move_uploaded_file ($_FILES['Fileconten']['tmp_name'],"artikel/".$_FILES['Fileconten']['name']);
      $sql_query = mysql_query("INSERT INTO uploadF (`id`,`Filename`,`Fileconten`,`photo`,`date`) VALUES (null,'$Filename','$Fileconten','$Photoname',now())");
      if ($sql_query)
	  {
          echo "{success:true}";
      } 
	  else
	  {
           echo "{success:true}";
      }
   }
		

?>