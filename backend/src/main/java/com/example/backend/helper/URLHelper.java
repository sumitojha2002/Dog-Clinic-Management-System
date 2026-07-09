package com.example.backend.helper;

import java.net.URI;

public class URLHelper {
    public static String CloudinaryUrlSlicerPublicId(String url){
        try{
            URI uri = new URI(url);
            String path = uri.getPath();
            String[] segments = path.split("/");
            String result = segments[segments.length-1];
            String[] returnResult = result.split("\\.");
            return returnResult[0];
        }catch(Exception e){
            e.printStackTrace();
           return "failed";
        }
    }

        public static String CloudinaryUrlSlicerFormat(String url){
        try{
            URI uri = new URI(url);
            String path = uri.getPath();
            String[] segments = path.split("/");
            String result = segments[segments.length-1];
            String[] returnResult = result.split("\\.");
            return returnResult[returnResult.length-1];
        }catch(Exception e){
            e.printStackTrace();
           return "failed";
        }
    }
}
