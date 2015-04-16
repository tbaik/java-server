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

    private static int HEADER = 0;
    private static int BODY = 1;

    private RequestParser() {
    }

    public static Request parseRequest(InputStream inputStream) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        Request request = new Request();
        try {
            String firstLine = reader.readLine();
            String[] headersAndBody = splitHeadersAndBody(reader);

            parseFirstLine(request, firstLine);
            parseHeaders(request, headersAndBody[HEADER]);
            if(headersAndBody.length == 2) {
                request.setBody(headersAndBody[BODY]);
            }
        } catch (IOException e) {
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

    public static String[] splitHeadersAndBody(BufferedReader reader) {
        String[] headersAndBody;
        String allLines = "";
        String line;

        try {
            while((line = reader.readLine()) != null) {
                   allLines += line + "\n";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        headersAndBody = allLines.split("\n\n");

        return headersAndBody;
    }
}
