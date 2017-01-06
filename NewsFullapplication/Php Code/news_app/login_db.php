<?php
include('includes/db_connection.php');
include('includes/function.php'); 

$username= mysql_real_escape_string($_POST['username']);
$password= mysql_real_escape_string($_POST['password']);
 
$authUser = adminUser($username,$password);
 
if (isset($_SESSION['ADMIN_USERNAME'])){
	
    header( "Location:dashboard");
	exit;
}
else
{
	$_SESSION['msg']="1";
	header( "Location:index");
	exit;
}
 
?> 