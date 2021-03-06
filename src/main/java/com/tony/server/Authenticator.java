package com.tony.server;

import com.tony.server.decoder.UserInfoDecoder;

import java.util.ArrayList;
import java.util.HashMap;

public class Authenticator {
    private static final int ENCODED_USER_INFO = 1;
    private final Logger logger;
    private ArrayList<Request> authenticationList;
    private ArrayList<String> authenticatedUsers;

    public Authenticator(Logger logger) {
        this.logger = logger;
        this.authenticationList = new ArrayList<>();
        this.authenticatedUsers = new ArrayList<>();
    }

    public void addToAuthenticationList(Request request) {
       authenticationList.add(request);
    }

    public void addToAuthenticatedUsers(String userInfo) {
        authenticatedUsers.add(userInfo);
    }

    public ArrayList<Request> getAuthenticationList() {
        return authenticationList;
    }

    public ArrayList<String> getAuthenticatedUsers() {
        return authenticatedUsers;
    }

    public boolean requiresAuthentication(Request request) {
        return authenticationList.contains(request);
    }

    public boolean isAuthenticated(Request request) {
        HashMap<String, String> headers = request.getHeaders();
        if(headers.containsKey("Authorization")){
            String authorizationValue = headers.get("Authorization");
            String encodedUserInfo = authorizationValue.split(" ")[ENCODED_USER_INFO];
            return authenticatedUsers.contains(UserInfoDecoder.decode(encodedUserInfo, logger));
        }
        return false;
    }

    public Logger getLogger() {
        return logger;
    }
}
