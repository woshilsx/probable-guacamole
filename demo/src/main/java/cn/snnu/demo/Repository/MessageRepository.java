package cn.snnu.demo.Repository; // 确保包路径正确

import org.springframework.data.jpa.repository.JpaRepository;
import cn.snnu.demo.model.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {
    // 可以添加一些自定义方法
}
