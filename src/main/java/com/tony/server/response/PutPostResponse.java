package com.tony.server.response;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class PutPostResponse extends Response {
    protected final String filePath;
    private String requestBody;

    public PutPostResponse(String filePath) {
        this.filePath = filePath;
        setStatus(Status.OK);
    }

    public byte[] respond(){
        putPostContent();
        return ResponseBuilder.buildResponse(getStatus(),
                getHeaders(), getBody()).getBytes();
    }

    public void putPostContent() {
        try(PrintWriter writer = new PrintWriter(filePath, "UTF-8")) {
            writer.print(requestBody);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void setRequestBody(String requestBody){
       this.requestBody = requestBody;
    }

    public String getRequestBody() {
        return requestBody;
    }

}
