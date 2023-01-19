package com.company;

import java.io.Serializable;
//Gson

public class User implements Serializable {
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
