package com.tony.server;

public class Main {
    public static void main(String[] args) {
        ArgumentParser argumentParser = new ArgumentParser();
        argumentParser.parseArguments(args);
        Server server = new Server(argumentParser.getPort());
        new Thread(server).start();
    }
}
