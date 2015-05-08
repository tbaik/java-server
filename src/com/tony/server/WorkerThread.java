package com.tony.server;

import com.tony.server.response.Response;

import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class WorkerThread implements Runnable {

    private final ResponseDeterminer responseDeterminer;
    private final Logger logger;
    private Socket clientSocket;

    public WorkerThread(Socket clientSocket, ResponseDeterminer responseDeterminer, Logger logger) throws IOException {
        this.clientSocket = clientSocket;
        this.responseDeterminer = responseDeterminer;
        this.logger = logger;
    }

    @Override
    public void run() {
        try {
            Request request = RequestParser.parseRequest(getInputStream());
            logger.storeLog("Received Request:\r\n");
            logger.storeLog(request.toString());

            Response response = responseDeterminer.determineResponse(request);
            byte[] responseBytes = response.respond();
            logger.storeLog("Sending Response:\r\n");
            logger.storeLog(new String(responseBytes));

            getOutputStream().write(responseBytes);
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
