package com.tony.server.response;

import com.tony.server.response.DirectoryResponse;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class DirectoryResponseTest {
    @Test
    public void testResponseBodyContainsLinks() throws Exception {
        ArrayList<String> uriList = new ArrayList<>();
        uriList.add("/file1");
        uriList.add("/file2");
        uriList.add("/image.gif");
        uriList.add("/patch-content.txt");

        DirectoryResponse directoryResponse =
                new DirectoryResponse(uriList);

        String expectedResponse = "HTTP/1.1 200 OK\r\n" +
                "\r\n" +
                "<a href=\"/file1\">/file1</a>\r\n" +
                "<a href=\"/file2\">/file2</a>\r\n" +
                "<a href=\"/image.gif\">/image.gif</a>\r\n" +
                "<a href=\"/patch-content.txt\">/patch-content.txt</a>\r\n";

        assertEquals(expectedResponse,
                new String(directoryResponse.respond()));
    }

    @Test
    public void testCreateBodyFromLinks() throws Exception {
        ArrayList<String> uriList = new ArrayList<>();
        uriList.add("/file1");
        uriList.add("/file2");
        uriList.add("/image.gif");

        String expectedResponse = "<a href=\"/file1\">/file1</a>\r\n" +
                "<a href=\"/file2\">/file2</a>\r\n" +
                "<a href=\"/image.gif\">/image.gif</a>\r\n";

        assertEquals(expectedResponse,
                DirectoryResponse.createBodyFromLinks(uriList));
    }
}