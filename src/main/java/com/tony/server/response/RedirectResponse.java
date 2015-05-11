package com.tony.server.response;

import java.util.HashMap;

public class RedirectResponse extends Response{
    public RedirectResponse(String redirectPath) {
        setStatus(Status.FOUND);
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Location", redirectPath);
        setHeaders(headers);
    }
}
