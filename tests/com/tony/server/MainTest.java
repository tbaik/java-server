package com.tony.server;

import com.tony.server.response.GetResponse;
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
        assertEquals("/partial_content.txt", uriList.get(6));
        assertEquals("/patch-content.txt", uriList.get(7));
        assertEquals("/text-file.txt", uriList.get(8));
    }

    @Test
    public void testCreateCobSpecRouter() throws Exception {
        Router cobSpecRouter = Main.createCobSpecRouter();
        Request getRequest = new Request();
        getRequest.setHttpMethod("GET");
        getRequest.setUri("/");

        Request putRequest = new Request();
        putRequest.setHttpMethod("PUT");
        putRequest.setUri("/form");


        assertEquals(new GetResponse().getClass(),
                cobSpecRouter.route(getRequest).getClass());
        assertEquals(new PutPostResponse().getClass(),
                cobSpecRouter.route(putRequest).getClass());
    }
}