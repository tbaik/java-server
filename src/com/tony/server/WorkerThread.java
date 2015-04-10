package com.tony.server;

import java.io.*;
import java.net.Socket;

public class WorkerThread implements Runnable {

    private Socket clientSocket;

    public WorkerThread(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
//            BufferedReader inputReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            OutputStream output = clientSocket.getOutputStream();
            output.write(("HTTP/1.1 200 Unauthorized\n\nfile1 contents").getBytes());
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
