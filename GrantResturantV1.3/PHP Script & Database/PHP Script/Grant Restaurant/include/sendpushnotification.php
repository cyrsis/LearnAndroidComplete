<?php
include 'application/config.php';
if(isset($_POST['orderconfirm']))
{
    $query=mysqli_query($con,"select * from tbl_adminlogin WHERE id='".$_SESSION['admin_id']."'");
    $res=mysqli_fetch_array($query);
    // Android
    $google_api_key=$res['google_api'];
    //iPhone
    $passphrace=$res['passphrace'];
    $certificate=$res['certificate'];
    // Notification Data
    $reg_id=$_POST['reg_id'];
    $order_date=$_POST['date'];
    $table_no=$_POST['tblno'];
    $order_id=$_POST['order_id'];
    $massage="Book table request is confirmed with table no ".$table_no."for Booking id :".$order_id." Date ".$order_date;
    $queryfordevice=mysqli_query($con,"select * from tbl_order WHERE id='$order_id'");
    $row=mysqli_fetch_array($queryfordevice);
    if($row['device'] == "Android")
    {
        define( 'API_ACCESS_KEY', $google_api_key );
        $registrationIds = array( $reg_id );
        $message = array("price" => $massage,"id"=>"order");
        $fields = array
        (
            'registration_ids' 	=> $registrationIds,
            'data'		=> $message
        );
        $headers = array
        (
            'Authorization: key=' . API_ACCESS_KEY,
            'Content-Type: application/json'
        );
        $ch = curl_init();
        curl_setopt( $ch,CURLOPT_URL, 'https://android.googleapis.com/gcm/send' );
        curl_setopt( $ch,CURLOPT_POST, true );
        curl_setopt( $ch,CURLOPT_HTTPHEADER, $headers );
        curl_setopt( $ch,CURLOPT_RETURNTRANSFER, true );
        curl_setopt( $ch,CURLOPT_SSL_VERIFYPEER, false );
        curl_setopt( $ch,CURLOPT_POSTFIELDS, json_encode( $fields ) );
        $result = curl_exec($ch);
        curl_close($ch);
        $dataj = json_decode($result);
        if ($dataj->success == 1) {
            $update=mysqli_query($con,"update tbl_order set confirm_order=1 WHERE id='$order_id'");
            if($update){
                $page = substr($_SERVER["SCRIPT_NAME"],strrpos($_SERVER["SCRIPT_NAME"],"/")+1);
                ?>
                <script>
                    alert("Order Confirm successfully");
                    window.location='<?php echo $page; ?>';
                </script><?php
            }
        } else {
            ?><script>alert("Please Try Again");</script><?php
        }
    }
    else
    {
        $tHost = 'gateway.sandbox.push.apple.com';
        $tPort = 2195;
        $tCert = $certificate;
        $tPassphrase = $passphrace;
        $tToken =$reg_id ;
        $tAlert = $massage;
        $tBadge = 8;
        $tSound = 'default';
        $tPayload = $massage;
        $tBody['aps'] = array (
            'alert' => $tAlert,
            'badge' => $tBadge,
            'sound' => $tSound,
            "id"=>"order"
        );
        $tBody ['payload'] = $tPayload;
        $tBody = json_encode ($tBody);
        $tContext = stream_context_create ();
        stream_context_set_option ($tContext, 'ssl', 'local_cert', $tCert);
        stream_context_set_option ($tContext, 'ssl', 'passphrase', $tPassphrase);
        $tSocket = stream_socket_client ('ssl://'.$tHost.':'.$tPort, $error, $errstr, 100, STREAM_CLIENT_CONNECT|STREAM_CLIENT_PERSISTENT, $tContext);
        if (!$tSocket)
            exit ("APNS Connection Failed: $error $errstr" . PHP_EOL);
        $tMsg = chr (0) . chr (0) . chr (32) . pack ('H*', $tToken) . pack ('n', strlen ($tBody)) . $tBody;
        $tResult = fwrite ($tSocket, $tMsg, strlen ($tMsg));
        if ($tResult)
        {
            $update=mysqli_query($con,"update tbl_order set confirm_order=1 WHERE id='$order_id'");
            if($update)
            {
                $page  = substr($_SERVER["SCRIPT_NAME"],strrpos($_SERVER["SCRIPT_NAME"],"/")+1);
                ?>
                <script>
                    alert("Order Confirm Successfully");
                    window.location='<?php echo $page; ?>';
                </script>
                <?php
            }
        }
        else
        {
            ?><script>alert("Please Try Again.");</script><?php
        }
        fclose ($tSocket);
    }
}
if(isset($_POST['rejectorder']))
{

    $query=mysqli_query($con,"select * from tbl_adminlogin WHERE id='".$_SESSION['admin_id']."'");
    $res=mysqli_fetch_array($query);
    $google_api_key=$res['google_api'];
    //iPhone
    $passphrace=$res['passphrace'];
    $certificate=$res['certificate'];
    // Notification Data
    $reg_id=$_POST['reg_id'];
    $order_date=$_POST['date'];
    $remark=$_POST['remark'];
    $order_id=$_POST['order_id'];
    $order_typedata = $_POST['order_type'];
    if($order_typedata == "Homeorder"){
        $massage = "Sorry your order is not confirmed due to ($remark)"." For Order id :".$order_id." Date ".$order_date;
        $queryfordevice = mysqli_query($con, "select * from tbl_order_homedelivery WHERE id='$order_id'");
        $row=mysqli_fetch_array($queryfordevice);
        $checkdevice = $row['user_type'];
    }
    else
    {
        $massage = "Sorry your order is not confirmed due to ($remark)"." For Order request id :".$order_id." Date ".$order_date;
        $queryfordevice = mysqli_query($con, "select * from tbl_order WHERE id='$order_id'");
        $row=mysqli_fetch_array($queryfordevice);
        $checkdevice = $row['device'];
    }
    if($checkdevice == "Android")
    {
        define( 'API_ACCESS_KEY', $google_api_key );
        $registrationIds = array( $reg_id );
        $message = array("price" => $massage,"id"=>"order");
        $fields = array
        (
            'registration_ids' 	=> $registrationIds,
            'data'			=> $message
        );
        $headers = array
        (
            'Authorization: key=' . API_ACCESS_KEY,
            'Content-Type: application/json'
        );
        $ch = curl_init();
        curl_setopt( $ch,CURLOPT_URL, 'https://android.googleapis.com/gcm/send' );
        curl_setopt( $ch,CURLOPT_POST, true );
        curl_setopt( $ch,CURLOPT_HTTPHEADER, $headers );
        curl_setopt( $ch,CURLOPT_RETURNTRANSFER, true );
        curl_setopt( $ch,CURLOPT_SSL_VERIFYPEER, false );
        curl_setopt( $ch,CURLOPT_POSTFIELDS, json_encode( $fields ) );
        $result = curl_exec($ch);
        curl_close($ch);
        $dataj = json_decode($result);
        if ($dataj->success == 1) {

            if($order_typedata == "Homeorder"){
                $update = mysqli_query($con, "update tbl_order_homedelivery set order_status=4 WHERE id='$order_id'");
            }
            else {
                $update = mysqli_query($con, "update tbl_order set confirm_order=2 WHERE id='$order_id'");
            }
            if($update)
            {
                $page = substr($_SERVER["SCRIPT_NAME"],strrpos($_SERVER["SCRIPT_NAME"],"/")+1);
                ?>
                <script>
                    alert("Order Reject Successfully");
                    window.location='<?php echo $page; ?>'
                </script><?php
            }
        }
        else
        {
            ?><script>alert("Please Try Again");</script><?php
        }
    }
    else
    {
        $tHost = 'gateway.sandbox.push.apple.com';
        $tPort = 2195;
        $tCert = $certificate;
        $tPassphrase = $passphrace;
        $tToken =$reg_id ;
        $tAlert = $massage;
        $tBadge = 8;
        $tSound = 'default';
        $tPayload = $massage;
        $tBody['aps'] = array
        (
            'alert' => $tAlert,
            'badge' => $tBadge,
            'sound' => $tSound,
            'id'=>"order"
        );
        $tBody ['payload'] = $tPayload;
        $tBody = json_encode ($tBody);
        $tContext = stream_context_create ();
        stream_context_set_option ($tContext, 'ssl', 'local_cert', $tCert);
        stream_context_set_option ($tContext, 'ssl', 'passphrase', $tPassphrase);
        $tSocket = stream_socket_client ('ssl://'.$tHost.':'.$tPort, $error, $errstr, 100, STREAM_CLIENT_CONNECT|STREAM_CLIENT_PERSISTENT, $tContext);
        if (!$tSocket)
            exit ("APNS Connection Failed: $error $errstr" . PHP_EOL);
        $tMsg = chr (0) . chr (0) . chr (32) . pack ('H*', $tToken) . pack ('n', strlen ($tBody)) . $tBody;
        $tResult = fwrite ($tSocket, $tMsg, strlen ($tMsg));
        if ($tResult) {
            $update=mysqli_query($con,"update tbl_order set confirm_order=2 WHERE id='$order_id'");
            if($update){
                $page = substr($_SERVER["SCRIPT_NAME"],strrpos($_SERVER["SCRIPT_NAME"],"/")+1);
                if($order_typedata == "Homeorder"){
                    $update = mysqli_query($con, "update tbl_order_homedelivery set order_status=4 WHERE id='$order_id'");
                }
                else {
                    $update = mysqli_query($con, "update tbl_order set confirm_order=2 WHERE id='$order_id'");
                }
                ?>
                <script>
                    alert("Order Reject Successfully");
                    window.location='<?php echo $page; ?>'
                </script><?php
            }
        }
        else {
            echo '{"id":"Please Try Again."}';
        }
        fclose ($tSocket);
    }
}
?>




