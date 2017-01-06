<?php
$query=mysqli_query($con,"select * from tbl_adminlogin WHERE id='".$_SESSION['admin_id']."'");
$res=mysqli_fetch_array($query);
?>
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
                        <div class="stat" style="line-height: 50px;">
                            <a href="notification.php" class="btn btn-large btn-primary btn-support-ask"><?php echo _("Android"); ?></a>
                            <a href="iphone.php" class="btn btn-large btn-secondary btn-support-contact"></i><?php echo _("Iphone"); ?></a>
                        </div>
                        <br/>
                    </div>
                </div>
            </div>
            <div class="span10">
                <div class="widget widget-table" id="validation">
                    <div class="widget-header">
                        <h3>
                            <i class="icon-th-list"></i>
                            <?php echo _("Notification Setting"); ?>
                        </h3>
                    </div>
                    <div class="widget-content">
                        <h4>Note : Certificate file is deffrent for local server and live server you see it.. Ex : certificate.pem </h4>
                        <br>
                        <form  id="contact-form" class="form-horizontal" enctype="multipart/form-data" method="post" novalidate="novalidate">

                            <fieldset>
                                <div class="control-group">

                                    <label class="control-label" for="name"><?php echo _("Apns Passphrace"); ?></label>

                                    <div class="controls">

                                        <input type="text" class="input-large" name="passp" value="<?php echo  $res['passphrace']; ?>" required>

                                    </div>

                                </div>
                                <div class="control-group">
                                    <label class="control-label" for="name"><?php echo _("Upload Certificate"); ?></label>
                                    <div class="controls">
                                        <input type="file" class="input-large"  name="file">
                                        <h5><?php echo $res['certificate']; ?></h5>
                                    </div>
                                </div>

                                <div class="form-actions">

                                    <?php if($_SESSION['demo']) { ?>
                                        <button type="submit" name="iphone" class="btn btn-danger btn-large"><i class="icon-star"></i> <?php echo _("Update Detail"); ?></button>
                                      <button type="reset" class="btn btn-large"><?php echo _("Cancel"); ?></button>
                                    <?php } else {
                                        ?>
                                        <button type="button" onclick="demo()"  name="iphone" class="btn btn-danger btn-large"><i class="icon-star"></i> <?php echo _("Update Detail"); ?></button>
                                        <button type="reset" class="btn btn-large"><?php echo _("Cancel"); ?></button>
                                        <?php

                                    } ?>
                                </div>
                            </fieldset>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<?php
if(isset($_POST['iphone'])){
    $tpass=$_POST['passp'];
    $file=$_FILES['file']['name'];
    $tmp_file=$_FILES['file']['tmp_name'];
    move_uploaded_file($tmp_file,"include/".$file);
    copy('include/'.$file, $file);
    $query=mysqli_query($con,"update tbl_adminlogin set passphrace='$tpass',certificate='$file' WHERE id='".$_SESSION['admin_id']."'");
    if($query){
        ?>
        <script>
            alert("Iphone Device Notification Setting saved Successfully");
            window.location='iphone.php';
        </script>
        <?php
    }
    else{
        ?>
        <script>
            alert(" Please Try Again ... ... ...  ");
        </script>
        <?php
    }
}
?>





