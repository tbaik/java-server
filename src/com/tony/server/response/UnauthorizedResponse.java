package com.tony.server.response;

public class UnauthorizedResponse extends Response{
    public UnauthorizedResponse() {
        setStatus(Status.UNAUTHORIZED);
        setBody("Authentication required");
    }
}
