package com.tony.server.decoder;

import com.tony.server.Logger;
import com.tony.server.decoder.UserInfoDecoder;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

public class UserInfoDecoderTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private Logger logger;

    @Before
    public void setUp() throws Exception {
        logger = new Logger();
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void tearDown() throws Exception {
        System.setOut(null);
    }

    @Test
    public void testDecodeUserInfoReturnsCorrectDecoding() throws Exception {
        Assert.assertEquals("admin:hunter2",
                UserInfoDecoder.decode("YWRtaW46aHVudGVyMg==", logger));
    }

    @Test
    public void testDecodeUserInfoReturnsErrorStringIfDecodingFails() throws Exception {
       assertEquals("Error in decoding.", UserInfoDecoder.decode("YWRtaW46aHVudGVyMg=", logger));
    }

    @Test
    public void testStoresExceptionInLogIfDecodingThrowsException() throws Exception {
        UserInfoDecoder.decode("YWRtaW46aHVudGVyMg=", logger);
        String expectedErrorLog = "java.lang.IllegalArgumentException: Input byte array has wrong 4-byte ending unit\n";

        assertEquals(expectedErrorLog, outContent.toString());
    }
}