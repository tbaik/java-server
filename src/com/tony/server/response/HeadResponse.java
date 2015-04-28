package com.tony.server.response;

import java.util.HashMap;

public class HeadResponse extends Response{
    public HeadResponse() {
        setStatusLine("HTTP/1.1 200 OK\n");
        setHeaders(new HashMap<>());
        setBody("");
    }
}
