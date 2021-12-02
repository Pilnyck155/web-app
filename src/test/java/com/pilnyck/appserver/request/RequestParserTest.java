package com.pilnyck.appserver.request;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class RequestParserTest {
    RequestParser requestParser = new RequestParser();
    Request request = new Request();

    @BeforeAll
    static void before() throws IOException {
        String line = "GET /wiki/страница HTTP/1.1\n" +
                "Host: ru.wikipedia.org\n" +
                "User-Agent: Mozilla/5.0 (X11; U; Linux i686; ru; rv:1.9b5) Gecko/2008050509 Firefox/3.0b5\n" +
                "Accept: text/html\n" +
                "Connection: close\n" + "";
        File file = new File("test.txt");
        file.createNewFile();
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));) {
            bufferedWriter.write(line);
        }

        String line2 = "Host: ru.wikipedia.org\n" +
                "User-Agent: Mozilla/5.0 (X11; U; Linux i686; ru; rv:1.9b5) Gecko/2008050509 Firefox/3.0b5\n" +
                "Accept: text/html\n" +
                "Connection: close\n" + "";
        File file2 = new File("test2.txt");
        file2.createNewFile();
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file2));) {
            bufferedWriter.write(line2);
        }



        File emptyFile = new File("testEmpty.txt");
        emptyFile.createNewFile();
    }

    @AfterAll
    static void after(){
        File file = new File("test.txt");
        file.delete();

        File file2 = new File("test2.txt");
        file2.delete();

        File emptyFile = new File("testEmpty.txt");
        emptyFile.delete();
    }


    @Test
    public void testInjectUriAndMethod() {
        String line = "GET /wiki/страница HTTP/1.1\n" +
                "Host: ru.wikipedia.org\n" +
                "User-Agent: Mozilla/5.0 (X11; U; Linux i686; ru; rv:1.9b5) Gecko/2008050509 Firefox/3.0b5\n" +
                "Accept: text/html\n" +
                "Connection: close\n" + "";
        requestParser.injectUriAndMethod(line, request);
        String actualMethod = String.valueOf(request.method);
        String actualUri = request.uri;

        String expectedMethod = "GET";
        String expectedUri = "/wiki/страница";
        assertEquals(expectedUri, actualUri);
        assertEquals(expectedMethod, actualMethod);
    }

    @Test
    public void testInjectHeaders() throws IOException {
        File file2 = new File("test2.txt");
        assertTrue(file2.exists());
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file2));

        requestParser.injectHeaders(bufferedReader, request);

        Map<String, String> expectedHeaders = new HashMap<>();
        expectedHeaders.put("Host", "ru.wikipedia.org");
        expectedHeaders.put("User-Agent", "Mozilla/5.0 (X11; U; Linux i686; ru; rv:1.9b5) Gecko/2008050509 Firefox/3.0b5");
        expectedHeaders.put("Accept", "text/html");
        expectedHeaders.put("Connection", "close");


        String actualHostHeader = request.headers.get("Host");
        String actualUserAgentHeader = request.headers.get("User-Agent");
        String actualAcceptHeader = request.headers.get("Accept");
        String actualConnectionHeader = request.headers.get("Connection");

        assertEquals(expectedHeaders.get("Host"), actualHostHeader);
        assertEquals(expectedHeaders.get("User-Agent"), actualUserAgentHeader);
        assertEquals(expectedHeaders.get("Accept"), actualAcceptHeader);
        assertEquals(expectedHeaders.get("Connection"), actualConnectionHeader);

        assertTrue(expectedHeaders.equals(request.headers));
    }

    @Test
    public void testInjectHeadersOnEmptyRequest() throws IOException {
        File emptyFile = new File("testEmpty.txt");
        assertTrue(emptyFile.exists());
        BufferedReader bufferedReader = new BufferedReader(new FileReader(emptyFile));
        Request request = new Request();

        requestParser.injectHeaders(bufferedReader, request);
        assertEquals(null, request.headers.get("Host"));
    }

    @Test
    public void testParseMethod() throws IOException {
        File file = new File("test.txt");
        assertTrue(file.exists());
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

        Request actualRequest = requestParser.parse(bufferedReader);

        String expectedMethod = "GET";
        String expectedUri = "/wiki/страница";
        Map<String, String> expectedHeaders = new HashMap<>();
        expectedHeaders.put("Host", "ru.wikipedia.org");
        expectedHeaders.put("User-Agent", "Mozilla/5.0 (X11; U; Linux i686; ru; rv:1.9b5) Gecko/2008050509 Firefox/3.0b5");
        expectedHeaders.put("Accept", "text/html");
        expectedHeaders.put("Connection", "close");

        assertEquals(expectedHeaders, actualRequest.headers);
        assertEquals(expectedMethod, actualRequest.method.toString());
        assertEquals(expectedUri, actualRequest.uri);
    }
}