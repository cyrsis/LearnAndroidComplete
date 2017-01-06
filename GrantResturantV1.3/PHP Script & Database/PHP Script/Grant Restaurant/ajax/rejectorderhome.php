<?php
include '../application/config.php';
$order_id=$_POST['data'];
$query=mysqli_query($con,"select * from  tbl_order_homedelivery WHERE id='$order_id'");
$res=mysqli_fetch_array($query);
?>
<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal">&times;</button>
    <h3><?php echo _("Reject Order"); ?></h3>
</div>
<form class="form-horizontal" method="post"  enctype="multipart/form-data">
    <div class="modal-body">
        <input type="hidden" name="reg_id" value="<?php echo $res['reg_id']; ?>">
        <input type="hidden" name="order_id" value="<?php echo $res['id']; ?>">
        <?php $date = date('d/m/Y',$res['timestamp']); ?>
        <input type="hidden" name="date" value="<?php echo $date ?>">
        <input type="hidden" name="order_type" value="Homeorder">
        <fieldset>
            <div class="control-group">
                <h5>Date : <b><?php echo $date; ?></b></h5>
                <h4>Sorry Your Order Is Not Confirm ! Please Try Again Another Menu. </h4>
                <h5 style="color: dimgrey">Remark</h5>
                <textarea style="width: 462px; height: 40px;" name="remark" placeholder="Write Remark"></textarea>
            </div>
        </fieldset>
    </div>
    <div class="modal-footer">
        <a href="#" class="btn" data-dismiss="modal"><?php echo _("Close"); ?></a>
        <button  class="btn btn-primary" name="rejectorder" type="submit"><?php echo _("Reject Order"); ?></button>
    </div>
</form>