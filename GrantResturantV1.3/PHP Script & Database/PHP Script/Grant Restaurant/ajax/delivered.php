<?php
include '../application/config.php';
session_start();
$id=$_POST['data'];
$query=mysqli_query($con,"select * from tbl_order_homedelivery WHERE id='$id'");
$row=mysqli_fetch_array($query);
$query=mysqli_query($con,"select * from tbl_adminlogin WHERE id='".$_SESSION['admin_id']."'");
$res=mysqli_fetch_array($query);
$google_api_key=$res['google_api'];
//iPhone
$passphrace=$res['passphrace'];
$certificate="../include/".$res['certificate'];
// Notification Data
$reg_id=$row['reg_id'];
$order_date=date('d-M-y', $row['timestamp']);
$order_id=$row['id'];
$massage="Order is delivered for Order request id :".$order_id." & Date ".$order_date;
if($row['user_type'] == "Android")
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
        $update=mysqli_query($con,"update tbl_order_homedelivery set order_status=3 WHERE id='$order_id'");
        if($update){
            $page = substr($_SERVER["SCRIPT_NAME"],strrpos($_SERVER["SCRIPT_NAME"],"/")+1);
            echo "True";
        }
    } else {
        echo "False";
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
    if ($tResult) {
        $update=mysqli_query($con,"update tbl_order_homedelivery set order_status=3 WHERE id='$order_id'");
        if($update)
        {
          echo "True";
        }
    }
    else {
        echo "False";
    }
    fclose ($tSocket);
}
