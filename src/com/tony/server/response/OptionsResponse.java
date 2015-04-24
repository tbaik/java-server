package com.tony.server.response;

import com.tony.server.Router;

import java.util.HashMap;

public class OptionsResponse extends Response {
    public OptionsResponse(Router router, String uri) {
        setStatusLine("HTTP/1.1 200 OK\n");
        setHeaders(createOptionsHeader(router, uri));
        setBody("");
    }

    public HashMap createOptionsHeader(Router router, String uri) {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Allow", router.allowedMethodsForURI(uri));
        return headers;
    }
}
