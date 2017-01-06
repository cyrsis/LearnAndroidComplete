<?php
include'../application/config.php';
$cname=$_POST['data'];
$up=mysqli_query($con,"select * from tbl_subcategory WHERE id='$cname'");
$upd=mysqli_fetch_array($up);
$iddd=$upd['cat_id'];
unlink("../uploads/".$upd['image']);
$query=mysqli_query($con,"delete from tbl_subcategory WHERE id='$cname'");
if($query){
    echo "deleted";
}
else{
    echo "not";
}
?>