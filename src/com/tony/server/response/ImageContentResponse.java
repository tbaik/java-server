package com.tony.server.response;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class ImageContentResponse extends Response{
    public ImageContentResponse(String fileName) {
        setStatusLine("HTTP/1.1 200 OK\n");
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "image/jpeg");
        setHeaders(headers);

        try {
            BufferedImage image = ImageIO.read(new File(fileName));
            String body = imageToHex(image);
            setBody(body);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String imageToHex(BufferedImage image) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "jpeg", baos);
            byte[] imageByteArray = baos.toByteArray();
            return new String(imageByteArray);

//            StringBuilder sb = new StringBuilder(imageByteArray.length * 2);
//            boolean addSpace = false;
//            for(byte imageByte: imageByteArray) {
//                sb.append(String.format("%02x", imageByte & 0xff));
//                if(addSpace){
//                    sb.append(" ");
//                }
//                addSpace = !addSpace;
//            }
//            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "no image";
    }
}
