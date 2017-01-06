<?php include('includes/header.php');?>
    

<?php include('includes/menu.php');?>

<?php 
    include('includes/function.php');
	include('language/language.php'); 
	include('purchase.php'); 
 	    
	if(isset($_POST['submit']))
	{
	     
	     if(verify_purchase($_POST['buyer'],$_POST['purchase_code']) == false)
	 	{
			 
			$_SESSION['msg']="Sorry, we are unable to verify your purchase.";
			header( "Location:app_verify");
			exit;
		}
		else
		{
			 $data = array(
			'buyer'  =>  $_POST['buyer'],
			'purchase_code' => $_POST['purchase_code'],
			'status' => '1'
			);
		  
		    $app_edit=Update('app_verify', $data, "WHERE id = '1'");
			 
			if ($app_edit > 0){
				
				$_SESSION['msg']="Save successfully...";
				header( "Location:app_verify");
				exit;
			} 	
		}
	     
		
		 
	}
	//Get Data
	$app_qry="SELECT * FROM app_verify WHERE id='1'";
	$app_result=mysql_query($app_qry);
	$app_row=mysql_fetch_assoc($app_result);
	 
?>

<div class="content">
        
        <div class="header">
            
            <h1 class="page-title">App Verify</h1>
        </div>
        
            <ul class="breadcrumb">
            <li><a href="dashboard">Home</a> <span class="divider">/</span></li>
            <li class="active">App Verify</li>
       	 </ul>

        <div class="container-fluid">
            <div class="row-fluid">
                    
 
<div class="well">
<p style="color:#990000; font-size:14px;" align="center">
					<?php if(isset($_SESSION['msg'])){ 
						?>
							
					<div class="alert alert-info">
       					 <button type="button" class="close" data-dismiss="alert">×</button>
        				 <?php echo $_SESSION['msg']; ?>
   					 </div>
                            
                            <?php unset($_SESSION['msg']);		
							
					}?>
                    
</p>
     
    <div id="myTabContent" class="tab-content">
    
      <div class="tab-pane active in" id="home">
    <form action="" method="post" class="jNice" enctype="multipart/form-data">
					 
					 
        
				<label>Buyer</label>
        <input type="text" name="buyer" id="buyer" value="<?php echo $app_row['buyer'];?>" class="input-xlarge">
        
        <label>Purchase Code</label>
        <input type="text" name="purchase_code" id="purchase_code" value="<?php echo $app_row['purchase_code'];?>" class="input-xlarge">  
        
         
         <div>
          
            <button class="btn btn-primary" type="submit" name="submit">Save</button>
             
            
        </div>
        </form>
      </div>
       
  </div>

</div>
     </div>
      
   

<?php include('includes/footer.php');?>                  