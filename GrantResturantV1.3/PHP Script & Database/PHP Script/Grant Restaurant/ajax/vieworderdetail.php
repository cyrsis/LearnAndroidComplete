<?php
include '../application/config.php';
$id=$_POST['data'];
$query=mysqli_query($con,"select * from tbl_order_homedelivery WHERE id='$id'");
$res=mysqli_fetch_array($query);
?>
<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal">&times;</button>
    <h3><?php echo _("Order Detail"); ?></h3>
</div>
<form  class="form-horizontal" method="post"  enctype="multipart/form-data">
    <div class="modal-body">
        <table class="table table-striped table-bordered table-highlight" id="example">
            <thead>
            <tr>
                <th>Image</th>
                <th>Food Name</th>
                <th>Quantity</th>
                <th>Price</th>
                <th>Total Amount</th>
            </tr>
            </thead>
            <tbody>

            <?php
            if($res['orderdesc'] != "") {
                $desc = $res['orderdesc'];
                $arr = explode(";", $desc);
                foreach ($arr as $val) {
                    $a = explode(",", $val);
                    unset($arr);
                    foreach ($a as $val1) {
                        $vald = $val1 . ",";
                        $datya = substr($val1, strpos($val1, "=") + 1);
                        $arr[] = $datya;

                    }
                    if(isset($arr[0]) && isset($arr[1]) && isset($arr[2]) && isset($arr[3]) && isset($arr[4])) {
                        $selmenu=mysqli_query($con,"select * from tbl_subcategory WHERE id='".$arr[0]."'");
                        $row=mysqli_fetch_array($selmenu);
                        ?>
                        <tr>
                            <td><img src="uploads/<?php echo $row['image']; ?>" style="height: 50px;width: 50px;border-radius:40px;"></td>
                            <td><?php echo $row['name']; ?></td>
                            <td><?php echo $arr[2]; ?></td>
                            <td><?php echo "$".$arr[1]; ?></td>
                            <td><?php echo "$".$arr[3]; ?></td>
                        </tr>
                        <?php
                    }
                }
            }
            ?>

            </tbody>
            <!--<thead>
            <tr>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
                <th>
                    <?php
/*                    if($res['orderdesc1'] != "") {
                        $val = $res['orderdesc1'];
                        $a = explode(",", $val);
                        unset($arr);
                        foreach ($a as $val1) {
                            $vald = $val1 . ",";
                            $datya = substr($val1, strpos($val1, "=") + 1);
                            $arr[] = $datya;
                        }
                        if ($res['price'] != "") {
                            echo "Total Amount : <font color='black'>$" . $res['price'] . "</font><br>";
                        }
                    }
                    */?>
                </th>
            </tr>
            </thead>-->
        </table>
       <!-- <div align="right" style="font-size: 15px;"><font color="#696969">Total Amount : </font> $500.00 </div>-->
    </div>
</form>