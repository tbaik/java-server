package com.tony.server.response;

public class PreconditionFailedResponse extends Response{
    public PreconditionFailedResponse() {
        setStatus(Status.PRECONDITION_FAILED);
    }
}
