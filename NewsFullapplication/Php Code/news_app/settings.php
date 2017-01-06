<?php include('includes/header.php');?>
    

<?php include('includes/menu.php');?>

<?php 
    include('includes/function.php');
	  include('messages/messages.php'); 
		require_once("thumbnail_images.class.php");
 		
		mysql_query("SET NAMES 'utf8'");	
	 
	 
	
	 	 
			$qry="SELECT * FROM tbl_settings where id='1'";
			$result=mysql_query($qry);
			$settings_row=mysql_fetch_assoc($result);
 
	if(isset($_POST['submit']))
	{
		
		$img_res=mysql_query("SELECT * FROM tbl_settings WHERE id='1'");
		$img_row=mysql_fetch_assoc($img_res);
			
			if($img_row['app_logo']!="")
			{
				unlink('upload/'.$img_row['app_logo']);				 
				 
			}	 
		 
		
		if($_FILES['app_logo']['name']!="")
		{
			  
			
			$app_logo=$_FILES['app_logo']['name'];
			$pic1=$_FILES['app_logo']['tmp_name'];
		
			$tpath1='upload/'.$app_logo;
				
			copy($pic1,$tpath1);
				 
				$data = array(
				'app_name'  =>  $_POST['app_name'],
				'app_logo'  =>  $app_logo,
				'app_email'  =>  $_POST['app_email'],
				'app_website'  =>  addslashes($_POST['app_website']),
				'app_description'  => addslashes($_POST['app_description'])								 	 
				);
		}
		else
		{
		 
				$data = array(
					'app_name'  =>  $_POST['app_name'],					 
					'app_email'  =>  $_POST['app_email'],
					'app_website'  =>  addslashes($_POST['app_website']),
					'app_description'  => addslashes($_POST['app_description'])								 	 
					);
		}
		 
		$news_edit=Update('tbl_settings', $data, "WHERE id = '1'");
		
			 
			if ($news_edit > 0){
				
				$_SESSION['msg']="15";
				header( "Location:settings");
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
            $("#appsettings").validate({
                rules: {
                    app_name: "required"										 								                
									},
                   
                messages: {
                    app_name: "Please enter apps name"
										 
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

#appsettings label.error {
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
            
            <h1 class="page-title">Apps Settings</h1>
        </div>
        
            <ul class="breadcrumb">
            <li><a href="dashboard">Home</a> <span class="divider">/</span></li>
            <li class="active">Apps Settings</li>
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
    <form action="" name="appsettings" id="appsettings" method="post" class="jNice" enctype="multipart/form-data">
					  
        <label>Apps Name</label>
        <input type="text" name="app_name" id="app_name" value="<?php echo $settings_row['app_name'];?>" class="input-xlarge">
        
        <label>Apps Logo</label>
        <input type="file" name="app_logo" id="app_logo" value="" class="input-xlarge">      
          
          <?php if($settings_row['app_logo']){?><img src="upload/<?php echo $settings_row['app_logo'];?>" width="72" width="72" /><?php }?>
        
        <label>Apps Email</label>
        <input type="text" name="app_email" id="app_email" value="<?php echo $settings_row['app_email'];?>" class="input-xlarge">
        
         <label>Apps Website</label>
        <input type="text" name="app_website" id="app_website" value="<?php echo $settings_row['app_website'];?>" class="input-xlarge">        
        
        <label>Apps Description</label>
        <textarea name="app_description" id="app_description" class="input-xlarge" cols="60" rows="10"><?php echo $settings_row['app_description'];?></textarea>
        
         								 <script>                             
                            CKEDITOR.replace( 'app_description' );
                        </script>
          
          
          <label>&nbsp;</label>
         <div>
          
            <button class="btn btn-primary" type="submit" name="submit">Save Settings</button>
             
            
        </div>
        </form>
      </div>
       
  </div>

</div>
     </div>
       
   

<?php include('includes/footer.php');?>                  