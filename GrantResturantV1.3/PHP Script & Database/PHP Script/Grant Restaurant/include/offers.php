<style>
    .no-js #loader
    {
        display: none;
    }
    .js #loader
    {
        display: block; position: absolute; left: 100px; top: 0;
    }
    .se-pre-con
    {
        position: fixed;
        left: 0px;
        top: 0px;
        width: 100%;
        height: 100%;
        z-index: 9999;
        background: url(img/gifloading.gif) center no-repeat #fff;
        opacity: 0.7;
    }
</style>
<div class="se-pre-con" hidden id="loading"></div>
<div id="content">
    <div class="container">
        <div class="row">
            <div class="span2">
                <div class="widget">
                    <div class="widget-header">
                        <h3>
                            <i class="icon-tasks"></i>
                            <?php echo _("Quick Stats"); ?>
                        </h3>
                    </div>
                    <div class="widget-content">
                        <div class="stat">
                            <div class="stat-header">
                                <?php if($_SESSION['demo'] == 1)
                                { ?>
                                    <a href="#addfood" data-toggle="modal"  class="btn btn-medium btn-tertiary">
                                        <i class="icon-plus"></i>
                                        <?php echo _("Add New Offers"); ?>
                                    </a>
                                    <?php
                                }
                                else
                                {
                                    ?>
                                    <a href="#" onclick="demo()"  class="btn btn-medium btn-tertiary">
                                        <i class="icon-plus"></i>
                                        <?php echo _("Add New Offers"); ?>
                                    </a>
                                    <?php

                                }
                                ?>
                            </div>
                        </div>
                        <br/>
                    </div>
                </div>
            </div>
            <div class="span10">
                <div class="widget widget-table">
                    <div class="widget-header">
                        <h3>
                            <i class="icon-th-list"></i>
                            <?php echo _("Restaurant Offers"); ?>
                        </h3>
                    </div>
                    <div class="widget-content">
                        <table class="table table-striped table-bordered table-highlight" id="example">
                            <thead>
                            <tr>
                                <th>#</th>
                                <th><?php echo _("Title"); ?></th>
                                <th><?php echo _("Description"); ?></th>
                                <th><?php echo _("Action"); ?></th>
                            </tr>
                            </thead>
                            <tbody>
                            <?php
                            $query1=mysqli_query($con,"select * from tbl_offerdata ORDER BY id DESC ");
                            while($data=mysqli_fetch_array($query1)){ ?>
                                <tr>
                                    <td class="center">
                                        <?php echo $data['id']; ?>
                                    </td>
                                    <td class=" center">
                                        <b> <?php echo $data['title']; ?></b>
                                    </td>
                                    <td class="">
                                        <?php echo $data['description']; ?>
                                    </td>
                                    <td class="center">
                                        <?php if($_SESSION['demo'] == 1){ ?>
                                            <a href="#editoffer" class="btn btn-tertiary" data-toggle="modal" onclick="editoffer(<?php echo $data['id']; ?>)" title="Edit Offres"><?php echo _("Edit"); ?></a>
                                            <a class="btn btn-danger" data-toggle="modal" onclick="deleteoffer(<?php echo $data['id']; ?>)" title="Delete Offers"><?php echo _("Delete"); ?></a>
                                        <?php }
                                        else{
                                            ?>
                                            <a href="#" class="btn btn-tertiary" onclick="demo(<?php echo $data['id']; ?>)" title="Edit Offres"><?php echo _("Edit"); ?></a>
                                            <a class="btn btn-danger"  onclick="demo(<?php echo $data['id']; ?>)" title="Delete Offers"><?php echo _("Delete"); ?></a>
                                            <?php
                                        }?>
                                    </td>
                                </tr>
                            <?php } ?>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="modal fade hide" id="addfood">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h3><?php echo _("Add New Offers"); ?></h3>
    </div>
    <form  id="contact-form" class="form-horizontal" method="post" novalidate="novalidate">
        <div class="modal-body">
            <fieldset>
                <div class="control-group">
                    <label class="control-label" for="name"><?php echo _("Title"); ?></label>
                    <div class="controls">
                        <input type="text" class="input-large " name="title"  required>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="name"><?php echo _("Descriptions"); ?></label>
                    <div class="controls">
                        <textarea name="desc" class="input-large" required></textarea>
                    </div>
                </div>
            </fieldset>
        </div>
        <div class="modal-footer">
            <a href="#" class="btn" data-dismiss="modal"><?php echo _("Close"); ?></a>
            <button  class="btn btn-primary" name="addoffer" type="submit"><?php echo _("Save changes"); ?></button>
        </div>
    </form>
