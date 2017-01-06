<?php session_start();
include'translate.php';
if(isset($_SESSION['uname'])) { ?>
    <!DOCTYPE html>
    <html lang="en">
    <head>
        <meta charset="utf-8">
        <title><?php echo _("Restaurant Admin | Detail"); ?> </title>
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
        <script src="js/jquery.validate.js"></script>
        <script src="js/demo.validate.js"></script>
    </head>
    <body>
    <?php
    include'application/config.php';
    include 'include/header.php';
    include 'include/menu.php';
    include 'include/resdetail.php';
    include 'include/footer.php';
    ?>
    </body>
    </html>
<?php } else{ ?><script>window.location='index.php'</script><?php } ?>
