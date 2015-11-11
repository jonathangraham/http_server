package com.jgraham.httpserver.ResponseBuilderTests;

import com.jgraham.httpserver.ResponseBuilder.Status200Builder;
import com.jgraham.httpserver.ResponseBuilder.iResponseBuilder;
import org.junit.Assert;
import org.junit.Test;

public class Status200BuilderTest {
    @Test
    public void returns200Response() throws Exception {
        iResponseBuilder response = new Status200Builder();
        byte[] output = response.getResponse();
        Assert.assertEquals(new String(output), "HTTP/1.1 200 OK\r\n\r\n");
    }
}
