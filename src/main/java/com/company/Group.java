package com.company;

import com.google.gson.annotations.Expose;

import java.util.concurrent.ConcurrentHashMap;

class ReqGroup {
    private int userId;
    private String groupName;

    public String getGroupName() {
        return groupName;
    }

    public int getUserId() {
        return userId;
    }
}

public class Group {
    @Expose(serialize = true)
    private String groupName;
    @Expose(serialize = true)
    private int id;
    @Expose(serialize = true)
    private int curUsers;
    @Expose(serialize = false)
    private boolean isClosed;
    @Expose(serialize = false)
    final private ConcurrentHashMap<Integer, User> users;
    @Expose(serialize = false)
    final private ConcurrentHashMap<Integer, User> admins;

    public Group(String groupName, User user) {
        this.groupName = groupName;
        this.curUsers = 1;
        this.isClosed = false;
        this.users = new ConcurrentHashMap<>();
        users.put(user.getId(), user);
        this.admins = new ConcurrentHashMap<>();
        admins.put(user.getId(), user);
    }

    public void add(User user) {
        users.put(user.getId(), user);
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
        int id = user.getId();
        if (users.contains(id) || (admins.contains(id) && admins.size() > 1)) {
            users.remove(id);
            curUsers--;
            isRemoved = true;
        }

        return isRemoved;
    }

    public boolean setAdmin(User user) {
        boolean isSet = false;
        int id = user.getId();
        if ( !admins.containsKey(id) ) {
            admins.put(id, user);
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

    public ConcurrentHashMap<Integer, User> getUsers() {
        return users;
    }

    public ConcurrentHashMap<Integer, User> getAdmins() {
        return admins;
    }
}
