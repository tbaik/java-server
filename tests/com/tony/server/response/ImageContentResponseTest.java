package com.tony.server.response;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ImageContentResponseTest {
    @Test
    public void testResponseHasCorrectContentTypeJPEGWhenGivenJPEGFile() throws Exception {
        ImageContentResponse imageContentResponse = new ImageContentResponse(System.getProperty("user.dir") + "/public/image.jpeg");
        String expectedResponse = "HTTP/1.1 200 OK\n" +
                "Content-Type: image/jpeg";

        assertEquals(expectedResponse, new String(imageContentResponse.respond()).substring(0, 40));
    }

    @Test
    public void testResponseHasCorrectContentTypePNGWhenGivenPNGFile() throws Exception {
        ImageContentResponse imageContentResponse = new ImageContentResponse(System.getProperty("user.dir") + "/public/image.png");
        String expectedResponse = "HTTP/1.1 200 OK\n" +
                "Content-Type: image/png";

        assertEquals(expectedResponse, new String(imageContentResponse.respond()).substring(0,39));
    }

    @Test
    public void testImageTypeReturnsCorrectTypeGivenPath() throws Exception {
        String pngPath = System.getProperty("user.dir") + "/public/image.png";
        assertEquals("image/png", ImageContentResponse.imageType(pngPath));
    }
}