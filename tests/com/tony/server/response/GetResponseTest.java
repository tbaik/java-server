package com.tony.server.response;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;

public class GetResponseTest {
    @Test
    public void testResponseGivenFileHasBodyIfBodyExists() throws Exception {
        GetResponse getResponse = new GetResponse(System.getProperty("user.dir") + "/public/form");

        String expectedResponse = "HTTP/1.1 200 OK\n";
        assertEquals(expectedResponse, new String(getResponse.respond()));

        new File(System.getProperty("user.dir") + "/public/form").delete();
    }
//
//    @Test
//    public void testGetContent() throws Exception {
//        GetResponse getResponse = new GetResponse(System.getProperty("user.dir") + "/public/file1");
//
//        String expectedContent = "file1 content";
//
//        assertEquals(expectedContent, getResponse.getContent());
//    }
}