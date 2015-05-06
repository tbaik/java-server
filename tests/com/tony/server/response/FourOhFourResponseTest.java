package com.tony.server.response;

import org.junit.Test;

import static org.junit.Assert.*;

public class FourOhFourResponseTest {
    @Test
    public void testResponseBuiltWithCorrectResponse() throws Exception {
        FourOhFourResponse fourOhFourResponse = new FourOhFourResponse();
        String expectedResponse = "HTTP/1.1 404 Not Found\r\n";

        assertEquals(expectedResponse, new String(fourOhFourResponse.respond()));
    }
}