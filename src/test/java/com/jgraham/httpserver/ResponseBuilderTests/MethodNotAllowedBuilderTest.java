package com.jgraham.httpserver.ResponseBuilderTests;

import com.jgraham.httpserver.ResponseBuilder.*;
import org.junit.Assert;
import org.junit.Test;

public class MethodNotAllowedBuilderTest {
    @Test
    public void returnsFourOhFiveResponse() throws Exception {
        iResponseBuilder response = new MethodNotAllowedBuilder();
        byte[] output = response.getResponse();
        Assert.assertEquals(new String(output), "HTTP/1.1 405 Method Not Allowed\r\n\r\n");
    }
}
