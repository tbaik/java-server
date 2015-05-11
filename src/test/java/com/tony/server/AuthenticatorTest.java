package com.tony.server;

import com.tony.server.Authenticator;
import com.tony.server.Logger;
import com.tony.server.Request;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AuthenticatorTest {
    private Authenticator authenticator;
    private Request getRequest;

    @Before
    public void setUp() throws Exception {
        System.setOut(new PrintStream(new ByteArrayOutputStream()));
        authenticator = new Authenticator(new Logger());
        getRequest = new Request("GET", "/blah");
        authenticator.addToAuthenticationList(getRequest);
        authenticator.addToAuthenticatedUsers("admin:hunter2");
    }

    @After
    public void tearDown() throws Exception {
        System.setOut(null);
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
}
