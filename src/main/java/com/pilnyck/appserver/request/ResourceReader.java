package com.pilnyck.appserver.request;

import java.io.*;

public class ResourceReader {
    public static String webAppPath;
    public  static String uri;

    public ResourceReader(String webAppPath) {
        ResourceReader.webAppPath = webAppPath;
    }
    public static void setUri(String uri) {
        ResourceReader.uri = uri;
    }

     public String readResources(String uri) {
        String content = webAppPath + uri;
        File file = new File(content);
         StringBuilder stringBuilder = new StringBuilder();
        if (!file.exists()){
            return new String("File not exist");
        }
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(file));){
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            return stringBuilder.toString();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
