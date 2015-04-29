package com.tony.server.response;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

public class PutPostResponse extends Response {
    private final String filePath;
    private String requestBody;

    public PutPostResponse(String filePath) {
        this.filePath = filePath;
        setStatusLine("HTTP/1.1 200 OK\n");
        setHeaders(new HashMap<>());
        setBody("");
    }

    public byte[] respond(){
        putPostContent();
        return ResponseBuilder.buildResponse(getStatusLine(),
                getHeaders(), getBody()).getBytes();
    }

    public void putPostContent() {
        try {
            PrintWriter writer = new PrintWriter(filePath, "UTF-8");
            writer.print(requestBody);
            writer.close();
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
