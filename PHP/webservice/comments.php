<?php

require("config.inc.php");

$query = "Select * FROM comments";

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
        $post             = array();
		$post["post_id"]  = $row["post_id"];
        $post["username"] = $row["username"];
        $post["title"]    = $row["title"];
        $post["message"]  = $row["message"];
        
        
        array_push($response["posts"], $post);
    }
    
    echo json_encode($response);
    
} else {
    $response["success"] = 0;
    $response["message"] = "Yemek listesi bulunamadı!";
    die(json_encode($response));
}
?>
