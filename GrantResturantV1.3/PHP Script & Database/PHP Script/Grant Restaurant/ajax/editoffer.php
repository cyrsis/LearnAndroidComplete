<?php
include'../application/config.php';
session_start();
include'../translate.php';
$id=$_POST['data'];
$query=mysqli_query($con,"select * from tbl_offerdata WHERE id='$id'");
$res=mysqli_fetch_array($query);
?>
<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal">&times;</button>
    <h3><?php echo _("Update Offers"); ?></h3>
</div>
<form  id="contact-form" class="form-horizontal" method="post" novalidate="novalidate" enctype="multipart/form-data">
    <div class="modal-body">
        <fieldset>
            <div class="control-group">
                <label class="control-label" for="name"><?php echo _("Title"); ?></label>
                <div class="controls">
                    <input type="text" class="input-large" name="title" value="<?php echo $res['title']; ?>"  required>
                </div>
            </div>
            <input type="hidden" name="id" value="<?php echo $res['id'];  ?>">
            <div class="control-group">
                <label class="control-label" for="name"><?php echo _("Descriptions"); ?></label>
                <div class="controls">
                    <textarea name="desc" class="input-large" required><?php echo $res['description']; ?></textarea>
                </div>
            </div>
        </fieldset>
    </div>
    <div class="modal-footer">
        <a href="#" class="btn" data-dismiss="modal"><?php echo _("Close"); ?></a>
        <button  class="btn btn-primary" name="editoffer" type="submit"><?php echo _("Save changes"); ?></button>
    </div>
</form>