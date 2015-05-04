package com.tony.server.response;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FileContentResponseTest extends FileTest{
    @Test
    public void testResponseContainsFileContentsWhenGivenFileName() throws Exception {
        String testFilePath = System.getProperty("user.dir") + "/testFile1";
        writeTestFile(testFilePath, "file1 contents");

        FileContentResponse fileContentResponse = new FileContentResponse(testFilePath);
        String expectedResponse = "HTTP/1.1 200 OK\n\n" +
                "file1 contents";

        assertEquals(expectedResponse, new String(fileContentResponse.respond()));
        deleteTestFile(testFilePath);
    }

    @Test
    public void testRespondsWith500ResponseOnWrongFile() throws Exception {
        FileContentResponse fileContentResponse = new FileContentResponse(System.getProperty("user.dir") + "/nonexistantFile");
        String expectedResponse = "HTTP/1.1 500 Internal Server Error\n" +
                "\n" +
                "No Such File!";

        assertEquals(expectedResponse, new String(fileContentResponse.respond()));
    }
}