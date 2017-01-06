<?php
include'../application/config.php';
if (isset($_POST['email']) /*&& isset($_POST['user_id'])*/
    && isset($_POST['name']) /*&& isset($_POST['username'])*/
    && isset($_POST['address']) && isset($_POST['number'])
    /*&& isset($_POST['reference'])*/ && isset($_POST['neighborhood'])
    && isset($_POST['city']) && isset($_POST['zipcode'])
    && isset($_POST['state']) && isset($_POST['details'])
    && isset($_POST['reg_id']) && isset($_POST['user_type'])
    && isset($_POST['orderdesc']) &&  isset($_POST['orderdesc1'])
)
{
    //$user_id=$_POST['user_id'];
    $email = addslashes($_POST['email']);
    /* $pass = $_POST['password'];*/
    $fullname = addslashes($_POST['name']);
    /*$uname = addslashes($_POST['username']);*/
    $address=addslashes($_POST['address']);
    $number=$_POST['number'];
   // $refrence=addslashes($_POST['reference']);
    $neighborhood=addslashes($_POST['neighborhood']);
    $city=addslashes($_POST['city']);
    $state=addslashes($_POST['state']);
    $details=addslashes($_POST['details']);
    $zipcode=$_POST['zipcode'];
    $reg_id=$_POST['reg_id'];
    $user_type=$_POST['user_type'];
    $desc=addslashes($_POST['orderdesc']);
    $desc1=addslashes($_POST['orderdesc1']);
    $time=time();
    $up = mysqli_query($con, "insert into tbl_order_homedelivery
              (`name`, `email`,
               `address`, `number`,
               `neighborhood`,
                 `city`, `zipcode`, `state`,
                  `details`, `reg_id`,
                   `user_type`, `order_status`,
                    `timestamp`, `orderdesc`,
                     `orderdesc1`)
              VALUES
              ('$fullname','$email',
              '$address',
              '$number',
              '$neighborhood',
              '$city',
              '$zipcode',
              '$state',
              '$details',
              '$reg_id',
              '$user_type',
              0,
              '$time',
              '$desc',
              '$desc1'
              )");
    if($up){
            $device_id=$_POST['device_id'];
            $setdevice=mysqli_query($con,"select * from tbl_devicedata WHERE mobile_device='$device_id'");
            $true=mysqli_num_rows($setdevice);
            if($true){
                $uptodate=mysqli_query($con,"update tbl_devicedata set device_id='".$_POST['reg_id']."',device='".$_POST['user_type']."' where mobile_device='$device_id'");
            }
            else{
                $insertdevice=mysqli_query($con,"insert into tbl_devicedata (device_id,device,mobile_device) VALUES ('".$_POST['reg_id']."','".$_POST['user_type']."','$device_id')");
            }
            $json[]=array("id"=>"True");
            $jdata['Status'] =  $json;
            echo json_encode($jdata);
    }
    else{
        $ar[]=array("id"=>"False");
        $arr['Status']=$ar;
        echo json_encode($arr);
    }
}
else{
    $ar[]=array("id"=>"False");
    $arr['Status']=$ar;
    echo json_encode($arr);
}
?>