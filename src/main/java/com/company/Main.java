package com.company;

//проверка на чет кол-во челов в группе

import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Main {
    public static void main(String[] args) {
        Application app = new Application(81, 0, 89, 0, 89);

        new Thread(() -> {
            app.getVersion();
        }).start();

        new Thread(() -> {
            app.registerUser();
        }).start();

        new Thread(() -> {
            app.getUsers();
        }).start();
    }
}

