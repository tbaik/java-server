package com.tony.server.response;

import com.tony.server.response.InternalErrorResponse;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class InternalErrorResponseTest {
    @Test
    public void testRespondsWithGivenErrorInBody() throws Exception {
        InternalErrorResponse response = new InternalErrorResponse("This is the Error!");
        String expectedResponse = "HTTP/1.1 500 Internal Server Error\r\n" +
                "\r\n" +
                "This is the Error!";

        assertEquals(expectedResponse, new String(response.respond()));
    }
}