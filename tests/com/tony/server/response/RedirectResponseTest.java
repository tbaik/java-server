package com.tony.server.response;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class RedirectResponseTest {
    @Test
    public void testResponseReturnsStatus302() throws Exception {
        RedirectResponse redirectResponse = new RedirectResponse();
        String response = new String(redirectResponse.respond());
        String responseLines[] = response.split("\n");

        int statusLine = 0;
        assertEquals("HTTP/1.1 302 Found", responseLines[statusLine]);
    }

    @Test
    public void testLocationContent() throws Exception {
        RedirectResponse redirectResponse = new RedirectResponse();
        String response = new String(redirectResponse.respond());
        String responseLines[] = response.split("\n");
        int headerLine = 1;

        assertEquals("Location: http://localhost:5000/", responseLines[headerLine]);

    }
}