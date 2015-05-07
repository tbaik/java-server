package com.tony.server.response;

import com.tony.server.Logger;

public class LogResponse extends Response{
    private final Logger logger;

    public byte[] respond(){
        setBody(logger.getLog());
        return ResponseBuilder.buildResponse(getStatus(),
                getHeaders(), getBody()).getBytes();
    } 
    
    public LogResponse(Logger logger) {
        this.logger = logger;
        setStatus(Status.OK);
    }
}
