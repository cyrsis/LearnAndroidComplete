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
                                    <?php echo _("Add New Food Category"); ?>
                                </a>
                                <?php }
                                else{
                                    ?>
                                    <a href="#" onclick="demo()" class="btn btn-medium btn-tertiary">
                                        <i class="icon-plus"></i>
                                        <?php echo _("Add New Food Category"); ?>
                                    </a>
                                    <?php
                                }
                                ?>
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
                            <?php echo _("Food Category"); ?>
                        </h3>
                    </div>
                    <div class="widget-content">
                        <table class="table table-striped table-bordered table-highlight" id="example">
                            <thead>
                            <tr>
                                <th><?php echo _("#");?></th>
                                <th width="450"><?php echo _("Name"); ?></th>
                                <th><?php echo _("Action"); ?></th>
                            </tr>
                            </thead>
                            <tbody>
                            <?php
                            $query=mysqli_query($con,"select * from tbl_foodcategory order by id desc");
                            while($res=mysqli_fetch_array($query)){
                            ?>
                            <tr>
                                <td><i class="icon-arrow-right"></i></td>
                                <td><?php echo $res['name']; ?></td>
                                <td>
                                    <?php if($_SESSION['demo'] == 1){ ?>
                                    <a href="#viewmenu" onclick="viewfood(<?php echo $res['id']; ?>)" data-toggle="modal" class="btn btn-info"><?php echo _("View Menu"); ?></a>
                                    <a href="#editfood" onclick="dataid(<?php echo $res['id']; ?>)" data-toggle="modal" class="btn btn-success"><?php echo _("Edit"); ?></a>
                                    <a onclick="deletefood(<?php echo $res['id']; ?>)" class="btn btn-danger"><?php echo _("Delete"); ?></a>
                                    <?php } else{
                                        ?>
                                        <a href="#viewmenu" onclick="demo(<?php echo $res['id']; ?>)" data-toggle="modal" class="btn btn-info"><?php echo _("View Menu"); ?></a>
                                        <a href="#editfood" onclick="demo(<?php echo $res['id']; ?>)" data-toggle="modal" class="btn btn-success"><?php echo _("Edit"); ?></a>
                                        <a onclick="demo(<?php echo $res['id']; ?>)" class="btn btn-danger"><?php echo _("Delete"); ?></a>
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
        <h3><?php echo _("Add New Food Category"); ?></h3>
    </div>
    <form  id="contact-form" class="form-horizontal" method="post" novalidate="novalidate" enctype="multipart/form-data">
        <div class="modal-body">
            <fieldset>
                <div class="control-group">
                    <label class="control-label" for="name"><?php echo _("Category Name"); ?></label>
                    <div class="controls">
                        <input type="text" class="input-large" name="cname" value="<?php echo  $res['name']; ?>" required>
                    </div>
                </div>
                <!--<div class="control-group">
                    <label class="control-label" for="name"><?php /*echo _("Select Image"); */?></label>
                    <div class="controls">
                        <input type="file" class="input-large" name="file" required>
                    </div>
                </div>-->
                <div class="control-group">
            </fieldset>
        </div>
        <div class="modal-footer">
            <a href="#" class="btn" data-dismiss="modal"><?php echo _("Close"); ?></a>
            <button  class="btn btn-primary" name="addfoodcat" type="submit"><?php echo _("Save changes"); ?></button>
        </div>
    </form>
</div>

<div class="modal fade hide" style="width: 800px;margin-left: -400px;" id="viewmenu" >
    <div id="viewfood"></div>
    <script>
        function viewfood(id){
            var dataid = id;
            $.ajax({
                type: "POST",
                url: 'ajax/viewfood.php',
                data: "data=" + dataid,
                success: function(fooddata)
                {
                    document.getElementById("viewfood").innerHTML=fooddata;
                }
            });
        }
    </script>
</div>
<div class="modal fade hide" id="editfood">
    <div id="datafood"></div>
    <script>
        function dataid(id){
           var dataid = id;
            $.ajax({
                type: "POST",
                url: 'ajax/foodcat.php',
                data: "data=" + dataid,
                success: function(fooddata)
                {
                    document.getElementById("datafood").innerHTML=fooddata;
                }
            });
        }
    </script>
</div>
<script>
    function deletefood(id){
        var dataid = id;
        var x = confirm("<?php echo _("Are You Sure You Want To Delete"); ?> !!!");
        if(x) {
            $.ajax({
                type: "POST",
                url: 'ajax/deletefood.php',
                data: "data=" + dataid,
                success: function (data) {
                    if (data == "deleted") {
                        alert("! <?php echo _("Category Delete Successfuly"); ?> !!! ");
                        window.location='foodcategory.php';
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
if(isset($_POST['addfoodcat'])) {
    $cname = $_POST['cname'];

//$target_Path = "uploads/";
//$target_Path = $target_Path.$id.$_FILES['file']['name'];
//$imgpath=$id.$_FILES['file']['name'];
//move_uploaded_file( $_FILES['file']['tmp_name'], $target_Path );
    $query = mysqli_query($con, "insert into tbl_foodcategory (name) VALUE ('$cname')");
    if ($query) {
        ?>
        <script>
            window.location = 'foodcategory.php';
        </script>
        <?php
    } else {
        ?>
        <script>

        </script>
        <?php
    }
}
if(isset($_POST['editfoodcat'])){
        $cid = $_POST['cid'];
        $cname = $_POST['cname'];
        $query = mysqli_query($con, "UPDATE `tbl_foodcategory` SET `name`='$cname' WHERE id='$cid'");
        if ($query) {
            ?>
            <script>
                window.location = 'foodcategory.php';
            </script>
            <?php
        }
        else{
            ?>
            <script>
                window.location = 'foodcategory.php';
            </script>
            <?php
        }
}










?>



