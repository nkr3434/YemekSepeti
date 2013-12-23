<html>
<head>
</head>
<body>
<?php
$secim=$_REQUEST['secim'];

if($secim==NULL) $secim=0;

echo "<font color=\"#FF0000\">Veritabanı Silinecek..</font> Bütün Kayıtlarınız Kaybolacak!!! Bunu yapmak istediğinize emin misiniz?<br>
<form ENCTYPE=\"multipart/form-data\" action=\"resetdb.php?secim=1\" method=\"POST\">
<input type=submit name=\"Gonder\" value=\"Veritabanını Resetle!!\">
</form>
";

if($secim==1)
{
require("settings/Database_Settings.set");
require("data/Database_Connect.dat");

mysql_query("DROP Table users");
mysql_query("DROP Table comments");

mysql_query("CREATE Table users(id int not null AUTO_INCREMENT, username Char(255) CHARACTER SET utf8 COLLATE utf8_turkish_ci, password Char(255) CHARACTER SET utf8 COLLATE utf8_turkish_ci, primary key(id))");
mysql_query("CREATE Table comments(id int not null AUTO_INCREMENT, username Char(255) CHARACTER SET utf8 COLLATE utf8_turkish_ci, title Char(255) CHARACTER SET utf8 COLLATE utf8_turkish_ci, message text CHARACTER SET utf8 COLLATE utf8_turkish_ci, primary key(id))");











//mysql_query("INSERT INTO YetkiliKullanicilar(KullaniciAdi, Sifre) VALUES ('" . md5("admin") . "', '" . md5("admin") . "')", $baglanti);


require("data/Database_Close.dat");
echo "<br><font color=\"#00FF00\">Veritabanı Resetlendi..</font>";
}
?>
</body>
</html>