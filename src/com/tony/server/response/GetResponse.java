package com.tony.server.response;

import java.util.HashMap;

public class GetResponse extends Response {

    private HashMap<String, String> headers = new HashMap<>();
    private String body = null;

    @Override
    public String getStatusLine() {
        return "HTTP/1.1 200 OK\n";
    }

    @Override
    public HashMap<String, String> getHeaders() {
        return headers;
    }

    @Override
    public String getBody() {
        return body;
    }
}
