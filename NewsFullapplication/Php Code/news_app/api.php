<?php include("includes/db_connection.php");
 
 	mysql_query("SET NAMES 'utf8'"); 
	//mysql_query('SET CHARACTER SET utf8');
	include("purchase.php");	

	if(purchase_status() == false)
 	{
		echo "<p>Sorry, we are unable to verify your purchase.</p>";
		exit;
	}
	else
	{
	
	
	if(isset($_GET['cat_id']))
	{
			//$query="SELECT * FROM tbl_news_category WHERE cid='".$_GET['cat_id']."' ORDER BY tbl_news_category.cid DESC";		
			//$resouter = mysql_query($query);
			
			$query="SELECT * FROM tbl_news_category c,tbl_news n WHERE c.cid=n.cat_id and c.cid='".$_GET['cat_id']."' ORDER BY n.nid DESC";			
			$resouter = mysql_query($query);
			
	}
	else if(isset($_GET['latest_news']))
	{
			$limit=$_GET['latest_news'];	 	
			
			$query="SELECT * FROM tbl_news_category c,tbl_news n WHERE c.cid=n.cat_id ORDER BY n.nid DESC LIMIT $limit";			
			$resouter = mysql_query($query);
	}
	else if(isset($_GET['apps_details']))
	{ 
			$query="SELECT * FROM tbl_settings WHERE id='1'";		
			$resouter = mysql_query($query);
	}
	else
	{	
			$query="SELECT * FROM tbl_news_category ORDER BY cid DESC";			
			$resouter = mysql_query($query);
	}
     
    $set = array();
     
    $total_records = mysql_num_rows($resouter);
    if($total_records >= 1){
     
      while ($link = mysql_fetch_array($resouter, MYSQL_ASSOC)){
	   
        $set['NewsApp'][] = $link;
      }
    }
     
     echo $val= str_replace('\\/', '/', json_encode($set,JSON_UNESCAPED_UNICODE));
	 	 }
	 
?>