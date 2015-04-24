package com.tony.server.response;

import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class ResponseTest {

    @Test
    public void testRespondBuildsResponseForExtendingClass() throws Exception {
        MockResponse mockResponse = new MockResponse();
        String expectedResponse = "HTTP/1.1 200 OK\n" +
                "Content-Length: 4\n" +
                "Content-Type: Content-Type: text/xml; charset=utf-8\n" +
                "\n" +
                "body";
        assertEquals(expectedResponse, mockResponse.respond());
    }

    private class MockResponse extends Response{

        @Override
        public String getStatusLine() {
            return "HTTP/1.1 200 OK\n";
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