package com.tony.server.response;

import java.util.HashMap;

public class InternalErrorResponse extends Response{
    public InternalErrorResponse(String errorMessage) {
        setStatusLine("HTTP/1.1 500 Internal Server Error\n");
        setHeaders(new HashMap<>());
        setBody(errorMessage);
    }
}
