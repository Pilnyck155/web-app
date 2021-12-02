package com.pilnyck.appserver.request;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class RequestTest {

    @BeforeAll
    static void before() throws IOException {
        String line = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "  <link rel=\"stylesheet\" href=\"styles.css\">\n" +
                "</head>\n" +
                "<body>\n" +
                "\n" +
                "<h1>This is a heading</h1>\n" +
                "\n" +
                "</body>\n" +
                "</html>";
        File dir = new File("src/test/java/resources");
        dir.mkdir();
        File file = new File(dir, "test1.txt");
        file.createNewFile();
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));) {
            bufferedWriter.write(line);
        }
        File emptyFile = new File(dir, "testEmpty.txt");
        emptyFile.createNewFile();
    }

    @AfterAll
    static void after() {
        File dir = new File("src/test/java/resources");

        File file2 = new File("src/test/java/resources", "test2.txt");
        file2.delete();

        File emptyFile = new File(dir, "testEmpty.txt");
        emptyFile.delete();

        File file = new File(dir, "test1.txt");
        file.delete();

        dir.delete();
    }

    @DisplayName("read content from file in successfully")
    @Test
    public void testReadContentFromFileMethod() throws IOException {
        //ResponseWriter responseWriter = new ResponseWriter();
        String setWebAppPath = "src/test/java/resources/";
        //File file = new File(setWebAppPath)
        ResourceReader resourceReader = new ResourceReader(setWebAppPath);
        //String actual = resourceReader.readResources("test1.txt");
        String actual = resourceReader.readResources("test1.txt");
        String expected = "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "  <link rel=\"stylesheet\" href=\"styles.css\">" +
                "</head>" +
                "<body>" +
                "<h1>This is a heading</h1>" +
                "</body>" +
                "</html>";
        assertEquals(expected, actual);
    }

    @DisplayName("write response content successfully")
    @Test
    public void testWriteResponseMethod() throws IOException {
        ResponseWriter responseWriter = new ResponseWriter();

        File file2 = new File("src/test/java/resources", "test2.txt");
        file2.createNewFile();
        //File file2 = new File("src/test/java/resources", "test2.txt");

        String content = "<!DOCTYPE html>" +
                "<html lang=\"en\">" +
                "<head>" +
                "    <meta charset=\"UTF-8\">" +
                "    <link rel=\"stylesheet\" href=\"css/style.css\">" +
                "    <title>Title</title>" +
                "</head>" +
                "<body>" +
                "    <h1>Hello, web!</h1>" +
                "    <p>My first paragraph.</p>" +
                "    <h2>Hello, sam!</h2>" +
                "    <p>My second paragraph.</p>" +
                "    <h3>Hello, band!</h3>" +
                "    <p>My third paragraph.</p>" +
                "</body>" +
                "</html>";
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file2));) {
            responseWriter.writeResponse(content, bufferedWriter);
        }
        String expected = "HTTP/1.1 200 OK" + "<!DOCTYPE html>" +
                "<html lang=\"en\">" +
                "<head>" +
                "    <meta charset=\"UTF-8\">" +
                "    <link rel=\"stylesheet\" href=\"css/style.css\">" +
                "    <title>Title</title>" +
                "</head>" +
                "<body>" +
                "    <h1>Hello, web!</h1>" +
                "    <p>My first paragraph.</p>" +
                "    <h2>Hello, sam!</h2>" +
                "    <p>My second paragraph.</p>" +
                "    <h3>Hello, band!</h3>" +
                "    <p>My third paragraph.</p>" +
                "</body>" +
                "</html>";
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file2));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line);
        }
        String actual = stringBuilder.toString();
        assertEquals(expected, actual);
    }
}