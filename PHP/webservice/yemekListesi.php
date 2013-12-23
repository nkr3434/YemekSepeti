<?php

require("config.inc.php");

$query = "Select * FROM urun";

try {
    $stmt   = $db->prepare($query);
    $result = $stmt->execute($query_params);
}
catch (PDOException $ex) {
    $response["success"] = 0;
    $response["message"] = "Database Hatası!";
    die(json_encode($response));
}

$rows = $stmt->fetchAll();

if ($rows) {
    $response["success"] = 1;
    $response["message"] = "Yemek listesi bulundu!";
    $response["posts"]   = array();
    
    foreach ($rows as $row) {
        $post = array();
		$post["urun_id"] = $row["urun_id"];
        $post["urun_name"] = $row["urun_name"];
        $post["urun_company_id"] = $row["urun_company_id"];
        $post["urun_fiyati"] = $row["urun_fiyati"];
        $post["urun_durum"] = $row["urun_durum"];
        
        
        array_push($response["posts"], $post);
    }
    
    echo json_encode($response);
    
} else {
    $response["success"] = 0;
    $response["message"] = "Yemek listesi bulunamadı!";
    die(json_encode($response));
}
?>
