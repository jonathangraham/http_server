package com.jgraham.httpserver.ResponseBuilderTests;

import com.jgraham.httpserver.ResponseBuilder.*;
import org.junit.Assert;
import org.junit.Test;

public class ParameterDecodeBuilderTest {
    @Test
    public void getBasicParameters() throws Exception {
        iResponseBuilder response = new ParameterDecodeBuilder("/parameter?first&second");
        byte[] output = response.getResponse();
        Assert.assertEquals(new String(output), "HTTP/1.1 200 OK\r\n\r\nfirstsecond");
    }

    @Test
    public void getEncodedParameters() throws Exception {
        iResponseBuilder response = new ParameterDecodeBuilder("/parameters?variable_1=Operators%20%3C%2C%20%3E%2C%20%3D%2C%20!%3D%3B%20%2B%2C%20-%2C%20*%2C%20%26%2C%20%40%2C%20%23%2C%20%24%2C%20%5B%2C%20%5D%3A%20%22is%20that%20all%22%3F&variable_2=stuff");
        byte[] output = response.getResponse();
        Assert.assertEquals(new String(output), "HTTP/1.1 200 OK\r\n\r\nvariable_1 = Operators <, >, =, !=; +, -, *, &, @, #, $, [, ]: \"is that all\"?variable_2 = stuff");
    }
}
