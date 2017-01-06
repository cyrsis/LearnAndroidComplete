<?php include('includes/header.php');?>
    

<?php include('includes/menu.php');?>

<?php 
    include('includes/function.php');
	include('messages/messages.php'); 
 	
	mysql_query("SET NAMES 'utf8'");	
	 
	$tableName="tbl_news_category";		
	$targetpage = "category"; 	
	$limit = 15; 
	
	$query = "SELECT COUNT(*) as num FROM $tableName";
	$total_pages = mysql_fetch_array(mysql_query($query));
	$total_pages = $total_pages['num'];
	
	$stages = 3;
	$page=0;
	if(isset($_GET['page'])){
	$page = mysql_escape_string($_GET['page']);
	}
	if($page){
		$start = ($page - 1) * $limit; 
	}else{
		$start = 0;	
		}	
	
	 
	$cat_qry="SELECT * FROM tbl_news_category ORDER BY tbl_news_category.cid LIMIT $start, $limit";	  
	$cat_result=mysql_query($cat_qry);
	
	if(isset($_GET['cat_id']))
	{
		
		$img_res=mysql_query('SELECT * FROM tbl_news_category WHERE cid=\''.$_GET['cat_id'].'\'');
		$img_row=mysql_fetch_assoc($img_res);
			
			if($img_row['category_image']!="")
			{
				unlink('upload/category/'.$img_row['category_image']);
				 
			}	 	
		
		 
		Delete('tbl_news_category','cid='.$_GET['cat_id'].'');
		
		 $_SESSION['msg']="7";
		 header( "Location:category");
		 exit;
	}
	
	
	//Active and Deactive status
	if(isset($_GET['status_deactive_id']))
	{
		$data = array('status'  =>  '0');
		
		$edit_list=Update('tbl_news_category', $data, "WHERE cid = '".$_GET['status_deactive_id']."'");
		
		 $_SESSION['msg']="9";
		 header( "Location:category");
		 exit;
	}
	if(isset($_GET['status_active_id']))
	{
		$data = array('status'  =>  '1');
		
		$edit_list=Update('tbl_news_category', $data, "WHERE cid = '".$_GET['status_active_id']."'");
		
		$_SESSION['msg']="8";
		 header( "Location:category");
		 exit;
	}
	
	
?>
<div class="content">
        
        <div class="header">
            
            <h1 class="page-title"><?php echo $lang['PAGE_CATEGORY_LIST']; ?></h1>
        </div>
        
            <ul class="breadcrumb">
            <li><a href="dashboard"><?php echo $lang['DEFAULT_MSG_HOME']; ?></a> <span class="divider">/</span></li>
            <li class="active"><?php echo $lang['PAGE_CATEGORY_LIST']; ?></li>
       	 </ul>

         <div class="container-fluid">
            <div class="row-fluid">
                    
<div class="btn-toolbar">
    <button class="btn btn-primary" onclick="window.location.href='add-category?add'"><i class="icon-plus"></i> <?php echo $lang['PAGE_CATEGORY_NEW_CATEGORY']; ?></button>
     
  <div class="btn-group">
  </div>
</div>
<div class="well">

<p style="color:#990000; font-size:14px;" align="center">
					<?php if(isset($_SESSION['msg'])){ 
						?>
							
					<div class="alert alert-info">
       					 <button type="button" class="close" data-dismiss="alert">×</button>
        				 <?php echo $admin_lang[$_SESSION['msg']] ; ?>
   					 </div>
                            
                            <?php unset($_SESSION['msg']);		
							
					}?>
                    
</p>

    <table class="table">
      <thead>
        <tr>
          <th>#</th>
          <th><?php echo $lang['PAGE_CATEGORY_CATEGORY_NAME']; ?></th>
          <th><?php echo $lang['PAGE_CATEGORY_CATEGORY_IMAGE']; ?></th> 
          <th style="width: 26px;"></th>
        </tr>
      </thead>
      <tbody>
        <?php 
					$i=1;
					while($cat_row=mysql_fetch_array($cat_result))
					{
				?>
        
        <tr>
          <td><?php echo $i;?></td>
          <td><?php echo $cat_row['category_name'];?></td>  
          <td><img src="upload/category/<?php if($cat_row['category_image']){ echo $cat_row['category_image'];}else{ echo "Default.png";}?>" width="100" height="100"></td>           
          <td>
          		<?php if($cat_row['status']!="0"){?>
              <a href="category?status_deactive_id=<?php echo $cat_row['cid'];?>" title="Change Status"><i class="icon-plus"></i></a>
              <?php }else{?>
              <a href="category?status_active_id=<?php echo $cat_row['cid'];?>" title="Change Status"><i class="icon-minus"></i></a>
              <?php }?>
              
              <a href="add-category?cat_id=<?php echo $cat_row['cid'];?>"><i class="icon-pencil"></i></a>
              <a href="#category<?php echo $cat_row['cid'];?>" role="button" data-toggle="modal" title="Delete Category"><i class="icon-remove"></i></a>               
              
              <div class="modal small hide fade" id="category<?php echo $cat_row['cid'];?>" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
        <h3 id="myModalLabel"><?php echo $lang['DEFAULT_MSG_DELETE_CONFIRM']; ?></h3>
    </div>
    <div class="modal-body">
        <p class="error-text"><i class="icon-warning-sign modal-icon"></i><?php echo $lang['PAGE_CATEGORY_DELETE_MSG']; ?></p>
    </div>
    <div class="modal-footer">
        <button class="btn" data-dismiss="modal" aria-hidden="true"><?php echo $lang['DEFAULT_MSG_CANCEL']; ?></button>
        <button class="btn btn-danger" data-dismiss="modal" onclick="window.location.href='category?cat_id=<?php echo $cat_row['cid'];?>'"><?php echo $lang['DEFAULT_MSG_DELETE']; ?></button>
    </div>
</div>
          </td>
        </tr>
       <?php $i++;}?>   
      </tbody>
    </table>
  		 
