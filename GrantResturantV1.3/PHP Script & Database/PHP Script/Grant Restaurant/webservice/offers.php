<?php
include("../application/config.php");
    $query=mysqli_query($con,"select * from tbl_offerdata ");
    while($res=mysqli_fetch_assoc($query))
    {
        $json[]=$res;
    }
    if(isset($json))
    {
        $jsondata['Offers'] = $json;
        echo json_encode($jsondata);
    }
    else
    {
        $arr=array("id"=>"offers Not Found");
        $jsondata['Offers'] = $arr ;
        echo json_encode($jsondata);
    }
?>