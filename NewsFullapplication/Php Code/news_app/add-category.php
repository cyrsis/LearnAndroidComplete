<?php include('includes/header.php');?>
    

<?php include('includes/menu.php');?>

<?php 
    include('includes/function.php');
	  include('messages/messages.php'); 
		require_once("thumbnail_images.class.php");
 		
		mysql_query("SET NAMES 'utf8'");	
	 
	if(isset($_POST['submit']) and isset($_GET['add']))
	{
	 
	 	if($_FILES['category_image']['name'])
		{
	 
	 			if($_FILES['category_image']['type']=="image/jpg" or $_FILES['category_image']['type']=="image/jpeg" or $_FILES['category_image']['type']=="image/png" or $_FILES['category_image']['type']=="image/gif" or $_FILES['category_image']['type']=="image/bmp")
				{
	 				
						$category_image=rand(0,99999)."_".$_FILES['category_image']['name'];
						$pic1=$_FILES['category_image']['tmp_name'];
					
						$tpath1='upload/category/'.$category_image;
							
						copy($pic1,$tpath1);
							 
									$thumbpath='upload/category/'.$category_image;
									$obj_img = new thumbnail_images();
									$obj_img->PathImgOld = $tpath1;
									$obj_img->PathImgNew =$thumbpath;
									$obj_img->NewWidth = 100;
									$obj_img->NewHeight = 100;
									if (!$obj_img->create_thumbnail_images()) 
										{
											echo $_SESSION['msg']="Thumbnail not created... please upload image again";
											 
										}	
										
										$data = array(
										'category_name'  =>  $_POST['category_name'],
										'category_image'  =>  $category_image				 	 
										);
				}
				else
				{
					echo $_SESSION['msg']="This is not an image";
					 
				}
							 
		}
		else
		{
						$data = array(
						'category_name'  =>  $_POST['category_name']	 
						);
		}
	 
			

			$qry = Insert('tbl_news_category',$data);

		
			$_SESSION['msg']="5";
			header("location:add-category?add");		 
			exit;
		
	}
	
	if(isset($_GET['cat_id']))
	{
			 
			$qry="SELECT * FROM tbl_news_category where cid='".$_GET['cat_id']."'";
			$result=mysql_query($qry);
			$row=mysql_fetch_assoc($result);

	}
	if(isset($_POST['submit']) and isset($_POST['cat_id']))
	{
		
		$img_res=mysql_query('SELECT * FROM tbl_news_category WHERE cid=\''.$_POST['cat_id'].'\'');
		$img_row=mysql_fetch_assoc($img_res);
			
			if($img_row['category_image']!="")
			{
				unlink('upload/category/'.$img_row['category_image']);
				 
			}	 
		 
		
		if($_FILES['category_image']['name']!="")
		{
			
					if($_FILES['category_image']['type']=="image/jpg" or $_FILES['category_image']['type']=="image/jpeg" or $_FILES['category_image']['type']=="image/png" or $_FILES['category_image']['type']=="image/gif" or $_FILES['category_image']['type']=="image/bmp")
				{
				
								$category_image=rand(0,99999)."_".$_FILES['category_image']['name'];
								$pic1=$_FILES['category_image']['tmp_name'];
						
								$tpath1='upload/category/'.$category_image;
									
								copy($pic1,$tpath1);
								 
										$thumbpath='upload/category/'.$category_image;
										$obj_img = new thumbnail_images();
										$obj_img->PathImgOld = $tpath1;
										$obj_img->PathImgNew =$thumbpath;
										$obj_img->NewWidth = 100;
										$obj_img->NewHeight = 100;
										if (!$obj_img->create_thumbnail_images()) 
											{
											echo $_SESSION['msg']="Thumbnail not created... please upload image again";
												exit;
											}	 
							
										$data = array(
									'category_name'  =>  $_POST['category_name'],
									'category_image'  =>  $category_image				 	 
									);
				}
				else
				{
					echo $_SESSION['msg']="This is not an image";
					 
				}
		}
		else
		{
		
		
		 
		$data = array(
					'category_name'  =>  $_POST['category_name']					 
				); 
		}
		 
		$cat_edit=Update('tbl_news_category', $data, "WHERE cid = '".$_POST['cat_id']."'");
		
			 
			if ($cat_edit > 0){
				
				$_SESSION['msg']="6";
				header( "Location:add-category?cat_id=".$_POST['cat_id']);
				exit;
			} 	
		
	 
	}
 
