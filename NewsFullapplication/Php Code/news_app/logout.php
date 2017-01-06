<?php
include('includes/db_connection.php');
include('includes/function.php'); 

	unset($_SESSION['ADMIN_ID']);
	unset($_SESSION['ADMIN_USERNAME']);

	$_SESSION['msg']="3";
	header( "Location:index");
	exit;
  
?> 