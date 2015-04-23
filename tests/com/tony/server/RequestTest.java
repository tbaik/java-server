package com.tony.server;

import org.junit.Test;

import static org.junit.Assert.*;

public class RequestTest {

    @Test
    public void testOverrideEqualsOnHTTPMethodAndURI() throws Exception {
        Request getRequest = new Request();
        getRequest.setHttpMethod("GET");
        getRequest.setUri("/blah");

        Request getRequest2 = new Request();
        getRequest2.setHttpMethod("GET");
        getRequest2.setUri("/blah");

        Request postRequest = new Request();
        postRequest.setHttpMethod("POST");
        postRequest.setUri("/blah");

        assertEquals(getRequest, getRequest2);
        assertNotEquals(getRequest, postRequest);
    }

    @Test
    public void testOverrideHashCodeOnHTTPMethodAndURI() throws Exception {
        Request getRequest = new Request();
        getRequest.setHttpMethod("GET");
        getRequest.setUri("/blah");

        Request getRequest2 = new Request();
        getRequest2.setHttpMethod("GET");
        getRequest2.setUri("/blah");

        Request postRequest = new Request();
        postRequest.setHttpMethod("POST");
        postRequest.setUri("/blah");

        assertEquals(getRequest.hashCode(), getRequest2.hashCode());
        assertNotEquals(getRequest.hashCode(), postRequest.hashCode());
    }

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