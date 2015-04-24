package com.tony.server.response;

import java.util.HashMap;

public class GetResponse extends Response {
    public GetResponse() {
        setStatusLine("HTTP/1.1 200 OK\n");
        setHeaders(new HashMap<>());
        setBody("");
    }
}
