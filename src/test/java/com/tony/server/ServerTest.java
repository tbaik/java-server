package com.tony.server;

import com.tony.server.Authenticator;
import com.tony.server.Logger;
import com.tony.server.ResponseDeterminer;
import com.tony.server.Router;
import com.tony.server.Server;
import org.junit.Before;
import org.junit.Test;

import java.net.Socket;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ServerTest {
    private Server server;
    private Logger logger;
    private ResponseDeterminer responseDeterminer;

    @Before
    public void setUp() throws Exception {
        int port = 5005;
        Router router = new Router();
        ArrayList<String> uriList = new ArrayList<>();
        logger = new Logger();
        Authenticator authenticator = new Authenticator(logger);
        responseDeterminer = new ResponseDeterminer(router, uriList, authenticator);

        server = new Server(port, responseDeterminer, logger);
    }

    @Test
    public void testNewServerIsNotRunning() throws Exception {
        assertFalse(server.isRunning());
    }

    @Test
    public void testItAcceptsAClientRequest() throws Exception {
        new Thread(server).start();

        Socket clientMockSocket = new Socket("localhost", 5005);
        clientMockSocket.getOutputStream().write("GET / HTTP/1.1\n".getBytes());

        assertEquals(5005, server.getServerSocket().getLocalPort());
        assertEquals(clientMockSocket.getChannel(),
                server.getServerSocket().getChannel());
    }

    @Test
    public void testOpenSocketSetsServerSocketPortAndStartsServer() throws Exception {
        Server newServer = new Server(5050, responseDeterminer, logger);
        newServer.openSocket();
        assertTrue(newServer.isRunning());
        assertTrue(newServer.getServerSocket() != null);
    }
}