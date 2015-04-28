package com.tony.server.response;

import java.util.HashMap;

public class GetResponse extends Response {
    private final String filePath;

    public GetResponse(String filePath) {
        this.filePath = filePath;
        setStatusLine("HTTP/1.1 200 OK\n");
        setHeaders(new HashMap<>());
        setBody("");
    }

   public String respond(){

      return ResponseBuilder.buildResponse(getStatusLine(),
              getHeaders(), getBody());
   }
}
