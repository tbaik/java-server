package com.tony.server;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
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
            return authorizedUsers.contains(decodeUserInfo(encodedUserInfo));
        }
        return false;
    }

    public String decodeUserInfo(String encodedUserInfo) {
        String decodedUserInfo;
        try{
           decodedUserInfo = new String(Base64.getDecoder().decode(encodedUserInfo));
        } catch(IllegalArgumentException e){
            logger.storeLog(e.toString());
            return "Error in decoding.";
        }
        return decodedUserInfo;
    }

    public boolean matchesEtag(String etag, String filePath) {
        File currentFile = new File(filePath);

        if(!currentFile.isFile()){
            return false;
        }
        try {
            byte[] currentFileText = Files.readAllBytes(currentFile.toPath());
            String currentFileEtag = encodeWithSHA1(currentFileText);
            return currentFileEtag.contains(etag);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;

    }

    public String encodeWithSHA1(byte[] text) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        messageDigest.update(text);
        text = messageDigest.digest();

        StringBuilder sb = new StringBuilder(text.length * 2);
        for(byte textByte: text) {
            sb.append(String.format("%02x", textByte & 0xff));
        }
        return sb.toString();
    }
}
