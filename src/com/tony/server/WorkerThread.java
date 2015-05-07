package com.tony.server;

import com.tony.server.response.ImageContentResponse;
import com.tony.server.response.Response;
import com.tony.server.response.ResponseBuilder;

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
            logRequest(request.toString());

            Response response = responseDeterminer.determineResponse(request);
            byte[] responseBytes = response.respond();
            logResponse(response, responseBytes);

            getOutputStream().write(responseBytes);
            getOutputStream().close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void logRequest(String requestMessage) {
        logger.log("Received Request:\r\n");
        logger.log(requestMessage);
    }

    public void logResponse(Response response, byte[] responseMessage) {
        if(response.getClass().equals(ImageContentResponse.class)){
            logger.log(ResponseBuilder.buildResponse(
                    response.getStatus(), response.getHeaders(), "Image body not shown in log"));
        } else {
            logger.log("Sending Response:\r\n");
            logger.log(new String(responseMessage));
        }
    }

    public OutputStream getOutputStream() throws IOException {
        return clientSocket.getOutputStream();
    }

    public InputStream getInputStream() throws IOException {
        return clientSocket.getInputStream();
    }
}
