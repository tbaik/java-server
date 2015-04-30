package com.tony.server;

import com.tony.server.response.FileContentResponse;
import com.tony.server.response.PutPostResponse;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MainTest {
    @Test
    public void testCreateURIListHasAllURIFromDirectory() throws Exception {
        String directory = System.getProperty("user.dir");
        File file = new File(directory + "/testfile.txt");
        file.createNewFile();
        ArrayList<String> uriList = Main.createURIList(directory);

        assertTrue(uriList.contains("/testfile.txt"));
        file.delete();
    }

    @Test
    public void testCreateCobSpecRouterAddsResponsesToRouter() throws Exception {
        ArrayList<String> uriList = new ArrayList();
        Router cobSpecRouter = Main.createCobSpecRouter(System.getProperty("user.dir") + "/public/", uriList, new Logger());

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
