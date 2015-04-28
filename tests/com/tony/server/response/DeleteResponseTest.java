package com.tony.server.response;

import org.junit.Test;

import java.io.File;
import java.io.PrintWriter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DeleteResponseTest {
    @Test
    public void testDeletesResponseOnRespond() throws Exception {
        DeleteResponse deleteResponse = new DeleteResponse(System.getProperty("user.dir") + "/public/test_form");
        String expectedResponse = "HTTP/1.1 200 OK\n";

        PrintWriter writer = new PrintWriter(System.getProperty("user.dir") + "/public/test_form", "UTF-8");
        writer.println("The first line");
        writer.close();

        assertTrue(new File(System.getProperty("user.dir") + "/public/test_form").isFile());
        assertEquals(expectedResponse, deleteResponse.respond());
        assertFalse(new File(System.getProperty("user.dir") + "/public/test_form").isFile());
    }
}