<?php   
	#Admin Messages	 
	 
	switch ($_SESSION['lang']) {
  case 'en':
  	include('messages.en.php');
  break;  

  case 'es':   
		include('messages.es.php');
  break;
	
	case 'ar':   
		include('messages.ar.php');
  break;
	
	case 'de':   
		include('messages.de.php');
  break;
	
	case 'fr':   
		include('messages.fr.php');
  break;
	 
  default:
  include('messages.en.php');

}
	  
?>
