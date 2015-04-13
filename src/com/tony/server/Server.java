package com.tony.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable{
    private final int port;
    private ServerSocket serverSocket;
    private boolean isRunning = true;
    private ExecutorService threadPool = Executors.newFixedThreadPool(100);

    public Server(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        openSocket();
        while(isRunning) {
            Socket clientSocket;
            try {
                clientSocket = serverSocket.accept();
                System.out.println("Accepted a new client request");

                threadPool.execute(new WorkerThread(clientSocket));
            } catch (IOException e) {
                if (isRunning) {
                    System.err.println("Server stopped");
                    break;
                }
                throw new RuntimeException(
                        "Error accepting client connection", e);
            }
        }
        threadPool.shutdown();
    }

    private void openSocket() {
        try {
            this.serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stopServer() {
        isRunning = false;
        try {
            serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException("Error closing server", e);
        }
    }
}
