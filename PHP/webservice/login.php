<?php

require("config.inc.php");

if (!empty($_POST)) {
    $query = "SELECT * FROM company_users WHERE username = :username";
    
    $query_params = array(
        ':username' => $_POST['username']
    );
    
    try {
        $stmt   = $db->prepare($query);
        $result = $stmt->execute($query_params);
    }
    catch (PDOException $ex) {
        $response["success"] = 0;
        $response["message"] = "Database hatası. Lütfen tekrar deneyin!!";
        die(json_encode($response));
        
    }
    
    $validated_info = false;
    
    $row = $stmt->fetch();
    if ($row) {
        if (md5($_POST['password']) === md5($row['password'])) {
            $login_ok = true;
        }
    }
    
    if ($login_ok) {
        $response["success"] = 1;
        $response["message"] = "Giriş başarılı!";
        die(json_encode($response));
    } else {
        $response["success"] = 0;
        $response["message"] = "Giriş başarısız!";
        die(json_encode($response));
    }
} else {
?>
		<h1>Giriş</h1> 
		<form action="login.php" method="post"> 
		    Kullanıcı Adı:<br /> 
		    <input type="text" name="username" placeholder="username" /> 
		    <br /><br /> 
		    Şifre:<br /> 
		    <input type="password" name="password" placeholder="password" value="" /> 
		    <br /><br /> 
		    <input type="submit" value="Login" /> 
		</form> 
		<a href="register.php">Kayıt Ol</a>
	<?php
}

?> 
