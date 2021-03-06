package com.tony.server.decoder;

import com.tony.server.decoder.ParameterDecoder;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ParameterDecoderTest {
    @Test
    public void testReturnEmptyStringForNoQueryParams() throws Exception {
        String uri = "/parameters?";
        String expected = "";

        Assert.assertEquals(expected, ParameterDecoder.decodeURI(uri));
    }

    @Test
    public void testDecodeForOneParam() throws Exception {
        String encodedURI = "/parameters?variable=%3C%2C%20%3E%2C%20%3D";
        String expectedString = "variable = <, >, =";

        assertEquals(expectedString, ParameterDecoder.decodeURI(encodedURI));
    }

    @Test
    public void testDecodeGivenURIForTwoOrMoreParams() throws Exception {
        String encodedURI = "/parameters?variable_1=Operators%20%3C%2C%20%3E%" +
                "2C%20%3D%2C%20!%3D%3B%20%2B%2C%20-%2C%20*%2C%20%26%2C%20%40%" +
                "2C%20%23%2C%20%24%2C%20%5B%2C%20%5D%3A%20%22is%20that%20all" +
                "%22%3F&variable_2=stuff";
        String expectedString = "variable_1 = Operators <, >, =, !=; +, -, *, &, @, #, $, [, ]: \"is that all\"?\r\n" +
                "variable_2 = stuff";
        assertEquals(expectedString, ParameterDecoder.decodeURI(encodedURI));
    }
}