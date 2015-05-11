package com.tony.server;

import com.tony.server.response.FileContentResponse;
import com.tony.server.response.HeadResponse;
import com.tony.server.response.PartialContentResponse;
import com.tony.server.response.PutPostResponse;
import com.tony.server.response.Response;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RouterTest {
    private Router router;

    @Before
    public void setUp() throws Exception {
        router = new Router();
    }

    @Test
    public void testRouteGivesBackCorrectResponse() throws Exception {
        Request fileContentRequest = new Request("GET", "/form");

        Response getResponse = new FileContentResponse("/form");
        router.addRoute(fileContentRequest, getResponse);

        assertEquals(getResponse, router.route(fileContentRequest));
    }

    @Test
    public void testRouteReturnsNewPutPostResponseWithBodyIfPutOrPost() throws Exception {
        Request putRequest = new Request("PUT", "/form");
        putRequest.setBody("data=hello");

        Response putPostResponse = new PutPostResponse("filepath");
        router.addRoute(putRequest, putPostResponse);

        assertEquals("data=hello", ((PutPostResponse) router.route(putRequest)).getRequestBody());
        new File(System.getProperty("user.dir") + "/public/form").delete();
    }

    @Test
    public void testRouteForPartialContentSetsRangeHeader() throws Exception {
        Request partialRequest = new Request("GET", "/partial_something");
        partialRequest.addToHeaders("Range", "something in here");

        Router router = new Router();
        PartialContentResponse response = new PartialContentResponse("filepath");
        router.addRoute(partialRequest, response);

        assertEquals("something in here", ((PartialContentResponse) router.route(partialRequest)).getRangeHeader());
    }

    @Test
    public void testHasRouteWithSameRequestMethodsAndSameURI() throws Exception {
        Request getFileRequest = new Request("GET", "/something");
        Request getFileRequest2 = new Request("GET", "/something");

        router.addRoute(getFileRequest, new FileContentResponse("/something"));

        assertTrue(router.hasRoute(getFileRequest2));
    }
    @Test
    public void testHasRouteWithSameRequestMethodsAndDifferentURI() throws Exception {
        Request getFileRequest = new Request("GET", "/something");
        Request getFileRequest2 = new Request("GET", "/somethingElse");

        router.addRoute(getFileRequest, new FileContentResponse("/something"));

        assertFalse(router.hasRoute(getFileRequest2));
    }

    @Test
    public void testAllowedMethodsForURI() throws Exception {
        router.addRoute(new Request("GET", "/method_options"), new FileContentResponse("/method_options"));
        router.addRoute(new Request("HEAD", "/method_options"), new HeadResponse());
        router.addRoute(new Request("POST", "/method_options"), new PutPostResponse("/method_options"));
        router.addRoute(new Request("PUT", "/method_options"), new PutPostResponse("/method_options"));
        String uri = "/method_options";
        assertEquals("GET,POST,HEAD,PUT,OPTIONS",
                router.allowedMethodsForURI(uri));
    }
}