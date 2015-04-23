package com.tony.server;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class MainTest {
    @Test
    public void testCreateURIListHasAllURIFromDirectory() throws Exception {
        String directory = System.getProperty("user.dir") + "/public/";
        ArrayList<String> uriList = Main.createURIList(directory);
        assertEquals("/file1", uriList.get(1));
        assertEquals("/file2", uriList.get(2));
        assertEquals("/image.gif", uriList.get(3));
        assertEquals("/image.jpeg", uriList.get(4));
        assertEquals("/image.png", uriList.get(5));
        assertEquals("/partial_content.txt", uriList.get(6));
        assertEquals("/patch-content.txt", uriList.get(7));
        assertEquals("/text-file.txt", uriList.get(8));
    }
}