<?php include('includes/header.php');?>
    

<?php include('includes/menu.php');?>

<?php 
    include('includes/function.php');
	  include('messages/messages.php'); 
		require_once("thumbnail_images.class.php");
 		
		mysql_query("SET NAMES 'utf8'");	
	 
	if(isset($_POST['submit']) and isset($_GET['add']))
	{
	 
	 		if($_FILES['news_image']['name'])
			{
			
					if($_FILES['news_image']['type']=="image/jpg" or $_FILES['news_image']['type']=="image/jpeg" or $_FILES['news_image']['type']=="image/png" or $_FILES['news_image']['type']=="image/gif" or $_FILES['news_image']['type']=="image/bmp")
				{
			
							$news_image=rand(0,99999)."_".$_FILES['news_image']['name'];
							$pic1=$_FILES['news_image']['tmp_name'];
						
							$tpath1='upload/'.$news_image;
								
							copy($pic1,$tpath1);
								 
										$thumbpath='upload/thumbs/'.$news_image;
										$obj_img = new thumbnail_images();
										$obj_img->PathImgOld = $tpath1;
										$obj_img->PathImgNew =$thumbpath;
										$obj_img->NewWidth = 72;
										$obj_img->NewHeight = 72;
										if (!$obj_img->create_thumbnail_images()) 
											{
											echo $_SESSION['msg']="Thumbnail not created... please upload image again";
												exit;
											}	 
					 
					 
										$data = array(
										'cat_id'  =>  $_POST['category_id'],
										'news_heading'  =>  addslashes($_POST['news_heading']),
										'news_description'  =>  addslashes($_POST['news_description']),
										'news_date'  =>  $_POST['news_date'],
										'news_image'  =>  $news_image				 	 
										);
				}
			}
			else
			{
					
											$data = array(
						'cat_id'  =>  $_POST['category_id'],
						'news_heading'  =>  addslashes($_POST['news_heading']),
						'news_description'  =>  addslashes($_POST['news_description']),
						'news_date'  =>  $_POST['news_date']	 
						);

						
			}

			$qry = Insert('tbl_news',$data);

		
			$_SESSION['msg']="10";
			header("location:add-news?add");		 
			exit;
		
	}
	
	if(isset($_GET['news_id']))
	{
			 
			$qry="SELECT * FROM tbl_news where nid='".$_GET['news_id']."'";
			$result=mysql_query($qry);
			$news_row=mysql_fetch_assoc($result);

	}
	if(isset($_POST['submit']) and isset($_POST['news_id']))
	{
		
		$img_res=mysql_query('SELECT * FROM tbl_news WHERE nid=\''.$_POST['news_id'].'\'');
		$img_row=mysql_fetch_assoc($img_res);
			
			if($img_row['news_image']!="")
			{
				unlink('upload/'.$img_row['news_image']);
				unlink('upload/thumbs/'.$img_row['news_image']);
				 
			}	 
		 
		
		if($_FILES['news_image']['name']!="")
		{
			  	if($_FILES['news_image']['type']=="image/jpg" or $_FILES['news_image']['type']=="image/jpeg" or $_FILES['news_image']['type']=="image/png" or $_FILES['news_image']['type']=="image/gif" or $_FILES['news_image']['type']=="image/bmp")
				{
			
								$news_image=rand(0,99999)."_".$_FILES['news_image']['name'];
								$pic1=$_FILES['news_image']['tmp_name'];
							
								$tpath1='upload/'.$news_image;
									
								copy($pic1,$tpath1);
									 
											$thumbpath='upload/thumbs/'.$news_image;
											$obj_img = new thumbnail_images();
											$obj_img->PathImgOld = $tpath1;
											$obj_img->PathImgNew =$thumbpath;
											$obj_img->NewWidth = 72;
											$obj_img->NewHeight = 72;
											if (!$obj_img->create_thumbnail_images()) 
												{
												echo $_SESSION['msg']="Thumbnail not created... please upload image again";
													exit;
												}	 
						 
						 
								$data = array(
								'cat_id'  =>  $_POST['category_id'],
								'news_heading'  =>  addslashes($_POST['news_heading']),
								'news_description'  => addslashes($_POST['news_description']),
								'news_date'  =>  $_POST['news_date'],
								'news_image'  =>  $news_image				 	 
								);
				}
		}
		else
		{
		
		
		 
		$data = array(
			'cat_id'  =>  $_POST['category_id'],
			'news_heading'  =>  addslashes($_POST['news_heading']),
			'news_description'  =>  addslashes($_POST['news_description']),
			'news_date'  =>  $_POST['news_date']			 		 	 
			);
		}
		 
		$news_edit=Update('tbl_news', $data, "WHERE nid = '".$_POST['news_id']."'");
		
			 
			if ($news_edit > 0){
				
				$_SESSION['msg']="11";
				header( "Location:add-news?news_id=".$_POST['news_id']);
				exit;
			} 	
		
	 
	}
	
			//Get category
			echo $cat_qry="SELECT * FROM tbl_news_category";
			$cat_result=mysql_query($cat_qry);
 
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
                    category_id: "required",
										news_heading: "required",
										news_description: "required",
										news_date: "required"										                
									},
                   
                messages: {
                    category_id: "Please enter category name",
										news_heading: "Please enter news heading",
										news_description: "Please enter news description",
										news_date: "Please select news date"
                     
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

#addeditenews label.error {
    color: #FB3A3A;
    display: inline-block;
    margin: 4px 0 5px 20px;
    padding: 0;
    text-align: left;
    width: 220px;
}
</style>

