package com.tony.server.response;

import java.util.HashMap;

public class RedirectResponse extends Response{
    public RedirectResponse() {
        setStatusLine("HTTP/1.1 302 Found\n");
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Location", "http://localhost:5000/");
        setHeaders(headers);
    }
}
