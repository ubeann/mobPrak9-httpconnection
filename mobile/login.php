<?php
    $user = $_GET['user'];//get nilai user from client
	$password = $_GET['password'];//get nilai pass from client
	$sret = '';

	if ($user=='admin' && $password=='admin'){
		$sret = 'Login Success';
	}else{
		$sret = 'Login Fail, User dan Password Salah';
	}
	echo $sret;

?>