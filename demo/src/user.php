<?php
$servername = "localhost"; // 数据库服务器
$username = "root"; // 数据库用户名
$password = "tqt20040907"; // 数据库密码
$dbname = "user_db"; // 数据库名称

// 创建数据库连接
$conn = new mysqli($servername, $username, $password, $dbname);

// 检查连接
if ($conn->connect_error) {
    die("连接失败: " . $conn->connect_error);
}
?>