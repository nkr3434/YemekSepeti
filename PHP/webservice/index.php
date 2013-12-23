<?php

require("config.inc.php");

if (!empty($_POST)) {
    if (empty($_POST['username']) || empty($_POST['password'])) {
		
	    $response["success"] = 0;
	    $response["message"] = "Kullanıcı adı veya şifre boş olamaz.";

        die(json_encode($response));
    }
    
    $query = " SELECT 1 FROM users WHERE username = :user";
	
    $query_params = array(':user' => $_POST['username']);
    
    try {
	
        $stmt   = $db->prepare($query);
        $result = $stmt->execute($query_params);
    }
    catch (PDOException $ex) {
        die("Failed to run query: " . $ex->getMessage());

		$response["success"] = 0;
		$response["message"] = "Database hatası. Lütfen tekrar deneyin!";
		die(json_encode($response));
    }
    
    $row = $stmt->fetch();
    if ($row) {
        die("This username is already in use");
		$response["success"] = 0;
		$response["message"] = "Üzgünüm bu kullanıcı adı zaten kullanılıyor";
		die(json_encode($response));
    }
    
    $query = "INSERT INTO users ( username, password ) VALUES ( :user, :pass ) ";
    
    $query_params = array(
        ':username' => $_POST['username'],
        ':password' => $_POST['password']
    );
    
    try {
        $stmt   = $db->prepare($query);
        $result = $stmt->execute($query_params);
    }
    catch (PDOException $ex) {
        die("Failed to run query: " . $ex->getMessage());
		$response["success"] = 0;
		$response["message"] = "Database hatası. Lütfen tekrar deneyin!";
		die(json_encode($response));
    }

	$response["success"] = 1;
	$response["message"] = "Yeni kullanıcı eklendi!";
	echo json_encode($response);
	
} else {
?>
	<h1>Kayıt Ol</h1> 
	<form action="index.php" method="post"> 
	    Kullanıcı Adı:<br /> 
	    <input type="text" name="username" value="" /> 
	    <br /><br /> 
	    Şifre:<br /> 
	    <input type="password" name="password" value="" /> 
	    <br /><br /> 
	    <input type="submit" value="Register New User" /> 
	</form>
	<?php
}

?>
