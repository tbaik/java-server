package com.tony.server.response;

import java.util.ArrayList;
import java.util.HashMap;

public class DirectoryResponse extends Response{
    public DirectoryResponse(ArrayList uriList) {
        setStatusLine("HTTP/1.1 200 OK\n");
        setHeaders(new HashMap<>());
        setBody(createBodyFromLinks(uriList));
    }

    public static String createBodyFromLinks(ArrayList<String> uriList) {
        String body = "";
        for(String uri : uriList){
           body +=  "<a href=\"" + uri + "\">" + uri + "</a>\n";
        }
        return body;
    }
}
