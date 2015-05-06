package com.tony.server;

import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;

public class Authenticator {
    private static final int ENCODED_USER_INFO = 1;
    private ArrayList<Request> authenticationList;
    private ArrayList<String> authenticatedUsers;

    public Authenticator() {
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
            return authenticatedUsers.contains(decodeUserInfo(encodedUserInfo));
        }
        return false;
    }

    public static String decodeUserInfo(String encodedUserInfo) {
        String decodedUserInfo;
        try{
           decodedUserInfo = new String(Base64.getDecoder().decode(encodedUserInfo));
        } catch(IllegalArgumentException e){
           return "Error in decoding.";
        }
        return decodedUserInfo;
    }
}
