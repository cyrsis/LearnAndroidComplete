<?php include('includes/db_connection.php');
			include('session_check.php');
			include('common.php');
			
			mysql_query("SET NAMES 'utf8'"); 
?>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    
    <title><?php echo $lang['PAGE_TITLE']; ?></title>
    <meta content="IE=edge,chrome=1" http-equiv="X-UA-Compatible">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <link rel="stylesheet" type="text/css" href="lib/bootstrap/css/bootstrap.css">
    
    <link rel="stylesheet" type="text/css" href="stylesheets/theme.css">
    <link rel="stylesheet" href="lib/font-awesome/css/font-awesome.css">

    <script src="lib/jquery-1.7.2.min.js" type="text/javascript"></script>
		<script src="ckeditor/ckeditor.js"></script>
    <!-- Demo page code -->

    <style type="text/css">
        #line-chart {
            height:300px;
            width:800px;
            margin: 0px auto;
            margin-top: 1em;
        }
        .brand { font-family: georgia, serif; }
        .brand .first {
            color: #ccc;
            font-style: italic;
        }
        .brand .second {
            color: #fff;
            font-weight: bold;
        }
    </style>

    <!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>

  </head>

  <!--[if lt IE 7 ]> <body class="ie ie6"> <![endif]-->
  <!--[if IE 7 ]> <body class="ie ie7 "> <![endif]-->
  <!--[if IE 8 ]> <body class="ie ie8 "> <![endif]-->
  <!--[if IE 9 ]> <body class="ie ie9 "> <![endif]-->
  <!--[if (gt IE 9)|!(IE)]><!--> 
  <body class=""> 
  <!--<![endif]-->
    
    <div class="navbar">
        <div class="navbar-inner">
                <ul class="nav pull-right">
                    
                     
                    <li id="fat-menu" class="dropdown">
                        <a href="#" role="button" class="dropdown-toggle" data-toggle="dropdown">
                            <i class="icon-user"></i> <?php echo $_SESSION['ADMIN_USERNAME'];?>
                            <i class="icon-caret-down"></i>
                        </a>

                        <ul class="dropdown-menu">
                            <li><a tabindex="-1" href="edit-profile"><?php echo $lang['DEFAULT_MSG_MY_ACCOUNT']; ?></a></li>
                            <li class="divider"></li>                             
                            <li><a tabindex="-1" href="logout"><?php echo $lang['DEFAULT_MSG_LOGOUT']; ?></a></li>
                        </ul>
                    </li>
                    
                </ul>
                <a class="brand" href="dashboard"><img src="images/logo.png"></a>
                
                <div id="languages">
                <a href="?lang=en"><img src="images/en.png"></a>
                <a href="?lang=es"><img src="images/es.png"></a>
                <a href="?lang=ar"><img src="images/ar.png"></a>  
                <a href="?lang=de"><img src="images/de.png"></a>
                <a href="?lang=fr"><img src="images/fr.png"></a>                
                </div>
        </div>
    </div>
    