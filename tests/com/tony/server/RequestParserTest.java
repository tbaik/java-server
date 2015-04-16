package com.tony.server;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;

import static org.junit.Assert.*;

public class RequestParserTest {
    byte out[] = ("GET /logs HTTP/1.1\n" +
            "Host: localhost:5000\n" +
            "Connection: Keep-Alive\n" +
            "User-Agent: Apache-HttpClient/4.3.5 (java 1.5)\n" +
            "Accept-Encoding: gzip,deflate\n\n" +
            "Body Text\n" +
            "Body text2").getBytes();
    ByteArrayInputStream inputStream = new ByteArrayInputStream(out);

    @Test
    public void requestReturnedByParseRequestTest() throws Exception {
        Request request = RequestParser.parseRequest(inputStream);

        assertEquals("GET", request.getHttpMethod());
        assertEquals("/logs", request.getURI());
        assertEquals("localhost:5000", request.getHeaders().get("Host"));
        assertEquals("Keep-Alive", request.getHeaders().get("Connection"));
        assertEquals("Body Text\nBody text2\n", request.getBody());
    }

    @Test
    public void parsesHTTPMethodTest() throws Exception {
        Request request = new Request();
        RequestParser.parseFirstLine(request, "GET /logs HTTP/1.1\n");
        assertEquals("GET", request.getHttpMethod());
    }

    @Test
    public void parsesURITest() throws Exception {
        Request request = new Request();
        RequestParser.parseFirstLine(request, "GET /logs HTTP/1.1\n");
        assertEquals("/logs", request.getURI());
    }

    @Test
    public void parsesHeaderTest() throws Exception {
        String headers = "Host: localhost:5000\n" +
            "Connection: Keep-Alive\n" +
            "User-Agent: Apache-HttpClient/4.3.5 (java 1.5)\n" +
            "Accept-Encoding: gzip,deflate";
        Request request = new Request();
        RequestParser.parseHeaders(request, headers);
        assertEquals("localhost:5000" ,request.getHeaders().get("Host"));
    }

    @Test
    public void grabAllHeadersTest() throws Exception {
       String headers = "Host: localhost:5000\n" +
            "Connection: Keep-Alive\n" +
            "User-Agent: Apache-HttpClient/4.3.5 (java 1.5)\n" +
            "Accept-Encoding: gzip,deflate";
       BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
       reader.readLine();
       int header = 0;
       assertEquals(headers, RequestParser.splitHeadersAndBody(reader)[header]);
    }

    @Test
    public void grabHeadersWhenNoBodyTest() throws Exception {
        byte out2[] = ("GET /logs HTTP/1.1\n" +
                "Host: localhost:5000\n" +
                "Connection: Keep-Alive\n" +
                "User-Agent: Apache-HttpClient/4.3.5 (java 1.5)\n" +
                "Accept-Encoding: gzip,deflate\n").getBytes();
        ByteArrayInputStream inputStream2 = new ByteArrayInputStream(out2);

        String headers = "Host: localhost:5000\n" +
                "Connection: Keep-Alive\n" +
                "User-Agent: Apache-HttpClient/4.3.5 (java 1.5)\n" +
                "Accept-Encoding: gzip,deflate\n";
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream2));
        reader.readLine();
        int header = 0;
        assertEquals(headers, RequestParser.splitHeadersAndBody(reader)[header]);
    }

    @Test
    public void grabBodyTest() throws Exception {
        String bodyText = "Body Text\n" +
                "Body text2\n";
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        reader.readLine();
        int body = 1;
        assertEquals(bodyText, RequestParser.splitHeadersAndBody(reader)[body]);
    }
}