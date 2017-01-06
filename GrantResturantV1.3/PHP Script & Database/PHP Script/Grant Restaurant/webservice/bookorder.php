<?php
include'../application/config.php';
if(isset($_GET['name']) && isset($_GET['datetime']) && isset($_GET['people']) && isset($_GET['phone']) && isset($_GET['reg_id']) && isset($_GET['device']))
{
    $date=date("d/m/Y");
    $unik=uniqid();
    $query=mysqli_query($con,"insert into tbl_order
    (`name`, `no_people`, `datetime`, `phone`, `orderdis`, `comment`,`orderdis2`,`date`,`reg_id`,`device`,`unik`)
     VALUES
    (
    '".addslashes($_GET['name'])."',
    '".addslashes($_GET['people'])."',
    '".addslashes($_GET['datetime'])."',
    '".$_GET['phone']."',
    '".addslashes($_GET['orderdis'])."',
    '".addslashes($_GET['comment'])."',
    '".addslashes($_GET['orderdis2'])."',
    '$date',
    '".$_GET['reg_id']."',
    '".$_GET['device']."',
    '$unik'
    )");
    if($query)
    {
        $device_id=$_GET['device_id'];
        $setdevice=mysqli_query($con,"select * from tbl_devicedata WHERE  mobile_device='$device_id' ");
        $true=mysqli_num_rows($setdevice);
        if($true)
        {
            $uptodate=mysqli_query($con,"update tbl_devicedata set device_id='".$_GET['reg_id']."',device='".$_GET['device']."' where mobile_device='$device_id'");
        }
        else
        {
           $insertdevice=mysqli_query($con,"insert into tbl_devicedata (device_id,device,mobile_device) VALUES ('".$_GET['reg_id']."','".$_GET['device']."','$device_id')");
        }
        $queryselid=mysqli_query($con,"select * from tbl_order WHERE unik='$unik'");
        $order=mysqli_fetch_array($queryselid);
        $id=array("id"=>$order['id']);
        $data[]=$id;
        $d['Massage']=$data;
        echo json_encode($d);
    }
    else
    {
        $id=array("id"=>"False");
        $data[]=$id;
        $d['Massage']=$data;
        echo json_encode($d);
    }
}
else
{
    $id=array("id"=>"False");
    $data[]=$id;
    $d['Massage']=$data;
    echo json_encode($d);
}
// http://192.168.1.108/singleres/webservice/bookorder.php?name={}&datetime={}&people={}&phone={}&reg_id={}&device={}
?>


