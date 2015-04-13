package com.tony.server;

import org.junit.Test;

import static org.junit.Assert.*;

public class ArgumentParserTest {
    @Test
    public void getPortDefault5000Test() throws Exception {
        ArgumentParser ap = new ArgumentParser(new String[]{"hi"});
        assertEquals(5000, ap.getPort());
    }

    @Test
    public void getDirectoryDefaultPublicDirTest() throws Exception {
        ArgumentParser ap = new ArgumentParser(new String[]{"hi"});
        assertEquals(System.getProperty("user.dir") + "/public/", ap.getDirectory());
    }

    @Test
    public void parsesPortTest() throws Exception {
        ArgumentParser ap = new ArgumentParser(new String[]{"-p", "4000"});
        assertEquals(4000, ap.getPort());
    }

    @Test
    public void parsesDirectoryTest() throws Exception {
        ArgumentParser ap = new ArgumentParser(new String[]{"-d", "/path/to/somewhere"});
        assertEquals("/path/to/somewhere", ap.getDirectory());
    }

    @Test
    public void parsesBothArguments() throws Exception {
        ArgumentParser ap = new ArgumentParser(new String[]{"-p", "4000", "-d", "/path/to/somewhere"});
        assertEquals(4000, ap.getPort());
        assertEquals("/path/to/somewhere", ap.getDirectory());
    }
}