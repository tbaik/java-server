package com.tony.server;

import com.tony.server.response.DeleteResponse;
import com.tony.server.response.FileContentResponse;
import com.tony.server.response.HeadResponse;
import com.tony.server.response.PutPostResponse;
import com.tony.server.response.OptionsResponse;
import com.tony.server.response.ImageContentResponse;
import com.tony.server.response.DirectoryResponse;

import java.io.File;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArgumentParser argumentParser = new ArgumentParser();
        argumentParser.parseArguments(args);

        ArrayList<String> uriList = createURIList(argumentParser.getDirectory());
        Router router = createCobSpecRouter(argumentParser.getDirectory(), uriList);
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
        uriList.remove("/.DS_Store");
        uriList.remove("/form");
        uriList.remove("/method_options");
        return uriList;
    }

    public static Router createCobSpecRouter(String directoryPath, ArrayList uriList) {
        Router router = new Router();
        router.addRoute(new Request("GET", "/"), new DirectoryResponse(uriList));
        router.addRoute(new Request("GET", "/form"), new FileContentResponse(directoryPath + "/form"));
        router.addRoute(new Request("POST", "/form"), new PutPostResponse(directoryPath + "/form"));
        router.addRoute(new Request("PUT", "/form"), new PutPostResponse(directoryPath + "/form"));
        router.addRoute(new Request("DELETE", "/form"), new DeleteResponse(directoryPath + "/form"));
        router.addRoute(new Request("GET", "/method_options"), new FileContentResponse(directoryPath + "/method_options"));
        router.addRoute(new Request("HEAD", "/method_options"), new HeadResponse());
        router.addRoute(new Request("POST", "/method_options"), new PutPostResponse(directoryPath + "/method_options"));
        router.addRoute(new Request("PUT", "/method_options"), new PutPostResponse(directoryPath + "/method_options"));
        router.addRoute(new Request("OPTIONS", "/method_options"), new OptionsResponse(router.allowedMethodsForURI("/method_options"), "/method_options"));
        router.addRoute(new Request("GET", "/file1"), new FileContentResponse(directoryPath + "file1"));
        router.addRoute(new Request("GET", "/image.jpeg"), new ImageContentResponse(directoryPath + "image.jpeg"));
        router.addRoute(new Request("GET", "/image.png"), new ImageContentResponse(directoryPath + "image.png"));
        router.addRoute(new Request("GET", "/image.gif"), new ImageContentResponse(directoryPath + "image.gif"));
        return router;
    }
}

