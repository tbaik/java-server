package com.tony.server;

import org.junit.Test;

import java.net.Socket;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class ServerTest {

    @Test
    public void testItAcceptsAClientRequest() throws Exception {
        Router router = new Router();
        ArrayList<String> uriList = new ArrayList<>();
        ResponseDeterminer responseDeterminer =
                new ResponseDeterminer(router, uriList);
        Server server = new Server(4000, responseDeterminer);

        new Thread(server).start();
        Socket clientMockSocket = new Socket("localhost", 4000);
        clientMockSocket.getOutputStream().write("GET / HTTP/1.1\n".getBytes());
        assertEquals(4000, server.getServerSocket().getLocalPort());
        assertEquals(clientMockSocket.getChannel() ,
                server.getServerSocket().getChannel());
    }

}