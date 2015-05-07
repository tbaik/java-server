package com.tony.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable{
    private final int port;
    private final ResponseDeterminer responseDeterminer;
    private final Logger logger;
    private ServerSocket serverSocket;
    private boolean isRunning = true;
    private ExecutorService threadPool = Executors.newFixedThreadPool(50);

    public Server(int port, ResponseDeterminer responseDeterminer, Logger logger) {
        this.port = port;
        this.responseDeterminer = responseDeterminer;
        this.logger = logger;
    }

    @Override
    public void run() {
        openSocket();
        while(isRunning) {
            try {
                Socket clientSocket = serverSocket.accept();
                threadPool.execute(new WorkerThread(clientSocket, responseDeterminer, logger));
            } catch (IOException e) {
                if (isRunning) {
                    System.err.println("Server stopped");
                    break;
                }
                throw new RuntimeException(
                        "Error accepting client connection", e);
            }
        }
        stopServer();
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
            threadPool.shutdown();
            serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException("Error closing server", e);
        }
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }
}
