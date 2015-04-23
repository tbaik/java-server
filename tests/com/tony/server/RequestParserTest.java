package com.tony.server;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;

import static org.junit.Assert.*;

public class RequestParserTest {
    byte input[] = ("GET /logs HTTP/1.1\n" +
            "Host: localhost:5000\n" +
            "Connection: Keep-Alive\n" +
            "User-Agent: Apache-HttpClient/4.3.5 (java 1.5)\n" +
            "Accept-Encoding: gzip,deflate\n\n" +
            "Body Text\n" +
            "Body text2").getBytes();
    ByteArrayInputStream inputStream = new ByteArrayInputStream(input);

    @Test
    public void testRequestReturnedByParseRequest() throws Exception {
        Request request = RequestParser.parseRequest(inputStream);

        assertEquals("GET", request.getHttpMethod());
        assertEquals("/logs", request.getURI());
        assertEquals("localhost:5000", request.getHeaders().get("Host"));
        assertEquals("Keep-Alive", request.getHeaders().get("Connection"));
        assertEquals("Body Text\nBody text2\n", request.getBody());
    }

    @Test
    public void testParseFirstLineParsesHTTPMethod() throws Exception {
        Request request = new Request();
        RequestParser.parseFirstLine(request, "GET /logs HTTP/1.1\n");

        assertEquals("GET", request.getHttpMethod());
    }

    @Test
    public void testParseFirstLineparsesURI() throws Exception {
        Request request = new Request();
        RequestParser.parseFirstLine(request, "GET /logs HTTP/1.1\n");

        assertEquals("/logs", request.getURI());
    }

    @Test
    public void testParseHeaders() throws Exception {
        String headers = "Host: localhost:5000\n" +
            "Connection: Keep-Alive\n" +
            "User-Agent: Apache-HttpClient/4.3.5 (java 1.5)\n" +
            "Accept-Encoding: gzip,deflate";
        Request request = new Request();
        RequestParser.parseHeaders(request, headers);

        assertEquals("localhost:5000" ,request.getHeaders().get("Host"));
    }

    @Test
    public void testItGrabAllHeaders() throws Exception {
       String headers = "Host: localhost:5000\n" +
            "Connection: Keep-Alive\n" +
            "User-Agent: Apache-HttpClient/4.3.5 (java 1.5)\n" +
            "Accept-Encoding: gzip,deflate\n\n";
       BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
       reader.readLine();

       assertEquals(headers, RequestParser.grabHeaders(reader));
    }

    @Test
    public void testItGrabsAllHeadersButNoBodyWhenNoBody() throws Exception {
        byte input2[] = ("GET /logs HTTP/1.1\n" +
                "Host: localhost:5000\n" +
                "Connection: Keep-Alive\n" +
                "User-Agent: Apache-HttpClient/4.3.5 (java 1.5)\n" +
                "Accept-Encoding: gzip,deflate\n").getBytes();
        ByteArrayInputStream inputStream2 = new ByteArrayInputStream(input2);

        String headers = "Host: localhost:5000\n" +
                "Connection: Keep-Alive\n" +
                "User-Agent: Apache-HttpClient/4.3.5 (java 1.5)\n" +
                "Accept-Encoding: gzip,deflate\n";
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream2));
        reader.readLine();

        assertEquals(headers, RequestParser.grabHeaders(reader));
        assertEquals("", RequestParser.grabBody(reader, 0));
    }

    @Test
    public void testItGrabBodyWhenThereIsABody() throws Exception {
        String bodyText = "Body Text\n" +
                "Body text2";
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        reader.readLine();
        RequestParser.grabHeaders(reader);

        assertEquals(bodyText, RequestParser.grabBody(reader, 20));
    }
}