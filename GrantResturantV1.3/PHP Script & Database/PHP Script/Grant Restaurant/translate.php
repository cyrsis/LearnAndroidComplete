<?php
include'application/config.php';
$uname=$_SESSION['uname'];
$query=mysqli_query($con,"select * from tbl_adminlogin WHERE username='$uname'");
$res=mysqli_fetch_array($query);
$locale = false;
if($res['language'] == "Auto")
{
    $bln = substr($_SERVER['HTTP_ACCEPT_LANGUAGE'], 0, 2);
    switch ($bln)
    {
        case "fr":
            $locale="fr_FR";
            break;
        case "en":
            $locale="en_US";
            break;
        default:
            $locale="en_US";
            break;
    }
}
else
{
    $locale=$res['language'];
}
putenv("LANGUAGE=$locale");
setlocale(LC_ALL, $locale);
$domain = "messages";
bindtextdomain($domain, "locale");
bind_textdomain_codeset($domain, "UTF-8");
textdomain($domain);
?>