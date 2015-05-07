package com.tony.server;

import com.tony.server.response.FileContentResponse;
import com.tony.server.response.FourOhFourResponse;
import com.tony.server.response.MethodNotAllowedResponse;
import com.tony.server.response.UnauthorizedResponse;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class ResponseDeterminerTest {
    private ResponseDeterminer responseDeterminer;
    private Router router;
    private ArrayList<String> uriList;
    private Authenticator authenticator;

    @Before
    public void setUp() throws Exception {
        uriList = new ArrayList<>();
        authenticator = new Authenticator(new Logger());
        router = new Router();
        responseDeterminer = new ResponseDeterminer(router, uriList, authenticator);
    }

    @Test
    public void testDetermineResponseReturnsResponseIfInRouter() throws Exception {
        String filePath = "some_path/form";
        Request getRequest = new Request("GET", "/form");
        router.addRoute(getRequest, new FileContentResponse(filePath));

        assertEquals(new FileContentResponse("/pathsomewhere").getClass(),
                responseDeterminer.determineResponse(getRequest).getClass());
    }

    @Test
    public void testDetermineResponseReturnsMethodNotAllowedIfNotInRouterButInURIList() throws Exception {
        uriList.add("/text-file.txt");
        uriList.add("/file1");

        Request putRequest = new Request("PUT", "/file1");
        Request postRequest = new Request("POST", "/text-file.txt");

        assertEquals(new MethodNotAllowedResponse().getClass(),
                responseDeterminer.determineResponse(putRequest).getClass());
        assertEquals(new MethodNotAllowedResponse().getClass(),
                responseDeterminer.determineResponse(postRequest).getClass());
    }

    @Test
    public void testDetermineResponseReturnsFourOhFourWhenNotFindingRouterNorURI() throws Exception {
        Request putRequest = new Request("PUT", "/file1");

        assertEquals(new FourOhFourResponse().getClass(),
                responseDeterminer.determineResponse(putRequest).getClass());
    }

    @Test
    public void testFourOhOneResponseOnRequiredAuthenticationButIsNotAuthenticated() throws Exception {
        Request request = new Request("GET", "/file_needs_authorization");
        router.addRoute(request, new FourOhFourResponse());

        authenticator.addToAuthenticatedUsers("user:notAuthorized");
        authenticator.addToAuthenticationList(request);

        assertEquals(new UnauthorizedResponse().getClass(),
                responseDeterminer.determineResponse(request).getClass());
    }
}