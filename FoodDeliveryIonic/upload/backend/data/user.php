<?php 
	$user=json_decode(file_get_contents('php://input'));  //get user from 
	if($user->mail=='hani@hamrounihani.com' && $user->pass=='codecanyon') 
		session_start();
		$_SESSION['uid']=uniqid('ang_');
		print $_SESSION['uid'];
?>