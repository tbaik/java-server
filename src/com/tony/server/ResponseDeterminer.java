package com.tony.server;

import com.tony.server.encoder.SHA1Encoder;
import com.tony.server.response.FourOhFourResponse;
import com.tony.server.response.MethodNotAllowedResponse;
import com.tony.server.response.ParameterDecodeResponse;
import com.tony.server.response.PatchResponse;
import com.tony.server.response.PreconditionFailedResponse;
import com.tony.server.response.Response;
import com.tony.server.response.UnauthorizedResponse;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
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
            } else if(request.getHttpMethod().equals("PATCH")){
                String filePath = ((PatchResponse)router.route(request)).getFilePath();
                if(!fileEtagMatchesGivenEtag(filePath, request.getHeaders().get("If-Match"))) {
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

    protected boolean fileEtagMatchesGivenEtag(String filePath, String etag) {
        File currentFile = new File(filePath);

        if(!currentFile.isFile()){
            return false;
        }
        try {
            byte[] currentFileText = Files.readAllBytes(currentFile.toPath());
            String currentFileEtag = SHA1Encoder.encode(currentFileText, authenticator.getLogger());
            return currentFileEtag.equals(etag);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
