<?php
if(isset($_POST['name']) && isset($_POST['address']))
{
    $ar[]=array("id"=>$_POST['name'],"address"=>$_POST['address']);
    echo json_encode($ar);
}
else
{
    echo '[{"id":"False"}]';
}

?>