package com.tony.server.response;

import com.tony.server.Logger;

import java.util.HashMap;

public class LogResponse extends Response{
    private final Logger logger;

    public byte[] respond(){
        setBody(logger.getLog());
        return ResponseBuilder.buildResponse(getStatusLine(),
                getHeaders(), getBody()).getBytes();
    } 
    
    public LogResponse(Logger logger) {
        this.logger = logger;
        setStatusLine("HTTP/1.1 200 OK");
        setHeaders(new HashMap<>());
    }
}
