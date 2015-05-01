package com.tony.server.response;

import java.util.HashMap;

public class PreconditionFailedResponse extends Response{
    public PreconditionFailedResponse() {
        setStatus(Status.PRECONDITION_FAILED);
        setHeaders(new HashMap<>());
        setBody("");
    }
}
