package com.company;

public class Main {
    public static void main(String[] args) {
        Application app = new Application(80, 0, 89, 0, 88);

        new Thread(() -> {
            app.registerUser();
            app.getUsers();
        }).start();

        new Thread(() -> {
            app.createGroup();
            app.getGroups();

            //app.req();
        }).start();

        new Thread(() -> {
            app.startSecretSanta();
        });
    }
}

