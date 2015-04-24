package com.tony.server;

import com.tony.server.response.Response;

import java.io.*;
import java.net.Socket;

public class WorkerThread implements Runnable {

    private final ResponseDeterminer responseDeterminer;
    private Socket clientSocket;

    public WorkerThread(Socket clientSocket, ResponseDeterminer responseDeterminer) throws IOException {
        this.clientSocket = clientSocket;
        this.responseDeterminer = responseDeterminer;
    }

    @Override
    public void run() {
        try {
            Request request = RequestParser.parseRequest(getInputStream());
            Response response = responseDeterminer.determineResponse(request);
            getOutputStream().write(response.respond().getBytes());
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
