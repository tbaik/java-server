package com.tony.server;

import org.junit.Test;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class WorkerThreadTest {
    @Test
    public void testClientSocketSendsMessageBack() throws Exception {
        Router router = Main.createCobSpecRouter();
        ArrayList uriList = new ArrayList();
        MockWorker worker = new MockWorker(new Socket(),
                new ResponseDeterminer(router, uriList));
        worker.run();
        assertEquals("HTTP/1.1 200 OK\n",
                worker.getOutputStream().toString());
    }

   private class MockWorker extends WorkerThread {
       byte input[] = ("GET / HTTP/1.1\n" +
               "Host: localhost:5000\n" +
               "Connection: Keep-Alive\n" +
               "User-Agent: Apache-HttpClient/4.3.5 (java 1.5)\n" +
               "Accept-Encoding: gzip,deflate\n").getBytes();
       private ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
       private ByteArrayInputStream inputStream = new ByteArrayInputStream(input);

       public MockWorker(Socket clientSocket, ResponseDeterminer responseDeterminer)
               throws IOException {
           super(clientSocket, responseDeterminer);
       }

       public OutputStream getOutputStream() {
           return outputStream;
       }

       public InputStream getInputStream() {
           return inputStream;
       }
   }
}