package com.tony.server.response;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;

public class ParameterDecodeResponse extends Response{
    public ParameterDecodeResponse(String encodedUri) {
        setStatus(Status.OK);
        setHeaders(new HashMap<>());
        setBody(decodeURI(encodedUri));
    }

    public static String decodeURI(String encodedURI) {
        encodedURI = encodedURI.replace("&", "\r\n");
        encodedURI = encodedURI.replace("=", " = ");
        String decodedURI = "";
        try {
            decodedURI =  URLDecoder.decode(encodedURI, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        decodedURI = decodedURI.replace("/parameters?", "");
        return decodedURI;
    }
}
