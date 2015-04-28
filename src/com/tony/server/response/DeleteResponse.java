package com.tony.server.response;

import java.io.File;
import java.util.HashMap;

public class DeleteResponse extends Response{
    private final String filePath;

    public DeleteResponse(String filePath) {
        this.filePath = filePath;
        setStatusLine("HTTP/1.1 200 OK\n");
        setHeaders(new HashMap<>());
        setBody("");
    }

    public String respond(){
        new File(filePath).delete();
        return ResponseBuilder.buildResponse(getStatusLine(),
                getHeaders(), getBody());
    }
}
