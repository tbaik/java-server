package com.tony.server;

import org.junit.Test;

import static org.junit.Assert.*;

public class ArgumentParserTest {
    @Test
    public void testGetPortDefault5000() throws Exception {
        ArgumentParser ap = new ArgumentParser();
        assertEquals(5000, ap.getPort());
    }

    @Test
    public void testGetDirectoryDefaultPublicDir() throws Exception {
        ArgumentParser ap = new ArgumentParser();
        assertEquals(System.getProperty("user.dir") + "/public/", ap.getDirectory());
    }

    @Test
    public void testParseArgumentsParsesPort() throws Exception {
        ArgumentParser ap = new ArgumentParser();
        ap.parseArguments(new String[]{"-p", "4000"});
        assertEquals(4000, ap.getPort());
    }

    @Test
    public void testParseArgumentsParsesDirectory() throws Exception {
        ArgumentParser ap = new ArgumentParser();
        ap.parseArguments(new String[]{"-d", "/path/to/somewhere"});
        assertEquals("/path/to/somewhere", ap.getDirectory());
    }

    @Test
    public void testParseArgumentsParsesBothArguments() throws Exception {
        ArgumentParser ap = new ArgumentParser();
        ap.parseArguments(new String[]{"-p", "4000", "-d", "/path/to/somewhere"});
        assertEquals(4000, ap.getPort());
        assertEquals("/path/to/somewhere", ap.getDirectory());
    }
}