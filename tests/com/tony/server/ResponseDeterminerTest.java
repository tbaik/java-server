package com.tony.server;

import com.tony.server.response.GetResponse;
import org.junit.Test;

import static org.junit.Assert.*;

public class ResponseDeterminerTest {
    @Test
    public void testHttpGetHandler() throws Exception {
        ResponseDeterminer responseDeterminer = new ResponseDeterminer(new Router());
        assertEquals(new GetResponse(), responseDeterminer.httpGetHandler());
    }


}