package com.tony.server.response;

import com.tony.server.response.HeadResponse;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HeadResponseTest {
    @Test
    public void testResponseBuiltWithCorrectResponse() throws Exception {
        HeadResponse headResponse = new HeadResponse();
        String expectedResponse = "HTTP/1.1 200 OK\r\n";

        assertEquals(expectedResponse, new String(headResponse.respond()));
    }
}