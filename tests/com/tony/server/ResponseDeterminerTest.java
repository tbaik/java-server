package com.tony.server;

import com.tony.server.response.FileContentResponse;
import com.tony.server.response.FourOhFourResponse;
import com.tony.server.response.MethodNotAllowedResponse;
import com.tony.server.response.UnauthorizedResponse;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class ResponseDeterminerTest {
    @Test
    public void testDetermineResponseReturnsResponseIfInRouter() throws Exception {
        Logger logger = new Logger();
        ArrayList<String> uriList = new ArrayList<>();
        Router router = Main.createCobSpecRouter(System.getProperty("user.dir") + "/public/", uriList, logger);
        Authenticator authenticator = new Authenticator();
        ResponseDeterminer responseDeterminer =
                new ResponseDeterminer(router, uriList, authenticator);
        Request getRequest = new Request("GET", "/form");

        assertEquals(new FileContentResponse("/form").getClass(),
                responseDeterminer.determineResponse(getRequest).getClass());
    }

    @Test
    public void testDetermineResponseReturnsMethodNotAllowedIfNotInRouterButInURIList() throws Exception {
        Logger logger = new Logger();
        ArrayList<String> uriList = new ArrayList<>();
        uriList.add("/text-file.txt");
        uriList.add("/file1");

        Router router = Main.createCobSpecRouter(System.getProperty("user.dir") + "/public/", uriList, logger);
        Authenticator authenticator = new Authenticator();
        ResponseDeterminer responseDeterminer =
                new ResponseDeterminer(router, uriList, authenticator);
        Request putRequest = new Request("PUT", "/file1");
        Request postRequest = new Request("POST", "/text-file.txt");

        assertEquals(new MethodNotAllowedResponse().getClass(),
                responseDeterminer.determineResponse(putRequest).getClass());
        assertEquals(new MethodNotAllowedResponse().getClass(),
                responseDeterminer.determineResponse(postRequest).getClass());
    }

    @Test
    public void testDetermineResponseReturnsFourOhFourWhenNotFindingRouterNorURI() throws Exception {
        Logger logger = new Logger();
        ArrayList<String> uriList = new ArrayList<>();
        Authenticator authenticator = new Authenticator();
        Router router = Main.createCobSpecRouter(System.getProperty("user.dir") + "/public/", uriList, logger);
        ResponseDeterminer responseDeterminer =
                new ResponseDeterminer(router, uriList, authenticator);
        Request putRequest = new Request("PUT", "/file1");

        assertEquals(new FourOhFourResponse().getClass(),
                responseDeterminer.determineResponse(putRequest).getClass());
    }

    @Test
    public void testFourOhOneResponseOnRequiredAuthenticationButIsNotAuthenticated() throws Exception {
        Request request = new Request("GET", "/file_needs_authorization");

        Logger logger = new Logger();
        ArrayList<String> uriList = new ArrayList<>();

        Router router = new Router();
        router.addRoute(request, new FourOhFourResponse());

        Authenticator authenticator = new Authenticator();
        authenticator.addToAuthenticatedUsers("user:notAuthorized");
        authenticator.addToAuthenticationList(request);

        ResponseDeterminer responseDeterminer =
                new ResponseDeterminer(router, uriList, authenticator);

        assertEquals(new UnauthorizedResponse().getClass(),
                responseDeterminer.determineResponse(request).getClass());

    }
}