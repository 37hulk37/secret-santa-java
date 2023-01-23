package com.company;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sun.net.httpserver.HttpServer;

import java.io.*;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

public class Application extends Server {
    private HttpServer server;

    public Application(int port, int userLb, int userRb, int groupLb, int groupRb) {
        super(userLb, userRb, groupLb, groupRb);
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

                ReqGroup reqGroup = gson.fromJson(buffRead, ReqGroup.class);
                createGroup(reqGroup.getGroupName(), users.get(reqGroup.getUserId()));

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
                Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
                OutputStream out = request.getResponseBody();
                BufferedWriter buffWriter = new BufferedWriter(new OutputStreamWriter(out));

                request.sendResponseHeaders(200, 1000*getSizeListGroups());
                String str = gson.toJson(getListGroups(), new TypeToken<ArrayList<Group>>() {}.getType());
                buffWriter.write(str);
                buffWriter.close();
                buffWriter.flush();
            } else {
                request.sendResponseHeaders(405, -1);
            }
        }));
    }

    public void addToGroup() {
        server.createContext("/add-to-group", (request -> {
            if ("POST".equals(request.getRequestMethod())) {
                Gson gson = new Gson();
                InputStream in = request.getRequestBody();
                BufferedReader buffRead = new BufferedReader(new InputStreamReader(in));

                ReqGroup reqGroup = gson.fromJson(buffRead, ReqGroup.class);
                Group group = groups.get(reqGroup.getGroupName());
                if ( !group.isClosed() ) {
                    group.add(users.get(reqGroup.getUserId()));
                    request.sendResponseHeaders(201, -1);
                } else {
                    request.sendResponseHeaders(406, -1);
                }


                in.close();
            } else {
                request.sendResponseHeaders(405, -1);
            }
        }));
    }

    public void startSecretSanta() {
        server.createContext("/start-secret-santa", (request -> {
            if ("POST".equals(request.getRequestMethod())) {
                Gson gson = new Gson();
                InputStream in = request.getRequestBody();
                BufferedReader buffRead = new BufferedReader(new InputStreamReader(in));

                ReqGroup reqGroup = gson.fromJson(buffRead, ReqGroup.class);

                System.out.println(reqGroup.getGroupName());

                Group group = groups.get(reqGroup.getGroupName());
                if (group.getCurUsers() % 2 == 0 &&
                        group.getAdmins().containsKey(reqGroup.getUserId())) {

                    group.setClosed(true);
                    setSantas(Collections.list(group.getUsers().keys()));

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
