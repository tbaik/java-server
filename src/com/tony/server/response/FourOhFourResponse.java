package com.tony.server.response;

import java.util.HashMap;

public class FourOhFourResponse extends Response{
    public FourOhFourResponse() {
        setStatusLine("HTTP/1.1 404 Not Found\n");
        setHeaders(new HashMap<>());
        setBody("");
    }
}
