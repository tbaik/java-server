package com.tony.server;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.net.Socket;

import static org.junit.Assert.*;

public class ServerTest {

    @Test
    public void testItAcceptsAClientRequest() throws Exception {
        Server server = new Server(4000);
        new Thread(server).start();
        Socket clientMockSocket = new Socket("localhost", 4000);
        clientMockSocket.getOutputStream().write("something\n\n".getBytes());
        assertEquals(4000, server.getServerSocket().getLocalPort());
        assertEquals(clientMockSocket.getChannel() ,
                server.getServerSocket().getChannel());
    }

}