package com.tony.server;

import javax.swing.*;
import java.util.HashMap;

public class Request {
    private String httpMethod;
    private String uri;
    private String body = "";
    private HashMap<String,String> headers;

    public Request() {
        headers = new HashMap<String, String>();
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getURI() {
        return uri;
    }

    public void addToHeaders(String key, String value) {
        headers.put(key, value);
    }

    public HashMap<String, String> getHeaders() {
        return headers;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getBody() {
        return body;
    }
}
