<?php $setc=mysqli_query($con,"select * from tbl_setcurrency ");$cu=mysqli_fetch_array($setc); ?>
<div id="content">
    <div class="container">
        <div class="row">


            <div class="span12">

                <div class="widget widget-table">


                    <div class="widget-header">

                        <h3>

                            <i class="icon-th-list"></i>

                            <?php echo _("Restaurant Orders"); ?>

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
                            while($data=mysqli_fetch_array($query1)){ ?>
                                <tr class="odd gradeX">
                                    <td><i class="icon-shopping-cart"></i></td>
                                    <td class="center">
                                        <?php echo $data['id']; ?>
                                    </td>
                                    <td class=" center">
                                        <a href=""> <?php echo $data['name']; ?></a>
                                    </td>
                                    <td class="center">
                                        <span class="badge_style b_high"><?php echo $data['no_people']; ?></span>
                                    </td>
                                    <td class="center">
                                        <?php echo $data['phone']; ?>
                                    </td>
                                    <td>
                                        <?php echo $data['comment']; ?>
                                    </td>
                                    <td >
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
                                    </td >
                                    <td  class="center">
                                        <?php
                                        if($_SESSION['demo'] == 1) {
                                            if ($data['confirm_order'] == 0) {
                                                ?>
                                                <a href="#confirm" onclick="orderconfirm(<?php echo $data['id']; ?>)"
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
                                            <a onclick="deleteorder(<?php echo $data['id']; ?>)" class="btn btn-tertiary" title="Delete Order"><?php echo _("Delete"); ?></a>
                                            <?php
                                        }
                                        else{
                                            if ($data['confirm_order'] == 0) {
                                                ?>
                                                <a href="#confirm" onclick="demo()"
                                                   data-toggle="modal" class="btn btn-success"
                                                   title="Delete Order"><?php echo _("Confirm"); ?></a>
                                                <a href="#rejects" onclick="demo()"
                                                   data-toggle="modal" class="btn btn-danger"
                                                   title="Reject Order"><?php echo _("Reject"); ?></a>
                                                <?php
                                            } elseif ($data['confirm_order'] == 1) {
                                                ?>
                                                <a href="#rejects" onclick="demo()"
                                                   data-toggle="modal" class="btn btn-danger"
                                                   title="Reject Order"><?php echo _("Reject"); ?></a>
                                                <?php
                                            } else {

                                            }
                                            ?>
                                            <a onclick="demo(<?php echo $data['id']; ?>)" class="btn btn-tertiary" title="Delete Order"><?php echo _("Delete"); ?></a>
                                            <?php
                                        }
                                        ?>

                                    </td>

                                </tr>
                            <?php } ?>



                            </tbody>

                        </table>


                    </div>
                    <!-- /widget-content -->


                </div>





            </div> <!-- /.span8 -->



        </div> <!-- /.row -->





    </div> <!-- /.container -->



</div> <!-- /#content -->
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
                        window.location='resorder.php';
                    }
                    else {
                        alert("false");
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




