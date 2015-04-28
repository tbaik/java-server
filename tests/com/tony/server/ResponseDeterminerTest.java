package com.tony.server;

import com.tony.server.response.FourOhFourResponse;
import com.tony.server.response.GetResponse;
import com.tony.server.response.MethodNotAllowedResponse;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ResponseDeterminerTest {
    @Test
    public void testDetermineResponseReturnsResponseIfInRouter() throws Exception {
        Router router = Main.createCobSpecRouter(System.getProperty("user.dir") + "/public/");
        ArrayList<String> uriList = new ArrayList<>();
        ResponseDeterminer responseDeterminer =
                new ResponseDeterminer(router, uriList);
        Request getRequest = new Request("GET", "/");

        assertEquals(new GetResponse("/").getClass(),
                responseDeterminer.determineResponse(getRequest).getClass());
    }

    @Test
    public void testDetermineResponseReturnsMethodNotAllowedIfNotInRouterButInURIList() throws Exception {
        Router router = Main.createCobSpecRouter(System.getProperty("user.dir") + "/public/");
        ArrayList<String> uriList = new ArrayList<>();
        uriList.add("/file1");
        uriList.add("/text-file.txt");
        ResponseDeterminer responseDeterminer =
                new ResponseDeterminer(router, uriList);
        Request putRequest = new Request("PUT", "/file1");
        Request postRequest = new Request("POST", "/text-file.txt");

        assertEquals(new MethodNotAllowedResponse().getClass(),
                responseDeterminer.determineResponse(putRequest).getClass());
        assertEquals(new MethodNotAllowedResponse().getClass(),
                responseDeterminer.determineResponse(postRequest).getClass());
    }

    @Test
    public void testDetermineResponseReturnsFourOhFourWhenNotFindingRouterNorURI() throws Exception {
        Router router = Main.createCobSpecRouter(System.getProperty("user.dir") + "/public/");
        ArrayList<String> uriList = new ArrayList<>();
        ResponseDeterminer responseDeterminer =
                new ResponseDeterminer(router, uriList);
        Request putRequest = new Request("PUT", "/file1");

        assertEquals(new FourOhFourResponse().getClass(),
                responseDeterminer.determineResponse(putRequest).getClass());
    }
}