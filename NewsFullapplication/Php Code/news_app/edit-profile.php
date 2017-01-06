<?php include('includes/header.php');?>
    

<?php include('includes/menu.php');?>

<?php 
    include('includes/function.php');
	include('messages/messages.php'); 
 
	
	$result= Single('admin','id',$_SESSION['ADMIN_ID']);
	$row=mysql_fetch_assoc($result);
	
	if(isset($_POST['submit']))
	{
		 	
				
			if($_POST['password'])
			{
				$data = array(
					'password'  =>  md5($_POST['password'])     
				);	
				
			}
			else
			{
				 
				$data = array(
					'username'  =>  $_POST['username'],
					'email'     =>  $_POST['email']    
				);
			}
			
			$admin_pro=Update('admin', $data, "WHERE id = '1'");
			 
			if ($admin_pro > 0){
				
				$_SESSION['msg']="2";
				header( "Location:edit-profile");
				exit;
			} 	
	}
	
	 
?>
<div class="content">
        
        <div class="header">
            
            <h1 class="page-title"><?php echo $lang['PAGE_PROFILE_EDIT']; ?></h1>
        </div>
        
            <ul class="breadcrumb">
            <li><a href="dashboard"><?php echo $lang['DEFAULT_MSG_HOME']; ?></a> <span class="divider">/</span></li>
            <li class="active"><?php echo $lang['PAGE_PROFILE_EDIT']; ?></li>
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
    <ul class="nav nav-tabs">
      <li class="active"><a href="#home" data-toggle="tab"><?php echo $lang['PAGE_PROFILE_PROFILE']; ?></a></li>
      <li><a href="#profile" data-toggle="tab"><?php echo $lang['PAGE_PROFILE_PASSWORD']; ?></a></li>
    </ul>
    <div id="myTabContent" class="tab-content">
    
      <div class="tab-pane active in" id="home">
    <form id="tab" method="post" action="" name="admin_profile">
        <label><?php echo $lang['PAGE_PROFILE_USERNAME']; ?></label>
        <input type="text" name="username" value="<?php echo $row['username'];?>" class="input-xlarge">         
        <label><?php echo $lang['PAGE_PROFILE_EMAIL']; ?></label>
        <input type="text" name="email" value="<?php echo $row['email'];?>" class="input-xlarge">
         
         <div>
            <button class="btn btn-primary" type="submit" name="submit"><?php echo $lang['PAGE_PROFILE_UPDATE']; ?></button>
        </div>
        </form>
      </div>
      <div class="tab-pane fade" id="profile">
    <form id="tab2" action="" method="post">
        <label><?php echo $lang['PAGE_PROFILE_NEW_PASSWORD']; ?></label>
        <input type="password" name="password" value="" class="input-xlarge">
        <div>
            <button class="btn btn-primary" type="submit" name="submit"><?php echo $lang['PAGE_PROFILE_UPDATE']; ?></button>
        </div>
    </form>
      </div>
  </div>

</div>
     </div>
       
   

<?php include('includes/footer.php');?>                  