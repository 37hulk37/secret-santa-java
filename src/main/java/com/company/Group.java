package com.company;

import java.io.Serializable;
import java.util.HashMap;
// Gson

public class Group implements Serializable {
    private int curUsers;
    final private int maxUsers;
    private boolean isClosed;
    final private HashMap<Integer, String> users;
    final private HashMap<Integer, String> admins;

    public Group(User user, int maxUsers) {
        this.curUsers = 1;
        this.maxUsers = maxUsers;
        this.isClosed = (curUsers == maxUsers);
        this.users = new HashMap<>();
        users.put(user.getId(), user.getName());
        this.admins = new HashMap<>();
        admins.put(user.getId(), user.getName());
    }

    public synchronized void add(User user) {
        if ( !isClosed && curUsers < maxUsers) {
            users.put(user.getId(), user.getName());
            curUsers++;

            this.isClosed = (curUsers == maxUsers);
        }
    }

    public int getCurUsers() {
        return curUsers;
    }

    public int getMaxUsers() {
        return maxUsers;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public void setClosed(boolean closed) {
        isClosed = closed;
    }

    public synchronized boolean removeUser(User user) {
        boolean isRemoved = false;
        long id = user.getId();
        if (users.containsKey(id)) {
            users.remove(id);
            isRemoved = true;
        }

        return isRemoved;
    }

    public synchronized boolean setAdmin(User user) {
        boolean isSet = false;
        int id = user.getId();
        if ( !admins.containsKey(id) ) {
            admins.put(id, user.getName());
            isSet = true;
        }

        return isSet;
    }

    public synchronized HashMap<Integer, String> getUsers() {
        return users;
    }

    public synchronized HashMap<Integer, String> getAdmins() {
        return admins;
    }
}
