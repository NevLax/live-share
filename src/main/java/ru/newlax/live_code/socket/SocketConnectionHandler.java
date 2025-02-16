package ru.newlax.live_code.socket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import ru.newlax.live_code.LaxProtocol.SimpleProtocol;
import ru.newlax.live_code.LaxProtocol.UserController;

public class SocketConnectionHandler extends TextWebSocketHandler {

    @Autowired
    private UserController userController;
    @Autowired
    private SimpleProtocol protocol;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        userController.removeUser(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        super.handleTextMessage(session, message);
        protocol.processMessage(session, message);
    }

}