<link rel="stylesheet" href="css/jquery-ui.css">
   
<script src="js/jquery-ui.js"></script>
 <script>
  $(function() {
    $( "#news_date" ).datepicker({
      dateFormat:"mm-dd-yy",
			maxDate: "+1M +10D"
    });
  });
  </script>


<div class="content">
        
        <div class="header">
            
            <h1 class="page-title"><?php if(isset($_GET['news_id'])){?><?php echo $lang['PAGE_NEWS_EDIT_NEWS']; ?><?php }else {?><?php echo $lang['PAGE_NEWS_ADD_NEWS']; ?><?php }?></h1>
        </div>
        
            <ul class="breadcrumb">
            <li><a href="dashboard"><?php echo $lang['DEFAULT_MSG_HOME']; ?></a> <span class="divider">/</span></li>
            <li class="active"><?php if(isset($_GET['news_id'])){?><?php echo $lang['PAGE_NEWS_EDIT_NEWS']; ?><?php }else {?><?php echo $lang['PAGE_NEWS_ADD_NEWS']; ?><?php }?></li>
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
    <form action="" name="addeditenews" id="addeditenews" method="post" class="jNice" enctype="multipart/form-data">
					 
					<input  type="hidden" name="news_id" value="<?php echo $_GET['news_id'];?>" />
        
        <label><?php echo $lang['PAGE_NEWS_CATEGORY']; ?></label>
        <select name="category_id" id="category_id" class="input-xlarge">
          <option value="">-- <?php echo $lang['PAGE_NEWS_SELECT_CATEGORY']; ?> --</option>
          <?php while($cat_row=mysql_fetch_array($cat_result)){?>
          <option value="<?php echo $cat_row['cid'];?>" <?php if($cat_row['cid']==$news_row['cat_id']){?>selected<?php }?>><?php echo $cat_row['category_name'];?></option>
          <?php }?>
        </select>  
          
        <label><?php echo $lang['PAGE_NEWS_HEADING']; ?></label>
        <input type="text" name="news_heading" id="news_heading" value="<?php if(isset($_GET['news_id'])){echo $news_row['news_heading'];}?>" class="input-xlarge">
        
        
        <label><?php echo $lang['PAGE_NEWS_DESCRIPTION']; ?></label>
        <textarea name="news_description" id="news_description" class="input-xlarge" cols="60" rows="10"><?php if(isset($_GET['news_id'])){echo $news_row['news_description'];}?></textarea>
        
         								 <script>                             
                            CKEDITOR.replace( 'news_description' );
                        </script>
        
        <label>&nbsp;</label>
        <label><?php echo $lang['PAGE_NEWS_DATE']; ?></label>
        <input type="text" name="news_date" id="news_date" value="<?php if(isset($_GET['news_id'])){echo $news_row['news_date'];}?>" class="input-xlarge">
        
        <label><?php echo $lang['PAGE_NEWS_IMAGE']; ?></label>
        <input type="file" name="news_image" id="news_image" value="" class="input-xlarge">      
          
          <?php if($news_row['news_image']){?><img src="upload/<?php echo $news_row['news_image'];?>" width="72" width="72" /><?php }?>
          
          <label>&nbsp;</label>
         <div>
          
            <button class="btn btn-primary" type="submit" name="submit"><?php if(isset($_GET['news_id'])){?><?php echo $lang['PAGE_NEWS_EDIT_NEWS']; ?><?php }else {?><?php echo $lang['PAGE_NEWS_ADD_NEWS']; ?><?php }?></button>
             
            
        </div>
        </form>
      </div>
       
  </div>

</div>
     </div>
       
   

<?php include('includes/footer.php');?>                  