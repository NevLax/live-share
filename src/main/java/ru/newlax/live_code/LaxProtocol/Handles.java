package ru.newlax.live_code.LaxProtocol;

public enum Handles {
    STREAMER("STREAMER"),
    LISTENER("LISTENER"),
    MESSAGE("MESSAGE"),
    ADD_LINE("ADD_LINE"),
    REMOVE_LINE("REMOVE_LINE"),
    EDIT("EDIT");

    private final String value;

    Handles(String value) {
        this.value = value;
    }

    public static Handles fromString(String str) {
        for (Handles handle : values()) {
            if (handle.value.equalsIgnoreCase(str)) {
                return handle;
            }
        }
        throw new IllegalArgumentException("Not a values from " + Handles.class.getSimpleName() + " " + str);
    }
}
