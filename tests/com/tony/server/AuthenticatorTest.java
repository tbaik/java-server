package com.tony.server;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AuthenticatorTest {
    @Test
    public void testAddToAuthenticationList() throws Exception {
        Authenticator authenticator = new Authenticator();
        authenticator.addToAuthenticationList(new Request("GET", "/blah"));

        assertTrue(authenticator.getAuthenticationList().contains(new Request("GET", "/blah")));
    }

    @Test
    public void testAddToAuthenticatedUsers() throws Exception {
        Authenticator authenticator = new Authenticator();
        authenticator.addToAuthenticatedUsers("admin:hunter2");

        assertTrue(authenticator.getAuthenticatedUsers().contains("admin:hunter2"));
    }

    @Test
    public void testRequiresAuthenticationReturnsTrueIfIncludedInList() throws Exception {
        Authenticator authenticator = new Authenticator();
        authenticator.addToAuthenticationList(new Request("GET", "/blah"));

        assertTrue(authenticator.requiresAuthentication(new Request("GET", "/blah")));
    }

    @Test
    public void testRequiresAuthenticationReturnsFalseIfNotInList() throws Exception {
        Authenticator authenticator = new Authenticator();
        authenticator.addToAuthenticationList(new Request("GET", "/blah"));

        assertFalse(authenticator.requiresAuthentication(new Request("GET", "/somethingElse")));
    }

    @Test
    public void testIsAuthenticatedReturnsTrueIfMatchesUserInfo() throws Exception {
        Authenticator authenticator = new Authenticator();
        authenticator.addToAuthenticationList(new Request("GET", "/blah"));
        authenticator.addToAuthenticatedUsers("admin:hunter2");

        Request authenticatedRequest = new Request("GET", "/blah");
        authenticatedRequest.addToHeaders("Authorization", "Basic YWRtaW46aHVudGVyMg==");
        assertTrue(authenticator.isAuthenticated(authenticatedRequest));
    }

    @Test
    public void testIsAuthenticatedReturnsFalseIfErrorOnDecode() throws Exception {
        Authenticator authenticator = new Authenticator();
        authenticator.addToAuthenticationList(new Request("GET", "/blah"));
        authenticator.addToAuthenticatedUsers("admin:hunter2");

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
