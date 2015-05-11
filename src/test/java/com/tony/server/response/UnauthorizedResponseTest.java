package com.tony.server.response;

import com.tony.server.response.UnauthorizedResponse;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UnauthorizedResponseTest {
    @Test
    public void testResponseContains401AndAuthenticationRequiredBody() throws Exception {
        UnauthorizedResponse response = new UnauthorizedResponse();
        String expectedResponse = "HTTP/1.1 401 Unauthorized\r\n" +
                "\r\n" +
                "Authentication required";

        assertEquals(expectedResponse, new String(response.respond()));
    }
}