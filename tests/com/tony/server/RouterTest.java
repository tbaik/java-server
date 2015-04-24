package com.tony.server;

import com.tony.server.response.GetResponse;
import com.tony.server.response.OptionsResponse;
import com.tony.server.response.PutPostResponse;
import com.tony.server.response.Response;
import org.junit.Test;

import static org.junit.Assert.*;

public class RouterTest {
    @Test
    public void testRouteGivesBackCorrectResponse() throws Exception {
        Request getRequest = new Request();
        getRequest.setHttpMethod("GET");
        getRequest.setUri("/");

        Router router = new Router();
        Response getResponse = new GetResponse();
        router.addRoute(getRequest, getResponse);

        assertEquals(getResponse, router.route(getRequest));
    }

    @Test
    public void testHasRouteWithSameRequestMethodsAndSameURI() throws Exception {
        Request getRequest = new Request();
        getRequest.setHttpMethod("GET");
        getRequest.setUri("/something");

        Request getRequest2 = new Request();
        getRequest2.setHttpMethod("GET");
        getRequest2.setUri("/something");

        Router router = new Router();
        router.addRoute(getRequest, new GetResponse());

        assertTrue(router.hasRoute(getRequest2));
    }
    @Test
    public void testHasRouteWithSameRequestMethodsAndDifferentURI() throws Exception {
        Request getRequest = new Request();
        getRequest.setHttpMethod("GET");
        getRequest.setUri("/something");

        Request getRequest2 = new Request();
        getRequest2.setHttpMethod("GET");
        getRequest2.setUri("/somethingElse");

        Router router = new Router();
        router.addRoute(getRequest, new GetResponse());

        assertFalse(router.hasRoute(getRequest2));
    }
    @Test
    public void testAddRoute() throws Exception {
        Router router = new Router();
        Request getRequest = new Request();
        getRequest.setHttpMethod("GET");
        router.addRoute(getRequest, new GetResponse());

    }

    @Test
    public void testAllowedMethodsForURI() throws Exception {
        Router router = new Router();
        router.addRoute(new Request("GET", "/method_options"), new GetResponse());
        router.addRoute(new Request("HEAD", "/method_options"), new GetResponse());
        router.addRoute(new Request("POST", "/method_options"), new PutPostResponse());
        router.addRoute(new Request("PUT", "/method_options"), new PutPostResponse());
        String uri = "/method_options";
        assertEquals("GET,POST,HEAD,PUT,OPTIONS",
                router.allowedMethodsForURI(uri));
    }
}