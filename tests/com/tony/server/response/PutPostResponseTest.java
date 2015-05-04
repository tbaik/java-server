package com.tony.server.response;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PutPostResponseTest {
    @Test
    public void testResponseBuiltWithCorrectResponseAndFileIsCreated() throws Exception {
        new File(System.getProperty("user.dir") + "/public/form").delete();
        PutPostResponse putPostResponse = new PutPostResponse(System.getProperty("user.dir") + "/public/form");
        String expectedResponse = "HTTP/1.1 200 OK\n";

        assertEquals(expectedResponse, new String(putPostResponse.respond()));
        assertTrue(new File(System.getProperty("user.dir") + "/public/form").isFile());

        new File(System.getProperty("user.dir") + "/public/form").delete();
    }

    @Test
    public void testPutPostContentDumpsBodyIntoFile() throws Exception {
        new File(System.getProperty("user.dir") + "/public/form").delete();
        PutPostResponse putPostResponse = new PutPostResponse(System.getProperty("user.dir") + "/public/form");
        putPostResponse.putPostContent();
        assertTrue(new File(System.getProperty("user.dir") + "/public/form").isFile());

        new File(System.getProperty("user.dir") + "/public/form").delete();
    }
}