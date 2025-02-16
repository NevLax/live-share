package ru.newlax.live_code.LaxProtocol;

import org.springframework.stereotype.Controller;
import org.springframework.web.socket.WebSocketSession;

import java.util.*;

@Controller
public class UserController {

    private final Map<String, List<WebSocketSession>> listeners = Collections.synchronizedMap(new HashMap<>());
    private final Map<WebSocketSession, String> streamers = Collections.synchronizedMap(new HashMap<>());
    private final Map<String, String> lastMessages = Collections.synchronizedMap(new HashMap<>());

    public void addListener(String name, WebSocketSession session) {
        listeners.computeIfAbsent(name, _ -> new ArrayList<>()).add(session);
    }

    public void addStreamer(String name, WebSocketSession session) { streamers.put(session, name); }

    public List<WebSocketSession> getListenerByName(String name) { return listeners.get(name); }

    public String getStreamerName(WebSocketSession session) { return streamers.get(session); }

    public void setLastMessage(WebSocketSession session, String message) { lastMessages.put(streamers.get(session), message); }

    public String getLastMessage(String name) { return lastMessages.get(name); }

    public void removeUser(WebSocketSession session) {
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
