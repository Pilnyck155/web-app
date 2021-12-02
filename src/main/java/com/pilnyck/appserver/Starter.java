package com.pilnyck.appserver;

import com.pilnyck.appserver.server.Server;

public class Starter {
    public static void main(String[] args) {
        Server server = new Server();
        server.setPort(3000);
        server.setWebAppPath("src/main/resources/webapp");
        server.start();
    }
}
