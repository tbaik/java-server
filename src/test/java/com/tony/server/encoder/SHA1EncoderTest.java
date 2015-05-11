package com.tony.server.encoder;

import com.tony.server.Logger;
import com.tony.server.encoder.SHA1Encoder;
import org.junit.Assert;
import org.junit.Test;

public class SHA1EncoderTest {
    @Test
    public void testEncodeWithSHA1ReturnsCorrectSHA1Encoding() throws Exception {
        byte[] text = "text".getBytes();
        String textAsSHA1 = "372ea08cab33e71c02c651dbc83a474d32c676ea";
        Assert.assertEquals(textAsSHA1, SHA1Encoder.encode(text, new Logger()));
    }
}