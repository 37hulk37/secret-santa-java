package com.company;

//проверка на чет кол-во челов в группе
// add work with threads

public class Main {
    public static void main(String[] args) {
        Application app = new Application(80, 0, 89);

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

