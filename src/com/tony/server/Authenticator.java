package com.tony.server;

import com.tony.server.decoder.UserInfoDecoder;
import com.tony.server.encoder.SHA1Encoder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;

public class Authenticator {
    private static final int ENCODED_USER_INFO = 1;
    private final Logger logger;
    private ArrayList<Request> authorizationList;
    private ArrayList<String> authorizedUsers;

    public Authenticator(Logger logger) {
        this.logger = logger;
        this.authorizationList = new ArrayList<>();
        this.authorizedUsers = new ArrayList<>();
    }

    public void addToAuthorizationList(Request request) {
       authorizationList.add(request);
    }

    public void addToAuthorizedUsers(String userInfo) {
        authorizedUsers.add(userInfo);
    }

    public ArrayList<Request> getAuthorizationList() {
        return authorizationList;
    }

    public ArrayList<String> getAuthorizedUsers() {
        return authorizedUsers;
    }

    public boolean requiresAuthorization(Request request) {
        return authorizationList.contains(request);
    }

    public boolean isAuthorized(Request request) {
        HashMap<String, String> headers = request.getHeaders();
        if(headers.containsKey("Authorization")){
            String authorizationValue = headers.get("Authorization");
            String encodedUserInfo = authorizationValue.split(" ")[ENCODED_USER_INFO];
            return authorizedUsers.contains(UserInfoDecoder.decode(encodedUserInfo, logger));
        }
        return false;
    }

    public boolean matchesEtag(String etag, String filePath) {
        File currentFile = new File(filePath);

        if(!currentFile.isFile()){
            return false;
        }
        try {
            byte[] currentFileText = Files.readAllBytes(currentFile.toPath());
            String currentFileEtag = SHA1Encoder.encode(currentFileText, logger);
            return currentFileEtag.contains(etag);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
