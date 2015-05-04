package com.tony.server.response;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ResponseBuilder {
    public static String buildResponse(
            String statusLine, HashMap<String, String> headers, String body) {
        return statusLine + buildHeaderString(headers) + buildBody(body);
    }

    public static String buildHeaderString(HashMap<String, String> headers) {
        String headerString = "";

        for(Map.Entry<String, String> entry : headers.entrySet()){
            headerString += buildHeaderLine(entry.getKey(), entry.getValue());
        }
        return headerString;
    }

    public static String buildHeaderLine(String key, String value) {
        return key + ": " + value + "\n";
    }

    public static String buildBody(String body) {
        if((body == null) || (body.equals(""))){
            return "";
        } else{
            return "\n" + body;
        }
    }

    public static byte[] buildImageResponse(String statusLine, HashMap<String, String> headers, byte[] imageBytes) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            baos.write(statusLine.getBytes());
            baos.write(buildHeaderString(headers).getBytes());
            baos.write("\n".getBytes());
            baos.write(imageBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return baos.toByteArray();
    }
}
