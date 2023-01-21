package com.company;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpServer;

import java.io.*;
import java.net.InetSocketAddress;

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

                registerUser(new User(newUser.getName(), generateUserId()));

                request.sendResponseHeaders(200, -1);
                in.close();
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
                User user = new User(newGroup.getUsername(), generateUserId());

                createGroup(newGroup.getGroupName(), user);

                request.sendResponseHeaders(200, -1);
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

                System.out.println(getListGroups());
                request.sendResponseHeaders(200, 100*getSizeGroupUsers());
                gson.toJson(getListGroups(), buffWriter);

                buffWriter.close();
                buffWriter.flush();

            } else {
                request.sendResponseHeaders(405, -1);
            }
        }));
    }

//    public void closeGroup() {
//        server.createContext("/close-group", (request -> {
//            if ("PUT".equals(request.getRequestMethod())) {
//                Gson gson = new Gson();
//                InputStream in = request.getRequestBody();
//                BufferedReader buffRead = new BufferedReader(new InputStreamReader(in));
//
//                NewGroup newGroup = gson.fromJson(buffRead, NewGroup.class);
//                User user = new User(newGroup.getUsername(), generateUserId());
//
//
//
//                request.sendResponseHeaders(200, -1);
//                in.close();
//            } else {
//                request.sendResponseHeaders(405, -1);
//            }
//        }));
//    }
}
