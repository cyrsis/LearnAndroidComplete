<?php
include '../application/config.php';
$id=$_POST['data'];
$query=mysqli_query($con,"select * from tbl_order_homedelivery WHERE id='$id'");
$res=mysqli_fetch_array($query);
?>
<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal">&times;</button>
    <h3><?php echo _("Contact Detail"); ?></h3>
</div>
<form  class="form-horizontal" method="post"  enctype="multipart/form-data">
    <div class="modal-body">
        <table class="table table-striped table-bordered table-highlight" id="example">
            <tr>
                <th>Name</th>
                <td><?php echo $res['name']; ?></td>
            </tr>
            <tr>
                <th>Email</th>
                <td><?php echo $res['email']; ?></td>
            </tr>
            <tr>
                <th>Address</th>
                <td><?php echo $res['address']; ?></td>
            </tr>
            <tr>
                <th>Mobile Number</th>
                <td><?php echo $res['number']; ?></td>
            </tr>
            <tr>
                <th>Reference</th>
                <td><?php echo $res['reference']; ?></td>
            </tr>
            <tr>
                <th>Neighborhood</th>
                <td><?php echo $res['neighborhood']; ?></td>
            </tr>
            <tr>
                <th>City</th>
                <td><?php echo $res['city']; ?></td>
            </tr>
            <tr>
                <th>Zipcode</th>
                <td><?php echo $res['zipcode']; ?></td>
            </tr>
            <tr>
                <th>State</th>
                <td><?php echo $res['state']; ?></td>
            </tr>
            <tr>
                <th>Details</th>
                <td><?php echo $res['details']; ?></td>
            </tr>
        </table>
    </div>
</form>