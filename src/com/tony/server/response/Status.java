package com.tony.server.response;

public enum Status {
    OK("200", "OK"),
    NO_CONTENT("204", "No Content"),
    FOUND("302", "Found"),
    UNAUTHORIZED("401", "Unauthorized"),
    NOT_FOUND("404", "Not Found"),
    METHOD_NOT_ALLOWED("405", "Method Not Allowed"),
    PRECONDITION_FAILED("412", "Precondition Failed"),
    INTERNAL_ERROR("500", "Internal Server Error");

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

