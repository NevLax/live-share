package ru.newlax.live_code.LaxProtocol;

import org.springframework.stereotype.Repository;
import org.springframework.web.socket.WebSocketSession;

import java.util.*;

@Repository
public class UserController {

    private final Map<String, List<WebSocketSession>> listeners = Collections.synchronizedMap(new HashMap<>());
    private final Map<WebSocketSession, String> streamers = Collections.synchronizedMap(new HashMap<>());

    public void addListener(String name, WebSocketSession session) {
        listeners.computeIfAbsent(name, _ -> new ArrayList<>()).add(session);
    }

    public void addStreamer(String name, WebSocketSession session) { streamers.put(session, name); }

    public List<WebSocketSession> getListenerByName(String name) { return listeners.get(name); }

    public List<WebSocketSession> getListenerByStreamer(WebSocketSession streamer) {
        return getListenerByName(getStreamerName(streamer));
    }

    public String getStreamerName(WebSocketSession session) { return streamers.get(session); }

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
        String streamerName = getStreamerName(session);

        listeners.remove(streamerName);
        streamers.remove(session);
    }
}