?>

<script src="js/jquery.min.js"></script>

<script type="text/javascript" src="js/jquery.validate.min.js"></script>

<script type="text/javascript">
(function($,W,D)
{
    var JQUERY4U = {};

    JQUERY4U.UTIL =
    {
        setupFormValidation: function()
        {
            //form validation rules
            $("#addeditecategory").validate({
                rules: {
                    category_name: "required"										                
									},
                   
                messages: {
                    category_name: "Please enter category name"
                     
                },
                submitHandler: function(form) {
                    form.submit();
                }
            });
        }
    }

    //when the dom has loaded setup form validation rules
    $(D).ready(function($) {
        JQUERY4U.UTIL.setupFormValidation();
    });

})(jQuery, window, document);
</script>
<style>

#addeditecategory label.error {
    color: #FB3A3A;
    display: inline-block;
    margin: 4px 0 5px 20px;
    padding: 0;
    text-align: left;
    width: 220px;
}
</style>

<div class="content">
        
        <div class="header">
            
            <h1 class="page-title"><?php if(isset($_GET['cat_id'])){?><?php echo $lang['PAGE_CATEGORY_CATEGORY_EDIT_CAT']; ?><?php }else {?><?php echo $lang['PAGE_CATEGORY_CATEGORY_ADD_CAT']; ?><?php }?></h1>
        </div>
        
            <ul class="breadcrumb">
            <li><a href="dashboard"><?php echo $lang['DEFAULT_MSG_HOME']; ?></a> <span class="divider">/</span></li>
            <li class="active"><?php if(isset($_GET['cat_id'])){?><?php echo $lang['PAGE_CATEGORY_CATEGORY_EDIT_CAT']; ?><?php }else {?><?php echo $lang['PAGE_CATEGORY_CATEGORY_ADD_CAT']; ?><?php }?></li>
       	 </ul>

        <div class="container-fluid">
            <div class="row-fluid">
                    
 
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
     
    <div id="myTabContent" class="tab-content">
    
      <div class="tab-pane active in" id="home">
    <form action="" name="addeditecategory" id="addeditecategory" method="post" class="jNice" onsubmit="<?php if(!isset($_GET['cat_id'])){?>return checkValidation(this);<?php }else { ?>return editValidation(this);<?php }?>" enctype="multipart/form-data">
					 
					<input  type="hidden" name="cat_id" value="<?php echo $_GET['cat_id'];?>" />
        <label><?php echo $lang['PAGE_CATEGORY_CATEGORY_NAME']; ?></label>
        <input type="text" name="category_name" id="category_name" value="<?php if(isset($_GET['cat_id'])){echo $row['category_name'];}?>" class="input-xlarge">
        
        <label><?php echo $lang['PAGE_CATEGORY_CATEGORY_IMAGE']; ?></label>
        <input type="file" name="category_image" id="category_image" value="" class="input-xlarge">      
          
          
          <label>&nbsp;</label>
         <div>
          
            <button class="btn btn-primary" type="submit" name="submit"><?php if(isset($_GET['cat_id'])){?><?php echo $lang['PAGE_CATEGORY_CATEGORY_EDIT_CAT']; ?><?php }else {?><?php echo $lang['PAGE_CATEGORY_CATEGORY_ADD_CAT']; ?><?php }?></button>
             
            
        </div>
        </form>
      </div>
       
  </div>

</div>
     </div>
       
   

<?php include('includes/footer.php');?>                  