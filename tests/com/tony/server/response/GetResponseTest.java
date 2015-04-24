package com.tony.server.response;

import org.junit.Test;

import static org.junit.Assert.*;

public class GetResponseTest {
    @Test
    public void testResponseBuiltWithCorrectResponse() throws Exception {
        GetResponse getResponse = new GetResponse();
        String expectedResponse = "HTTP/1.1 200 OK\n";

        assertEquals(expectedResponse, getResponse.respond());
    }
}