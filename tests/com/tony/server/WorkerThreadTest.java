package com.tony.server;

import com.tony.server.WorkerThread;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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

       private ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

       public MockWorker(Socket clientSocket) throws IOException {
           super(clientSocket);
       }

       public OutputStream getOutputStream() {
           return outputStream;
       }
   }
}