package com.tony.server;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class WorkerThreadTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void cleanUpStreams() {
        System.setOut(null);
    }

    @Test
    public void testClientSocketSendsMessageBack() throws Exception {
        ArrayList uriList = new ArrayList();
        Router router = Main.createCobSpecRouter(System.getProperty("user.dir") + "/public/", uriList, new Logger());
        Authenticator authenticator = new Authenticator();
        MockWorker worker = new MockWorker(new Socket(),
                new ResponseDeterminer(router, uriList, authenticator)
                , new Logger());
        worker.run();
        assertEquals("HTTP/1.1 200 OK\n",
                worker.getOutputStream().toString());
    }

    @Test
    public void testRunningWorkerLogsRequestAndResponseToConsoleAndLoggerObject() throws Exception {
        ArrayList uriList = new ArrayList();
        Router router = Main.createCobSpecRouter(System.getProperty("user.dir") + "/public/", uriList, new Logger());
        Logger logger = new Logger();
        Authenticator authenticator = new Authenticator();
        MockWorker worker = new MockWorker(new Socket(),
                new ResponseDeterminer(router, uriList, authenticator), logger);
        worker.run();

        String expectedLog = "Received Request:\n" +
                "\n" +
                "GET / HTTP/1.1\n" +
                "Connection: Keep-Alive\n" +
                "User-Agent: Apache-HttpClient/4.3.5 (java 1.5)\n" +
                "Host: localhost:5000\n" +
                "Accept-Encoding: gzip,deflate\n" +
                "\n" +
                "Sending Response:\n" +
                "\n" +
                "HTTP/1.1 200 OK\n\n";
        assertEquals(expectedLog, outContent.toString());
        assertEquals(expectedLog, logger.getLog());
    }

    private class MockWorker extends WorkerThread {
        byte input[] = ("GET / HTTP/1.1\n" +
                "Host: localhost:5000\n" +
                "Connection: Keep-Alive\n" +
                "User-Agent: Apache-HttpClient/4.3.5 (java 1.5)\n" +
                "Accept-Encoding: gzip,deflate\n").getBytes();
        private ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        private ByteArrayInputStream inputStream = new ByteArrayInputStream(input);

        public MockWorker(Socket clientSocket, ResponseDeterminer responseDeterminer, Logger logger)
                throws IOException {
           super(clientSocket, responseDeterminer, logger);
       }

       public OutputStream getOutputStream() {
           return outputStream;
       }

       public InputStream getInputStream() {
           return inputStream;
       }
   }
}