package ru.newlax.live_code.LaxProtocol;

import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Repository
public class MessageStorage {

    private final Map<String, LinesScope> messageStorage = Collections.synchronizedMap(new HashMap<>());

    public void removeLine(String name, int index) {
        messageStorage.get(name).removeLine(index);
    }

    public void addLine(String name) {
        messageStorage.get(name).addLine("");
    }

    public void addLine(String name, String line) {
        messageStorage.get(name).addLine(line);
    }

    public void insertLine(String name, int index) {
        messageStorage.get(name).insertLine(index, "");
    }

    public void insertLine(String name, int index, String line) {
        messageStorage.get(name).insertLine(index, line);
    }

    public void editLine(String name, int index, String line) {
        messageStorage.get(name).editLine(index, line);
    }

    public void initScope(String name) {
        messageStorage.put(name, new LinesScope());
    }

    public void initScope(String name, String text) {
        messageStorage.put(name, new LinesScope(text));
    }

    public void remove(String name) {
        messageStorage.remove(name);
    }
}
