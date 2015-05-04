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
        String testFilePath = System.getProperty("user.dir") + "/test_form";
        File testFile = new File(testFilePath);
        DeleteResponse deleteResponse = new DeleteResponse(testFilePath);
        String expectedResponse = "HTTP/1.1 200 OK\n";

        PrintWriter writer = new PrintWriter(testFilePath, "UTF-8");
        writer.println("The first line");
        writer.close();

        assertTrue(testFile.isFile());
        assertEquals(expectedResponse, new String(deleteResponse.respond()));
        assertFalse(testFile.isFile());
    }

}