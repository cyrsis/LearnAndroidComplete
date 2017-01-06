<?php include('includes/header.php');?>
    

<?php include('includes/menu.php');?>

<?php

	//Total category count
	$category_query = "SELECT COUNT(*) as num FROM tbl_news_category";
	$total_category = mysql_fetch_array(mysql_query($category_query));
	$total_category = $total_category['num'];
	
	//Total news count
	$news_query = "SELECT COUNT(*) as num FROM tbl_news";
	$total_news = mysql_fetch_array(mysql_query($news_query));
	$total_news = $total_news['num'];

?>
   
    <div class="content">
        
        <div class="header">
             

            <h1 class="page-title"><?php echo $lang['MENU_DASHBOARD']; ?></h1>
        </div>
        
        <ul class="breadcrumb">
            <li><a href="dashboard"><?php echo $lang['DEFAULT_MSG_HOME']; ?></a> <span class="divider">/</span></li>
            <li class="active"><?php echo $lang['MENU_DASHBOARD']; ?></li>
        </ul>

        <div class="container-fluid">
            <div class="row-fluid">
                    

<div class="row-fluid">
 
    <div class="block">
        <a href="#page-stats" class="block-heading" data-toggle="collapse"><?php echo $lang['DEFAULT_MSG_LATEST_STATS']; ?></a>
        <div id="page-stats" class="block-body collapse in">

            <div class="stat-widget-container">
                <div class="stat-widget">
                    <div class="stat-button">
                        <p class="title"><?php echo $total_category;?></p>
                        <p class="detail"><?php echo $lang['PAGE_NEWS_CATEGORY']; ?></p>                         
                    </div>
                </div>
                
                <div class="stat-widget">
                    <div class="stat-button">
                        <p class="title"><?php echo $total_news;?></p>
                        <p class="detail"><?php echo $lang['PAGE_NEWS_LIST']; ?></p>                         
                    </div>
                </div>

    
            </div>
        </div>
    </div>
</div>

                 
           
  <?php include('includes/footer.php');?>                  