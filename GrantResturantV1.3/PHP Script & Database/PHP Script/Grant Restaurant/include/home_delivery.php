<?php
$setc=mysqli_query($con,"select * from tbl_setcurrency ");$cu=mysqli_fetch_array($setc);
?>
<style>
    .no-js #loader {  display: none;  }
    .js #loader { display: block; position: absolute; left: 100px; top: 0; }
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
                    <?php
                    $setc=mysqli_query($con,"select * from tbl_setcurrency ");$cu=mysqli_fetch_array($setc);
                    $query=mysqli_query($con,"select * from tbl_order WHERE status=1 "); $r1=mysqli_num_rows($query);
                    $today=mysqli_query($con,"select * from tbl_order");
                    $i=0;
                    while($data=mysqli_fetch_array($today))
                    {
                        $dt=$data['date'];
                        $date = date("d/m/Y");
                        if($dt == $date)
                        {
                            $i++;
                        }
                    }
                    $today1=mysqli_query($con,"select * from tbl_order");
                    $j=0;
                    while($data1=mysqli_fetch_array($today1))
                    {
                        $dt2=$data1['date'];
                        $date1 = date("d/m/Y");
                        if($dt2 != $date1)
                        {
                            $j++;
                        }
                    }
                    ?>
                    <?php $query1=mysqli_query($con,"select * from tbl_order "); $r2=mysqli_num_rows($query1); ?>
                    <?php $query2=mysqli_query($con,"select * from tbl_order "); $r3=mysqli_num_rows($query2); ?>
                    <?php
                    $todayorder=$i;
                    $pasteorder=$j;
                    $totalorder=$r2;
                    $a=strlen($pasteorder);
                    $b=strlen($totalorder);
                    $c=strlen($todayorder);
                    function percentage($val1, $val2, $precision)
                    {
                        $division = $val1 / $val2;
                        $res = $division * 100;
                        $res = round($res, $precision);
                        return $res;
                    }
                    if($c==1)
                    {
                        $tds = percentage($todayorder, 10, 0);
                    }
                    elseif($c==2)
                    {
                        $tds = percentage($todayorder, 100, 0);
                    }
                    elseif($c==3)
                    {
                        $tds = percentage($todayorder, 1000, 0);
                    }
                    else
                    {
                        $tds = percentage($todayorder, 10000, 0);
                    }
                    if($a==1)
                    {
                        $pst = percentage($pasteorder, 10, 0);
                    }
                    elseif($a==2)
                    {
                        $pst = percentage($pasteorder, 100, 0);
                    }
                    elseif($a==3){
                        $pst = percentage($pasteorder, 1000, 0);
                    }
                    else{
                        $pst = percentage($pasteorder, 10000, 0);
                    }
                    if($b==1){
                        $ttl = percentage($totalorder, 10, 0);
                    }
                    elseif($b==2){
                        $ttl = percentage($totalorder, 100, 0);
                    }
                    elseif($b==3){
                        $ttl = percentage($totalorder, 1000, 0);
                    }
                    else{
                        $ttl = percentage($totalorder, 10000, 0);
                    }

                    $selhomeorder=mysqli_query($con,"select * from tbl_order_homedelivery ORDER BY id DESC ");
                    $thome=mysqli_num_rows($selhomeorder);
                    $e=strlen($thome);
                    if($e==1){
                        $ttle = percentage($thome, 10, 0);
                    }
                    elseif($e==2){
                        $ttle = percentage($thome, 100, 0);
                    }
                    elseif($e==3){
                        $ttle = percentage($thome, 1000, 0);
                    }
                    elseif($e = 4){
                        $ttle = percentage($thome, 10000, 0);
                    }
                    else{
                        $ttle = percentage($thome, 100000, 0);
                    }
                    ?>
                    <div class="widget-content">
                        <div class="stat">
                            <div class="stat-header">
                                <a href="dashboard.php" class="btn btn-small btn-info" title="Orders For Home Delivery Service"><?php echo _("Home Delivery Order"); ?></a>
                                <div class="stat-value">
                                    <b><?php echo $thome; ?></b>
                                </div>
                            </div>
                            <div class="progress progress-info progress-striped">
                                <div class="bar" style="width: <?php echo $ttle; ?>%;"></div> <!-- /.bar -->
                            </div>
                        </div>
                        <div class="stat">
                            <div class="stat-header">
                                <a href="dashboard.php" class="btn btn-small btn-primary " title="Today's Request For Book Table"> <?php echo _("Request For Booktable"); ?></a>
                                <div class="stat-value">
                                    <b><?php echo $i; ?></b>
                                </div>
                            </div>
                            <div class="progress progress-primary progress-striped">
                                <div class="bar" style="width: <?php echo $tds; ?>%;"></div> <!-- /.bar -->
                            </div>
                        </div>
                        <div class="stat">
                            <div class="stat-header">
                                <a href="pastorder.php" class="btn btn-small btn-secondary " title="Past Request For Book Table"> <?php echo _("Past Request"); ?></a>
                                <div class="stat-value">
                                    <b><?php echo $j; ?></b>
                                </div>
                            </div>
                            <div class="progress progress-secondary progress-striped">
                                <div class="bar" style="width: <?php echo $pst; ?>%;"></div> <!-- /.bar -->
                            </div>
                        </div>
                        <div class="stat">
                            <div class="stat-header">
                                <a href="totalorder.php" class="btn btn-small btn-tertiary " title="Total Request For Book Table"> <?php echo _("Total Request"); ?></a><!-- /.stat-label -->
                                <!-- /.stat-label -->
                                <div class="stat-value">
                                    <b><?php echo $r2; ?></b>
                                </div> <!-- /.stat-value -->
                            </div> <!-- /stat-header -->
                            <div class="progress progress-tertiary progress-striped">
                                <div class="bar" style="width: <?php echo $ttl; ?>%;"></div> <!-- /.bar -->
                            </div>
                        </div>
                        <br />
                    </div>
                </div>
            </div>
            <div class="span10">
                <div class="widget widget-table">
                    <div class="widget-header">
                        <h3>
                            <i class="icon-th-list"></i>
                            <?php echo _("Total Orders"); ?>
                        </h3>
                    </div>
                    <!-- /widget-header -->
                    <div class="widget-content">
                        <table class="table table-striped table-bordered table-highlight" id="example">
                            <thead>
                            <tr>
                                <th>#</th>
                                <th>id</th>
                                <th><?php echo _("Name"); ?></th>
                                <th><?php echo _("Phone"); ?></th>
                                <th><?php echo _("Created At"); ?></th>
                                <th><?php echo _("Order Detail"); ?></th>
                                <th><?php echo _("Status"); ?></th>
                                <th><?php echo _("Action"); ?></th>
                            </tr>
                            </thead>
                            <tbody>
                            <?php
                                $queryselhomeorder=mysqli_query($con,"select * from tbl_order_homedelivery ORDER by id DESC ");
                                while($res=mysqli_fetch_array($queryselhomeorder)) {
                                    ?>
                                    <tr>
                                        <td><i class="icon-shopping-cart"></i></td>
                                        <td><?php echo $res['id']; ?></td>
                                        <td><a href="#detail" onclick="persondetail(<?php echo $res['id']; ?>)" data-toggle="modal"><?php echo $res['name']; ?></a></td>
                                        <td><?php echo $res['number']; ?></td>
                                        <?php
                                        $time=$res['timestamp'];
                                        $formatted_date=date("c", $time);
                                        ?>


                                        <td><div class='display_div' title="<?php echo $formatted_date; ?>"></div></td>
                                        <td>
                                           <!-- <a href="#orderdetail" onclick="vieworderdetail(<?php /*echo $res['id']; */?>)" data-toggle="modal">
                                                <i class="badge badge-success">View Order Detail</i>
                                            </a>-->
                                            <?php
                                            if($res['orderdesc'] != ""){
                                            $desc=$res['orderdesc'];
                                            $arr = explode(",",$desc);
                                            foreach($arr as $val)
                                            {
                                                $a=explode(";",$val);
                                                unset($arr);
                                                foreach($a as $val1)
                                                {
                                                    $vald=$val1.",";
                                                    $datya=substr($val1, strpos($val1, "=") + 1);
                                                    $arr[]= $datya;

                                                }
                                                ?>
                                                <div style="line-height: 15px;color: gray; width:200px; ">
                                                <?php
                                                if($arr[1] != "" && $arr[3] != "") {
                                                    echo $arr[2]." ";
                                                    echo '"'.$arr[0].'"';

                                                    echo " Price : " . $cu['symbol'] . $arr[1];
                                                    echo " Total : " . $cu['symbol'] . $arr[3];
                                                }
                                                ?><br/></div><?php
                                            }
                                            ?>

                                            <div style="line-height: 15px;color: gray;">
                                                <?php
                                                echo "<br>";
                                                echo "Total Amount : ".$cu['symbol'].$res['orderdesc1']."<br/>";
                                                ?>
                                                <?php }
                                                else
                                                {
                                                    echo $data['orderdis2'];
                                                }?>

                                        </td>
                                        <td>
                                            <?php
                                            if($res['order_status'] == 0){
                                                ?>
                                                <i class="badge badge-warning">New</i>

                                                <?php
                                            }
                                            elseif($res['order_status'] == 1){
                                                ?>
                                                <i class="badge badge-success">Confirm Order</i>
                                                <?php
                                            }
                                            elseif($res['order_status'] == 2){
                                                ?>
                                                <i class="badge badge-info">Out For Delivery</i>
                                                <?php
                                            }
                                            elseif($res['order_status'] == 3){
                                                ?>
                                                <i class="badge badge-inverse">Delivered</i>
                                                <?php
                                            }
                                            else{
                                                ?>
                                                <i class="badge badge-important">Order Rejected</i>
                                                <?php
                                            }
                                            ?>
                                        </td>
                                        <td>
                                            <?php
                                            if($res['order_status'] == 0){
                                                ?>
                                                <button class="badge badge-success" onclick="confirmorder(<?php echo $res['id']; ?>)" type="button">Confirm</button>
                                                <button class="badge badge-info" onclick="rejectorder(<?php echo $res['id']; ?>)" type="button" href="#rejectorder" data-toggle="modal" >Reject</button>
                                                <button class="badge badge-important" type="button" onclick="deletehomeorder(<?php echo $res['id']; ?>)">Delete</button>
                                                <?php
                                            }
                                            elseif($res['order_status'] == 1){
                                                ?>
                                                <button class="badge badge-info" onclick="outfordelivery(<?php echo $res['id']; ?>)" type="button">Out For Delivery</button>
                                                <?php
                                            }
                                            elseif($res['order_status'] == 2)
                                            {
                                                ?>
                                                <button class="badge badge-inverse" onclick="delivered(<?php echo $res['id']; ?>)" type="button">Delivered</button>
                                                <?php
                                            }
                                            elseif($res['order_status'] == 3)
                                            {
                                                ?>
                                                <button class="badge badge-important" type="button" onclick="deletehomeorder(<?php echo $res['id']; ?>)">Delete</button>
                                                <?php
                                            }
                                            else
                                            {
                                                ?>
                                                <button class="badge badge-important" onclick="deletehomeorder(<?php echo $res['id']; ?>)" type="button">Delete</button>
                                                <?php
                                            }
                                            ?>
                                        </td>
                                    </tr>
                                    <?php
                                }
                            ?>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="modal fade hide" id="confirm">
    <div id="orderconfirm">

    </div>
