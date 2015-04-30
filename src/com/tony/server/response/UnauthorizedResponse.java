package com.tony.server.response;

import java.util.HashMap;

public class UnauthorizedResponse extends Response{
    public UnauthorizedResponse() {
        setStatusLine("HTTP/1.1 401 Unauthorized\n");
        setHeaders(new HashMap<>());
        setBody("Authentication required");
    }
}
