<?php
include'../application/config.php';
$id=$_POST['data'];
$query = mysqli_query($con, "delete from tbl_foodcategory WHERE id='$id'");
if($query){
    $qury=mysqli_query($con,"delete  from tbl_subcategory WHERE cat_id='$id'");
    if($qury){
       echo "deleted";
    }
    else{

    }
}
?>