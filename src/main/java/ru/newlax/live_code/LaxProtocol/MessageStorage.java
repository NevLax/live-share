package ru.newlax.live_code.LaxProtocol;

import java.util.ArrayList;
import java.util.List;

public class MessageStorage {

    List<String> scopeLines = new ArrayList<>();

    public MessageStorage(String text) {
        scopeLines.addAll(List.of(
                text.split("\n")
        ));
    }

    public void addLine() {
        this.addLine("");
    }

    public void addLine(String message) {
        scopeLines.add(message);
    }

    public void insertLine(int index, String line) {
        scopeLines.add(index, line);
    }

    public void removeLine(int index) {
        scopeLines.remove(index);
    }

    public String getAllText() {
        return String.join("", scopeLines);
    }
}
