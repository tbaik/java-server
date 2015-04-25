package com.tony.server;

import com.tony.server.response.FourOhFourResponse;
import com.tony.server.response.MethodNotAllowedResponse;
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
        } else if(uriList.contains(request.getURI())){
            return new MethodNotAllowedResponse();
        }
        return new FourOhFourResponse();
    }
}
