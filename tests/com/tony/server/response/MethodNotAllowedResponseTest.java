package com.tony.server.response;

import org.junit.Test;

import static org.junit.Assert.*;

public class MethodNotAllowedResponseTest {
    @Test
    public void testResponseBuiltWithCorrectResponse() throws Exception {
        MethodNotAllowedResponse methodNotAllowedResponse = new MethodNotAllowedResponse();
        String expectedResponse = "HTTP/1.1 405 Method Not Allowed\n";

        assertEquals(expectedResponse, methodNotAllowedResponse.respond());
    }
}