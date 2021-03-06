package com.tony.server.response;

import com.tony.server.response.ParameterDecodeResponse;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ParameterDecodeResponseTest {
    @Test
    public void testRespondsWithDecodedParameters() throws Exception {
        String encodedURI = "/parameters?variable_1=Operators%20%3C%2C%20%3E%" +
                "2C%20%3D%2C%20!%3D%3B%20%2B%2C%20-%2C%20*%2C%20%26%2C%20%40%" +
                "2C%20%23%2C%20%24%2C%20%5B%2C%20%5D%3A%20%22is%20that%20all" +
                "%22%3F&variable_2=stuff";
        ParameterDecodeResponse response = new ParameterDecodeResponse(encodedURI);

        String expectedResponse = "HTTP/1.1 200 OK\r\n" +
                "\r\n" +
                "variable_1 = Operators <, >, =, !=; +, -, *, &, @, #, $, [, ]: \"is that all\"?\r\n" +
                "variable_2 = stuff";
        assertEquals(expectedResponse, new String(response.respond()));
    }
}