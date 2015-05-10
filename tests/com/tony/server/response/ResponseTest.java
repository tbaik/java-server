package com.tony.server.response;

import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class ResponseTest {
    @Test
    public void testRespondBuildsResponseForExtendingClass() throws Exception {
        MockResponse mockResponse = new MockResponse();
        String expectedResponse = "HTTP/1.1 200 OK\r\n" +
                "Content-Length: 4\r\n" +
                "Content-Type: Content-Type: text/xml; charset=utf-8\r\n" +
                "\r\n" +
                "body";
        assertEquals(expectedResponse, new String(mockResponse.respond()));
    }

    private class MockResponse extends Response{

        @Override
        public Status getStatus() {
            return Status.OK;
        }

        @Override
        public HashMap<String, String> getHeaders() {
            HashMap<String, String> headers = new HashMap<>();
            headers.put("Content-Type", "Content-Type: text/xml; charset=utf-8");
            headers.put("Content-Length", "4");
            return headers;
        }

        @Override
        public String getBody() {
            return "body";
        }
    }
}