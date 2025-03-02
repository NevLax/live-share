package ru.newlax.live_code.LaxProtocol;

import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class LinesScope {

    private final List<String> scopeLines = new ArrayList<>();

    public LinesScope(String text) {
        scopeLines.addAll(List.of(
                text.split("\n")
        ));
    }

    public void addLine(String message) {
        scopeLines.add(message);
    }

    public void insertLine(int index, String line) {
        scopeLines.add(index, line);
    }

    public void editLine(int index, String line) {
        scopeLines.set(index, line);
    }

    public void removeLine(int index) {
        scopeLines.remove(index);
    }

    public String getAllText() {
        return String.join("", scopeLines);
    }
}
