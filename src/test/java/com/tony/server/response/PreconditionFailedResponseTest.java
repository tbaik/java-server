package com.tony.server.response;

import com.tony.server.response.PreconditionFailedResponse;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PreconditionFailedResponseTest {
    @Test
    public void testResponseContains412Status() throws Exception {
        PreconditionFailedResponse response = new PreconditionFailedResponse();

        assertEquals("HTTP/1.1 412 Precondition Failed\r\n", new String(response.respond()));
    }
}