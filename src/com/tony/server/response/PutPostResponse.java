package com.tony.server.response;

import java.util.HashMap;

public class PutPostResponse extends Response {
    public PutPostResponse() {
        setStatusLine("HTTP/1.1 200 OK\n");
        setHeaders(new HashMap<>());
        setBody("");
    }
}
