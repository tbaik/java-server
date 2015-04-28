package com.tony.server;

import com.tony.server.response.FileContentResponse;
import com.tony.server.response.PutPostResponse;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class MainTest {
    @Test
    public void testCreateURIListHasAllURIFromDirectory() throws Exception {
        String directory = System.getProperty("user.dir") + "/public/";
        ArrayList<String> uriList = Main.createURIList(directory);
        assertEquals("/file1", uriList.get(1));
        assertEquals("/file2", uriList.get(2));
        assertEquals("/image.gif", uriList.get(3));
        assertEquals("/image.jpeg", uriList.get(4));
        assertEquals("/image.png", uriList.get(5));
        assertEquals("/method_options", uriList.get(6));
        assertEquals("/partial_content.txt", uriList.get(7));
        assertEquals("/patch-content.txt", uriList.get(8));
        assertEquals("/text-file.txt", uriList.get(9));
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