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
                        <div class="stat">
                            <div class="stat-header">
                                <?php if($_SESSION['demo'] == 1){ ?>
                                <a href="#addfood" data-toggle="modal"  class="btn btn-medium btn-tertiary">
                                    <i class="icon-plus"></i>
                                    <?php echo _("Add New Menu"); ?>
                                </a>
                                <?php } else {
                                    ?>
                                    <a href="#" onclick="demo()"  class="btn btn-medium btn-tertiary">
                                        <i class="icon-plus"></i>
                                        <?php echo _("Add New Menu"); ?>
                                    </a>
                                    <?php
                                } ?>

                            </div>
                        </div>
                        <br/>
                    </div>
                </div>
            </div>
            <div class="span10">
                <div class="widget widget-table">
                    <div class="widget-header">
                        <h3>
                            <i class="icon-th-list"></i>
                            <?php echo _("Restaurant Menu"); ?>
                        </h3>
                    </div>
                    <div class="widget-content">
                        <table class="table table-striped table-bordered table-highlight" id="example">
                            <thead>
                            <tr>
                                <th><?php echo _("Image"); ?></th>
                                <th><?php echo _("Menu Name"); ?></th>
                                <th><?php echo _("Food Category"); ?></th>
                                <th><?php echo _("Price"); ?></th>
                                <th><?php echo _("Action"); ?></th>
                            </tr>
                            </thead>
                            <tbody>
                            <?php
                            $qury=mysqli_query($con,"select * from tbl_subcategory ORDER BY id DESC ");
                            while($row=mysqli_fetch_array($qury)){?>
                                <tr>
                                    <td>
                                        <img height="60"  width="60"  src="uploads/<?php echo $row['image']; ?>"/>
                                    </td>
                                    <td>
                                        <?php echo $row['name']; ?>
                                    </td>
                                    <?php $foodnamef=mysqli_query($con,"select * from tbl_foodcategory WHERE id='".$row['cat_id']."'");$foodname=mysqli_fetch_array($foodnamef); ?>
                                    <td>
                                        <?php echo $foodname['name']; ?>
                                    </td>
                                    <td>
                                        <?php
                                        $setc=mysqli_query($con,"select * from tbl_setcurrency ");$cu=mysqli_fetch_array($setc);
                                        echo $cu['symbol'].$row['price'];
                                        ?>

                                    </td>
                                    <td class="center">

                                        <?php if($_SESSION['demo'] == 1) { ?>
                                             <a href="#editmenu" data-toggle="modal"
                                                 onclick="editmenu(<?php echo $row['id']; ?>)" title="Edit Menu" class="btn btn-tertiary"><?php echo _("Edit"); ?></a>
                                             <a onclick="deletemenu(<?php echo $row['id']; ?>)" title="Delete Menu" class="btn btn-danger"><?php echo _("Delete"); ?></a>
                                        <?php } else {
                                            ?>
                                            <a href="#editmenu" data-toggle="modal"
                                               onclick="demo(<?php echo $row['id']; ?>)" title="Edit Menu" class="btn btn-tertiary"><?php echo _("Edit"); ?></a>
                                            <a onclick="demo(<?php echo $row['id']; ?>)" title="Delete Menu" class="btn btn-danger"><?php echo _("Delete"); ?></a>

                                            <?php

                                        } ?>
                                    </td>
                                </tr>
                            <?php } ?>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="modal fade hide" id="addfood">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h3><?php echo _("Add New Menu"); ?></h3>
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
                            ?>
                            <option value="<?php echo $all['id']; ?>"><?php echo $all['name']; ?></option>
                            <?php } ?>
                        </select>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="name"><?php echo _("Menu Name"); ?></label>
                    <div class="controls">
                        <input type="text" class="input-large" name="cname"  required>
                    </div>
                </div>

                <div class="control-group">
                    <label class="control-label" for="name"><?php echo _("Select Image"); ?></label>
                    <div class="controls">
                        <input type="file" class="input-large" name="file" required>
                    </div>
                </div>
                <!--<div class="control-group">
                    <label class="control-label" for="name"><?php /*echo _("Menu Id"); */?></label>
                    <div class="controls">
                        <input type="text" class="input-large" name="menuid"  required>
                    </div>
                </div>-->
                <?php
                $setc=mysqli_query($con,"select * from tbl_setcurrency ");$cu=mysqli_fetch_array($setc);
                ?>
                <div class="control-group">
                    <label class="control-label" for="name"><?php echo _("Price"); ?></label>
                    <div class="controls">
                        <span class="add-on"><?php echo $cu['symbol']; ?></span><input class="span2" id="prependedInput" name="sprice" size="16" type="text" >
                    </div>
                </div>
                <!--<div class="control-group">
                    <label class="control-label" for="name">Medium Item Price</label>
                    <div class="controls">
                        <span class="add-on"><?php /*echo $cu['symbol']; */?></span><input class="span2" id="prependedInput" name="mprice" size="16" type="text" >
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="name">Large Item Price</label>
                    <div class="controls">
                        <span class="add-on"><?php /*echo $cu['symbol']; */?></span><input class="span2" id="prependedInput" name="lprice" size="16" type="text" >
                    </div>
                </div>-->
                <div class="control-group">
                    <label class="control-label" for="name"><?php echo _("Category Description"); ?></label>
                    <div class="controls">
                        <textarea class="input-large" name="desc"  required></textarea>
                    </div>
                </div>
                <div class="control-group">
            </fieldset>
        </div>
        <div class="modal-footer">
            <a href="#" class="btn" data-dismiss="modal"><?php echo _("Close"); ?></a>
            <button  class="btn btn-primary" name="addmenu" type="submit"><?php echo _("Save changes"); ?></button>
        </div>
    </form>
