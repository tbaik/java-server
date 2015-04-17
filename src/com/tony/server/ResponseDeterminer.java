package com.tony.server;

import com.tony.server.response.GetResponse;
import com.tony.server.response.Response;

public class ResponseDeterminer {
    private final Router router;

    public ResponseDeterminer(Router router) {
        this.router = router;
    }

    public Response httpGetHandler() {
        return new GetResponse();
    }
}
