package com.tony.server;

import java.io.File;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArgumentParser argumentParser = new ArgumentParser();
        argumentParser.parseArguments(args);
        Server server = new Server(argumentParser.getPort());
        new Thread(server).start();
    }

    public static ArrayList<String> createURIList(String directoryPath) {
        File folder = new File(directoryPath);
        File[] listOfFiles = folder.listFiles();
        ArrayList<String> uriList = new ArrayList<String>();

        for(int i = 0; i < listOfFiles.length; i++) {
            if(listOfFiles[i].isFile()) {
                uriList.add("/" + listOfFiles[i].getName());
            }
        }
        return uriList;
    }
}
