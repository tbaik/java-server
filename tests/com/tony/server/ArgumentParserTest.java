package com.tony.server;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class ArgumentParserTest {
    private ArgumentParser argumentParser;

    @Before
    public void setUp() throws Exception {
        argumentParser = new ArgumentParser();
    }

    @Test
    public void testGetPortDefault5000() throws Exception {
        assertEquals(5000, argumentParser.getPort());
    }

    @Test
    public void testGetDirectoryDefaultPublicDir() throws Exception {
        assertEquals(System.getProperty("user.dir") + "/public/",
                argumentParser.getDirectory());
    }

    @Test
    public void testParseArgumentsParsesPort() throws Exception {
        argumentParser.parseArguments(new String[]{"-p", "4000"});
        assertEquals(4000, argumentParser.getPort());
    }

    @Test
    public void testParseArgumentsParsesDirectory() throws Exception {
        argumentParser.parseArguments(new String[]{"-d", "/path/to/somewhere"});
        assertEquals("/path/to/somewhere", argumentParser.getDirectory());
    }

    @Test
    public void testParseArgumentsParsesBothArguments() throws Exception {
        argumentParser.parseArguments(new String[]{"-p", "4000", "-d", "/path/to/somewhere"});
        assertEquals(4000, argumentParser.getPort());
        assertEquals("/path/to/somewhere", argumentParser.getDirectory());
    }
}