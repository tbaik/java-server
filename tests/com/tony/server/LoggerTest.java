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
}