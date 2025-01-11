// 用户头像点击跳转
function goToUserPage() {
  window.location.href = 'userPage.html';  // 这里可以设置用户主页的链接
}

// 游戏按钮点击事件
function startGame() {
  alert("游戏开始！");
  // 可以在这里开始加载游戏内容或转到游戏页面
}

// 角色与场景互动
function moveCharacter(event) {
  const scene = document.querySelector('.scene');  // 获取场景元素
  const character = document.getElementById('character');  // 获取角色元素

  // 获取场景的位置和大小
  const sceneRect = scene.getBoundingClientRect();
  console.log(`sceneRect: ${JSON.stringify(sceneRect)}`);  // 调试信息，查看场景的矩形信息

  // 获取点击的坐标，相对于页面
  const clickX = event.clientX;
  const clickY = event.clientY;
  console.log(`点击位置: x = ${clickX}, y = ${clickY}`);

  // 计算相对于场景的位置
  const sceneX = clickX - sceneRect.left;
  const sceneY = clickY - sceneRect.top;
  console.log(`相对于场景的位置: x = ${sceneX}, y = ${sceneY}`);

  // 确保角色在场景范围内
  const characterWidth = character.offsetWidth;
  const characterHeight = character.offsetHeight;
  const maxX = sceneRect.width - characterWidth;
  const maxY = sceneRect.height - characterHeight;

  // 更新角色的位置
  const newX = Math.min(Math.max(0, sceneX - characterWidth / 2), maxX);
  const newY = Math.min(Math.max(0, sceneY - characterHeight / 2), maxY);

  console.log(`新的角色位置: x = ${newX}, y = ${newY}`);

  character.style.left = `${newX}px`;
  character.style.top = `${newY}px`;
}