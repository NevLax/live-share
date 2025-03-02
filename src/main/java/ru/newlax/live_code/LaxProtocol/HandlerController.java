package ru.newlax.live_code.LaxProtocol;

import org.springframework.stereotype.Controller;
import org.springframework.web.socket.WebSocketSession;

@Controller
public class HandlerController {

    private final UserController userController;
    private final MessageStorage messageStorage;

    public HandlerController(MessageStorage messageStorage, UserController userController) {
        this.userController = userController;
        this.messageStorage = messageStorage;
    }

    public void processHandler(WebSocketSession session, Handles handle, String message) {

    }

    public void closeConnection(WebSocketSession session) {
        userController.closeConnection(session);
    }
}