</div>
<?php
								// Initial page num setup
	if ($page == 0){$page = 1;}
	$prev = $page - 1;	
	$next = $page + 1;							
	$lastpage = ceil($total_pages/$limit);		
	$LastPagem1 = $lastpage - 1;					
	
	
	$paginate = '';
	if($lastpage > 1)
	{	
	 
	
		$paginate .= "<div class='pagination'><ul>";
		// Previous
		if ($page > 1){
			$paginate.= "<li><a href='$targetpage?page=$prev'>Prev</a></li>";
		}else{
			$paginate.= "<li><a href='#'>Prev</a></li>";	}
			

		
		// Pages	
		if ($lastpage < 7 + ($stages * 2))	// Not enough pages to breaking it up
		{	
			for ($counter = 1; $counter <= $lastpage; $counter++)
			{
				if ($counter == $page){
					$paginate.= "<li><a href='$targetpage?page=$counter'>$counter</a></li>";
				}else{
					$paginate.= "<li><a href='$targetpage?page=$counter'>$counter</a></li>";}					
			}
		}
		elseif($lastpage > 5 + ($stages * 2))	// Enough pages to hide a few?
		{
			// Beginning only hide later pages
			if($page < 1 + ($stages * 2))		
			{
				for ($counter = 1; $counter < 4 + ($stages * 2); $counter++)
				{
					if ($counter == $page){
						$paginate.= "<li><a href='$targetpage?page=$counter'>$counter</a></li>";
					}else{
						$paginate.= "<li><a href='$targetpage?page=$counter'>$counter</a></li>";}					
				}
				$paginate.= "<li><a href='#'>...</a></li>";
				$paginate.= "<li><a href='$targetpage?page=$LastPagem1'>$LastPagem1</a></li>";
				$paginate.= "<li><a href='$targetpage?page=$lastpage'>$lastpage</a></li>";		
			}
			// Middle hide some front and some back
			elseif($lastpage - ($stages * 2) > $page && $page > ($stages * 2))
			{
				$paginate.= "<li><a href='$targetpage?page=1'>1</a></li>";
				$paginate.= "<li><a href='$targetpage?page=2'>2</a></li>";
				$paginate.= "<li><a href='#'>...</a></li>";
				for ($counter = $page - $stages; $counter <= $page + $stages; $counter++)
				{
					if ($counter == $page){
						$paginate.= "<li><a href='$targetpage?page=$counter'>$counter</a></li>";
					}else{
						$paginate.= "<li><a href='$targetpage?page=$counter'>$counter</a></li>";}					
				}
				$paginate.= "<li><a href='#'>...</a></li>";
				$paginate.= "<li><a href='$targetpage?page=$LastPagem1'>$LastPagem1</a></li>";
				$paginate.= "<li><a href='$targetpage?page=$lastpage'>$lastpage</a></li>";		
			}
			// End only hide early pages
			else
			{
				$paginate.= "<li><a href='$targetpage?page=1'>1</a></li>";
				$paginate.= "<li><a href='$targetpage?page=2'>2</a></li>";
				$paginate.= "<li><a href='#'>...</a></li>";
				for ($counter = $lastpage - (2 + ($stages * 2)); $counter <= $lastpage; $counter++)
				{
					if ($counter == $page){
						$paginate.= "<li><a href='$targetpage?page=$counter'>$counter</a></li>";
					}else{
						$paginate.= "<li><a href='$targetpage?page=$counter'>$counter</a></li>";}					
				}
			}
		}
					
				// Next
		if ($page < $counter - 1){ 
			$paginate.= "<li><a href='$targetpage?page=$next'>next</a></li>";
		}else{
			$paginate.= "<li><a href='#'>next</a></li>";
			}
			
		$paginate.= "</ul></div>";		
	
	
}
  
 // pagination
 echo $paginate;
								?>	

 
 

<?php include('includes/footer.php');?>                  