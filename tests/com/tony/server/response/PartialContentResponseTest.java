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

    @Test
    public void testGetRange() throws Exception {
        response.setRangeHeader("byte=1-7");
        String expectedRangeString = "1-7";

        assertEquals(expectedRangeString, response.getRange());
    }

    @Test
    public void testGetBeginningIndexReturnsLengthMinusEndingIndexWhenStartsWithIndexSeparator() throws Exception {
        String range = "-6";
        String[] indices = range.split("-");
        int expectedBeginningIndex = wholeFileString.length() - 6;

        assertEquals(expectedBeginningIndex,
                response.getBeginningIndex(wholeFileString.length(), range, indices));
    }

    @Test
    public void testGetBeginningIndexReturnsFirstIntegerElseWise() throws Exception {
        String range = "2-6";
        String[] indices = range.split("-");
        int expectedBeginningIndex = 2;

        assertEquals(expectedBeginningIndex,
                response.getBeginningIndex(wholeFileString.length(), range, indices));
    }

    @Test
    public void testGetEndingIndexReturnsWholeFileLengthIfRangeStartsWithIndexSeparator() throws Exception {
        String range = "-6";
        String[] indices = range.split("-");
        int expectedEndingIndex = wholeFileString.length();

        assertEquals(expectedEndingIndex,
                response.getEndingIndex(wholeFileString.length(), range, indices));
    }

    @Test
    public void testGetEndingIndexReturnsWholeFileLengthIfRangeEndsWithIndexSeparator() throws Exception {
        String range = "6-";
        String[] indices = range.split("-");
        int expectedEndingIndex = wholeFileString.length();

        assertEquals(expectedEndingIndex,
                response.getEndingIndex(wholeFileString.length(), range, indices));
    }

    @Test
    public void testGetEndingIndexReturnsGivenEndingIndexPlusOneWhenHasBothIndices() throws Exception {
        String range = "2-6";
        String[] indices = range.split("-");
        int expectedEndingIndex = 7;

        assertEquals(expectedEndingIndex,
                response.getEndingIndex(wholeFileString.length(), range, indices));
    }
}