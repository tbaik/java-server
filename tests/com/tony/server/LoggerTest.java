package com.tony.server;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

public class LoggerTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void cleanUpStreams() {
        System.setOut(null);
    }

    @Test
    public void testStoreLogAppendsStringToLog() throws Exception {
        Logger logger = new Logger();
        logger.storeLog("Some Log Message");

        assertEquals("Some Log Message\r\n", logger.getLog());
    }

    @Test
    public void testStoreLogPrintsMessageToConsole() throws Exception {
        Logger logger = new Logger();
        logger.storeLog("Some Log Message!");

        assertEquals("Some Log Message!\n", outContent.toString());
    }

    @Test
    public void testShortenMessageIfTooLong() throws Exception {
        Logger logger = new Logger();
        String repeatedString =
                "000000000000000000000000000000000000".replaceAll("0",
                        "Filler Text That does not mean anything at all!" +
                                "There will be more than 5000 characters with this" +
                                "This test is mainly for image logs that contain" +
                                "Thousands of characters for no reason");
        logger.storeLog("HTTP/1.1 200 OK\r\n" +
                "Content-Type: image/jpeg\r\n" +
                repeatedString);
        assertEquals("HTTP/1.1 200 OK\r\n" +
                "Content-Type: image/jpeg\r\n" + repeatedString.substring(0, 157) + "\r\n",
                logger.getLog());
    }
}