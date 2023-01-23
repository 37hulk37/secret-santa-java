package com.company;

import com.google.gson.annotations.Expose;

class NewUser {
    private String name;

    public NewUser(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

public class User {
    private int id;
    @Expose
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
