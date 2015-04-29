package com.tony.server.response;

import com.tony.server.Main;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class DirectoryResponseTest {
    @Test
    public void testResponseBodyContainsLinks() throws Exception {
        String directoryPath = System.getProperty("user.dir") + "/public/";
        ArrayList<String> uriList = Main.createURIList(directoryPath);
        DirectoryResponse directoryResponse =
                new DirectoryResponse(uriList);

        String expectedResponse = "HTTP/1.1 200 OK\n" +
                "\n" +
                "<a href=\"/file1\">/file1</a>\n" +
                "<a href=\"/file2\">/file2</a>\n" +
                "<a href=\"/image.gif\">/image.gif</a>\n" +
                "<a href=\"/image.jpeg\">/image.jpeg</a>\n" +
                "<a href=\"/image.png\">/image.png</a>\n" +
                "<a href=\"/partial_content.txt\">/partial_content.txt</a>\n" +
                "<a href=\"/patch-content.txt\">/patch-content.txt</a>\n" +
                "<a href=\"/text-file.txt\">/text-file.txt</a>\n";
        assertEquals(expectedResponse,
                new String(directoryResponse.respond()));
    }

    @Test
    public void testCreateBodyFromLinks() throws Exception {
        String directoryPath = System.getProperty("user.dir") + "/public/";
        ArrayList<String> uriList = Main.createURIList(directoryPath);

        String expectedResponse = "<a href=\"/file1\">/file1</a>\n" +
                "<a href=\"/file2\">/file2</a>\n" +
                "<a href=\"/image.gif\">/image.gif</a>\n" +
                "<a href=\"/image.jpeg\">/image.jpeg</a>\n" +
                "<a href=\"/image.png\">/image.png</a>\n" +
                "<a href=\"/partial_content.txt\">/partial_content.txt</a>\n" +
                "<a href=\"/patch-content.txt\">/patch-content.txt</a>\n" +
                "<a href=\"/text-file.txt\">/text-file.txt</a>\n";
        assertEquals(expectedResponse,
                DirectoryResponse.createBodyFromLinks(uriList));
    }
}