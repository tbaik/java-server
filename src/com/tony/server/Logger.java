package com.tony.server;

public class Logger {
    private String log;

    public Logger() {
        this.log = "";
    }

    public void storeLog(String logMessage) {
        log += logMessage + "\r\n";
        System.out.println(logMessage);
    }

    public String getLog() {
        return log;
    }
}
