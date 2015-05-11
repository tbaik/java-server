package com.tony.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public final class RequestParser {
    private static int HTTPMETHOD = 0;
    private static int URI = 1;

    private static int KEY = 0;
    private static int VALUE = 1;

    public static Request parseRequest(InputStream inputStream) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        Request request = new Request();

        try {
            String firstLine = reader.readLine();
            String allHeaderLines = grabHeaders(reader);

            parseFirstLine(request, firstLine);
            parseHeaders(request, allHeaderLines);
            if(reader.ready()){
                int contentLength = Integer.parseInt(request.getHeaders().get("Content-Length"));
                String allBodyLines = grabBody(reader, contentLength);
                request.setBody(allBodyLines);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return request;
    }

    public static void parseFirstLine(Request request, String firstLine) {
       String[] splitFirstLine = firstLine.split(" ");

       request.setHttpMethod(splitFirstLine[HTTPMETHOD]);
       request.setUri(splitFirstLine[URI]);
    }

    public static void parseHeaders(Request request, String headers) {
        String[] splitHeaders = headers.split("\n");

        for(int count = 0; count < splitHeaders.length; count++){
            String[] header = splitHeaders[count].split(": ");
            request.addToHeaders(header[KEY], header[VALUE]);
        }
    }

    public static String grabHeaders(BufferedReader reader) {
        String allLines = "";
        try {
            for(String line = reader.readLine(); line != null; line = reader.readLine()){
                allLines += line + "\n";
                if(line.equals("")){
                   break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return allLines;
    }

    public static String grabBody(BufferedReader reader, int contentLength) {
        char[] body = new char[contentLength];
        try {
            reader.read(body);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String(body);
    }
}
