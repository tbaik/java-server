package com.tony.server;

import com.tony.server.response.GetResponse;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ResponseDeterminerTest {
    @Test
    public void testDetermineResponseReturnsResponseIfInRouter() throws Exception {
        Router router = Main.createCobSpecRouter();
        ArrayList<String> uriList = new ArrayList<>();
        ResponseDeterminer responseDeterminer =
                new ResponseDeterminer(router, uriList);
        Request getRequest = new Request("GET", "/");

        assertEquals(new GetResponse().getClass(),
                responseDeterminer.determineResponse(getRequest).getClass());
    }
}