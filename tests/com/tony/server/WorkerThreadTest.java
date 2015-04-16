package com.tony.server;

import org.junit.Test;

import java.io.*;
import java.net.Socket;

import static org.junit.Assert.*;

public class WorkerThreadTest {
    @Test
    public void testClientSocketSendsMessageBack() throws Exception {
        MockWorker worker = new MockWorker(new Socket());
        worker.run();
        assertEquals("HTTP/1.1 200 Unauthorized\n" +
                "\n" +
                "file1 contents", worker.getOutputStream().toString());
    }

   private class MockWorker extends WorkerThread {
       byte input[] = ("GET /logs HTTP/1.1\n" +
               "Host: localhost:5000\n" +
               "Connection: Keep-Alive\n" +
               "User-Agent: Apache-HttpClient/4.3.5 (java 1.5)\n" +
               "Accept-Encoding: gzip,deflate\n").getBytes();
       private ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
       private ByteArrayInputStream inputStream = new ByteArrayInputStream(input);

       public MockWorker(Socket clientSocket) throws IOException {
           super(clientSocket);
       }

       public OutputStream getOutputStream() {
           return outputStream;
       }

       public InputStream getInputStream() {
           return inputStream;
       }
   }
}