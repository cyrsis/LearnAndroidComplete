<?php error_reporting(0);
/**
 * Copyright 2014 Viaviweb.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain
 * a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */


#Admin Login
function adminUser($username, $password){
     
    $sql = "SELECT id, username FROM admin where username = '".$username."' and password = '".md5($password)."'";
     
	$result = mysql_query($sql);
    $num_rows = mysql_num_rows($result);
    if ($num_rows > 0){
        while ($row = mysql_fetch_array($result)){
            $_SESSION['ADMIN_ID'] = $row['id'];
						$_SESSION['ADMIN_USERNAME'] = $row['username'];
             
        return true; 
        }
    }
    
}


# Insert Data 
function Insert($table, $data){

    $fields = array_keys( $data );  
    $values = array_map( "mysql_real_escape_string", array_values( $data ) );
    mysql_query( "INSERT INTO $table(".implode(",",$fields).") VALUES ('".implode("','", $values )."');") or die( mysql_error() );

}



// Update Data, Where clause is left optional
function Update($table_name, $form_data, $where_clause='')
{
    // check for optional where clause
    $whereSQL = '';
    if(!empty($where_clause))
    {
        // check to see if the 'where' keyword exists
        if(substr(strtoupper(trim($where_clause)), 0, 5) != 'WHERE')
        {
            // not found, add key word
            $whereSQL = " WHERE ".$where_clause;
        } else
        {
            $whereSQL = " ".trim($where_clause);
        }
    }
    // start the actual SQL statement
    $sql = "UPDATE ".$table_name." SET ";

    // loop and build the column /
    $sets = array();
    foreach($form_data as $column => $value)
    {
         $sets[] = "`".$column."` = '".$value."'";
    }
    $sql .= implode(', ', $sets);

    // append the where statement
    $sql .= $whereSQL;
 		 
    // run and return the query result
    return mysql_query($sql);
}



//Delete Data, the where clause is left optional incase the user wants to delete every row!
function Delete($table_name, $where_clause='')
{
    // check for optional where clause
    $whereSQL = '';
    if(!empty($where_clause))
    {
        // check to see if the 'where' keyword exists
        if(substr(strtoupper(trim($where_clause)), 0, 5) != 'WHERE')
        {
            // not found, add keyword
            $whereSQL = " WHERE ".$where_clause;
        } else
        {
            $whereSQL = " ".trim($where_clause);
        }
    }
    // build the query
    $sql = "DELETE FROM ".$table_name.$whereSQL;
	 
    // run and return the query result resource
    return mysql_query($sql);
}

//Select Data, Function(table,column_name,identifier)
function Single($table,$tcol,$tid) 
{
     
    /*Query and link identifier were in the wrong order*/
    return mysql_query("SELECT * FROM ".$table." WHERE ".$tcol."=".$tid."");
}
 

//Get date
function Viavi_Datetime() {
    $tz_object = new DateTimeZone('Asia/Kolkata');
    //date_default_timezone_set('Brazil/East'); 
    $datetime = new DateTime();
    $datetime->setTimezone($tz_object);     
return $datetime->format('Y\-m\-d\ h:i:s');
}
 
 
//F
function Viavi_email()
{
	$headers = 'MIME-Version: 1.0' . "\r\n";
	$headers .= 'Content-type: text/html; charset=iso-8859-1' . "\r\n";

	// Mail it
   if(@mail($to, $subject, $message, $headers)){
   	
		$mail_send="OK";
       	return $mail_send;		 
   }

   else
   {
         $mail_send= "Fail";
		 return $mail_send;
	}
		
}


function breadcrumbs(){
  $path = $_SERVER["PHP_SELF"];
	$parts = explode('/',$path);
	if (count($parts) < 2)
	{
	echo("home");
	}
	else
	{
	echo ("<a href=\"/\">home</a> &raquo; ");
	for ($i = 1; $i < count($parts); $i++)
    	{
    	if (!strstr($parts[$i],"."))
        	{
        	echo("<li><a href=\"");
        	for ($j = 0; $j <= $i; $j++) {echo $parts[$j]."/";};
        	echo("\">". str_replace('-', ' ', $parts[$i])."</a><span class='divider'>/</span></li>");
        	}
    	else
        	{
       	 	$str = $parts[$i];
        	$pos = strrpos($str,".");
        	$parts[$i] = substr($str, 0, $pos);
        	echo str_replace('-', ' ', $parts[$i]);
        	};
    	};
	};  
}
 
?>