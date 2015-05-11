package com.tony.server.response;

public class InternalErrorResponse extends Response{
    public InternalErrorResponse(String errorMessage) {
        setStatus(Status.INTERNAL_ERROR);
        setBody(errorMessage);
    }
}
