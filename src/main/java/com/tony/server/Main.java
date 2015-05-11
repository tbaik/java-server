package com.tony.server;

import com.tony.server.response.DeleteResponse;
import com.tony.server.response.DirectoryResponse;
import com.tony.server.response.FileContentResponse;
import com.tony.server.response.HeadResponse;
import com.tony.server.response.ImageContentResponse;
import com.tony.server.response.LogResponse;
import com.tony.server.response.OptionsResponse;
import com.tony.server.response.PatchResponse;
import com.tony.server.response.PutPostResponse;
import com.tony.server.response.RedirectResponse;
import com.tony.server.response.PartialContentResponse;

import java.io.File;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Server server = createServer(args);
        startServer(server);
    }

    public static Server createServer(String[] args) {
        ArgumentParser argumentParser = new ArgumentParser();
        argumentParser.parseArguments(args);

        Logger logger = new Logger();
        Authenticator authenticator = new Authenticator(logger);
        authenticator.addToAuthenticatedUsers("admin:hunter2");
        authenticator.addToAuthenticationList(new Request("GET", "/logs"));

        ArrayList<String> uriList = createURIList(argumentParser.getDirectory());
        Router router = createCobSpecRouter(argumentParser.getDirectory(), uriList, logger);
        ResponseDeterminer responseDeterminer =
                new ResponseDeterminer(router, uriList, authenticator);

        return new Server(argumentParser.getPort(), responseDeterminer, logger);
    }

    public static void startServer(Server server) {
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

    public static Router createCobSpecRouter(String directoryPath, ArrayList uriList, Logger logger) {
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
        router.addRoute(new Request("GET", "/redirect"), new RedirectResponse("http://localhost:5000/"));
        router.addRoute(new Request("GET", "/logs"), new LogResponse(logger));
        router.addRoute(new Request("GET", "/patch-content.txt"), new FileContentResponse(directoryPath + "/patch-content.txt"));
        router.addRoute(new Request("PATCH", "/patch-content.txt"), new PatchResponse(directoryPath + "/patch-content.txt"));
        router.addRoute(new Request("GET", "/partial_content.txt"), new PartialContentResponse(directoryPath + "/partial_content.txt"));
        return router;
    }
}

