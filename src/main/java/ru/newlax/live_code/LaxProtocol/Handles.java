package ru.newlax.live_code.LaxProtocol;

public enum Handles {
    STREAMER("STREAMER"),
    LISTENER("LISTENER"),
    MESSAGE("MESSAGE"),
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
