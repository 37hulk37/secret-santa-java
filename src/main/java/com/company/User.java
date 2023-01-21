package com.company;

import java.io.Serializable;
//Gson

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
    final private int id;
    final private String name;

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
