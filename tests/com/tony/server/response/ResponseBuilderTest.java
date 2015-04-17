package com.tony.server.response;

import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class ResponseBuilderTest {
    @Test
    public void testBuildResponse() throws Exception {
        String correctResponse = "HTTP/1.1 200 OK\n" +
                "Content-Length: length\n" +
                "Content-Type: text/xml; charset=utf-8\n" +
                "\n" +
                "  <EnlightenResponse xmlns=\"http://clearforest.com/\">\n" +
                "  <EnlightenResult>string</EnlightenResult>\n" +
                "  </EnlightenResponse>";
        String statusLine = "HTTP/1.1 200 OK\n";
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Length", "length");
        headers.put("Content-Type", "text/xml; charset=utf-8");
        String body = "  <EnlightenResponse xmlns=\"http://clearforest.com/\">\n" +
                "  <EnlightenResult>string</EnlightenResult>\n" +
                "  </EnlightenResponse>";

        assertEquals(correctResponse,
                ResponseBuilder.buildResponse(statusLine, headers, body));

    }

    @Test
    public void testBuildHeaderString() throws Exception {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Length", "length");
        headers.put("Content-Type", "text/xml; charset=utf-8");

        String correctHeaderString = "Content-Length: length\n" +
                "Content-Type: text/xml; charset=utf-8\n";

        assertEquals(correctHeaderString, ResponseBuilder.buildHeaderString(headers));
    }

    @Test
    public void testBuildHeaderLine() throws Exception {
        assertEquals("Content-Type: text/xml\n", ResponseBuilder.buildHeaderLine("Content-Type", "text/xml"));
    }

    @Test
    public void testBuildBodyWhenBodyNull() throws Exception {
        assertEquals("", ResponseBuilder.buildBody(null));
    }

    @Test
    public void testBuildBodyWhenHasBody() throws Exception {
        assertEquals("\nblah", ResponseBuilder.buildBody("blah"));

    }
}