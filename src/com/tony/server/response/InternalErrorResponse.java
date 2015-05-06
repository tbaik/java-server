package com.tony.server.response;

public class InternalErrorResponse extends Response{
    public InternalErrorResponse(String errorMessage) {
        setStatus(Status.INTERNALERROR);
        setBody(errorMessage);
    }
}
