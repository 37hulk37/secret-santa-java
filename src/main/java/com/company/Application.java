package com.company;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpServer;

import java.io.*;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

public class Application extends Server {
    private HttpServer server;

    public Application(int port, int lb, int rb) {
        super(lb, rb);
        try {
            this.server = HttpServer.create(new InetSocketAddress(port), 0);
            server.setExecutor(null);
            server.start();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public void getVersion() {
        server.createContext("/version", (request -> {
            if ("GET".equals(request.getRequestMethod())) {
                String v = "0.1.0";
                Gson gson = new Gson();
                gson.toJson(v);

                request.sendResponseHeaders(200, gson.toString().length());
                OutputStream out = request.getResponseBody();
                out.write(v.getBytes());
                out.flush();
                out.close();
            } else {
                request.sendResponseHeaders(405, -1);
            }
        }));
    }

    public void registerUser() {
        server.createContext("/register-user", (request -> {
            if ("POST".equals(request.getRequestMethod())) {
                Gson gson = new Gson();
                InputStream in = request.getRequestBody();
                BufferedReader buffRead = new BufferedReader(new InputStreamReader(in));

                NewUser newUser = gson.fromJson(buffRead, NewUser.class);
                User user = new User(newUser.getName(), generateUserId());
                registerUser(user);

                OutputStream out = request.getResponseBody();
                BufferedWriter buffWriter = new BufferedWriter(new OutputStreamWriter(out));

                int id = user.getId();
                request.sendResponseHeaders(201, Integer.toString(id).length());
                gson.toJson(id, buffWriter);

                in.close();
                buffWriter.close();
                buffWriter.flush();
            } else {
                request.sendResponseHeaders(405, -1);
            }
        }));
    }

    public void getUsers() {
        server.createContext("/get-users", (request -> {
            if ("GET".equals(request.getRequestMethod())) {
                Gson gson = new Gson();
                OutputStream out = request.getResponseBody();
                BufferedWriter buffWriter = new BufferedWriter(new OutputStreamWriter(out));

                request.sendResponseHeaders(200, 100*getSizeListUsers());
                gson.toJson(getListUsers(), buffWriter);
                buffWriter.close();
                buffWriter.flush();
            } else {
                request.sendResponseHeaders(405, -1);
            }
        }));
    }

    public void createGroup() {
        server.createContext("/create-group", (request -> {
            if ("POST".equals(request.getRequestMethod())) {
                Gson gson = new Gson();
                InputStream in = request.getRequestBody();
                BufferedReader buffRead = new BufferedReader(new InputStreamReader(in));

                NewGroup newGroup = gson.fromJson(buffRead, NewGroup.class);
                createGroup(newGroup.getGroupName(), users.get(newGroup.getUserId()));

                request.sendResponseHeaders(201, -1);
                in.close();
            } else {
                request.sendResponseHeaders(405, -1);
            }
        }));
    }

    public void getGroups() {
        server.createContext("/get-groups", (request -> {
            if ("GET".equals(request.getRequestMethod())) {
                Gson gson = new Gson();
                OutputStream out = request.getResponseBody();
                BufferedWriter buffWriter = new BufferedWriter(new OutputStreamWriter(out));

                request.sendResponseHeaders(200, 1000*getSizeListGroups());
                gson.toJson(getListGroups(), buffWriter);

                buffWriter.close();
                buffWriter.flush();
            } else {
                request.sendResponseHeaders(405, -1);
            }
        }));
    }

    public void setSantas() {
        server.createContext("/start-secret-santa", (request -> {
            if ("POST".equals(request.getRequestMethod())) {
                Gson gson = new Gson();
                InputStream in = request.getRequestBody();
                BufferedReader buffRead = new BufferedReader(new InputStreamReader(in));

                NewGroup newGroup = gson.fromJson(buffRead, NewGroup.class);

                String groupName = newGroup.getGroupName();
                if (groups.get(groupName).getCurUsers() % 2 == 0 &&
                        groups.get(groupName).getAdmins().containsKey(newGroup.getUserId())) {

                    setSantas(Collections.list(groups.get(groupName).getUsers().keys()));

                    request.sendResponseHeaders(200, -1);
                } else {
                    request.sendResponseHeaders(406, -1);
                }


                in.close();
            } else {
                request.sendResponseHeaders(405, -1);
            }
        }));


    }
}
