package com.tony.server;

import com.tony.server.response.GetResponse;
import com.tony.server.response.PutPostResponse;
import com.tony.server.response.Response;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class RouterTest {
    @Test
    public void testRouteGivesBackCorrectResponse() throws Exception {
        Request getRequest = new Request("GET", "/");

        Router router = new Router();
        Response getResponse = new GetResponse("/");
        router.addRoute(getRequest, getResponse);

        assertEquals(getResponse, router.route(getRequest));
    }

    @Test
    public void testRouteReturnsNewPutPostResponseWithBodyIfPutOrPost() throws Exception {
        Request putRequest = new Request("PUT", "/form");
        putRequest.setBody("data=hello");

        Router router = new Router();
        Response putPostResponse = new PutPostResponse("filepath");
        router.addRoute(putRequest, putPostResponse);

        assertEquals("data=hello", ((PutPostResponse) router.route(putRequest)).getRequestBody());
        new File(System.getProperty("user.dir") + "/public/form").delete();
    }

    @Test
    public void testHasRouteWithSameRequestMethodsAndSameURI() throws Exception {
        Request getRequest = new Request("GET", "/something");
        Request getRequest2 = new Request("GET", "/something");

        Router router = new Router();
        router.addRoute(getRequest, new GetResponse("/something"));

        assertTrue(router.hasRoute(getRequest2));
    }
    @Test
    public void testHasRouteWithSameRequestMethodsAndDifferentURI() throws Exception {
        Request getRequest = new Request("GET", "/something");
        Request getRequest2 = new Request("GET", "/somethingElse");

        Router router = new Router();
        router.addRoute(getRequest, new GetResponse("/something"));

        assertFalse(router.hasRoute(getRequest2));
    }
    @Test
    public void testAddRoute() throws Exception {
        Router router = new Router();
        Request getRequest = new Request();
        getRequest.setHttpMethod("GET");
        router.addRoute(getRequest, new GetResponse("/something"));

    }

    @Test
    public void testAllowedMethodsForURI() throws Exception {
        Router router = new Router();
        router.addRoute(new Request("GET", "/method_options"), new GetResponse("/method_options"));
        router.addRoute(new Request("HEAD", "/method_options"), new GetResponse("/method_options"));
        router.addRoute(new Request("POST", "/method_options"), new PutPostResponse("/method_options"));
        router.addRoute(new Request("PUT", "/method_options"), new PutPostResponse("/method_options"));
        String uri = "/method_options";
        assertEquals("GET,POST,HEAD,PUT,OPTIONS",
                router.allowedMethodsForURI(uri));
    }
}