</div>

<div class="modal fade hide" id="editmenu" >
    <div id="viewmenu"></div>
    <script>
        function editmenu(id){
            var dataid = id;
            $.ajax({
                type: "POST",
                url: 'ajax/editmenu.php',
                data: "data=" + dataid,
                success: function(fooddata)
                {
                    document.getElementById("viewmenu").innerHTML=fooddata;
                }
            });
        }
    </script>
</div>
<script>
    function deletemenu(id){
        var dataid = id;
        var x = confirm("<?php echo _("Are You Sure You Want To Delete"); ?> !!!");
        if(x) {
            $.ajax({
                type: "POST",
                url: 'ajax/deletemenu.php',
                data: "data=" + dataid,
                success: function (data) {
                    if (data == "deleted") {
                        alert("! <?php echo _("Category Delete Successfuly"); ?> !!! ");
                        window.location='menu.php';
                    }
                    else {
                        alert("false");
                    }
                }
            });
        }
        else{
            return false;
        }
    }
</script>

<?php
if(isset($_POST['addmenu'])){
    $cid=$_POST['type'];
    $cname=addslashes($_POST['cname']);
  /*  $menuid=$_POST['menuid'];*/
    $sprice=$_POST['sprice'];
   /* $mprice=$_POST['mprice'];
    $lprice=$_POST['lprice'];*/
    $desc=addslashes($_POST['desc']);
    function cwUpload($field_name = '', $target_folder = '', $file_name = '', $thumb = FALSE, $thumb_folder = '', $thumb_width = '', $thumb_height = ''){
        $target_path = $target_folder;
        $thumb_path = $thumb_folder;
        $filename_err = explode(".",$_FILES[$field_name]['name']);
        $filename_err_count = count($filename_err);
        $file_ext = $filename_err[$filename_err_count-1];
        $r1 = chr(rand(ord('a'), ord('z')));
        $r2 = chr(rand(ord('a'), ord('z')));
        $r3 = chr(rand(ord('a'), ord('z')));
        $id=$r1.$r2.$r3;
        $fileName = $id.$_FILES[$field_name]['name'];
        $upload_image = $target_path.basename($fileName);
        if(move_uploaded_file($_FILES[$field_name]['tmp_name'],$upload_image)) {
            if($thumb == TRUE) {
                $thumbnail = $thumb_path.$fileName;
                list($width,$height) = getimagesize($upload_image);
                $thumb_create = imagecreatetruecolor($thumb_width,$thumb_height);
                switch($file_ext){
                    case 'jpg':
                        $source = imagecreatefromjpeg($upload_image);
                        break;
                    case 'jpeg':
                        $source = imagecreatefromjpeg($upload_image);
                        break;
                    case 'png':
                        $source = imagecreatefrompng($upload_image);
                        break;
                    case 'gif':
                        $source = imagecreatefromgif($upload_image);
                        break;
                    default:
                        $source = imagecreatefromjpeg($upload_image);
                }
                imagecopyresized($thumb_create,$source,0,0,0,0,$thumb_width,$thumb_height,$width,$height);
                switch($file_ext){
                    case 'jpg' || 'jpeg':
                        imagejpeg($thumb_create,$thumbnail,100);
                        break;
                    case 'png':
                        imagepng($thumb_create,$thumbnail,100);
                        break;
                    case 'gif':
                        imagegif($thumb_create,$thumbnail,100);
                        break;
                    default:
                        imagejpeg($thumb_create,$thumbnail,100);
                }
            } return $fileName;
        } else { return false; }
    }
    $upload_img = cwUpload('file','uploads/image/','',TRUE,'uploads/','200','97');
    $thumb_src = 'uploads/'.$upload_img;
    $imagepath =  $upload_img;
    $query=mysqli_query($con,"insert into tbl_subcategory (cat_id,name,image,price,description) VALUE ('$cid','$cname','$imagepath','$sprice','$desc')");
    if($query){
        ?>
        <script>
            alert("! <?php echo _("Menu Add Successfully"); ?> !!!")
            window.location='menu.php';
        </script>
        <?php
    }
    else{
        ?>
        <script>

        </script>
        <?php
    }


}
if(isset($_POST['editmenu'])){
    if($_FILES['file']['name']) {
        $ftype=$_POST['type'];
        $cid = $_POST['cid'];
        $sprice=$_POST['sprice'];
       /* $mprice=$_POST['mprice'];
        $lprice=$_POST['lprice'];*/
        $desc=addslashes($_POST['desc']);
        $up=mysqli_query($con,"select * from tbl_subcategory WHERE id='$cid'");
        $upd=mysqli_fetch_array($up);
        $iddd=$upd['cat_id'];
        unlink("uploads/".$upd['image']);
        $cname =addslashes($_POST['cname']);
        function cwUpload($field_name = '', $target_folder = '', $file_name = '', $thumb = FALSE, $thumb_folder = '', $thumb_width = '', $thumb_height = ''){
            $target_path = $target_folder;
            $thumb_path = $thumb_folder;
            $filename_err = explode(".",$_FILES[$field_name]['name']);
            $filename_err_count = count($filename_err);
            $file_ext = $filename_err[$filename_err_count-1];
            $r1 = chr(rand(ord('a'), ord('z')));
            $r2 = chr(rand(ord('a'), ord('z')));
            $r3 = chr(rand(ord('a'), ord('z')));
            $id=$r1.$r2.$r3;
            $fileName = $id.$_FILES[$field_name]['name'];
            $upload_image = $target_path.basename($fileName);
            if(move_uploaded_file($_FILES[$field_name]['tmp_name'],$upload_image)) {
                if($thumb == TRUE) {
                    $thumbnail = $thumb_path.$fileName;
                    list($width,$height) = getimagesize($upload_image);
                    $thumb_create = imagecreatetruecolor($thumb_width,$thumb_height);
                    switch($file_ext){
                        case 'jpg':
                            $source = imagecreatefromjpeg($upload_image);
                            break;
                        case 'jpeg':
                            $source = imagecreatefromjpeg($upload_image);
                            break;
                        case 'png':
                            $source = imagecreatefrompng($upload_image);
                            break;
                        case 'gif':
                            $source = imagecreatefromgif($upload_image);
                            break;
                        default:
                            $source = imagecreatefromjpeg($upload_image);
                    }
                    imagecopyresized($thumb_create,$source,0,0,0,0,$thumb_width,$thumb_height,$width,$height);
                    switch($file_ext){
                        case 'jpg' || 'jpeg':
                            imagejpeg($thumb_create,$thumbnail,100);
                            break;
                        case 'png':
                            imagepng($thumb_create,$thumbnail,100);
                            break;
                        case 'gif':
                            imagegif($thumb_create,$thumbnail,100);
                            break;
                        default:
                            imagejpeg($thumb_create,$thumbnail,100);
                    }
                } return $fileName;
            } else { return false; }
        }
        $upload_img = cwUpload('file','uploads/image/','',TRUE,'uploads/','200','97');
        $thumb_src = 'uploads/'.$upload_img;
        $imagepath =  $upload_img;
        $query = mysqli_query($con, "UPDATE `tbl_subcategory` SET `cat_id`='$ftype',`name`='$cname',image='$imagepath',price='$sprice',description='$desc' WHERE id='$cid'");
        if ($query) {
            ?>
            <script>
                alert("! <?php echo _("Menu Update Successfully"); ?> !!! ");
                window.location = 'menu.php';
            </script>
            <?php
        } else {
            ?>
            <script>

            </script>
            <?php
        }
    }
    else{
        $ftype=$_POST['type'];
        $cid = $_POST['cid'];
        $cname =addslashes($_POST['cname']);
        $sprice=$_POST['sprice'];
        $mprice=$_POST['mprice'];
        $lprice=$_POST['lprice'];
        $desc=addslashes($_POST['desc']);
        $up=mysqli_query($con,"select * from tbl_subcategory WHERE id='$cid'");
        $upd=mysqli_fetch_array($up);
        $iddd=$upd['cat_id'];
        $query = mysqli_query($con, "UPDATE `tbl_subcategory` SET `cat_id`='$ftype',`name`='$cname',price='$sprice',description='$desc' WHERE id='$cid'");
        if ($query) {
            ?>
            <script>
                alert("! <?php echo _("Menu Update Successfully"); ?> !!! ");
                window.location = 'menu.php';
            </script>
            <?php
        } else {
            ?>
            <script>

            </script>
            <?php
        }
    }
}



?>