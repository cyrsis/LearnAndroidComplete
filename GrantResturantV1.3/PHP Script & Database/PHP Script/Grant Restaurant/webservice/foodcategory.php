<?php
include'../application/config.php';
if(isset($_GET['search'])) {
    $query= mysqli_query($con, " SELECT *  FROM tbl_foodcategory order by id desc");
    $query1 = mysqli_query($con, " SELECT *  FROM tbl_foodcategory order by id desc");
    $qd = mysqli_fetch_array($query1);
    if ($qd) {
        if ($qd) {
            while ($res = mysqli_fetch_assoc($query)) {
                unset($data1);
                $id = $res['id'];
                $qury = mysqli_query($con, "select * from tbl_subcategory WHERE name LIKE '" . $_GET['search'] . "%' && cat_id='".$res['id']."'");
                $qury1 = mysqli_query($con, "select * from tbl_subcategory WHERE name LIKE '" . $_GET['search'] . "%' && cat_id='".$res['id']."'");
                $check = mysqli_num_rows($qury1);
                    while ($row = mysqli_fetch_assoc($qury)) {
                        $data1[] = array("id" => $row['id'], "name" => $row['name'],
                            "price" => $row['price'],
                            "desc" => $row['description'],
                            "thumbnail" => $row['image'],
                            "image" => "image/" . $row['image']
                        );
                    }
                    if (isset($data1)) {
                        $jdata=$data1;
                    } else {
                        $jdata = [0];
                    }
                    if (isset($data1)) {
                        $data[] = array(
                            "name" => $res['name'],
                            $res['name'] => $jdata
                        );
                    }
                    else{

                    }

            }
            if(isset($data)) {
                $json['menu'] = $data;
            }
            else{
                $arr[]=array("id"=>"Item Not Found");
                $json['menu'] = $arr;
            }
        }
        echo json_encode($json,JSON_UNESCAPED_SLASHES);
    }
    else{
        $arr[]=array("id"=>"Item Not Found");
        $json['menu'] = $arr;
        echo json_encode($json,JSON_UNESCAPED_SLASHES);
    }
}
else
{
    $query = mysqli_query($con, " SELECT  * FROM tbl_foodcategory ORDER by id desc");
    $query1 = mysqli_query($con, " SELECT * FROM tbl_foodcategory ORDER by id desc ");
    $qd = mysqli_fetch_array($query1);
    if ($qd) {
        while ($res = mysqli_fetch_assoc($query)) {

            unset($data1);
            $id = $res['id'];
            $qury = mysqli_query($con, "select * from tbl_subcategory WHERE cat_id='$id'");
            while ($row = mysqli_fetch_assoc($qury)) {

                $data1[] = array("id" => $row['id'], "name" => $row['name'],
                    "price"=>$row['price'],
                    "desc"=>$row['description'],
                    "thumbnail" => $row['image'],
                    "image" => "image/".$row['image']
                );
            }
            if(isset($data1)){
                $jdata=$data1;
            }
            else{
                $jdata=[0];
            }
            if(isset($data1)) {
                $data[] = array(
                    "name" => $res['name'],
                    $res['name'] => $jdata
                );
            }
        }
        $json['menu'] = $data;
        echo json_encode($json,JSON_UNESCAPED_SLASHES);
    }
    else{
        $arr[]=array("id"=>"Item Not Found");
        $json['menu'] = $arr;
        echo json_encode($json,JSON_UNESCAPED_SLASHES);
    }

}

?>