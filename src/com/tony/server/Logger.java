package com.tony.server;

public class Logger {
    private String log;

    public Logger() {
        this.log = "";
    }

    public void log(String message) {
        log += message + "\r\n";
        System.out.println(message);
    }

    public String getLog() {
        return log;
    }
}
