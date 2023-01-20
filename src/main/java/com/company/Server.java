package com.company;

// have a look to a documentation about Runnable & Threads & Tasks

import java.security.SecureRandom;
import java.util.*;

public class Server {
    private HashSet<Group> groups;
    private HashSet<User> users;
    private Integer curId;
    private Integer rb;

    public Server(Integer lb, Integer rb) {
        this.groups = new HashSet<>();
        this.users = new HashSet<>();
        this.curId = lb;
        this.rb = rb;
    }

    private Integer getId(int[] ids) {
        SecureRandom random = new SecureRandom();
        return ids[random.nextInt(ids.length)];
    }

    public synchronized HashMap<Integer, Integer> setSantas(ArrayList<Integer> group) {
        HashMap<Integer, Integer> santas = new HashMap<>();
        int[] ids = new int[group.size()];

        int i = 0;
        for (Integer id: group) {
            ids[i] = id;
            i++;
        }

        Iterator<Integer> it = group.iterator();
        while (it.hasNext() && santas.size() < group.size() / 2) {
            int id = it.next();
            if ( !santas.containsKey(id) || !santas.containsValue(id)) {
                int otherId = getId(ids);

                while (id == otherId || santas.containsKey(otherId) || santas.containsValue(otherId)) {
                    otherId = getId(ids);
                }

                santas.put(id, otherId);
            }
        }

        return santas;
    }

    public synchronized Integer generateId() {
        if (curId < rb) {
            curId++;
        } else {
            System.out.println("Too much Users");
        }
        return curId;
    }

    public synchronized boolean registerUser(User user) {
        boolean isRegistered = false;

        if ( !users.contains(user.getId()) ) {
            users.add(user);
            isRegistered = true;
        }

        return isRegistered;
    }

    public synchronized boolean registerGroup(String groupName, User user, int maxUsers) {
        boolean isRegistered = false;

        if ( !groups.contains(groupName) ) {
            Group group = new Group(groupName, user, maxUsers);
            groups.add(group);
        }
        return isRegistered;
    }

    public synchronized boolean deleteGroup(Group group, User user) {
        boolean isDeleted = false;

        if (groups.contains(group)) {
            if (group.getAdmins().containsKey(user.getId())) {
                groups.remove(group);
                isDeleted = true;
            }
        }

        return isDeleted;
    }

    public HashSet<Group> getGroups() {
        return groups;
    }

    public HashSet<User> getUsers() {
        return users;
    }
}
