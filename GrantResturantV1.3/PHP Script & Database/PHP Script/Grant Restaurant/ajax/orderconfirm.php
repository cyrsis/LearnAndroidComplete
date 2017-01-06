<?php
include '../application/config.php';
$order_id=$_POST['data'];
$query=mysqli_query($con,"select * from  tbl_order WHERE id='$order_id'");
$res=mysqli_fetch_array($query);
?>
<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal">&times;</button>
    <h3><?php echo _("Confirm Order"); ?></h3>
</div>
<form  class="form-horizontal" method="post"  enctype="multipart/form-data">
    <div class="modal-body">
        <input type="hidden" name="reg_id" value="<?php echo $res['reg_id']; ?>">
        <input type="hidden" name="date" value="<?php echo $res['datetime']; ?>">
        <input type="hidden" name="order_id" value="<?php echo $order_id; ?>">
        <fieldset>
            <div class="control-group">
                <h5>Date : <b><?php echo $res['datetime']; ?></b></h5>
                <h4>Congratulation Your Order Has Been Confirm</h4>
                <b>In Table No</b>
                <input type="text" name="tblno" style="width: 40px;">
            </div>
        </fieldset>
    </div>
    <div class="modal-footer">
        <a href="#" class="btn" data-dismiss="modal"><?php echo _("Close"); ?></a>
        <button  class="btn btn-primary" name="orderconfirm" type="submit"><?php echo _("Confirm Order"); ?></button>
    </div>
</form>