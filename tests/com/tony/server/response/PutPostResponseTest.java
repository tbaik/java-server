package com.tony.server.response;

import org.junit.Test;

import static org.junit.Assert.*;

public class PutPostResponseTest {
    @Test
    public void testResponseBuiltWithCorrectResponse() throws Exception {
        PutPostResponse putPostResponse = new PutPostResponse();
        String expectedResponse = "HTTP/1.1 200 OK\n";

        assertEquals(expectedResponse, putPostResponse.respond());
    }
}