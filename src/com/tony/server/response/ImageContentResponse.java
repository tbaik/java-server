package com.tony.server.response;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

public class ImageContentResponse extends Response{

    private final String filePath;

    public byte[] respond(){
        try {
            byte[] imageBytes = Files.readAllBytes(Paths.get(filePath));
            return ResponseBuilder.buildImageResponse(getStatusLine(), getHeaders(), imageBytes);
        } catch (IOException e) {
            return new InternalErrorResponse(e.toString()).respond();
        }
    }

    public ImageContentResponse(String filePath) {
        this.filePath = filePath;
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", imageType(filePath));

        setStatusLine("HTTP/1.1 200 OK\n");
        setHeaders(headers);
    }

    public static String imageType(String filePath) {
        String[] splitString = filePath.split("\\.");
        return "image/" + splitString[splitString.length-1];
    }
}
