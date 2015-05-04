package com.tony.server.response;

import java.util.HashMap;

public class OptionsResponse extends Response {
    public OptionsResponse(String allowedMethods, String uri) {
        setStatusLine("HTTP/1.1 200 OK\n");
        setHeaders(createOptionsHeader(allowedMethods, uri));
        setBody("");
    }

    public HashMap createOptionsHeader(String allowedMethods, String uri) {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Allow", allowedMethods);
        return headers;
    }
}
