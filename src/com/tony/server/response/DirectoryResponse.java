package com.tony.server.response;

import java.util.ArrayList;

public class DirectoryResponse extends Response{
    public DirectoryResponse(ArrayList uriList) {
        setStatus(Status.OK);
        setBody(createBodyFromLinks(uriList));
    }

    public static String createBodyFromLinks(ArrayList<String> uriList) {
        String body = "";
        for(String uri : uriList){
           body +=  "<a href=\"" + uri + "\">" + uri + "</a>\r\n";
        }
        return body;
    }
}
