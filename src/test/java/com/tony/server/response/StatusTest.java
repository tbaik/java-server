package com.tony.server.response;

import com.tony.server.response.Status;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StatusTest {
    @Test
    public void testStatusEnumContainsStatusCode() throws Exception {
        Status okStatus = Status.OK;

        assertEquals("200",okStatus.getStatusCode());
    }

    @Test
    public void testStatusEnumContainsReasonPhrase() throws Exception {
        Status okStatus = Status.OK;

        assertEquals("OK", okStatus.getReasonPhrase());
    }
}