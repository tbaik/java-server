package com.tony.server.response;

public enum Status {
    OK("200", "OK"),
    FOUND("302", "Found"),
    UNAUTHORIZED("401", "Unauthorized"),
    NOTFOUND("404", "Not Found"),
    METHODNOTALLOWED("405", "Method Not Allowed"),
    INTERNALERROR("500", "Internal Server Error");

    private final String statusCode;
    private final String reasonPhrase;

    Status(String statusCode, String reasonPhrase) {
        this.statusCode = statusCode;
        this.reasonPhrase = reasonPhrase;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public String getReasonPhrase() {
        return reasonPhrase;
    }
}

