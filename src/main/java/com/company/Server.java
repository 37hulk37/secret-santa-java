package com.company;

import javax.swing.text.html.HTMLDocument;
import java.security.SecureRandom;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
public class Server {
    protected ConcurrentHashMap<Integer, User> users;
    protected ConcurrentHashMap<String, Group> groups;
    protected ConcurrentHashMap<Integer, Integer> santas;

    private int curUserId;
    private int userRb;
    private int curGroupId;
    private int groupRb;

    public Server(int userLb, int userRb, int groupLb, int groupRb) {
        this.groups = new ConcurrentHashMap<>();
        this.users = new ConcurrentHashMap<>();
        this.curUserId = userLb;
        this.userRb = userRb;
        this.curGroupId = groupLb;
        this.groupRb = groupRb;
    }

    private Integer getId(int[] ids) {
        SecureRandom random = new SecureRandom();
        return ids[random.nextInt(ids.length)];
    }

    public synchronized void setSantas(ArrayList<Integer> group) {
        santas = new ConcurrentHashMap<>();

        Collections.shuffle(group);

        for (int i = 0; i < group.size() / 2; i++) {
            santas.put(group.get(i), group.get(i+1));
            santas.put(group.get(i+1), group.get(i));
        }
    }

    public synchronized Integer generateUserId() {
        if (curUserId < userRb) {
            curUserId++;
        } else {
            System.out.println("Too much Users");
        }
        return curUserId;
    }

    public synchronized boolean registerUser(User user) {
        boolean isRegistered = false;

        if ( !users.contains(user.getId()) ) {
            users.put(user.getId(), user);
            isRegistered = true;
        }

        return isRegistered;
    }

    public synchronized boolean createGroup(String groupName, User user) {
        boolean isRegistered = false;

        if ( !groups.contains(groupName) ) {
            Group group = new Group(groupName, user);
            groups.put(group.getGroupName(), group);
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

    public ArrayList<Group> getListGroups() {
        return new ArrayList<>(groups.values());
    }

    public ArrayList<User> getListUsers() {
        return new ArrayList<>(users.values());
    }

    public int getSizeListGroups() { return groups.size(); }

    public int getSizeListUsers() { return users.size(); }
}
