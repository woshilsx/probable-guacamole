<?php
include 'user.php';

// 获取 POST 请求中的数据
$username= $_POST['username'];
$password = $_POST['password'];
$avatar = null;

if (isset($_FILES['avatar'])) {
    $avatar = 'uploads/' . basename($_FILES['avatar']['name']);
    move_uploaded_file($_FILES['avatar']['tmp_name'], $avatar);
}

// 验证输入
if (empty($nickname) || empty($password)) {
    echo json_encode(['success' => false, 'message' => '昵称和密码不能为空！']);
    exit;
}

// 哈希密码
$password_hash = password_hash($password, PASSWORD_BCRYPT);

// 插入用户数据
$sql = "INSERT INTO users (username, password_hash, gender, avatar_url)
        VALUES ('user' + (SELECT MAX(id) FROM users), ?, 'male', ?)";

$stmt = $conn->prepare($sql);
$stmt->bind_param("ss", $password_hash, $avatar);
if ($stmt->execute()) {
    $new_username = 'user' . $stmt->insert_id;
    echo json_encode(['success' => true, 'username' => $new_username]);
} else {
    echo json_encode(['success' => false, 'message' => '注册失败，请稍后再试。']);
}

$stmt->close();
$conn->close();
?>