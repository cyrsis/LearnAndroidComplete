<?php
include'../application/config.php';
$query=mysqli_query($con,"select * from tbl_singleresdetail");
$res=mysqli_fetch_array($query);
$imagequery=mysqli_query($con,"select * from tbl_multiimages");
$imagequery1=mysqli_query($con,"select * from tbl_multiimages");
$ch=mysqli_num_rows($imagequery1);
if($ch)
{
    while ($row = mysqli_fetch_array($imagequery))
    {
        $image[] = $row['image'];
    }
}
else{
    $image[] = "no images";
}
$location=array("address"=>$res['address'],"country"=>$res['country'],"state"=>$res['state']);
$resdetail=array("restaurantname"=>$res['name'],"images"=>$image,"location"=>$location,"phone"=>$res['phone'],
    "email"=>$res['email'],"website"=>$res['website'],"lat"=>$res['lat'],"long"=>$res['long']);
$data['Restaurant']=$resdetail;
echo json_encode($data);
?>