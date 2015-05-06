package com.tony.server;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AuthenticatorTest {
    private Authenticator authenticator;
    private Request getRequest;

    @Before
    public void setUp() throws Exception {
        authenticator = new Authenticator();
        getRequest = new Request("GET", "/blah");
        authenticator.addToAuthenticationList(getRequest);
        authenticator.addToAuthenticatedUsers("admin:hunter2");
    }

    @Test
    public void testAddToAuthenticationList() throws Exception {
        assertTrue(authenticator.getAuthenticationList().contains(getRequest));
    }

    @Test
    public void testAddToAuthenticatedUsers() throws Exception {
        assertTrue(authenticator.getAuthenticatedUsers().contains("admin:hunter2"));
    }

    @Test
    public void testRequiresAuthenticationReturnsTrueIfIncludedInList() throws Exception {
        assertTrue(authenticator.requiresAuthentication(getRequest));
    }

    @Test
    public void testRequiresAuthenticationReturnsFalseIfNotInList() throws Exception {
        assertFalse(authenticator.requiresAuthentication(new Request("GET", "/somethingElse")));
    }

    @Test
    public void testIsAuthenticatedReturnsTrueIfMatchesUserInfo() throws Exception {
        Request authenticatedRequest = new Request("GET", "/blah");
        authenticatedRequest.addToHeaders("Authorization", "Basic YWRtaW46aHVudGVyMg==");
        assertTrue(authenticator.isAuthenticated(authenticatedRequest));
    }

    @Test
    public void testIsAuthenticatedReturnsFalseIfErrorOnDecode() throws Exception {
        Request authenticatedRequest = new Request("GET", "/blah");
        authenticatedRequest.addToHeaders("Authorization", "Basic WrongEncodingLength=123");
        assertFalse(authenticator.isAuthenticated(authenticatedRequest));
    }

    @Test
    public void testDecodeUserInfoReturnsCorrectDecoding() throws Exception {
        assertEquals("admin:hunter2",
                Authenticator.decodeUserInfo("YWRtaW46aHVudGVyMg=="));
    }

    @Test
    public void testDecodeUserInfoReturnsErrorStringIfDecodingFails() throws Exception {
       assertEquals("Error in decoding.", Authenticator.decodeUserInfo("YWRtaW46aHVudGVyMg="));
    }
}
