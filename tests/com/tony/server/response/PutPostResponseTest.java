package com.tony.server.response;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PutPostResponseTest {
    @Test
    public void testResponseBuiltWithCorrectResponseAndFileIsCreated() throws Exception {
        String testFilePath = System.getProperty("user.dir") + "/testForm";
        File testFile = new File(testFilePath);
        testFile.delete();

        PutPostResponse putPostResponse = new PutPostResponse(testFilePath);
        String expectedResponse = "HTTP/1.1 200 OK\r\n";

        assertEquals(expectedResponse, new String(putPostResponse.respond()));
        assertTrue(testFile.isFile());

        testFile.delete();
    }

    @Test
    public void testPutPostContentDumpsBodyIntoFile() throws Exception {
        String testFilePath = System.getProperty("user.dir") + "/testForm";
        File testFile = new File(testFilePath);
        testFile.delete();

        PutPostResponse putPostResponse = new PutPostResponse(testFilePath);
        putPostResponse.putPostContent();

        assertTrue(testFile.isFile());

        testFile.delete();
    }
}