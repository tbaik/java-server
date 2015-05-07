package com.tony.server.response;

import com.tony.server.decoder.ParameterDecoder;

public class ParameterDecodeResponse extends Response{
    public ParameterDecodeResponse(String encodedUri) {
        setStatus(Status.OK);
        setBody(ParameterDecoder.decodeURI(encodedUri));
    }
}
