package main.server;

import java.util.HashSet;

public class ChatRoom {

    String name;
    String password;
    boolean isPrivate;
    HashSet<User> users;

    public ChatRoom(String name, String password, boolean isPrivate, User host) {
        this.name = name;
        this.password = password;
        this.isPrivate = isPrivate;
        users = new HashSet<>();
        users.add(host);
    }

}
