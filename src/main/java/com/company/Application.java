package com.company;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class Application extends Server {
    private HttpServer server;

    public Application(int port, int lbUser, int rbUser, int lbGroup, int rbGroup) {
        super(lbUser, rbUser, lbGroup, rbGroup);
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
                InputStream in = request.getRequestBody();
                String name = Integer.toString(in.read());

                registerUser(new User(name, generateUserId()));

                request.sendResponseHeaders(200, -1);
                OutputStream out = request.getResponseBody();
                out.flush();
                out.close();
                in.close();
            } else {
                request.sendResponseHeaders(405, -1);
            }
        }));
    }

    public void getUsers() {
        server.createContext("/get-users", (request -> {
            if ("GET".equals(request.getRequestMethod())) {
                String u = getListUsers().toString();
                request.sendResponseHeaders(200, u.length());
                OutputStream out = request.getResponseBody();

                out.write(u.getBytes());
                out.flush();
                out.close();
            } else {
                request.sendResponseHeaders(405, -1);
            }
        }));
    }

    public void createGroup() {
        server.createContext("/create-group", (request -> {
            if ("POST".equals(request.getRequestMethod())) {
                InputStream in = request.getRequestBody();
                String[] gr = Integer.toString(in.read()).split(",");
//                User user = new User(users.get(gr[1]), gr[1]);
//                Group group = new Group(gr[0], (), 6);

                System.out.println(gr);

            } else {
                request.sendResponseHeaders(405, -1);
            }
        }));
    }
}
