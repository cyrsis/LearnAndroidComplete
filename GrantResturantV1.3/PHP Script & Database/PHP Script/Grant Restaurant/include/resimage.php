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
                <div class="widget widget-table">
                    <div class="widget-header">
                        <h3>
                            <i class="icon-th-list"></i>
                            <?php echo _("Add Restaurant Images"); ?>
                        </h3>
                    </div>
                    <div class="widget-content">
                        <br>
                        <form  id="contact-form" class="form-horizontal" method="post" novalidate="novalidate" enctype="multipart/form-data">

                            <fieldset>

                                <div class="control-group">

                                    <label class="control-label" for="name"><?php echo _("Select Images"); ?></label>

                                    <div class="controls">

                                        <input type="file" class="input-large" name="photos[]" multiple required>

                                    </div>

                                </div>

                                <div class="form-actions">

                                    <?php
                                    if($_SESSION['demo'])
                                    {
                                        ?>
                                        <button type="submit" name="addimage" class="btn btn-danger btn-large"><i
                                                class="icon-star"></i><?php echo _("Add Images"); ?> </button>
                                        <button type="reset" class="btn btn-large"><?php echo _("Cancel"); ?></button>
                                        <?php
                                    }
                                    else
                                    {
                                        ?>
                                        <button type="button" onclick="demo()" name="addimage" class="btn btn-danger btn-large"><i
                                                class="icon-star"></i><?php echo _("Add Images"); ?> </button>
                                        <button type="reset" class="btn btn-large"><?php echo _("Cancel"); ?></button>

                                        <?php
                                    }
                                    ?>
                                </div>

                            </fieldset>

                        </form>

                    </div>
                </div>

                <div class="widget widget-table">
                    <div class="widget-header">
                        <h3>
                            <i class="icon-th-list"></i>
                            <?php echo _("Restaurant Images"); ?>
                        </h3>
                    </div>
                    <?php
                    $img=mysqli_query($con,"select * from tbl_multiimages");
                    $ch=mysqli_num_rows($img);
                    if($ch){
                    ?>
                    <div class="widget-content">
                        <br>
                        <div style="margin-left: 20px; line-height: 20px;">
                        <?php while ($image = mysqli_fetch_array($img)) { ?>

                                <img src="uploads/slide/<?php echo $image['image']; ?>" height="100" width="150"/> &nbsp;&nbsp;
                                <?php
                                if($_SESSION['demo'] == 1) {
                                    ?>
                                    <a href="resimages.php?imgid=<?php echo $image['id']; ?>"
                                       style="margin-left: -24px;"> <i class="icon-remove" style="color:red;"></i></a>
                                    <?php
                                }
                                else{
                                    ?>
                                    <a href="" onclick="demo()" style="margin-left: -24px;"> <i class="icon-remove" style="color:red;"></i></a>
                                    <?php
                                }

                            ?>
                        <?php } ?>
                        </div>
                            <?php } ?>

                        <br/>

                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<?php
if(isset($_POST['addimage'])){

    for($i=0; $i<count($_FILES['photos']['name']); $i++) {
        $tmpFilePath = $_FILES['photos']['tmp_name'][$i];
        if ($tmpFilePath != ""){
            $r1 = chr(rand(ord('a'), ord('z')));
            $r2 = chr(rand(ord('a'), ord('z')));
            $id=$r1.$r2;
            $newFilePath = "uploads/slide/" .$id. $_FILES['photos']['name'][$i];
            $imagepath= $id.$_FILES['photos']['name'][$i];
            if(move_uploaded_file($tmpFilePath, $newFilePath)) {
                $qry=mysqli_query($con,"insert into tbl_multiimages (image) VALUES ('$imagepath')");
            }
        }
    }
    ?><script>alert("! <?php echo _("Images add Successfully"); ?> !!! ");window.location='resimages.php';</script><?php
}
if(isset($_GET['imgid'])){
    $imgid=$_GET['imgid'];
    $qu=mysqli_query($con,"select * from tbl_multiimages WHERE id='$imgid'");
    $unlink=mysqli_fetch_array($qu);
    $image=$unlink['image'];
    unlink("uploads/slide/$image");
    $querydel=mysqli_query($con,"delete from tbl_multiimages WHERE id='$imgid'");
    if($querydel){
        ?>
        <script>
            window.location='resimages.php';
        </script>
        <?php
    }
    else{

    }
}



