<?php
include 'user.php';

$id = $_POST['id'];
$password = $_POST['password'];

// 查询用户数据
$sql = "SELECT id, username, password_hash FROM users WHERE username = ?";
$stmt = $conn->prepare($sql);
$stmt->bind_param("s", $id);
$stmt->execute();
$result = $stmt->get_result();

// 检查用户名是否存在
if ($result->num_rows > 0) {
    $user = $result->fetch_assoc();

    // 验证密码
    if (password_verify($password, $user['password_hash'])) {
        echo json_encode(['success' => true]);
    } else {
        echo json_encode(['success' => false, 'message' => '用户名或密码错误！']);
    }
} else {
    echo json_encode(['success' => false, 'message' => '用户名或密码错误！']);
}

$stmt->close();
$conn->close();
?>