package com.tony.server.response;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ResponseBuilder {
    private static final String HTTP_VERSION = "HTTP/1.1";
    private static final String SPACE = " ";
    private static final String CRLF = "\r\n";
    private static final String HEADER_SEPARATOR = ": ";

    public static String buildResponse(
            Status status, HashMap<String, String> headers, String body) {
        return buildStatusLine(status) + buildHeaderString(headers) + buildBody(body);
    }

    public static String buildHeaderString(HashMap<String, String> headers) {
        String headerString = "";

        for(Map.Entry<String, String> entry : headers.entrySet()){
            headerString += buildHeaderLine(entry.getKey(), entry.getValue());
        }
        return headerString;
    }

    public static String buildHeaderLine(String key, String value) {
        return key + HEADER_SEPARATOR + value + CRLF;
    }

    public static String buildBody(String body) {
        if((body == null) || (body.equals(""))){
            return "";
        } else{
            return CRLF + body;
        }
    }

    public static byte[] buildImageResponse(Status status, HashMap<String, String> headers, byte[] imageBytes) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            baos.write(buildStatusLine(status).getBytes());
            baos.write(buildHeaderString(headers).getBytes());
            baos.write(CRLF.getBytes());
            baos.write(imageBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return baos.toByteArray();
    }

    public static String buildStatusLine(Status status) {
        return HTTP_VERSION + SPACE + status.getStatusCode() + SPACE + status.getReasonPhrase() + CRLF;
    }
}
