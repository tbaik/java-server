package com.tony.server.encoder;

import com.tony.server.Logger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA1Encoder {
    public static String encode(byte[] text, Logger logger) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            logger.storeLog(e.toString());
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
