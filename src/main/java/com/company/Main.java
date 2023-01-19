package com.company;

//проверка на чет кол-во челов в группе

import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Main {
    public static void main(String[] args) {
        Server server = new Server(0, 89);
        //Server server = new Server(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        //server.go();

        User user1 = new User("a", server.generateId());
        User user2 = new User("b", server.generateId());
        User user3 = new User("c", server.generateId());
        User user4 = new User("d", server.generateId());
        User user5 = new User("e", server.generateId());
        User user6 = new User("f", server.generateId());
        Group group = new Group(user1, 6);
        group.add(user2);
        group.add(user3);
        group.add(user4);
        group.add(user5);
        group.add(user6);

        ArrayList<Integer> list = new ArrayList<>(group.getUsers().keySet());
        System.out.println(server.setSantas(list));
    }
}

