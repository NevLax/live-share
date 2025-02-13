package ru.newlax.live_code.LaxProtocol;

public class SimpleImplementation {

    public static User Classifier(String message) {
        String[] splits = message.split(" ");
        if ("STREAMER".equals(splits[0])) {
            return new User(Role.STREAMER, splits[1]);
        }
        if ("LISTENER".equals(splits[0])) {
            return new User(Role.LISTENER, splits[1]);
        }
        return new User(Role.OTHER, message);
    }

}
