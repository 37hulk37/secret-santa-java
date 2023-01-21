package com.company;

//проверка на чет кол-во челов в группе

public class Main {
    public static void main(String[] args) {
        Application app = new Application(80, 0, 89);

        new Thread(() -> {
            app.registerUser();
            app.getUsers();
        }).start();

        new Thread(() -> {
            app.createGroup();
            app.getGroups();
        }).start();
    }
}

