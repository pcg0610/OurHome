package com.ourhome.chat.interceptor;

import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

@Component
public class AuthChannelInterceptor implements ChannelInterceptor {

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {

        return message;
    }

    private boolean validateToken(String token) {
        return true;
    }

    private String getUserNameFromToken(String token) {
        return "user";
    }
}
