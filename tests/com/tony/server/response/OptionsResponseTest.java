package com.tony.server.response;

import com.tony.server.Request;
import com.tony.server.Router;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class OptionsResponseTest {
    @Test
    public void testResponseBuiltWithCorrectResponse() throws Exception {
        Router router = new Router();
        router.addRoute(new Request("GET", "/method_options"), new FileContentResponse("/method_options"));
        router.addRoute(new Request("HEAD", "/method_options"), new HeadResponse());
        router.addRoute(new Request("POST", "/method_options"), new PutPostResponse("path/method_options"));
        router.addRoute(new Request("PUT", "/method_options"), new PutPostResponse("path/method_options"));

        OptionsResponse optionsResponse = new OptionsResponse(router.allowedMethodsForURI("/method_options"), "/method_options");
        String expectedResponse = "HTTP/1.1 200 OK\r\n" +
                "Allow: GET,POST,HEAD,PUT,OPTIONS\r\n";

        assertEquals(expectedResponse, new String(optionsResponse.respond()));
    }

    @Test
    public void testCreateOptionsHeaderPutsAllowedMethodsInAllow() throws Exception {
        Router router = new Router();
        router.addRoute(new Request("GET", "/method_options"), new FileContentResponse("/method_options"));
        router.addRoute(new Request("HEAD", "/method_options"), new HeadResponse());
        router.addRoute(new Request("POST", "/method_options"), new PutPostResponse("path/method_options"));
        router.addRoute(new Request("PUT", "/method_options"), new PutPostResponse("path/method_options"));

        OptionsResponse optionsResponse = new OptionsResponse(router.allowedMethodsForURI("/method_options"), "/method_options");
        HashMap headers = optionsResponse.createOptionsHeader(router.allowedMethodsForURI("/method_options"), "/method_options");

        assertEquals("GET,POST,HEAD,PUT,OPTIONS", headers.get("Allow"));
    }
}