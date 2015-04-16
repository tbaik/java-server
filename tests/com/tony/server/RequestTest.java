package com.tony.server;

import org.junit.Test;

import static org.junit.Assert.*;

public class RequestTest {

    @Test
    public void testGettersAndSetters() throws Exception {
        Request request = new Request();

        request.setHttpMethod("GET");
        request.setUri("/logs");
        request.setBody("Stuff\nHere\n");

        assertEquals("GET", request.getHttpMethod());
        assertEquals("/logs", request.getURI());
        assertEquals("Stuff\nHere\n", request.getBody());

    }

    @Test
    public void testAddToHeaders() throws Exception {
        Request request = new Request();

        request.addToHeaders("Allow", "blahblah");

        assertEquals("blahblah", request.getHeaders().get("Allow"));

    }
}