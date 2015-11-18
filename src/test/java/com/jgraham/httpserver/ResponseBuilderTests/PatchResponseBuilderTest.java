package com.jgraham.httpserver.ResponseBuilderTests;

import com.jgraham.httpserver.ResponseBuilder.*;
import org.junit.Assert;
import org.junit.Test;

public class PatchResponseBuilderTest {
    @Test
    public void returns200Response() throws Exception {
        iResponseBuilder response = new PatchResponseBuilder();
        byte[] output = response.getResponse();
        Assert.assertEquals(new String(output), "HTTP/1.1 204 Not Found\r\n\r\n");
    }
}
