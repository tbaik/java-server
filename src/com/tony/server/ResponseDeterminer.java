package com.tony.server;

import com.tony.server.response.GetResponse;
import com.tony.server.response.Response;

import java.util.ArrayList;

public class ResponseDeterminer {
    private final Router router;
    private final ArrayList<String> uriList;

    public ResponseDeterminer(Router router, ArrayList<String> uriList) {
        this.router = router;
        this.uriList = uriList;
    }

    public Response determineResponse(Request request) {
        if(router.hasRoute(request)){
            return router.route(request);
        }
        return new GetResponse();
    }
}
