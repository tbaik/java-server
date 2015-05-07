package com.tony.server.response;

public class PatchResponse extends PutPostResponse{
    public PatchResponse(String filePath) {
        super(filePath);
        setStatus(Status.NO_CONTENT);
    }

    public String getFilePath(){
        return filePath;
    }
}
