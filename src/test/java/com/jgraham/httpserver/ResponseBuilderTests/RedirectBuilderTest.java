package com.jgraham.httpserver.ResponseBuilderTests;

import com.jgraham.httpserver.ResponseBuilder.*;
import org.junit.Assert;
import org.junit.Test;

public class RedirectBuilderTest {
    @Test
    public void returnsRedirectResponse() throws Exception {
        iResponseBuilder response = new RedirectBuilder();
        byte[] output = response.getResponse();
        Assert.assertEquals(new String(output), "HTTP/1.1 302 Found\r\nLocation: http://localhost:5000/\r\n");
    }
}