</div>

<div class="modal fade hide"  id="editoffer">
    <div id="editoffer"></div>
    <script>
        function editoffer(id){
            var dataid = id;
            $.ajax({
                type: "POST",
                url: 'ajax/editoffer.php',
                data: "data=" + dataid,
                success: function(fooddata)
                {
                    document.getElementById("editoffer").innerHTML=fooddata;
                }
            });
        }
    </script>
</div>

<script>
    function deleteoffer(id){
        var dataid = id;
        var x = confirm("<?php echo _("Are You Sure You Want To Delete"); ?> !!!");
        if(x) {
            $("#loading").show();
            $.ajax({
                type: "POST",
                url: 'ajax/deleteoffer.php',
                data: "data=" + dataid,
                success: function (data) {
                    if (data == "deleted") {
                        alert("! <?php echo _("Offers Delete Successfuly"); ?> !!! ");
                        window.location='offers.php';
                        $("#loading").hide();
                    }
                    else {
                        $("#loading").hide();
                        alert(data);
                    }
                }
            });
        }
        else{
            return false;
        }
    }
</script>
<?php
if(isset($_POST['addoffer']))
{
    $title=$_POST['title'];
    $desc=$_POST['desc'];
    $q=mysqli_query($con,"insert into tbl_offerdata (title,description) VALUES ('$title','$desc')");
    if($q){
        $query=mysqli_query($con,"select * from tbl_adminlogin WHERE id='".$_SESSION['admin_id']."'");
        $res=mysqli_fetch_array($query);
        $google_api_key=$res['google_api'];
        //iPhone
        $passphrace=$res['passphrace'];
        $certificate=$res['certificate'];
        // Notification Data
        $massage="$title : $desc ";
        $tHost = 'gateway.sandbox.push.apple.com';
        $tPort = 2195;
        $tCert = $certificate;
        $tPassphrase = $passphrace;

        $tAlert = $massage;
        $tBadge = 8;
        $tSound = 'default';
        $tPayload = $massage;
        $tBody['aps'] =
            array (
                'alert' => $tAlert,
                'badge' => $tBadge,
                'sound' => $tSound,
                "id"=>"offer"
            );
        $tBody ['payload'] = $tPayload;
        $tBody = json_encode ($tBody);
        $tContext = stream_context_create ();
        stream_context_set_option ($tContext, 'ssl', 'local_cert', $tCert);
        stream_context_set_option ($tContext, 'ssl', 'passphrase', $tPassphrase);
        $tSocket = stream_socket_client ('ssl://'.$tHost.':'.$tPort, $error, $errstr, 100, STREAM_CLIENT_CONNECT|STREAM_CLIENT_PERSISTENT, $tContext);
        $querydevice=mysqli_query($con,"select * from tbl_devicedata ORDER by id DESC ");
        while($res=mysqli_fetch_array($querydevice))
        {
            if($res['device'] == "Android")
            {
                $reg_id = $res['device_id'];
                define('API_ACCESS_KEY', $google_api_key);
                $registrationIds = array($reg_id);
                $message = array("price" => $massage,"id"=>"offer");
                $fields = array
                (
                    'registration_ids' => $registrationIds,
                    'data' => $message
                );
                $headers = array
                (
                    'Authorization: key=' . API_ACCESS_KEY,
                    'Content-Type: application/json'
                );
                $ch = curl_init();
                curl_setopt($ch, CURLOPT_URL, 'https://android.googleapis.com/gcm/send');
                curl_setopt($ch, CURLOPT_POST, true);
                curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);
                curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
                curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
                curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($fields));
                $result = curl_exec($ch);
                curl_close($ch);
            }
            else
            {
                $reg_id = $res['device_id'];
                $tToken =$reg_id;
                if (!$tSocket)
                    exit ("APNS Connection Failed: $error $errstr" . PHP_EOL);
                $tMsg = chr (0) . chr (0) . chr (32) . pack ('H*', $tToken) . pack ('n', strlen ($tBody)) . $tBody;
                $tResult = fwrite ($tSocket, $tMsg, strlen ($tMsg));
                if ($tResult)
                {
                    /* echo "admin send notification ";*/
                }
            }
        }
        fclose ($tSocket);
        ?>
        <script>
            alert("! <?php echo _("Offers Add Successfully"); ?> !!! ");
            window.location='offers.php';
        </script>
        <?php
    }
    else
    {
        ?>
        <script>
            alert("! <?php echo _("Please Try Again ..."); ?> !!! ");
        </script>
        <?php
    }
}
if(isset($_POST['editoffer']))
{
    $id=$_POST['id'];
    $title=$_POST['title'];
    $desc=$_POST['desc'];
    $q=mysqli_query($con,"update tbl_offerdata set title='$title',description='$desc' WHERE id='$id'");
    if($q){
        $query=mysqli_query($con,"select * from tbl_adminlogin WHERE id='".$_SESSION['admin_id']."'");
        $res=mysqli_fetch_array($query);
        $google_api_key=$res['google_api'];
        //iPhone
        $passphrace=$res['passphrace'];
        $certificate=$res['certificate'];
        // Notification Data
        $massage="$title : $desc ";
        $tHost = 'gateway.sandbox.push.apple.com';
        $tPort = 2195;
        $tCert = $certificate;
        $tPassphrase = $passphrace;

        $tAlert = $massage;
        $tBadge = 8;
        $tSound = 'default';
        $tPayload = $massage;
        @$tBody['aps'] = array (
            'alert' => $tAlert,
            'badge' => $tBadge,
            'sound' => $tSound,
            "id"=>"offer"
        );
        $tBody ['payload'] = $tPayload;
        $tBody = json_encode ($tBody);
        $tContext = stream_context_create ();
        stream_context_set_option ($tContext, 'ssl', 'local_cert', $tCert);
        stream_context_set_option ($tContext, 'ssl', 'passphrase', $tPassphrase);
        $tSocket = stream_socket_client ('ssl://'.$tHost.':'.$tPort, $error, $errstr, 100, STREAM_CLIENT_CONNECT|STREAM_CLIENT_PERSISTENT, $tContext);
        $querydevice=mysqli_query($con,"select * from tbl_devicedata ORDER by id DESC ");
        while($res=mysqli_fetch_array($querydevice)){
            if($res['device'] == "Android") {
                $reg_id = $res['device_id'];
                define('API_ACCESS_KEY', $google_api_key);
                $registrationIds = array($reg_id);
                $message = array("price" => $massage,"id"=>"offer");
                $fields = array
                (
                    'registration_ids' => $registrationIds,
                    'data' => $message
                );
                $headers = array
                (
                    'Authorization: key=' . API_ACCESS_KEY,
                    'Content-Type: application/json'
                );
                $ch = curl_init();
                curl_setopt($ch, CURLOPT_URL, 'https://android.googleapis.com/gcm/send');
                curl_setopt($ch, CURLOPT_POST, true);
                curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);
                curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
                curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
                curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($fields));
                $result = curl_exec($ch);
                curl_close($ch);
                $dataj = json_decode($result);
                if ($dataj->success == 1)
                {

                }
            }
            else
            {
                $reg_id = $res['device_id'];
                $tToken =$reg_id;
                if (!$tSocket)
                    exit ("APNS Connection Failed: $error $errstr" . PHP_EOL);
                $tMsg = chr (0) . chr (0) . chr (32) . pack ('H*', $tToken) . pack ('n', strlen ($tBody)) . $tBody;
                $tResult = fwrite ($tSocket, $tMsg, strlen ($tMsg));
                if ($tResult)
                {

                }
            }
        }
        fclose ($tSocket);
        ?>
        <script>
            alert("! <?php echo _("Offers Update Successfully"); ?> !!! ");
            window.location='offers.php';
        </script>
        <?php
    }
    else
    {

    }
}
?>

