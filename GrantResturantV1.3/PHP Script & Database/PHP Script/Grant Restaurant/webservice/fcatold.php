<?php
include'../application/config.php';
if(isset($_GET['search'])) {
    $query= mysqli_query($con, " SELECT *  FROM tbl_foodcategory WHERE name LIKE  '" . $_GET['search'] . "%'");
    $query1 = mysqli_query($con, " SELECT *  FROM tbl_foodcategory WHERE name LIKE  '" . $_GET['search'] . "%'");
    $setc=mysqli_query($con,"select * from tbl_setcurrency ");$cu=mysqli_fetch_array($setc);
    $qd = mysqli_fetch_array($query1);
    if ($qd) {
        if ($qd) {
            while ($res = mysqli_fetch_assoc($query)) {
                unset($data1);
                $id = $res['id'];
                $qury = mysqli_query($con, "select * from tbl_subcategory WHERE cat_id='$id'");
                while ($row = mysqli_fetch_assoc($qury)) {
                    $data1[] = array("id" => $row['id'], "name" => $row['name'],
                        /*"small item price"=>$row['sprice'],
                        "medium item price"=>$row['mprice'],
                        "large item price"=>$row['lprice'],*/
                        "price"=>$row['price'],
                        "desc"=>$row['description'],
                        "thumbnail" => $row['image'],
                        "image" => "image/".$row['image']
                    );
                }
                $data[] = array(
                    "name" => $res['name'],
                    "thumbnail" => $res['image'],
                    "image"=>"image/".$res['image'],
                    $res['name'] => $data1
                );
            }
            $json['menu'] = array("currency"=>$cu['symbol'],"Category With Menu"=>$data);
        }
        echo json_encode($json,JSON_UNESCAPED_SLASHES);
    }
    else {
        $querysub = mysqli_query($con, "select * from tbl_subcategory WHERE name LIKE '" . $_GET['search'] . "%' ");
        $querysub1 = mysqli_query($con, "select * from tbl_subcategory WHERE name LIKE '" . $_GET['search'] . "%' ");
        $setc=mysqli_query($con,"select * from tbl_setcurrency ");$cu=mysqli_fetch_array($setc);
        $check=mysqli_fetch_assoc($querysub1);
        if ($check) {
            while ($res = mysqli_fetch_assoc($querysub)) {
                $quryvi = mysqli_query($con, "select * from tbl_foodcategory WHERE id ='" . $res['cat_id'] . "'");
                $dat = mysqli_fetch_assoc($quryvi);
                if ($dat) {
                    if ($dat) {
                        unset($data1);
                        $id = $dat['id'];
                        $qury = mysqli_query($con, "select * from tbl_subcategory WHERE name LIKE '" . $_GET['search'] . "%'");
                        while ($row = mysqli_fetch_assoc($qury)) {
                            if($dat['id'] == $row['cat_id']) {
                                $data1[] = array("id" => $row['id'],
                                    "name" => $row['name'],
                                    /* "small item price"=>$row['sprice'],
                                     "medium item price"=>$row['mprice'],
                                     "large item price"=>$row['lprice'],*/
                                    "price"=>$row['price'],
                                    "desc"=>$row['description'],
                                    "thumbnail" => $row['image'],
                                    "image" => "image/".$row['image']
                                );
                            }

                        }
                        $data[] = array(
                            "name" => $dat['name'],
                            "thumbnail" => $dat['image'],
                            "image" => "image/".$row['image'],
                            $dat['name'] => $data1
                        );
                    }
                    $json['menu'] = array("currency"=>$cu['symbol'],"Category With Menu"=>$data);
                }

            }
            echo json_encode($json,JSON_UNESCAPED_SLASHES);
        }
        else
        {
            $id="Record Not Found";
            $j[]=$id;
            $jsondata['Error']=$j;
            echo json_encode($jsondata);
        }
    }


}
else
{
    $query = mysqli_query($con, " SELECT  * FROM tbl_foodcategory ");
    $query1 = mysqli_query($con, " SELECT * FROM tbl_foodcategory  ");
    $setc=mysqli_query($con,"select * from tbl_setcurrency ");$cu=mysqli_fetch_array($setc);
    $qd = mysqli_fetch_array($query1);
    if ($qd) {
        while ($res = mysqli_fetch_assoc($query)) {

            unset($data1);
            $id = $res['id'];
            $qury = mysqli_query($con, "select * from tbl_subcategory WHERE cat_id='$id'");
            while ($row = mysqli_fetch_assoc($qury)) {

                $data1[] = array("id" => $row['id'], "name" => $row['name'],
                    /* "small item price"=>$row['sprice'],
                     "medium item price"=>$row['mprice'],
                     "large item price"=>$row['lprice'],*/
                    "price"=>$row['price'],
                    "desc"=>$row['description'],
                    "thumbnail" => $row['image'],
                    "image" => "image/".$row['image']
                );

            }
            $data[] = array(
                "name" => $res['name'],
                "thumbnail" => $res['image'],
                "image" => "image/".$row['image'],
                $res['name'] => $data1
            );
        }
        $json['menu'] = array("currency"=>$cu['symbol'],"Category With Menu"=>$data);
    }
    echo json_encode($json,JSON_UNESCAPED_SLASHES);

}

?>