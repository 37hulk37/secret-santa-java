package com.company;

class NewUser {
    private String name;

    public String getName() {
        return name;
    }
}

public class User {
    private int id;
    private String name;

    public User(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}
