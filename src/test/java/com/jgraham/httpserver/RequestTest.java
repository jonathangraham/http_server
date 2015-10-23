package com.jgraham.httpserver;

import com.jgraham.httpserver.mocks.MockHttpSocket;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;

public class RequestTest {

    @Test
    public void getRequestTest() throws Exception {
        InputStream inputStream = new ByteArrayInputStream("foobar".getBytes());
        ByteArrayOutputStream outputStream = null;
        MockHttpSocket mockSocket = new MockHttpSocket(inputStream, outputStream);
        Request request = new Request(mockSocket);
        Assert.assertEquals("foobar", request.getRequest());
    }
}
