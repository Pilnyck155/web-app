package com.pilnyck.appserver.server;

import com.pilnyck.appserver.request.RequestHandler;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static int port;
    public static String webAppPath;

    public void setPort(int port) {
        Server.port = port;
    }

    public void setWebAppPath(String webAppPath) {
        Server.webAppPath = webAppPath;
    }

    public void start(){
        try (ServerSocket serverSocket = new ServerSocket(port);) {
            while (true) {
                try(Socket socket = serverSocket.accept();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));) {
                    RequestHandler requestHandler = new RequestHandler(socket, bufferedReader, bufferedWriter, webAppPath);
                    requestHandler.handle();
                }
            }
        } catch (IOException exception){
            exception.printStackTrace();
        }
    }
}
