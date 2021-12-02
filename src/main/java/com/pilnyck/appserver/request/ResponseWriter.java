package com.pilnyck.appserver.request;

import java.io.*;


public class ResponseWriter {

    public static String statusCode = "HTTP/1.1 200 OK";


    public static void writeResponse(String content, BufferedWriter bufferedWriter) throws IOException {
        bufferedWriter.write(statusCode);
        bufferedWriter.newLine();
        bufferedWriter.newLine();
        bufferedWriter.write(content);
    }
}
