package ru.newlax.live_code.LaxProtocol;

import org.springframework.stereotype.Repository;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.*;

@Repository
public class UserController {

    private final Map<String, List<WebSocketSession>> listeners = Collections.synchronizedMap(new HashMap<>());
    private final Map<WebSocketSession, String> streamers = Collections.synchronizedMap(new HashMap<>());
    private final Map<String, String> lastMessages = Collections.synchronizedMap(new HashMap<>());

    public void addListener(String name, WebSocketSession session) {
        listeners.computeIfAbsent(name, _ -> new ArrayList<>()).add(session);
        try {
            session.sendMessage(new TextMessage(
                    lastMessages.getOrDefault(name, "no message now")
            ));
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void addStreamer(String name, WebSocketSession session) { streamers.put(session, name); }

    public List<WebSocketSession> getListenerByName(String name) { return listeners.get(name); }

    public List<WebSocketSession> getListenerByStreamer(WebSocketSession streamer) {
        return getListenerByName(getStreamerName(streamer));
    }

    public String getStreamerName(WebSocketSession session) { return streamers.get(session); }

    public void setLastMessage(WebSocketSession session, String message) {
        lastMessages.put(streamers.get(session), message);
        broadcast(session, new TextMessage(message));
    }

    public String getLastMessage(String name) { return lastMessages.get(name); }

    public void closeConnection(WebSocketSession session) {
        if (listeners.containsValue(session)) { removeListener(session); }
        if (streamers.containsKey(session)) { removeStreamer(session); }
    }

    private void removeListener(WebSocketSession session) {
        for (String name : listeners.keySet()) {
            if (listeners.get(name).contains(session)) {
                listeners.get(name).remove(session);
                break;
            }
        }
    }

    private void removeStreamer(WebSocketSession session) {
        TextMessage CLOSE = new TextMessage("CLOSE");
        broadcast(session, CLOSE);
        listeners.remove(getStreamerName(session));
        streamers.remove(session);
    }

    public void broadcast(WebSocketSession streamer, TextMessage message) {
        for (WebSocketSession listener : getListenerByStreamer(streamer)) {
            try {
                listener.sendMessage(message);
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
    }

}
