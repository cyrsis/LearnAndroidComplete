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
            <div class="span10" >
                <div class="widget widget-table" id="validation">
                    <div class="widget-header">
                        <h3>
                            <i class="icon-th-list"></i>
                            <?php echo _("Notification Setting"); ?>
                        </h3>
                    </div>
                    <div class="widget-content">
                        <br/>
                        <form  id="contact-form" class="form-horizontal" method="post" novalidate="novalidate">

                            <fieldset>
                                <div class="control-group">

                                    <label class="control-label" for="name"><?php echo _("Google Api Key "); ?></label>

                                    <div class="controls">

                                        <input type="text" class="input-large" style="width: 500px;" name="apikey" value="<?php echo  $res['google_api']; ?>" placeholder="Api Key For You Have Create" required>

                                    </div>

                                </div>

                                <div class="form-actions">
                                    <?php if($_SESSION['demo'] == 1){ ?>
                                        <button type="submit" name="android" class="btn btn-danger btn-large"><i class="icon-star"></i> <?php echo _("Save Changes"); ?></button>
                                        <button type="reset" class="btn btn-large"><?php echo _("Cancel"); ?></button>
                                    <?php } else {
                                        ?>
                                        <button type="button" onclick="demo()" name="android" class="btn btn-danger btn-large"><i class="icon-star"></i> <?php echo _("Save Changes"); ?></button>
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
if(isset($_POST['android'])){

    $key=$_POST['apikey'];
    $query=mysqli_query($con,"update tbl_adminlogin set google_api='$key' WHERE id='".$_SESSION['admin_id']."'");
    if($query)
    {
     ?>
        <script>
            alert("Android Device Notification Setting saved Successfully");
            window.location='notification.php';
        </script>
        <?php
    }
    else
    {
        ?>
        <script>
            alert(" Please Try Again ... ... ...  ");
        </script>
        <?php
    }
}
?>





