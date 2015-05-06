package com.tony.server.response;

import java.io.File;

public class DeleteResponse extends Response{
    private final String filePath;

    public DeleteResponse(String filePath) {
        this.filePath = filePath;
        setStatus(Status.OK);
    }

    public byte[] respond(){
        new File(filePath).delete();
        return ResponseBuilder.buildResponse(getStatus(),
                getHeaders(), getBody()).getBytes();
    }
}
