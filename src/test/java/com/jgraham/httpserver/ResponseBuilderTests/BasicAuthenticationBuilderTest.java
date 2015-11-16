package com.jgraham.httpserver.ResponseBuilderTests;

import com.jgraham.httpserver.ResponseBuilder.*;
import org.junit.Assert;
import org.junit.Test;

public class BasicAuthenticationBuilderTest {

    @Test
    public void returnsBasicAuthenticationResponse() throws Exception {
        iResponseBuilder response = new BasicAuthenticationBuilder();
        byte[] output = response.getResponse();
        Assert.assertEquals(new String(output), "HTTP/1.1 200 OK\r\n\r\nGET /log HTTP/1.1\r\nPUT /these HTTP/1.1\r\nHEAD /requests HTTP/1.1");
    }
}
