package com.tony.server.response;

import org.junit.Test;

import static org.junit.Assert.*;

public class HeadResponseTest {
    @Test
    public void testResponseBuiltWithCorrectResponse() throws Exception {
        HeadResponse headResponse = new HeadResponse();
        String expectedResponse = "HTTP/1.1 200 OK\n";

        assertEquals(expectedResponse, new String(headResponse.respond()));
    }
}