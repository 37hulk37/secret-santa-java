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
    private String groupName;
    @Expose(serialize = false)
    private int id;
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

    @Override
    public String toString() {
        return "Group{" +
                "groupName='" + groupName + '\'' +
                ", isClosed=" + isClosed +
                '}';
    }
}
