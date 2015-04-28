package com.tony.server;

import com.tony.server.response.FileContentResponse;
import com.tony.server.response.HeadResponse;
import com.tony.server.response.PutPostResponse;
import com.tony.server.response.Response;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class RouterTest {
    @Test
    public void testRouteGivesBackCorrectResponse() throws Exception {
        Request fileContentRequest = new Request("GET", "/form");

        Router router = new Router();
        Response getResponse = new FileContentResponse("/form");
        router.addRoute(fileContentRequest, getResponse);

        assertEquals(getResponse, router.route(fileContentRequest));
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
        Request getFileRequest = new Request("GET", "/something");
        Request getFileRequest2 = new Request("GET", "/something");

        Router router = new Router();
        router.addRoute(getFileRequest, new FileContentResponse("/something"));

        assertTrue(router.hasRoute(getFileRequest2));
    }
    @Test
    public void testHasRouteWithSameRequestMethodsAndDifferentURI() throws Exception {
        Request getFileRequest = new Request("GET", "/something");
        Request getFileRequest2 = new Request("GET", "/somethingElse");

        Router router = new Router();
        router.addRoute(getFileRequest, new FileContentResponse("/something"));

        assertFalse(router.hasRoute(getFileRequest2));
    }
    @Test
    public void testAddRoute() throws Exception {
        Router router = new Router();
        Request getRequest = new Request();
        getRequest.setHttpMethod("GET");
        router.addRoute(getRequest, new FileContentResponse("/something"));

    }

    @Test
    public void testAllowedMethodsForURI() throws Exception {
        Router router = new Router();
        router.addRoute(new Request("GET", "/method_options"), new FileContentResponse("/method_options"));
        router.addRoute(new Request("HEAD", "/method_options"), new HeadResponse());
        router.addRoute(new Request("POST", "/method_options"), new PutPostResponse("/method_options"));
        router.addRoute(new Request("PUT", "/method_options"), new PutPostResponse("/method_options"));
        String uri = "/method_options";
        assertEquals("GET,POST,HEAD,PUT,OPTIONS",
                router.allowedMethodsForURI(uri));
    }
}