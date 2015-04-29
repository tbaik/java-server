package com.tony.server;

public class Logger {
    private String log;

    public Logger() {
        this.log = "";
    }

    public void storeLog(String logMessage) {
        if(logMessage.length() > 5000) {
            logMessage = logMessage.substring(0, 200);
        }
        log += logMessage + "\r\n";
        System.out.println(logMessage);
    }

    public String getLog() {
        return log;
    }
}
