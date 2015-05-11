package com.tony.server.response;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FileContentResponseTest extends FileTest{
    @Test
    public void testResponseContainsFileContentsWhenGivenFileName() throws Exception {
        String testFilePath = System.getProperty("user.dir") + "/testFile1";
        writeTestFile(testFilePath, "file1 contents");

        FileContentResponse fileContentResponse = new FileContentResponse(testFilePath);
        String expectedResponse = "HTTP/1.1 200 OK\r\n\r\n" +
                "file1 contents";

        assertEquals(expectedResponse, new String(fileContentResponse.respond()));
        deleteTestFile(testFilePath);
    }
}