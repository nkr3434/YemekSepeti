<?php

require("config.inc.php");

if (!empty($_POST)) {
    if (empty($_POST['username']) || empty($_POST['password'])) {
        
        $response["success"] = 0;
        $response["message"] = "Kullanıcı adınız ve şifreniz boş olamaz.";
        
        die(json_encode($response));
    }
    
    $query = " SELECT 1 FROM users WHERE username = :user";
    $query_params = array(
        ':user' => $_POST['username']
    );
    
    try {
        $stmt   = $db->prepare($query);
        $result = $stmt->execute($query_params);
    }
    catch (PDOException $ex) {
        $response["success"] = 0;
        $response["message"] = "Database hatası. Lütfen tekrar deneyin!";
        die(json_encode($response));
    }
    
    $row = $stmt->fetch();
    if ($row) {
        $response["success"] = 0;
        $response["message"] = "Üzgünüm bu kullanıcı adı zaten kullanılıyor";
        die(json_encode($response));
    }
    
    $query = "INSERT INTO users ( username, password ) VALUES ( :user, :pass ) ";
    
    $query_params = array(
        ':user' => $_POST['username'],
        ':pass' => $_POST['password']
    );
    
    try {
        $stmt   = $db->prepare($query);
        $result = $stmt->execute($query_params);
    }
    catch (PDOException $ex) {
        $response["success"] = 0;
        $response["message"] = "Database hatası. Lütfen tekrar deneyin!";
        die(json_encode($response));
    }
    
    $response["success"] = 1;
    $response["message"] = "Yeni Kullanıcı Eklendi!";
    echo json_encode($response);
    
} else {
?>
	<h1>Kayıt Ol</h1> 
	<form action="register.php" method="post"> 
	    Kullanıcı Adı:<br /> 
	    <input type="text" name="username" value="" /> 
	    <br /><br /> 
	    Şifre:<br /> 
	    <input type="password" name="password" value="" /> 
	    <br /><br /> 
	    <input type="submit" value="Yeni Kullanıcı Ekle" /> 
	</form>
	<?php
}

?>
