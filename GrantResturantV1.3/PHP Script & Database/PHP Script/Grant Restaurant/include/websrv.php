
<div id="content">
    <div class="container">
        <div class="row">
            <div class="span12">
                <div class="widget widget-table">
                    <div class="widget-header">
                        <h3>
                            <i class="icon-th-list"></i>
                            <?php echo _("Web Services"); ?>
                        </h3>
                    </div>
                    <div class="widget-content">
                        <table class="table table-striped table-bordered table-highlight" id="example">
                            <thead>
                            <tr>
                                <th>#</th>
                                <th><?php echo _("Descriptions"); ?></th>
                                <th><?php echo _("Json Url"); ?></th>
                            </tr>
                            </thead>
                            <tbody>
                            <?php
                            $query1=mysqli_query($con,"select * from tbl_webservice ORDER BY id DESC ");
                            while($data=mysqli_fetch_array($query1)){ ?>
                                <tr>
                                    <td class="center">
                                        <?php echo $data['id']; ?>
                                    </td>
                                    <td >
                                        <?php echo $data['desc']; ?>
                                    </td>
                                    <td >
                                        <a href="<?php echo $data['url']; ?>"> <?php echo $data['url']; ?></a>
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




