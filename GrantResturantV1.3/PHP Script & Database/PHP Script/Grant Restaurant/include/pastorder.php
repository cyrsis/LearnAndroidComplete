<?php $setc=mysqli_query($con,"select * from tbl_setcurrency ");$cu=mysqli_fetch_array($setc); ?>
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

                    </div> <!-- /.widget-header -->
                    <?php
                    $setc=mysqli_query($con,"select * from tbl_setcurrency ");$cu=mysqli_fetch_array($setc);
                    $query=mysqli_query($con,"select * from tbl_order WHERE status=1 "); $r1=mysqli_num_rows($query);
                    $today=mysqli_query($con,"select * from tbl_order");
                    $i=0;
                    while($data=mysqli_fetch_array($today))
                    {
                        $dt=$data['date'];
                        $date = date("d/m/Y");
                        if($dt == $date){
                            $i++;
                        }
                    }
                    $today1=mysqli_query($con,"select * from tbl_order");
                    $j=0;
                    while($data1=mysqli_fetch_array($today1))
                    {
                        $dt2=$data1['date'];
                        $date1 = date("d/m/Y");

                        if($dt2 != $date1){
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
                    if($c==1){
                        $tds = percentage($todayorder, 10, 0);
                    }
                    elseif($c==2){
                        $tds = percentage($todayorder, 100, 0);
                    }
                    elseif($c==3){
                        $tds = percentage($todayorder, 1000, 0);
                    }
                    else{
                        $tds = percentage($todayorder, 10000, 0);
                    }
                    if($a==1){
                        $pst = percentage($pasteorder, 10, 0);
                    }
                    elseif($a==2){
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
                                <a href="homedelivery.php" class="btn btn-small btn-info " title="Orders For Home Delivery Service"><?php echo _("Home Delivery Order"); ?></a>
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
                                <a href="dashboard.php" class="btn btn-small btn-primary" title="Today's Request For Book Table"> <?php echo _("Request For Booktable"); ?></a>
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
                                <a href="pastorder.php" class="btn btn-small btn-secondary" title="Past Request For Book Table"> <?php echo _("Past Request"); ?></a><!-- /.stat-label -->
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
                                <a href="totalorder.php" class="btn btn-small btn-tertiary" title="Total Request For Book Table"> <?php echo _("Total Request"); ?></a><!-- /.stat-label -->
                                <div class="stat-value">
                                    <b><?php echo $r2; ?></b>
                                </div>
                            </div>
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
                            <i class="icon-th-list"></i><?php echo _("Past Orders"); ?>
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
                                <th><?php echo _("People"); ?></th>
                                <th><?php echo _("Phone"); ?></th>
                                <th><?php echo _("Comment"); ?></th>
                                <th><?php echo _("Description"); ?></th>
                                <th><?php echo _("Order Date"); ?></th>
                                <th><?php echo _("Action"); ?></th>

                            </tr>
                            </thead>
                            <tbody>
                            <?php
                            $query1=mysqli_query($con,"select * from tbl_order ORDER BY id DESC ");
                            while($data=mysqli_fetch_array($query1)) {
                                $dt = $data['date'];
                                $date = date("d/m/20y");

                                if ($date != $dt) {
                                    ?>
                                    <tr class="odd gradeX">
                                        <td><i class="icon-shopping-cart"></i></td>
                                        <td class="center">
                                            <?php echo $data['id']; ?>
                                        </td>
                                        <td class=" center">
                                            <a href=""> <?php echo $data['name']; ?></a>
                                        </td>
                                        <td class="center">
                                                <span
                                                    class="badge_style b_high"><?php echo $data['no_people']; ?></span>
                                        </td>
                                        <td class="center">
                                            <?php echo $data['phone']; ?>
                                        </td>
                                        <td class="center">
                                            <?php echo $data['comment']; ?>
                                        </td>
                                        <td>
                                            <?php
                                            if($data['orderdis'] != ""){
                                            $desc=$data['orderdis'];
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
                                                echo "Total Amount : ".$cu['symbol'].$data['orderdis2']."<br/>";
                                                ?>
                                                <?php }
                                                else
                                                {
                                                    echo $data['orderdis2'];
                                                }?>
                                        </td>

                                        <td class="center">
                                            <?php echo $data['datetime']; ?>
                                        </td>
                                        <td class="center">
                                            <?php
                                            if($_SESSION['demo'] == 1) {
                                                if ($data['confirm_order'] == 0) {
                                                    ?>
                                                    <a href="#confirm"
                                                       onclick="orderconfirm(<?php echo $data['id']; ?>)"
                                                       data-toggle="modal" class="btn btn-success"
                                                       title="Delete Order"><?php echo _("Confirm"); ?></a>
                                                    <a href="#rejects" onclick="rejectorder(<?php echo $data['id']; ?>)"
                                                       data-toggle="modal" class="btn btn-danger"
                                                       title="Reject Order"><?php echo _("Reject"); ?></a>
                                                    <?php
                                                } elseif ($data['confirm_order'] == 1) {
                                                    ?>
                                                    <a href="#rejects" onclick="rejectorder(<?php echo $data['id']; ?>)"
                                                       data-toggle="modal" class="btn btn-danger"
                                                       title="Reject Order"><?php echo _("Reject"); ?></a>
                                                    <?php
                                                } else {

                                                }
                                                ?>
                                                <a onclick="deleteorder(<?php echo $data['id']; ?>)"
                                                   class="btn btn-tertiary"
                                                   title="Delete Order"><?php echo _("Delete"); ?></a>

                                                <?php
                                            }
                                            else{
                                                if ($data['confirm_order'] == 0) {
                                                    ?>
                                                    <a href="#confirm"
                                                       onclick="demo(<?php echo $data['id']; ?>)"
                                                       data-toggle="modal" class="btn btn-success"
                                                       title="Delete Order"><?php echo _("Confirm"); ?></a>
                                                    <a href="#rejects" onclick="demo(<?php echo $data['id']; ?>)"
                                                       data-toggle="modal" class="btn btn-danger"
                                                       title="Reject Order"><?php echo _("Reject"); ?></a>
                                                    <?php
                                                } elseif ($data['confirm_order'] == 1) {
                                                    ?>
                                                    <a href="#rejects" onclick="demo(<?php echo $data['id']; ?>)"
                                                       data-toggle="modal" class="btn btn-danger"
                                                       title="Reject Order"><?php echo _("Reject"); ?></a>
                                                    <?php
                                                } else {

                                                }
                                                ?>
                                                <a onclick="demo(<?php echo $data['id']; ?>)"
                                                   class="btn btn-tertiary"
                                                   title="Delete Order"><?php echo _("Delete"); ?></a>

                                                <?php
                                            }
                                                ?>
                                        </td>

                                    </tr>

                                <?php }
                            }?>
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
<div class="modal fade hide" id="rejects">
    <div id="rejectorder">

    </div>
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
                        window.location='pastorder.php';
                    }
                    else {
                        alert("! <?php echo _("Try Again Please"); ?> !!!");
                    }
                }
            });
        }
        else{
            return false;
        }
    }
    function orderconfirm(data){

        $.ajax({
            type: "POST",
            url: 'ajax/orderconfirm.php',
            data: "data=" + data,
            success: function (data) {
                if (data) {
                    document.getElementById("orderconfirm").innerHTML=data;
                }
                else {
                    alert("! <?php echo _("Try again please"); ?>!!!");
                }
            }
        });
    }
    function rejectorder(data){
        $.ajax({
            type: "POST",
            url: 'ajax/rejectorder.php',
            data: "data=" + data,
            success: function (data) {
                if (data) {
                    document.getElementById("rejectorder").innerHTML=data;
                }
                else {
                    alert("! <?php echo _("Try again please"); ?>!!!");
                }
            }
        });
    }
</script>
<?php include 'sendpushnotification.php';?>





