package com.tony.server.response;

import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class ResponseBuilderTest {
    @Test
    public void testBuildResponse() throws Exception {
        String correctResponse = "HTTP/1.1 200 OK\r\n" +
                "Content-Length: length\r\n" +
                "Content-Type: text/xml; charset=utf-8\r\n" +
                "\r\n" +
                "  <EnlightenResponse xmlns=\"http://clearforest.com/\">\r\n" +
                "  <EnlightenResult>string</EnlightenResult>\r\n" +
                "  </EnlightenResponse>";
        Status status = Status.OK;
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Length", "length");
        headers.put("Content-Type", "text/xml; charset=utf-8");
        String body = "  <EnlightenResponse xmlns=\"http://clearforest.com/\">\r\n" +
                "  <EnlightenResult>string</EnlightenResult>\r\n" +
                "  </EnlightenResponse>";

        assertEquals(correctResponse,
                ResponseBuilder.buildResponse(status, headers, body));

    }

    @Test
    public void testBuildImageResponse() throws Exception {
        String expectedResponse = "HTTP/1.1 200 OK\r\n" +
                "Content-Type: image/jpeg\r\n\r\n" +
                "Appended imageBytes";
        Status status = Status.OK;
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "image/jpeg");

        byte[] imageBytes = "Appended imageBytes".getBytes();

        assertEquals(expectedResponse, new String(ResponseBuilder.buildImageResponse(status, headers, imageBytes)));
    }

    @Test
    public void testBuildStatusLine() throws Exception {
        Status okStatus = Status.OK;
        String expectedStatusLine = "HTTP/1.1 200 OK\r\n";

        assertEquals(expectedStatusLine, ResponseBuilder.buildStatusLine(okStatus));
    }

    @Test
    public void testBuildHeaderString() throws Exception {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Length", "length");
        headers.put("Content-Type", "text/xml; charset=utf-8");

        String correctHeaderString = "Content-Length: length\r\n" +
                "Content-Type: text/xml; charset=utf-8\r\n";

        assertEquals(correctHeaderString, ResponseBuilder.buildHeaderString(headers));
    }

    @Test
    public void testBuildHeaderLine() throws Exception {
        assertEquals("Content-Type: text/xml\r\n", ResponseBuilder.buildHeaderLine("Content-Type", "text/xml"));
    }

    @Test
    public void testBuildBodyWhenBodyNull() throws Exception {
        assertEquals("", ResponseBuilder.buildBody(null));
    }

    @Test
    public void testBuildBodyWhenHasBody() throws Exception {
        assertEquals("\r\nblah", ResponseBuilder.buildBody("blah"));
    }
}