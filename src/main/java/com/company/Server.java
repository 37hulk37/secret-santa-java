package com.company;

// have a look to a documentation about Runnable & Threads & Tasks

import com.company.Group;
import com.company.User;

import java.security.SecureRandom;
import java.util.*;

public class Server {
    private HashSet<Group> groups;
    private Integer curId;
    private Integer rb;

    public Server(Integer lb, Integer rb) {
        this.groups = new HashSet<>();
        this.curId = lb;
        this.rb = rb;
    }

    private Integer getId(int[] ids) {
        SecureRandom random = new SecureRandom();
        return ids[random.nextInt(ids.length)];
    }

    public HashMap<Integer, Integer> setSantas(ArrayList<Integer> group) {
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

    //just starts server
    public void go() {

    }

    public void handleClient() {

    }
}
