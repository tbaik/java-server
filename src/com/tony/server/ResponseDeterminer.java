package com.tony.server;

import com.tony.server.response.FourOhFourResponse;
import com.tony.server.response.MethodNotAllowedResponse;
import com.tony.server.response.ParameterDecodeResponse;
import com.tony.server.response.Response;
import com.tony.server.response.UnauthorizedResponse;

import java.util.ArrayList;

public class ResponseDeterminer {
    private final Router router;
    private final ArrayList<String> uriList;
    private final Authenticator authenticator;

    public ResponseDeterminer(Router router, ArrayList<String> uriList, Authenticator authenticator) {
        this.router = router;
        this.uriList = uriList;
        this.authenticator = authenticator;
    }

    public Response determineResponse(Request request) {
        if(router.hasRoute(request)){
            if(authenticator.requiresAuthentication(request)
                    && !authenticator.isAuthenticated(request)){
                return new UnauthorizedResponse();
            } else {
                return router.route(request);
            }
        } else if(uriList.contains(request.getURI())){
            return new MethodNotAllowedResponse();
        } else if(request.getURI().startsWith("/parameters?")){
            return new ParameterDecodeResponse(request.getURI());
        }
        return new FourOhFourResponse();
    }
}
