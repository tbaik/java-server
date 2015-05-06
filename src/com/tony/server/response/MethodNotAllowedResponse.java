package com.tony.server.response;

public class MethodNotAllowedResponse extends Response{
    public MethodNotAllowedResponse() {
        setStatus(Status.METHODNOTALLOWED);
    }
}
