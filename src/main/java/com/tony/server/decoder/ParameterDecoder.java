package com.tony.server.decoder;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class ParameterDecoder {
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
