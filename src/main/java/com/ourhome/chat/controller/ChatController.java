package com.ourhome.chat.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import com.ourhome.chat.entity.ChatMessage;

@Controller
public class ChatController {

    @MessageMapping("/{channelId}")
    @SendTo("/sub/{channelId}")
    public ChatMessage sendMessage(ChatMessage chatMessage) {
        return chatMessage;
    }

    // @MessageMapping("/chat.addUser")
    // @SendTo("/sub/public")
    // public ChatMessage addUser(ChatMessage chatMessage, SimpMessageHeaderAccessor simpMessageHeaderAccessor) {
    //     simpMessageHeaderAccessor.getSessionAttributes().put("username", chatMessage.getSender());
    //     return chatMessage;
    // }
}
