package com.jgraham.httpserver;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class HttpSocketTest {

    public InputStream input = new InputStream() {
        @Override
        public int read() throws IOException {
            return 0;
        }
    };

    OutputStream output = new OutputStream() {
        @Override
        public void write(int b) throws IOException {
        }
    };

    @Test
    public void ServerConfigExists() {
        MockHttpSocket mockSocket = new MockHttpSocket(input, output);
        Assert.assertNotNull(mockSocket);
    }

    @Test
    public void canGetInputStream() throws Exception{
        MockHttpSocket mockSocket = new MockHttpSocket(input, output);
        Assert.assertEquals(input, mockSocket.getInputStream());
    }

    @Test
    public void canGetOutputStream() throws Exception {
        MockHttpSocket mockSocket = new MockHttpSocket(input, output);
        Assert.assertEquals(output, mockSocket.getOutputStream());
    }
}
