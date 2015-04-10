package com.tony.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable{
    private final int port;
    private ServerSocket socket;
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
                clientSocket = socket.accept();
                System.out.println("Accepted a new client request");
            } catch (IOException e) {
                if (isRunning) {
                    System.err.println("Server stopped");
                    break;
                }
                throw new RuntimeException(
                        "Error accepting client connection", e);
            }

            threadPool.execute(new WorkerThread(clientSocket));
        }
        threadPool.shutdown();
    }

    private void openSocket() {
        try {
            this.socket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stopServer() {
        isRunning = false;
        try {
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException("Error closing server", e);
        }
    }
}
