package com.tony.server.response;

import com.tony.server.Logger;
import com.tony.server.response.LogResponse;
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
        logger.log("This is a log");

        assertEquals("HTTP/1.1 200 OK\r\n\r\nThis is a log\r\n",
                new String(logResponse.respond()));
    }
}