<?php
    $usernameGet = $_GET['username'];
    $passwordGet = $_GET['password'];

    $server="localhost";
    $username="root";
    $password="";
    $database="mobile";

    $link = mysqli_connect($server,$username,$password) or die ("Cannot connect to the DB");
    mysqli_select_db($link,$database) or die("Cannot select DB");

    $query = "insert into users (username,password) values('".$usernameGet."','".$passwordGet."')";
    
    $result = mysqli_query($link,$query) or die("Error query: " .$query);

    echo "$usernameGet berhasil ditambahkan";
    
?>