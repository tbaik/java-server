package com.tony.server.response;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;

public class FileContentResponse extends Response{
    private final String filePath;

    public String respond(){
        try {
            if(new File(filePath).isFile()){
                setBody(new String(Files.readAllBytes(new File(filePath).toPath())));
            } else {
                setBody("");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseBuilder.buildResponse(getStatusLine(),
                getHeaders(), getBody());
    }

    public FileContentResponse(String filePath) {
        this.filePath = filePath;
        setStatusLine("HTTP/1.1 200 OK\n");
        setHeaders(new HashMap<>());
    }
}
