package com.tony.server;

import com.tony.server.response.GetResponse;
import com.tony.server.response.OptionsResponse;
import com.tony.server.response.PutPostResponse;

import java.io.File;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArgumentParser argumentParser = new ArgumentParser();
        argumentParser.parseArguments(args);

        Router router = createCobSpecRouter();
        ArrayList<String> uriList = createURIList(argumentParser.getDirectory());
        ResponseDeterminer responseDeterminer =
                new ResponseDeterminer(router, uriList);

        Server server = new Server(argumentParser.getPort(), responseDeterminer);
        new Thread(server).start();
    }

    public static ArrayList<String> createURIList(String directoryPath) {
        File folder = new File(directoryPath);
        File[] listOfFiles = folder.listFiles();
        ArrayList<String> uriList = new ArrayList<>();

        if(listOfFiles == null){
            return uriList;
        }

        for(int i = 0; i < listOfFiles.length; i++) {
            if(listOfFiles[i].isFile()) {
                uriList.add("/" + listOfFiles[i].getName());
            }
        }
        return uriList;
    }

    public static Router createCobSpecRouter() {
        Router router = new Router();
        router.addRoute(new Request("GET", "/"), new GetResponse());
        router.addRoute(new Request("POST", "/form"), new PutPostResponse());
        router.addRoute(new Request("PUT", "/form"), new PutPostResponse());
        router.addRoute(new Request("GET", "/method_options"), new GetResponse());
        router.addRoute(new Request("HEAD", "/method_options"), new GetResponse());
        router.addRoute(new Request("POST", "/method_options"), new PutPostResponse());
        router.addRoute(new Request("PUT", "/method_options"), new PutPostResponse());
        router.addRoute(new Request("OPTIONS", "/method_options"), new OptionsResponse(router, "/method_options"));
        return router;
    }
}