</div>
<div class="modal fade hide" id="rejectorder">
   <div id="rejectorderdata">
   </div>
</div>


<div class="modal fade hide" id="detail">
    <div id="person_detail">

    </div>
</div>

<div class="modal fade hide" id="rejects">
    <div id="rejectorder"></div>
</div>
<script>
    function deleteorder(id){
        var dataid = id;
        var x = confirm("<?php echo _("Are You Sure You Want To Delete"); ?> !!!");
        if(x) {
            $.ajax({
                type: "POST",
                url: 'ajax/deleteorder.php',
                data: "data=" + dataid,
                success: function (data) {
                    if (data == "deleted") {
                        alert("! <?php echo _("Order Delete Successfully"); ?> !!! ");
                        window.location='totalorder.php';
                    }
                    else {
                        alert("! <?php echo _("Try again Please"); ?>!!!");
                    }
                }
            });
        }
        else{
            return false;
        }
    }
    function orderconfirm(data){
        $("#loading").show();
        $.ajax({
            type: "POST",
            url: 'ajax/orderconfirm.php',
            data: "data=" + data,
            success: function (data) {
                if (data) {
                    $("#loading").hide();
                    document.getElementById("orderconfirm").innerHTML=data;
                }
                else {
                    $("#loading").hide();
                    alert("! <?php echo _("Try again please"); ?>!!!");
                }
            }
        });
    }
    function rejectorder(data){
        $.ajax({
            type: "POST",
            url: 'ajax/rejectorderhome.php',
            data: "data=" + data,
            success: function (data) {
                if (data) {
                    document.getElementById("rejectorderdata").innerHTML=data;
                }
                else {
                    $("#loading").hide();
                    alert("! <?php echo _("Try again please"); ?>!!!");
                }
            }
        });
    }
    function persondetail(data){
        $("#loading").show();
        $.ajax({
            type: "POST",
            url: 'ajax/persondetail.php',
            data: "data=" + data,
            success: function (data) {
                if (data) {
                    $("#loading").hide();
                    document.getElementById("person_detail").innerHTML=data;
                }
                else {
                    alert("! <?php echo _("Try again please"); ?>!!!");
                }
            }
        });
    }
    function vieworderdetail(data){
        $.ajax({
            type: "POST",
            url: 'ajax/vieworderdetail.php',
            data: "data=" + data,
            success: function (data) {
                if (data) {
                    document.getElementById("orderdetaildata").innerHTML=data;
                }
                else {
                    $("#loading").hide();
                    alert("! <?php echo _("Try again please"); ?>!!!");
                }
            }
        });
    }


    function confirmorder(data){
        $("#loading").show();
        $.ajax({
            type: "POST",
            url: 'ajax/confirmorder.php',
            data: "data=" + data,
            success: function (data) {
                if (data == "True") {
                    $("#loading").hide();
                    alert("!!! Order Confirm Successfully !");
                    window.location='homedelivery.php';
                }
                else
                {
                    $("#loading").hide();
                    alert("! <?php echo _("Try again please"); ?>!!!");
                }
            }
        });
    }
    function outfordelivery(data){
        $("#loading").show();
        $.ajax({
            type: "POST",
            url: 'ajax/outfordelivery.php',
            data: "data=" + data,
            success: function (data) {
                if (data == "True") {
                    $("#loading").hide();
                    alert("!!! Order Is Out For Delivery Success !");
                    window.location='homedelivery.php';
                }
                else
                {
                    $("#loading").hide();
                    alert("! <?php echo _("Try again please"); ?>!!!");
                }
            }
        });
    }
    function delivered(data){
        $("#loading").show();
        $.ajax({
            type: "POST",
            url: 'ajax/delivered.php',
            data: "data=" + data,
            success: function (data) {
                if (data == "True") {
                    $("#loading").hide();
                    alert("!!! Order Is Delivered Success !");
                    window.location='homedelivery.php';
                }
                else
                {
                    $("#loading").hide();
                    alert("! <?php echo _("Try again please"); ?>!!!");
                }
            }
        });
    }
    function deletehomeorder(data){
        var x = confirm("Are You Sure You Want To Delete ?");
        if(x) {
            $.ajax({
                type: "POST",
                url: 'ajax/deletehomeorder.php',
                data: "data=" + data,
                success: function (data) {
                    if (data == "True") {
                        alert("!!! Order Deleted Successfully !");
                        window.location = 'homedelivery.php';
                    }
                    else {

                        alert("! <?php echo _("Try again please"); ?>!!!");
                    }
                }
            });
        }
        else{
            return false;
        }
    }

</script>
<?php include 'sendpushnotification.php';?>




