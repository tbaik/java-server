package com.tony.server.response;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;

public class FileContentResponse extends Response{
    private final String filePath;

    public byte[] respond(){
        try {
            File file = new File(filePath);
            if(file.isFile()){
                setBody(new String(Files.readAllBytes(file.toPath())));
            } else {
                return new InternalErrorResponse("No Such File!").respond();
            }
        } catch (IOException e) {
            return new InternalErrorResponse(e.toString()).respond();
        }
        return ResponseBuilder.buildResponse(getStatusLine(),
                getHeaders(), getBody()).getBytes();
    }

    public FileContentResponse(String filePath) {
        this.filePath = filePath;
        setStatusLine("HTTP/1.1 200 OK\n");
        setHeaders(new HashMap<>());
    }
}
