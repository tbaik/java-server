package com.tony.server.response;

import com.tony.server.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

public class LogResponseTest {
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
    public void testResponseContains200AndLogsFromLogger() throws Exception {
        Logger logger = new Logger();
        LogResponse logResponse = new LogResponse(logger);
        logger.storeLog("This is a log");

        assertEquals("HTTP/1.1 200 OK\nThis is a log\n",
                new String(logResponse.respond()));
    }
}