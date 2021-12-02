package com.pilnyck.appserver.request;

import java.io.*;


public class ResponseWriter {

    public static String statusCode = "HTTP/1.1 200 OK";
    //public static String prefix = "HTTP/1.1 ";
    //StatusCode statusCode;

    public static void writeResponse(String content, BufferedWriter bufferedWriter) throws IOException {
        bufferedWriter.write(statusCode);
        //bufferedWriter.write(prefix);
        bufferedWriter.newLine();
        bufferedWriter.write(content);
        //StringBuilder stringBuilder = new StringBuilder();
        //File file = new File(content);
        //BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

//        String line = "";
//        while ((line = bufferedReader.readLine()) != null) {
//            bufferedWriter.write(line);
//        }
    }

//    public static String readContentFromFile(String content) throws IOException {
//        StringBuilder stringBuilder = new StringBuilder();
//        File file = new File(content);
//        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
//        String line = "";
//        while ((line = bufferedReader.readLine()) != null) {
//            stringBuilder.append(line);
//        }
//        return stringBuilder.toString();
//    }
}
//enum StatusCode
//{
//    //100, 300, 200, 400, 500
//    BAD_REQUEST(400, " Bad Request\r\n"),
//    NOT_FOUND(404, " Not Found \r\n"),
//    METHOD_NOT_ALLOWED(405, " Method not allowed\r\n"),
//    INTERNAL_SERVER_ERROR(500, " Internal Server Error \r\n");
//}
