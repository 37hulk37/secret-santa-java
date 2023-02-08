package com.company;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class AppExecutor {
    private ExecutorService pool;
    private int cores;
    private ArrayList<Future> futures;
    private Application app;

    public interface ThreadFunction {
        void apply();
    }

    public AppExecutor(String[] args) {
        cores = Runtime.getRuntime().availableProcessors();
        pool = Executors.newFixedThreadPool(cores);
        futures = new ArrayList<>();

        app = new Application(8080, Integer.parseInt(args[0]), Integer.parseInt(args[1]),
                Integer.parseInt(args[2]), Integer.parseInt(args[3]));

        addFuture(app::registerUser);
        addFuture(app::getUsers);
        addFuture(app::createGroup);
        addFuture(app::getGroups);
        addFuture(app::addToGroup);
        addFuture(app::leaveGroup);
        addFuture(app::startSecretSanta);
        addFuture(app::getRecipient);
    }

    private void addFuture(ThreadFunction func) {
        Future future = pool.submit(new Thread(() -> {
            func.apply();
        }));
        futures.add(future);
    }

    public static void main(String[] args) {
        new AppExecutor(args);
    }
}

