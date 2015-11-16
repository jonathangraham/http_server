package com.jgraham.httpserver.ResponseBuilderTests;

import com.jgraham.httpserver.ResponseBuilder.*;
import org.junit.Assert;
import org.junit.Test;


public class BasicAuthenticationRequiredTest {

    @Test
    public void returnsBasicAuthenticationRequiredResponse() throws Exception {
        iResponseBuilder response = new BasicAuthenticationRequiredBuilder();
        byte[] output = response.getResponse();
        Assert.assertEquals(new String(output), "HTTP/1.1 401 Unauthorized\r\n\r\nAuthentication required");
    }
}
