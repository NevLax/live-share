package ru.newlax.live_code.LaxProtocol;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.*;

public class Protocol {

    private static Map<String, List<WebSocketSession>> listeners = Collections.synchronizedMap(new HashMap<>());
    private static Map<WebSocketSession, String> streamers = Collections.synchronizedMap(new HashMap<>());

    private static void addListeners(String name, WebSocketSession session) {
        listeners.computeIfAbsent(name, k -> new ArrayList<>()).add(session);
    }

    private static void broadcast(WebSocketSession session, String message) {
        String name = streamers.get(session);

        for (WebSocketSession listener : listeners.get(name)) {
            try {
                listener.sendMessage(new TextMessage(message));
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
    }
    
    public static void getMessage(WebSocketSession session, TextMessage message) {
        User data = SimpleImplementation.Classifier(message.getPayload());
        switch (data.getRole()) {
            case Role.LISTENER -> addListeners(data.getName(), session);
            case Role.STREAMER -> streamers.put(session, data.getName());
            default -> broadcast(session, data.getName());
        }
    }
    
    public static void closeConnection(WebSocketSession session) {
        if (listeners.containsValue(session)) {
            for (String name : listeners.keySet()) {
                if (listeners.get(name).contains(session)) {
                    listeners.get(name).remove(session);
                    break;
                }
            }
        }
    }
}
