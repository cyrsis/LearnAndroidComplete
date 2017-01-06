<div id="header">
    <div class="container">
        &nbsp;&nbsp;&nbsp;
       <img src="uploads/logo.png" height="80" width="260">
        <div id="info">
            <a href="include/logout.php" id="info-trigger">
                <i class="icon-lock" style="color: white;" title="<?php echo $_SESSION['uname']; ?>"></i>
            </a>
            <div id="info-menu">
                <div class="info-details">
                    <h4><?php echo _("Welcome"); ?> ,<font color="orange"><?php echo $_SESSION['uname']; ?></font></h4>
                    <p>
                        <?php echo _("Logged in as"); ?> <font color="orange">Admin</font>
                        <br>
                        <a href="include/logout.php"><i class="icon-lock" style="margin-top:3px; color: white;" ></i>&nbsp;&nbsp;<?php echo _("logout"); ?> </a>

                    </p>
                </div>
            </div>
        </div>
    </div>
</div>