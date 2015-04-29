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
}