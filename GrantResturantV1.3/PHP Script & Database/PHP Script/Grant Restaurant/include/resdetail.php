<?php
$query=mysqli_query($con,"select * from tbl_singleresdetail");
$res=mysqli_fetch_array($query);
$query1=mysqli_query($con,"select * from tbl_singleresdetail");
$check=mysqli_num_rows($query1);
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
                                    <a href="resdetail.php" class="btn btn-medium btn-primary btn-support-ask"><i class="icon-info-sign"></i><?php echo _("Restaurant Detail"); ?></a>
                                    <a href="resimages.php" class="btn btn-medium btn-secondary btn-support-contact"><i class="icon-camera"></i><?php echo _("Restaurant Image"); ?></a>
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
                            <?php echo _("Restaurant Detail"); ?>
                        </h3>
                    </div>
                    <div class="widget-content">
                        <br/>
                        <form  id="contact-form" class="form-horizontal" method="post" novalidate="novalidate">

                            <fieldset>
                                <div class="control-group">

                                    <label class="control-label" for="name"><?php echo _("Restaurant Name"); ?></label>

                                    <div class="controls">

                                        <input type="text" class="input-large" name="resname" value="<?php echo  $res['name']; ?>" required>

                                    </div>

                                </div>
                                <div class="control-group">
                                    <label class="control-label" for="name"><?php echo _("Line 1 Address"); ?></label>
                                    <div class="controls">
                                        <input type="text" class="input-large"  value="<?php echo  $res['address']; ?>" name="address" required >
                                    </div>
                                </div>
                                <div class="control-group">

                                    <label class="control-label" for="name"><?php echo _("Country"); ?></label>

                                    <div class="controls">

                                        <input type="text" class="input-large"  value="<?php echo  $res['country']; ?>" name="country" required>

                                    </div>

                                </div>
                                <div class="control-group">

                                    <label class="control-label" for="name"><?php echo _("State"); ?></label>

                                    <div class="controls">

                                        <input type="text" class="input-large"  value="<?php echo  $res['state']; ?>" name="state" required>

                                    </div>

                                </div>
                                <div class="control-group">

                                    <label class="control-label" for="name"><?php echo _("Phone"); ?></label>

                                    <div class="controls">

                                        <input type="text" class="input-large"  value="<?php echo  $res['phone']; ?>" name="phone" required >

                                    </div>

                                </div>
                                <div class="control-group">

                                    <label class="control-label" for="email"><?php echo _("Email"); ?></label>

                                    <div class="controls">

                                        <input type="text" class="input-large"  value="<?php echo  $res['email']; ?>" name="email" id="email">

                                    </div>

                                </div>
                                <div class="control-group">

                                    <label class="control-label" for="name"><?php echo _("Website"); ?></label>

                                    <div class="controls">

                                        <input type="url" class="input-large"  value="<?php echo  $res['website']; ?>" name="website" required >

                                    </div>

                                </div>
                                <div class="control-group">

                                    <label class="control-label" for="name"><?php echo _("Latitude"); ?></label>

                                    <div class="controls">

                                        <input type="text" class="input-large"  value="<?php echo  $res['lat']; ?>" name="lat" required >

                                    </div>

                                </div>
                                <div class="control-group">

                                    <label class="control-label" for="name"><?php echo _("Longitude"); ?></label>

                                    <div class="controls">

                                        <input type="text" class="input-large" value="<?php echo  $res['long']; ?>" name="long" required >

                                    </div>

                                </div>

                                <div class="form-actions">
                                    <?php
                                    if($_SESSION['demo'] == 1) {
                                        ?>
                                        <?php if ($check) { ?>
                                            <button type="submit" name="update" class="btn btn-danger btn-large"><i
                                                    class="icon-star"></i> <?php echo _("Update Detail"); ?></button>
                                        <?php } else {
                                            ?>
                                            <button type="submit" class="btn btn-danger btn-large" name="add"><i
                                                    class="icon-star"></i> <?php echo _("Add Detail"); ?></button>
                                            <?php
                                        } ?>
                                        <button type="reset" class="btn btn-large"><?php echo _("Cancel"); ?></button>
                                        <?php
                                    }
                                    else{
                                     if ($check) { ?>
                                        <button type="button" onclick="demo()" name="update" class="btn btn-danger btn-large"><i
                                                class="icon-star"></i> <?php echo _("Update Detail"); ?></button>
                                    <?php } else {
                                        ?>
                                        <button type="button" onclick="demo()" class="btn btn-danger btn-large" name="add"><i
                                                class="icon-star"></i> <?php echo _("Add Detail"); ?></button>
                                        <?php
                                    } ?>
                                    <button type="reset" class="btn btn-large"><?php echo _("Cancel"); ?></button>
                                    <?php
                                    }
                                    ?>
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
if(isset($_POST['add'])){
    $name=$_POST['resname'];
    $addres=$_POST['address'];
    $country=$_POST['country'];
    $state=$_POST['state'];
    $phone=$_POST['phone'];
    $email=$_POST['email'];
    $website=$_POST['website'];
    $lat=$_POST['lat'];
    $long=$_POST['long'];
    $addres=mysqli_query($con,"INSERT INTO `tbl_singleresdetail`
      (`name`, `address`, `country`, `state`,`phone`, `email`, `website`, `lat`, `long`)
    VALUES ('$name','$addres','$country','$state','$phone','$email','$website','$lat','$long')
    ");
    if($addres){
        ?><script>
            alert("! <?php echo _("Restaurant Detail Add Successfully"); ?> !!!");
            window.location='resdetail.php';
        </script><?php
    }

}
if(isset($_POST['update'])){
    $qury=mysqli_query($con,"select * from tbl_singleresdetail");
    $re=mysqli_fetch_array($qury);
    $id=$re['id'];
    $name=$_POST['resname'];
    $addres=$_POST['address'];
    $country=$_POST['country'];
    $state=$_POST['state'];
    $phone=$_POST['phone'];
    $email=$_POST['email'];
    $website=$_POST['website'];
    $lat=$_POST['lat'];
    $long=$_POST['long'];
    $update=mysqli_query($con,"UPDATE `tbl_singleresdetail` SET
      `name`='$name',
      `address`='$addres',
      `country`='$country',
      `state`='$state',
      `phone`='$phone',
      `email`='$email',
      `website`='$website',
      `lat`='$lat',
      `long`='$long'
       WHERE 1
    ");
    if($update){
        ?><script>
            alert("! <?php echo _("Restaurant Detail Update Successfully"); ?> !!!");
            window.location='resdetail.php';
        </script><?php
    }
}
?>





