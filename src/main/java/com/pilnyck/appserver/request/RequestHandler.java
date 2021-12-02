package com.pilnyck.appserver.request;

import java.io.*;
import java.net.Socket;

public class RequestHandler{
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String webAppPath;

    public RequestHandler(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter, String webAppPath) {
        this.socket = socket;
        this.bufferedReader = bufferedReader;
        this.bufferedWriter = bufferedWriter;
        this.webAppPath = webAppPath;
    }

    public void handle() throws IOException {
        Request request = new RequestParser().parse(bufferedReader);
        ResourceReader resourceReader = new ResourceReader(webAppPath);
        String content = resourceReader.readResources(request.uri);
        ResponseWriter.writeResponse(content, bufferedWriter);
    }
}
