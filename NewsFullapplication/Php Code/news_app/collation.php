<?php
$db = mysql_connect('localhost','root','');
if(!$db)
echo "cannot connect to the database";
mysql_select_db('news_app');
$result=mysql_query('show tables');
while($tables = mysql_fetch_array($result)) {
foreach ($tables as $key => $value) {
mysql_query("ALTER TABLE $value COLLATE utf8_general_ci");
}
}
?>