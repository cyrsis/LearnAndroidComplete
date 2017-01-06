<?php include'../application/config.php';
session_start();
include'../translate.php';
$id=$_POST['data'];
$query=mysqli_query($con,"select * from tbl_foodcategory WHERE id='$id'");
$res=mysqli_fetch_array($query);
?>
<form  id="contact-form" class="form-horizontal" method="post" novalidate="novalidate" enctype="multipart/form-data">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h3><?php echo _("Edit Food Category For"); ?> <font color="orange"><?php echo $res['name'];  ?></font></h3>
    </div>
    <div class="modal-body">
        <fieldset>
            <div class="control-group">
                <label class="control-label" for="name"><?php echo _("Category Name"); ?></label>
                <div class="controls">
                    <input type="text" class="input-large" value="<?php echo $res['name']; ?>"  name="cname"  required>
                </div>
            </div>
            <input type="hidden" name="cid" value="<?php echo $id; ?>">
           <!-- <div class="control-group">
                <label class="control-label" for="name"><?php /*echo _("Change Image"); */?></label>
                <div class="controls">
                    <input type="file" class="input-large" name="file" required>
                </div>
            </div>
            <div class="control-group">
                <div class="controls">
                    <img src="uploads/<?php /*echo $res['image'];  */?>" height="100" width="150" align="center" />
                </div>
            </div>-->
        </fieldset>
    </div>
    <div class="modal-footer">
        <a href="#" class="btn" data-dismiss="modal"><?php echo _("Close"); ?></a>
        <button  class="btn btn-primary" name="editfoodcat" type="submit"><?php echo _("Save changes"); ?></button>
    </div>
</form>
