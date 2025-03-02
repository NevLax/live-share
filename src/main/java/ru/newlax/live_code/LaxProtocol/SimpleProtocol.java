package ru.newlax.live_code.LaxProtocol;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.Arrays;

@Component
public class SimpleProtocol {

    private final HandlerController handlerController;

    public SimpleProtocol(HandlerController handlerController) {
        this.handlerController = handlerController;
    }

    public void processMessage(WebSocketSession session, TextMessage message) {
        Handles handle = Handles.valueOf(getHandle(message.getPayload()));
        handlerController.processHandler(session, handle, message.getPayload());
    }

    public void closeConnection(WebSocketSession session) {
        handlerController.closeConnection(session);
    }

    private String getHandle(String message) {
        return Arrays.stream(
                message.lines()
                .findFirst()
                .orElse("")
                .split(" "))
                .findFirst()
                .orElse("OTHER");
    }
}
