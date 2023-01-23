package com.company;

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
