package com.tony.server.response;

import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class ImageContentResponseTest {
    @Test
    public void testResponseContainsImageContentsWhenGivenFileName() throws Exception {
        ImageContentResponse imageContentResponse = new ImageContentResponse(System.getProperty("user.dir") + "/public/image.jpeg");
        String expectedResponse = "HTTP/1.1 200 OK\n\n" +
                "file1 contents";

//        assertEquals(expectedResponse, imageContentResponse.respond());
    }

    @Test
    public void testImageToHexConvertsFile() throws Exception {
        BufferedImage image = ImageIO.read(new File(System.getProperty("user.dir") + "/public/image.jpeg"));
        String actualHex = ImageContentResponse.imageToHex(image);
        String expectedHex = "ffd8 ffe0 0010 4a46 4946 0001 0101 0060\n" +
                "0060 0000 ffe1 0060 4578 6966 0000 4949\n";

//        assertEquals(expectedHex, actualHex);
    }
}