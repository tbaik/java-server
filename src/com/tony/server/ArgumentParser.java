package com.tony.server;

public class ArgumentParser {
    private int port = 5000;
    private String directory = System.getProperty("user.dir") + "/public/";

    public ArgumentParser(String[] args) {
        if(args.length == 0){
        }
        else {
           for(int count = 0; count < args.length; count++){
               if(args[count] == "-p") {
                   port = Integer.parseInt(args[count + 1]);
               } else if (args[count] == "-d") {
                   directory = args[count + 1];
               }
           }
        }
    }


    public int getPort() {
        return port;
    }

    public String getDirectory() {
        return directory;
    }
}
