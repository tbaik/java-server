package com.tony.server.response;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileContentResponse extends Response{
    private final String filePath;

    public byte[] respond(){
        try {
            File file = new File(filePath);
            if(file.isFile()){
                setBody(new String(Files.readAllBytes(file.toPath())));
            } else {
                setBody("");
            }
        } catch (IOException e) {
            return new InternalErrorResponse(e.toString()).respond();
        }
        return ResponseBuilder.buildResponse(getStatus(),
                getHeaders(), getBody()).getBytes();
    }

    public FileContentResponse(String filePath) {
        this.filePath = filePath;
        setStatus(Status.OK);
    }
}
