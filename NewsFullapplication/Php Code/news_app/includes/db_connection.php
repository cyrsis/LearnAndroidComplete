<?php 
 error_reporting(0);
 ob_start();
 #Session Start
 session_start();
 header("Content-Type: text/html;charset=UTF-8");
 # Selects the database

 
 if($_SERVER['HTTP_HOST']=="localhost"){
 
	 DEFINE ('DB_USER', 'root');
	 DEFINE ('DB_PASSWORD', '');
	 DEFINE ('DB_HOST', 'localhost');
	 DEFINE ('DB_NAME', 'news');
 
 }else
 {
	 DEFINE ('DB_USER', 'apptific_news_app');
	 DEFINE ('DB_PASSWORD', ',-}o_40$wN3c');
	 DEFINE ('DB_HOST', 'localhost'); //host name depends on server
	 DEFINE ('DB_NAME', 'apptific_news'); 
 }

 $mysqli = @mysql_connect (DB_HOST, DB_USER, DB_PASSWORD) OR die ('Could not connect to MySQL');
 
 @mysql_select_db (DB_NAME) OR die ('Could not select the database');
 
?>