package com.company;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;
// Gson

public class Group implements Serializable {
    private String groupName;
    private int id;
    private int curUsers;
    private boolean isClosed;
    final private ConcurrentHashMap<Integer, String> users;
    final private ConcurrentHashMap<Integer, String> admins;

    public Group(String groupName, User user) {
        this.groupName = groupName;
        this.curUsers = 1;
        this.isClosed = false;
        this.users = new ConcurrentHashMap<>();
        users.put(user.getId(), user.getName());
        this.admins = new ConcurrentHashMap<>();
        admins.put(user.getId(), user.getName());
    }

    public void add(User user) {
        users.put(user.getId(), user.getName());
        curUsers++;
    }

    public int getCurUsers() {
        return curUsers;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public void setClosed(boolean closed) {
        isClosed = closed;
    }

    public boolean removeUser(User user) {
        boolean isRemoved = false;
        long id = user.getId();
        if (users.containsKey(id)) {
            users.remove(id);
            isRemoved = true;
        }

        return isRemoved;
    }

    public boolean setAdmin(User user) {
        boolean isSet = false;
        int id = user.getId();
        if ( !admins.containsKey(id) ) {
            admins.put(id, user.getName());
            isSet = true;
        }

        return isSet;
    }

    public int getGroupId() {
        return id;
    }

    public String getGroupName() {
        return groupName;
    }

    public ConcurrentHashMap<Integer, String> getUsers() {
        return users;
    }

    public ConcurrentHashMap<Integer, String> getAdmins() {
        return admins;
    }
}
