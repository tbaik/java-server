package com.tony.server.response;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ImageContentResponseTest extends FileTest{
    @Test
    public void testResponseHasCorrectContentTypeJPEGWhenGivenJPEGFile() throws Exception {
        String testFilePath = System.getProperty("user.dir") + "/testImage.jpeg";
        writeTestFile(testFilePath, "image file");
        ImageContentResponse imageContentResponse = new ImageContentResponse(testFilePath);
        String expectedResponse = "HTTP/1.1 200 OK\r\n" +
                "Content-Type: image/jpeg";

        assertEquals(expectedResponse, new String(imageContentResponse.respond()).substring(0, 41));
        deleteTestFile(testFilePath);
    }

    @Test
    public void testResponseHasCorrectContentTypePNGWhenGivenPNGFile() throws Exception {

        String testFilePath = System.getProperty("user.dir") + "/testImage.png";
        writeTestFile(testFilePath, "image file");
        ImageContentResponse imageContentResponse = new ImageContentResponse(testFilePath);
        String expectedResponse = "HTTP/1.1 200 OK\r\n" +
                "Content-Type: image/png";

        assertEquals(expectedResponse, new String(imageContentResponse.respond()).substring(0, 40));
        deleteTestFile(testFilePath);
    }

    @Test
    public void testImageTypeReturnsCorrectTypeGivenPath() throws Exception {
        String pngPath = System.getProperty("user.dir") + "/testImage.png";
        writeTestFile(pngPath, "image file");

        assertEquals("image/png", ImageContentResponse.imageType(pngPath));
        deleteTestFile(pngPath);
    }

    @Test
    public void testRespondsWith500ErrorIfNoSuchFile() throws Exception {
        ImageContentResponse imageContentResponse = new ImageContentResponse(System.getProperty("user.dir") + "/noimage.png");
        String expectedResponse = "HTTP/1.1 500 Internal Server Error\r\n" +
                "\r\n" +
                "java.nio.file.NoSuchFileException";

        assertTrue(new String(imageContentResponse.respond()).contains(expectedResponse));
    }
}