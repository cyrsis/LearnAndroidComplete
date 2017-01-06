<div id="nav">


    <div class="container">


        <a href="javascript:;" class="btn-navbar" data-toggle="collapse" data-target=".nav-collapse">

            <i class="icon-reorder"></i>

        </a>


        <div class="nav-collapse">


            <ul class="nav">


                <li class="nav-icon active">

                    <a href="dashboard.php">

                        <i class="icon-home"></i>

                        <?php echo _("Home"); ?>

                    </a>


                </li>
                <li class="dropdown ">

                    <a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown">

                        <i class="icon-retweet"></i>

                        <?php echo _("Restaurant"); ?>

                        <b class="caret"></b>

                    </a>


                    <ul class="dropdown-menu">

                        <li><a href="resdetail.php"><?php echo _("Restaurant Detail"); ?></a></li>

                        <li><a href="foodcategory.php"><?php echo _("Food Category"); ?></a></li>

                        <li><a href="menu.php"><?php echo _("Menu"); ?></a></li>

                    </ul>

                </li>
                <li class="nav-icon">

                    <a href="resorder.php">

                        <i class="icon-shopping-cart"></i>

                        <?php echo _("Order"); ?>

                    </a>


                </li>


                <li class="nav-icon">

                    <a href="offers.php">

                        <i class="icon-star"></i>

                        <?php echo _("Offers"); ?>

                    </a>


                </li>
                <?php if($_SESSION['demo']){ ?>
                <li class="nav-icon">

                    <a href="#currency" data-toggle="modal">

                        <i class="icon-eject"></i>

                        <?php echo _("Set Currency"); ?>

                    </a>


                </li>
                <li class="nav-icon">

                    <a href="#language" data-toggle="modal">

                        <i class="icon-refresh"></i>

                        <?php echo _("Set language"); ?>

                    </a>


                </li>
                    <li class="nav-icon">

                        <a href="webservice.php">

                            <i class="icon-exclamation-sign"></i>

                            <?php echo _("Web Service"); ?>

                        </a>


                    </li>
                <?php }
                 else{
                     ?>
                     <li class="nav-icon">

                         <a href="#" onclick="demo()">

                             <i class="icon-eject"></i>

                             <?php echo _("Set Currency"); ?>

                         </a>


                     </li>
                     <li class="nav-icon">

                         <a href="#" onclick="demo()">

                             <i class="icon-refresh"></i>

                             <?php echo _("Set language"); ?>

                         </a>

                     </li>
                     <li class="nav-icon">

                         <a href="#" onclick="demo()">

                             <i class="icon-exclamation-sign"></i>

                             <?php echo _("Web Service"); ?>

                         </a>


                     </li>
                     <?php
                 }?>

                <li class="nav-icon">

                    <a href="notification.php">

                        <i class="icon-pushpin"></i>

                        <?php echo _("Notification"); ?>

                    </a>
                </li>


            </ul>


            <ul class="nav pull-right">


                <li class="">

                    <form class="navbar-search pull-left" action="search.php" method="post">

                 <!--       <input type="text" name="search" class="search-query" placeholder="Search">-->

                        <button class="btn btn-tertiary"><i class="icon-search"></i><?php echo _("Help"); ?></button>

                    </form>

                </li>


            </ul>
        </div>
    </div>
</div>


<div class="modal fade hide" id="currency">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h3><?php echo _("Set Your Currency"); ?></h3>
    </div>
    <form  class="form-horizontal" method="post"  enctype="multipart/form-data">
        <div class="modal-body">
            <fieldset>
                <?php include'ajax/currencyarray.php';include'application/config.php'; ?>
                <?php $query=mysqli_query($con,"select * from tbl_setcurrency");$cu=mysqli_fetch_array($query); ?>
                <div class="control-group">
                    <label class="control-label" for="name"><?php echo _("Select Currency"); ?></label>
                    <div class="controls">
                        <span class="add-on"><?php echo $cu['symbol']; ?></span>
                        <select name="type" class="input-large" required>
                            <?php foreach($currency_symbols as $value) { ?>
                                <option value="<?php echo $value; ?>"><?php echo $value; ?></option>
                            <?php  } ?>
                        </select>
                    </div>
                </div>
            </fieldset>
        </div>
        <div class="modal-footer">
            <a href="#" class="btn" data-dismiss="modal"><?php echo _("Close"); ?></a>
            <button  class="btn btn-primary" name="setcur" type="submit"><?php echo _("Save changes"); ?></button>
        </div>
    </form>
</div>
<div class="modal fade hide" id="language">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h3><?php echo _("Set Your language"); ?></h3>
    </div>
    <form  class="form-horizontal" method="post"  enctype="multipart/form-data">
        <div class="modal-body">
            <fieldset>

                <div class="control-group">
                    <label class="control-label" for="name"><?php echo _("Select Language"); ?></label>
                    <div class="controls">
                        <select name="lang" class="input-large" required>
                                <option value="Auto">Auto</option>
                                <option value="en_US">English</option>
                                <option value="fr_FR">Franch</option>
                        </select>
                    </div>
                </div>
            </fieldset>
        </div>
        <div class="modal-footer">
            <a href="#" class="btn" data-dismiss="modal"><?php echo _("Close"); ?></a>
            <button  class="btn btn-primary" name="setlang" type="submit"><?php echo _("Save changes"); ?></button>
        </div>
    </form>
</div>
<?php
if(isset($_POST['setcur'])){
    $ctype=$_POST['type'];
    $set=mysqli_query($con,"update tbl_setcurrency set symbol='$ctype' WHERE id=1");
    if($set){
        ?>
        <script>
            alert("!!! <?php echo _("Currency Set Successfully"); ?> !!!");
            window.location='dashboard.php';
        </script>
        <?php
    }
    else{

    }
}
if(isset($_POST['setlang'])){
    $ctype=$_POST['lang'];
    $uname=$_SESSION['uname'];
    $set=mysqli_query($con,"update tbl_adminlogin set language='$ctype' WHERE username='$uname'");
    if($set){
        ?>
        <script>
            alert("!!! <?php echo _("Language Set Successfully"); ?> !!!");
            window.location='dashboard.php';
        </script>
        <?php
    }
    else{

    }
}
?>