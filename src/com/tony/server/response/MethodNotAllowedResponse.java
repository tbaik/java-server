package com.tony.server.response;

import java.util.HashMap;

public class MethodNotAllowedResponse extends Response{
    public MethodNotAllowedResponse() {
        setStatusLine("HTTP/1.1 405 Method Not Allowed\n");
        setHeaders(new HashMap<>());
        setBody("");
    }
}
