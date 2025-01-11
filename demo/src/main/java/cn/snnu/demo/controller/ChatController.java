package cn.snnu.demo.controller;

import cn.snnu.demo.Repository.MessageRepository; // 修改包路径
import cn.snnu.demo.model.ChatMessage;          // 修改包路径
import cn.snnu.demo.model.Message;              // 修改包路径
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;


@Controller
public class ChatController {

    @Autowired
    private MessageRepository messageRepository;

    private int userCount = 0;

    @GetMapping("/")
    public String home() {
        return "youxi";
    }

    @MessageMapping("/sendMessage")
    @SendTo("/topic/messages")
    public ChatMessage sendMessage(ChatMessage message) {
        if (userCount > 2) {
            message.setContent("只允许两个用户聊天！");
        } else {
            Message dbMessage = new Message();
            dbMessage.setSender(message.getSender());
            dbMessage.setContent(message.getContent());
            dbMessage.setTimestamp(LocalDateTime.now());
            messageRepository.save(dbMessage);
        }

        return message;
    }
}
