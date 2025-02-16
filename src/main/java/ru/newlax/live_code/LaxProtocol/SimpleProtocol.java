package ru.newlax.live_code.LaxProtocol;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.stream.Collectors;

@Component
public class SimpleProtocol {

    @Autowired
    private UserController userController;

    private void broadcast(WebSocketSession session, String message) {
    }
    
    public void processMessage(WebSocketSession session, TextMessage message) {
        Handles handle = getHandle(message);

        switch (handle) {
            case STREAMER -> userController.addStreamer(getName(message), session);
            case LISTENER -> userController.addListener(getName(message), session);
            case MESSAGE -> userController.setLastMessage(session, getMessage(message));
            case EDIT -> System.out.println("Edit handler");
        }
    }

    private Handles getHandle(TextMessage message) {
        return Handles.fromString(getFirstLine(message)[0]);
    }

    private String getName(TextMessage message) {
        return getFirstLine(message)[1];
    }

    private String getMessage(TextMessage message) {
        return message.getPayload()
                .lines()
                .skip(1)
                .collect(Collectors.joining("\n"));
    }

    private String[] getFirstLine(TextMessage message) {
        return message.getPayload()
                .lines()
                .findFirst()
                .orElse("")
                .split(" ");
    }
}
