package com.tony.server;

import java.util.HashMap;

public class Request {
    private String httpMethod;
    private String uri;
    private String body = "";
    private HashMap<String,String> headers;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Request request = (Request) o;

        if (httpMethod != null ? !httpMethod.equals(request.httpMethod) : request.httpMethod != null) return false;
        return !(uri != null ? !uri.equals(request.uri) : request.uri != null);

    }

    @Override
    public int hashCode() {
        int result = httpMethod != null ? httpMethod.hashCode() : 0;
        result = 31 * result + (uri != null ? uri.hashCode() : 0);
        return result;
    }

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
