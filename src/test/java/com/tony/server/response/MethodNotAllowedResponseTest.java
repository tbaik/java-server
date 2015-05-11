package com.tony.server.response;

import com.tony.server.response.MethodNotAllowedResponse;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MethodNotAllowedResponseTest {
    @Test
    public void testResponseBuiltWithCorrectResponse() throws Exception {
        MethodNotAllowedResponse methodNotAllowedResponse = new MethodNotAllowedResponse();
        String expectedResponse = "HTTP/1.1 405 Method Not Allowed\r\n";

        assertEquals(expectedResponse, new String(methodNotAllowedResponse.respond()));
    }
}