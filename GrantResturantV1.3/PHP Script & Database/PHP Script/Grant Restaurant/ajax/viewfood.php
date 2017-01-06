<?php include'../application/config.php';
session_start();
include'../translate.php';
$id=$_POST['data'];
$qury=mysqli_query($con,"select * from tbl_foodcategory WHERE id='$id'");
$data=mysqli_fetch_array($qury);
$qury1=mysqli_query($con,"select * from tbl_subcategory WHERE cat_id='$id'");
?>
<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal">&times;</button>
    <h3><?php echo _("View Category Menu For"); ?> <font color="orange"> <?php echo $data['name']; ?> </font></h3>
</div>

<div class="modal-body">
    <table class="table table-striped table-bordered table-highlight" id="example">
        <thead>
        <tr>
            <th><?php echo _("Image"); ?></th>
            <th><?php echo _("Name"); ?></th>
            <th><?php echo _("Price"); ?></th>
            <th><?php echo _("Description"); ?></th>
        </tr>
        </thead>
        <tbody>
        <?php
        while($data1=mysqli_fetch_array($qury1)) {
        ?>
        <form method="post" name="frm">
        <tr>
            <td >
                <a href="#"><img height="70" width="70"  src="uploads/<?php echo $data1['image']; ?>"></a>
            </td>
            <td >
                <?php echo $data1['name']; ?>
            </td >
            <td >
                <?php
                $setc=mysqli_query($con,"select * from tbl_setcurrency ");$cu=mysqli_fetch_array($setc);
                echo $cu['symbol'].$data1['price'];
                ?>
            </td>

            <td>
                <div style="width:150px; "><?php echo $data1['description']; ?></div>
            </td>

        </tr>
        <?php
        }
        ?>
        </form>
        </tbody>

    </table>
</div>