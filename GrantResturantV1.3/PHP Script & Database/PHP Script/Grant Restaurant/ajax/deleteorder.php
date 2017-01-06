<?php
include'../application/config.php';
$id=$_POST['data'];
$q=mysqli_query($con,"delete from tbl_order WHERE id='$id'");
if($q){
    echo "deleted";
}
else{
    echo "not";
}
?>