package com.tony.server;

import com.tony.server.response.FileContentResponse;
import com.tony.server.response.PutPostResponse;
import org.junit.Test;

import java.io.File;
import java.net.Socket;
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

    @Test
    public void testStartingServerWithServerCreatedFromArgsHasPortFromArgs() throws Exception {
        String[] args = {"-p", "6005"};
        Server server = Main.createServer(args);
        Main.startServer(server);

        Socket clientMockSocket = new Socket("localhost", 6005);
        clientMockSocket.getOutputStream().write("GET / HTTP/1.1\n".getBytes());
        assertEquals(6005, server.getServerSocket().getLocalPort());
    }

    @Test
    public void testStartServerMakesNewServerRun() throws Exception {
        int port = 6000;
        Router router = new Router();
        ArrayList<String> uriList = new ArrayList<>();
        Logger logger = new Logger();
        Authenticator authenticator = new Authenticator(logger);
        ResponseDeterminer responseDeterminer = new ResponseDeterminer(router, uriList, authenticator);
        Server server = new Server(port, responseDeterminer, logger);

        Main.startServer(server);

        Socket clientMockSocket = new Socket("localhost", 6000);
        clientMockSocket.getOutputStream().write("GET / HTTP/1.1\n".getBytes());
        assertTrue(server.isRunning());
    }
}
