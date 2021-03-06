package com.tony.server.response;

import java.util.HashMap;

public class OptionsResponse extends Response {
    public OptionsResponse(String allowedMethods, String uri) {
        setStatus(Status.OK);
        setHeaders(createOptionsHeader(allowedMethods, uri));
    }

    public HashMap createOptionsHeader(String allowedMethods, String uri) {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Allow", allowedMethods);
        return headers;
    }
}
