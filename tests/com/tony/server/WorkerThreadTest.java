package com.tony.server;

import com.tony.server.response.HeadResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class WorkerThreadTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private MockWorker worker;
    private Router router;
    private Logger logger;

    @Before
    public void setUp() throws Exception {
        System.setOut(new PrintStream(outContent));

        router = new Router();
        router.addRoute(new Request("GET", "/"), new HeadResponse());

        logger = new Logger();
        ArrayList uriList = new ArrayList();
        Authenticator authenticator = new Authenticator(logger);
        worker = new MockWorker(new Socket(),
                new ResponseDeterminer(router, uriList, authenticator),
                logger);
    }

    @After
    public void cleanUpStreams() {
        System.setOut(null);
    }

    @Test
    public void testClientSocketSendsMessageBack() throws Exception {
        worker.run();

        assertEquals("HTTP/1.1 200 OK\r\n", worker.getOutputStream().toString());
    }

    @Test
    public void testRunningWorkerLogsRequestAndResponseToLogger() throws Exception {
        worker.run();
        String expectedLog = "Received Request:\r\n" +
                "\r\n" +
                "GET / HTTP/1.1\r\n" +
                "Connection: Keep-Alive\r\n" +
                "User-Agent: Apache-HttpClient/4.3.5 (java 1.5)\r\n" +
                "Host: localhost:5000\r\n" +
                "Accept-Encoding: gzip,deflate\r\n" +
                "\r\n" +
                "Sending Response:\r\n" +
                "\r\n" +
                "HTTP/1.1 200 OK\r\n\r\n";

        assertEquals(expectedLog, logger.getLog());
    }

    @Test
    public void testRunningWorkerLogsRequestAndResponseToConsoleOutput() throws Exception {
        worker.run();

        String expectedLog = "Received Request:\r\n" +
                "\n" +
                "GET / HTTP/1.1\r\n" +
                "Connection: Keep-Alive\r\n" +
                "User-Agent: Apache-HttpClient/4.3.5 (java 1.5)\r\n" +
                "Host: localhost:5000\r\n" +
                "Accept-Encoding: gzip,deflate\r\n" +
                "\n" +
                "Sending Response:\r\n" +
                "\n" +
                "HTTP/1.1 200 OK\r\n\n";

        assertEquals(expectedLog, outContent.toString());
    }

    private class MockWorker extends WorkerThread {
        byte input[] = ("GET / HTTP/1.1\r\n" +
                "Host: localhost:5000\r\n" +
                "Connection: Keep-Alive\r\n" +
                "User-Agent: Apache-HttpClient/4.3.5 (java 1.5)\r\n" +
                "Accept-Encoding: gzip,deflate\r\n").getBytes();
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