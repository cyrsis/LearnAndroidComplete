<?php
$id=$_POST['data'];
include'../application/config.php';
session_start();
include'../translate.php';
$qury=mysqli_query($con,"select * from tbl_subcategory WHERE id='$id'");
$data=mysqli_fetch_array($qury);
$allcat1=mysqli_query($con,"select * from tbl_foodcategory WHERE id='".$data['cat_id']."'");
$name=mysqli_fetch_array($allcat1);
?>
<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal">&times;</button>
    <h3><?php echo _("Edit Menu For"); ?> <font color="orange"><?php echo $name['name']; ?></font></h3>
</div>
<form  id="contact-form" class="form-horizontal" method="post" novalidate="novalidate" enctype="multipart/form-data">
    <div class="modal-body">
        <fieldset>
            <div class="control-group">
                <label class="control-label" for="name"><?php echo _("Select Food Category"); ?></label>
                <div class="controls">
                    <select class="input-large" name="type" required>
                        <option value=""><?php echo _("Select Food Category"); ?></option>
                        <?php
                        $allcat=mysqli_query($con,"select * from tbl_foodcategory");
                        while($all=mysqli_fetch_array($allcat)){
                            if($all['id'] != $data['cat_id']){
                                ?>
                                <option value="<?php echo $all['id']; ?>"><?php echo $all['name']; ?></option>
                            <?php } else {
                                ?>
                                <option value="<?php echo $all['id']; ?>" selected><?php echo $all['name']; ?></option>
                                <?php
                            } ?>

                        <?php } ?>
                    </select>
                </div>
            </div>
            <input type="hidden" name="cid"  value="<?php echo $_POST['data']; ?>">
            <div class="control-group">
                <label class="control-label" for="name"><?php echo _("Menu Name"); ?></label>
                <div class="controls">
                    <input type="text" class="input-large" value="<?php echo $data['name']; ?>" name="cname"  required>
                </div>
            </div>

            <div class="control-group">
                <label class="control-label" for="name"><?php echo _("Select Image"); ?></label>
                <div class="controls">
                    <input type="file" class="input-large" name="file" required>
                </div>
            </div>
            <div class="control-group">
                <div class="controls">
                    <img src="uploads/<?php echo $data['image']; ?>" height="100" width="150">
                </div>
            </div>
            <?php
            $setc=mysqli_query($con,"select * from tbl_setcurrency ");$cu=mysqli_fetch_array($setc);
            ?>
            <div class="control-group">
                <label class="control-label" for="name"><?php echo _("Price"); ?></label>
                <div class="controls">
                    <span class="add-on"><?php echo $cu['symbol']; ?></span>
                    <input class="span2" id="prependedInput" value="<?php echo $data['price']; ?>" name="sprice" size="16" type="text" >
                </div>
            </div>
            <div class="control-group">
                <label class="control-label" for="name"><?php echo _("Category Description"); ?></label>
                <div class="controls">
                    <textarea class="input-large" name="desc"  required><?php echo $data['description']; ?></textarea>
                </div>
            </div>
            <div class="control-group">
        </fieldset>
    </div>
    <div class="modal-footer">
        <a href="#" class="btn" data-dismiss="modal"><?php echo _("Close"); ?></a>
        <button  class="btn btn-primary" name="editmenu" type="submit"><?php echo _("Save changes"); ?></button>
    </div>
</form>