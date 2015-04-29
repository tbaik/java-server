package com.tony.server;

import com.tony.server.response.FileContentResponse;
import com.tony.server.response.PutPostResponse;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MainTest {
    @Test
    public void testCreateURIListHasAllURIFromDirectory() throws Exception {
        String directory = System.getProperty("user.dir") + "/public/";
        ArrayList<String> uriList = Main.createURIList(directory);
        assertTrue(uriList.contains("/file1"));
        assertTrue(uriList.contains("/file2"));
        assertTrue(uriList.contains("/image.gif"));
        assertTrue(uriList.contains("/image.jpeg"));
        assertTrue(uriList.contains("/image.png"));
        assertTrue(uriList.contains("/method_options"));
        assertTrue(uriList.contains("/partial_content.txt"));
        assertTrue(uriList.contains("/patch-content.txt"));
        assertTrue(uriList.contains("/text-file.txt"));
    }

    @Test
    public void testCreateCobSpecRouterAddsResponsesToRouter() throws Exception {
        Router cobSpecRouter = Main.createCobSpecRouter(System.getProperty("user.dir") + "/public/");
        Request getRequest = new Request();
        getRequest.setHttpMethod("GET");
        getRequest.setUri("/form");

        Request putRequest = new Request();
        putRequest.setHttpMethod("PUT");
        putRequest.setUri("/form");


        assertEquals(new FileContentResponse("/form").getClass(),
                cobSpecRouter.route(getRequest).getClass());
        assertEquals(new PutPostResponse("/path").getClass(),
                cobSpecRouter.route(putRequest).getClass());
    }
}