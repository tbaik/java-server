package com.tony.server;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.io.PrintWriter;

import static org.junit.Assert.*;

public class AuthenticatorTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private Authenticator authenticator;
    private Request getRequest;

    @Before
    public void setUp() throws Exception {
        System.setOut(new PrintStream(outContent));
        authenticator = new Authenticator(new Logger());
        getRequest = new Request("GET", "/blah");
        authenticator.addToAuthorizationList(getRequest);
        authenticator.addToAuthorizedUsers("admin:hunter2");
    }

    @After
    public void tearDown() throws Exception {
        System.setOut(null);
    }

    @Test
    public void testAddToAuthenticationList() throws Exception {
        assertTrue(authenticator.getAuthorizationList().contains(getRequest));
    }

    @Test
    public void testAddToAuthenticatedUsers() throws Exception {
        assertTrue(authenticator.getAuthorizedUsers().contains("admin:hunter2"));
    }

   @Test
    public void testRequiresAuthenticationReturnsTrueIfIncludedInList() throws Exception {
        assertTrue(authenticator.requiresAuthorization(getRequest));
    }

    @Test
    public void testRequiresAuthenticationReturnsFalseIfNotInList() throws Exception {
        assertFalse(authenticator.requiresAuthorization(new Request("GET", "/somethingElse")));
    }

    @Test
    public void testIsAuthenticatedReturnsTrueIfMatchesUserInfo() throws Exception {
        Request authenticatedRequest = new Request("GET", "/blah");
        authenticatedRequest.addToHeaders("Authorization", "Basic YWRtaW46aHVudGVyMg==");
        assertTrue(authenticator.isAuthorized(authenticatedRequest));
    }

    @Test
    public void testIsAuthenticatedReturnsFalseIfErrorOnDecode() throws Exception {
        Request authenticatedRequest = new Request("GET", "/blah");
        authenticatedRequest.addToHeaders("Authorization", "Basic WrongEncodingLength=123");
        assertFalse(authenticator.isAuthorized(authenticatedRequest));
    }

    @Test
    public void testDecodeUserInfoReturnsCorrectDecoding() throws Exception {
        assertEquals("admin:hunter2",
                authenticator.decodeUserInfo("YWRtaW46aHVudGVyMg=="));
    }

    @Test
    public void testDecodeUserInfoReturnsErrorStringIfDecodingFails() throws Exception {
       assertEquals("Error in decoding.", authenticator.decodeUserInfo("YWRtaW46aHVudGVyMg="));
    }

    @Test
    public void testStoresExceptionInLogIfDecodingThrowsException() throws Exception {
        authenticator.decodeUserInfo("YWRtaW46aHVudGVyMg=");
        String expectedErrorLog = "java.lang.IllegalArgumentException: Input byte array has wrong 4-byte ending unit\n";

        assertEquals(expectedErrorLog, outContent.toString());
    }
    public void testMatchesEtag() throws Exception {
        PrintWriter writer = new PrintWriter(System.getProperty("user.dir") + "/public/testEtag", "UTF-8");
        writer.print("normal text");
        writer.close();

        String normalTextAsEtag = "1e63fd95c77abc6e56efcc92065f3de9cbcc0941";

        assertTrue(authenticator.matchesEtag(normalTextAsEtag,
                System.getProperty("user.dir") + "/public/testEtag"));

        new File(System.getProperty("user.dir") + "/public/testEtag").delete();
    }

    @Test
    public void testEncodeWithSHA1ReturnsCorrectSHA1Encoding() throws Exception {
        byte[] text = "text".getBytes();
        String textAsSHA1 = "372ea08cab33e71c02c651dbc83a474d32c676ea";
        assertEquals(textAsSHA1, authenticator.encodeWithSHA1(text));
    }
}
