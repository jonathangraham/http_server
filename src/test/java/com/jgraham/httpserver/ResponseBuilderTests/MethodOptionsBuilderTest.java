package com.jgraham.httpserver.ResponseBuilderTests;

import com.jgraham.httpserver.ResponseBuilder.MethodOptionsBuilder;
import com.jgraham.httpserver.ResponseBuilder.iResponseBuilder;
import org.junit.Assert;
import org.junit.Test;

public class MethodOptionsBuilderTest {
    @Test
    public void returnsFileContentsResponse() throws Exception {
        iResponseBuilder response = new MethodOptionsBuilder();
        String output = response.getResponse();
        Assert.assertTrue(output.contains("HTTP/1.1 200 OK\r\nAllow: GET,HEAD,POST,OPTIONS,PUT\r\n"));
    }
}
