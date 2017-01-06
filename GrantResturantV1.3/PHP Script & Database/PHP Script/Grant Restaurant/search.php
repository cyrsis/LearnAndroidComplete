<?php session_start();
include'translate.php';
if(isset($_SESSION['uname'])) { ?>
    <!DOCTYPE html>
    <html lang="en">
    <head>
        <meta charset="utf-8">
        <title><?php echo _("Search for :  Help"); ?>  </title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
        <meta name="apple-mobile-web-app-capable" content="yes">
        <!-- Styles -->
        <link rel="icon" href="Icon - Restaurant.png" type="image/png" sizes="16x16">
        <link href="css/bootstrap.css" rel="stylesheet">
        <link href="css/bootstrap-responsive.css" rel="stylesheet">
        <link href="css/bootstrap-overrides.css" rel="stylesheet">
        <link href="css/ui-lightness/jquery-ui-1.8.21.custom.css" rel="stylesheet">
        <link href="js/plugins/datatables/DT_bootstrap.css" rel="stylesheet">
        <link href="js/plugins/responsive-tables/responsive-tables.css" rel="stylesheet">
        <link href="css/slate.css" rel="stylesheet">
        <link href="css/slate-responsive.css" rel="stylesheet">
        <!-- Javascript -->
        <script src="js/jquery-1.7.2.min.js"></script>
        <script src="js/jquery-ui-1.8.21.custom.min.js"></script>
        <script src="js/jquery.ui.touch-punch.min.js"></script>
        <script src="js/bootstrap.js"></script>
        <script src="js/plugins/datatables/jquery.dataTables.js"></script>
        <script src="js/plugins/datatables/DT_bootstrap.js"></script>
        <script src="js/plugins/responsive-tables/responsive-tables.js"></script>
        <script src="js/demos/demo.tables.js"></script>
        <script src="js/htmlshiv.js"></script>
    </head>
    <body>
    <?php
    include'application/config.php';
    include 'include/header.php';
    include 'include/menu.php';
    ?>
    <div id="content">
        <div class="container">
            <div class="row">
                <div class="span12">
                    <div class="widget">
                        <div class="widget-content">
                            <h3><font color="orange"><?php echo _("You Search For :"); ?> </font><?php echo _("Help"); ?></h3>
                            <br />
                            <ol class="faq-list">
                                <li>
                                    <h4><?php echo  _("How To Add / Update Your Restaurant Detail?"); ?></h4>
                                    <p><?php echo  _("Select Restaurant Dropdown and Click And Lunch For"); ?> <b><?php echo  _("Restaurant Detail"); ?></b> <?php echo  _("You Can See Left Side Two Option"); ?>
                                    <b>1) <?php echo  _("Restaurant Detail"); ?>  2) <?php echo  _("Restaurant Image"); ?> </b> <?php echo  _("You can Select Otion and Update Your Restaurant Detail."); ?>
                                    </p>
                                </li>
                                <li>
                                    <h4><?php echo  _("How To Add Food Category ?"); ?></h4>
                                    <p><?php echo  _("Select Restaurant Dropdown You See Three Option Lunch"); ?> <b><?php echo  _("Food Category"); ?></b>.<?php echo  _("than Left Side See"); ?> <b><?php echo  _("Add New Food Category"); ?></b> <?php echo  _("Button Click And Lunch Show Popup Form Write Detail and Save Changes to show Alert Message Category Add Successfully.
                                    and display added category center portlet box with three option"); ?> <b><?php echo  _("View , Edit , Delete"); ?> </b> <?php echo  _("to action for your category."); ?>
                                    </p>
                                </li>
                                <li>
                                    <h4><?php echo  _("How To Add / Update / Delete Restaurant Menu?"); ?></h4>
                                    <p><?php echo  _("Select Restaurant Dropdown see three option and click to lunch for"); ?> <b><?php echo  _("Menu"); ?></b>.<?php echo  _("you can see left side"); ?> <b><?php echo  _("Add New Menu"); ?></b> <?php echo  _("click To open new popup fill detail and click to save change button to add successfully your menu."); ?>
                                    <b><?php echo  _("update"); ?></b> <?php echo  _("menu click to edit button to open popup for selected menu detail changes detail and click to save change button to update menu successfully."); ?>
                                    <b><?php echo  _("Delete"); ?></b> <?php echo  _("menu detail yuo can click to delete button to show popup for confirmation are sure want to delete click"); ?> <b><?php echo  _("ok"); ?></b> <?php echo  _("button to delete menu successfully."); ?></b>
                                    </p>
                                </li>
                                <li>
                                    <h4><?php echo  _("How to delete order ?"); ?></h4>
                                    <p><?php echo  _("open this link"); ?> <a href="dashboard.php"><?php echo  _("Delete order"); ?></a> <?php echo  _("to show all today's order with right side"); ?> <b><?php echo  _("Delete"); ?></b> <?php echo  _("options
                                    and left site three option to filter orders and delete order with search."); ?>
                                    </p>
                                </li>
                                <li>
                                    <h4><?php echo  _("How To Add / Update / Delete Offers Detail?"); ?></h4>
                                    <p><b><?php echo  _("Add New Offers"); ?></b> <?php echo  _("Open This Link"); ?> <a href="offers.php"><?php echo  _("Offers"); ?></a><?php echo  _("You can see left side"); ?>  <b><?php echo  _("Add New Offers"); ?></b> <?php echo  _("Click To Add New Offers
                                    show popup form fill all detail and click save change button to add offers successfully ."); ?>
                                    <b><?php echo  _("Edit Offers"); ?></b> <?php echo  _("See Left Side button click to view popup for selected offers detail changes detail and click save change button update offers detai."); ?>
                                    <b><?php  echo  _("Delete Offers"); ?></b> <?php echo  _("click to delete button to show  confirm box are sure so you click for"); ?> <b><?php  echo _("ok"); ?></b><?php echo _("button to delete offers."); ?></p>
                                </li>
                                <li>
                                    <h4><?php echo  _("How to set your currency ?"); ?></h4>
                                    <p><?php  echo  _("you Can See header menu click"); ?> <b><?php echo  _("Set currency"); ?></b> <?php echo  _("option to show popup with list of currency select one option and click
                                    to save change button to set your currency."); ?></p>
                                </li>
                                <li>
                                    <h4><?php echo  _("How To Chnage Your Login Username And Password ?"); ?></h4>
                                    <p><?php echo  _("First Of You Open"); ?> </p>
                                </li>
                            </ol>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

<?php  include 'include/footer.php'; ?>
</body>
</html>
<?php } else { ?><script>window.location='index.php'</script><?php } ?>
