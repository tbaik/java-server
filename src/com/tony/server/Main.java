package com.tony.server;

public class Main {
    public static void main(String[] args) {
        Server server = new Server(5000);
        new Thread(server).start();

//        try {
//            Thread.sleep(20 * 1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        server.stopServer();
    }
}
