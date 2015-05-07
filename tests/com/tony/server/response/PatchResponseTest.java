package com.tony.server.response;

import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PatchResponseTest extends FileTest{
    private PatchResponse response;
    private String filePath;

    @Before
    public void setUp() throws Exception {
        filePath = System.getProperty("user.dir") + "/patch_test";
        response = new PatchResponse(filePath);
    }

    @Test
    public void testInitializesWithFilePath() throws Exception {
        assertEquals(filePath, response.getFilePath());
    }

    @Test
    public void testResponseReturns204() throws Exception {
        response.setRequestBody("patch body");

        assertEquals("HTTP/1.1 204 No Content\r\n", new String(response.respond()));
        deleteTestFile(filePath);
    }

    @Test
    public void testFileIsChangedWithResponse() throws Exception {
        File testFile = new File(filePath);
        assertFalse(testFile.isFile());

        response.setRequestBody("patch body");
        response.respond();

        assertTrue(testFile.isFile());
        deleteTestFile(filePath);
    }
}