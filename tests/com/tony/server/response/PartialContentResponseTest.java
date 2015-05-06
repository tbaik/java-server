package com.tony.server.response;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PartialContentResponseTest extends FileTest{
    private String filePath;
    private PartialContentResponse response;
    private String wholeFileString;

    @Before
    public void setUp() throws Exception {
        filePath = System.getProperty("user.dir") + "/partial_test";
        wholeFileString = "this is a partial content response test";
        writeTestFile(filePath, wholeFileString);
        response = new PartialContentResponse(filePath);
    }

    @After
    public void tearDown() throws Exception {
        deleteTestFile(filePath);
    }

    @Test
    public void testSettingRangeHeaderMakesItRespondAccordingToIt() throws Exception {
        response.setRangeHeader("byte=0-4");
        String expectedResponse = "HTTP/1.1 206 Partial Content\r\n\r\nthis ";

        assertEquals(expectedResponse, new String(response.respond()));
    }

    @Test
    public void testGetPartialContentReturnsReducedContentForBeginningAndEnd() throws Exception {
        response.setRangeHeader("byte=1-7");
        String expectedPartialContent = "his is ";

        assertEquals(expectedPartialContent, response.getPartialContent(wholeFileString));
    }

    @Test
    public void testGetPartialContentReturnsReducedContentWhenOnlyBeginning() throws Exception {
        response.setRangeHeader("byte=1-");
        String expectedPartialContent = "his is a partial content response test";

        assertEquals(expectedPartialContent, response.getPartialContent(wholeFileString));
    }

    @Test
    public void testGetPartialContentReturnsReducedContentWhenOnlyEnding() throws Exception {
        response.setRangeHeader("byte=-6");
        String expectedPartialContent = "e test";

        assertEquals(expectedPartialContent, response.getPartialContent(wholeFileString));
    }
}