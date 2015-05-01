package com.tony.server.response;

public class PatchResponse extends PutPostResponse{
    private final String filePath;

    public PatchResponse(String filePath) {
        super(filePath);
        this.filePath = filePath;
        setStatus(Status.NO_CONTENT);
    }

    public String getFilePath(){
        return filePath;
    }
}
