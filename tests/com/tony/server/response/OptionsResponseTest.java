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
        router.addRoute(new Request("GET", "/method_options"), new GetResponse("/method_options"));
        router.addRoute(new Request("HEAD", "/method_options"), new GetResponse("/method_options"));
        router.addRoute(new Request("POST", "/method_options"), new PutPostResponse("path/method_options"));
        router.addRoute(new Request("PUT", "/method_options"), new PutPostResponse("path/method_options"));

        OptionsResponse optionsResponse = new OptionsResponse(router, "/method_options");
        String expectedResponse = "HTTP/1.1 200 OK\n" +
                "Allow: GET,POST,HEAD,PUT,OPTIONS\n";

        assertEquals(expectedResponse, optionsResponse.respond());
    }

    @Test
    public void testCreateOptionsHeaderPutsAllowedMethodsInAllow() throws Exception {
        Router router = new Router();
        router.addRoute(new Request("GET", "/method_options"), new GetResponse("/method_options"));
        router.addRoute(new Request("HEAD", "/method_options"), new GetResponse("/method_options"));
        router.addRoute(new Request("POST", "/method_options"), new PutPostResponse("path/method_options"));
        router.addRoute(new Request("PUT", "/method_options"), new PutPostResponse("path/method_options"));

        OptionsResponse optionsResponse = new OptionsResponse(router, "/method_options");
        HashMap headers = optionsResponse.createOptionsHeader(router, "/method_options");

        assertEquals("GET,POST,HEAD,PUT,OPTIONS", headers.get("Allow"));
    }
}