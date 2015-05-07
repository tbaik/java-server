package com.tony.server.response;

public class FourOhFourResponse extends Response{
    public FourOhFourResponse() {
        setStatus(Status.NOT_FOUND);
    }
}
