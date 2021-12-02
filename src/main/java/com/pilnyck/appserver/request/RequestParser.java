package com.pilnyck.appserver.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

public class RequestParser {
    BufferedReader bufferedReader;
    Request request;


    public Request parse(BufferedReader bufferedReader) throws IOException {
        Request request = new Request();
        String line = bufferedReader.readLine();
        injectUriAndMethod(line, request);
        injectHeaders(bufferedReader, request);
        return request;
    }

    public void injectUriAndMethod(String line, Request request) {
        String[] splitMessage = line.split(" ");
        request.method = HTTPMethods.valueOf(splitMessage[0]);
        request.uri = splitMessage[1];
    }

    public void injectHeaders(BufferedReader bufferedReader, Request request) throws IOException {
        request.headers = new HashMap<>();
        while (true) {
            String headers = bufferedReader.readLine();
            if ((headers != "")&&(headers != null)) {
                if (headers.contains(": ")){
                    String[] splitHeaders = headers.split(": ");
                        request.headers.put(splitHeaders[0], splitHeaders[1]);
                } else {
                   break;
                }
            } else {
                break;
            }
        }
    }
}
