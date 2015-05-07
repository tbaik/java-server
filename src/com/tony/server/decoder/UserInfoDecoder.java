package com.tony.server.decoder;

import com.tony.server.Logger;

import java.util.Base64;

public class UserInfoDecoder {
    public static String decode(String encodedUserInfo, Logger logger) {
        String decodedUserInfo;
        try{
           decodedUserInfo = new String(Base64.getDecoder().decode(encodedUserInfo));
        } catch(IllegalArgumentException e){
            logger.storeLog(e.toString());
            return "Error in decoding.";
        }
        return decodedUserInfo;
    }

}
