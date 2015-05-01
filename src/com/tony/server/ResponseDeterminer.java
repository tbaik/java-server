package com.tony.server;

import com.tony.server.response.FourOhFourResponse;
import com.tony.server.response.MethodNotAllowedResponse;
import com.tony.server.response.ParameterDecodeResponse;
import com.tony.server.response.PatchResponse;
import com.tony.server.response.PreconditionFailedResponse;
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
            if(authenticator.requiresAuthorization(request)
                    && !authenticator.isAuthorized(request)){
                return new UnauthorizedResponse();
            } else if(request.getHttpMethod().equals("PATCH")){
                String filePath = ((PatchResponse)router.route(request)).getFilePath();
                if(!authenticator.matchesEtag(request.getHeaders().get("If-Match"), filePath)) {
                    return new PreconditionFailedResponse();
                } else {
                    return router.route(request);
                }
            } else {
                return router.route(request);
            }
        } else if(uriList.contains(request.getURI())){
            return new MethodNotAllowedResponse();
        } else if(hasParameters(request)){
            return new ParameterDecodeResponse(request.getURI());
        }
        return new FourOhFourResponse();
    }

    private boolean hasParameters(Request request) {
        return request.getURI().contains("?");
    }
}
