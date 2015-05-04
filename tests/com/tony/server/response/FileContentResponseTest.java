package com.tony.server.response;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FileContentResponseTest {
    @Test
    public void testResponseContainsFileContentsWhenGivenFileName() throws Exception {
        FileContentResponse fileContentResponse = new FileContentResponse(System.getProperty("user.dir") + "/public/file1");
        String expectedResponse = "HTTP/1.1 200 OK\n\n" +
                "file1 contents";

        assertEquals(expectedResponse, new String(fileContentResponse.respond()));
    }

    @Test
    public void testRespondsWith500ResponseOnWrongFile() throws Exception {
        FileContentResponse fileContentResponse = new FileContentResponse(System.getProperty("user.dir") + "/public/nonexistantFile");
        String expectedResponse = "HTTP/1.1 500 Internal Server Error\n" +
                "\n" +
                "No Such File!";

        assertEquals(expectedResponse, new String(fileContentResponse.respond()));
    }
}