package com.example.websocketchatapi;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.Collection;

public class WebSocketChatHandler extends TextWebSocketHandler {

    private final Collection<WebSocketSession> sessions = new ArrayList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        sessions.add(session);

        System.out.println("opening connection with session: " + session.getId());
    }


    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        System.out.println("broadcasting message: " + message.getPayload() + " to " + sessions.size() + " sessions");

        for (WebSocketSession storagedSession : sessions) {
            storagedSession.sendMessage(message);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {

        sessions.remove(session);

        System.out.println("closing connection from: " + session.getId());
    }
}
