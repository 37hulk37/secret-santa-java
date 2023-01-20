package com.company;

// have a look to a documentation about Runnable & Threads & Tasks

import java.security.SecureRandom;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Server {
    protected ConcurrentHashMap<Integer, String> groups;
    protected ConcurrentHashMap<Integer, String> users;
    private int curUserId;
    private int rbUser;
    private int curGroupId;
    private int rbGroup;

    public Server(int lbUser, int rbUser, int lbGroup, int rbGroup) {
        this.groups = new ConcurrentHashMap<>();
        this.users = new ConcurrentHashMap<>();
        this.curUserId = lbUser;
        this.rbUser = rbUser;
        this.curGroupId = lbGroup;
        this.rbGroup = rbGroup;
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

    public synchronized Integer generateUserId() {
        if (curUserId < rbUser) {
            curUserId++;
        } else {
            System.out.println("Too much Users");
        }
        return curUserId;
    }

    public synchronized Integer generateGroupId() {
        if (curGroupId < rbGroup) {
            curGroupId++;
        } else {
            System.out.println("Too much Groups");
        }
        return curGroupId;
    }

    public synchronized boolean registerUser(User user) {
        boolean isRegistered = false;

        if ( !users.contains(user.getId()) ) {
            users.put(user.getId(), user.getName());
            isRegistered = true;
        }

        return isRegistered;
    }

    public synchronized boolean registerGroup(String groupName, User user) {
        boolean isRegistered = false;

        if ( !groups.contains(groupName) ) {
            Group group = new Group(groupName, user);
            groups.put(group.getGroupId(), group.getGroupName());
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

    public ConcurrentHashMap<Integer, String> getListGroups() {
        return groups;
    }

    public ConcurrentHashMap<Integer, String> getListUsers() {
        return users;
    }
}
