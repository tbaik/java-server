package com.tony.server;

import java.io.*;
import java.net.Socket;

public class WorkerThread implements Runnable {

    private Socket clientSocket;

    public WorkerThread(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            Request request = RequestParser.parseRequest(getInputStream());
            getOutputStream().write("HTTP/1.1 200 Unauthorized\n\nfile1 contents".getBytes());
            getOutputStream().close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public OutputStream getOutputStream() throws IOException {
        return clientSocket.getOutputStream();
    }

    public InputStream getInputStream() throws IOException {
        return clientSocket.getInputStream();
    }
}
