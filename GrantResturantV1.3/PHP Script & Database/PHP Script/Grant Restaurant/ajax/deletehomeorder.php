<?php
include '../application/config.php';
$id=$_POST['data'];
$query=mysqli_query($con,"delete from tbl_order_homedelivery WHERE id='$id'");
if($query){
    echo "True";
}
else{
    echo "False";
}
?>