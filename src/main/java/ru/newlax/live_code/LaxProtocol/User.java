package ru.newlax.live_code.LaxProtocol;

import lombok.Getter;

public class User {
    @Getter
    final Role role;
    @Getter
    final String name;

    public User(Role role, String name) {
        this.role = role;
        this.name = name;
    }

}
