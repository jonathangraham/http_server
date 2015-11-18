package com.jgraham.httpserver.ResponseBuilderTests;

import com.jgraham.httpserver.ResponseBuilder.*;
import org.junit.Assert;
import org.junit.Test;

public class FormResponseBuilderTest {
    @Test
    public void returnsResponseTest() throws Exception {
        iResponseBuilder response = new FormResponseBuilder("test");
        byte[] output = response.getResponse();
        Assert.assertEquals(new String(output), "HTTP/1.1 200 OK\r\n\r\ntest");
    }
}
