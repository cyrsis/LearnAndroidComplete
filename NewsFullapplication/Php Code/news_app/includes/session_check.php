<?php
 
	if(!isset($_SESSION['ADMIN_USERNAME']))
	{
		 
		session_destroy();
		 
		echo "<script language=javascript type=text/javascript>document.location='index';</script>";
	}
	 
	
?>