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
        Assert.assertTrue(request.getRequest().contains("foobar"));
    }

    @Test
    public void getRequestTest2() throws Exception {
        InputStream inputStream = new ByteArrayInputStream("GET / HTTP/1.1".getBytes());
        ByteArrayOutputStream outputStream = null;
        MockHttpSocket mockSocket = new MockHttpSocket(inputStream, outputStream);
        Request request = new Request(mockSocket);
        Assert.assertTrue(request.getRequest().contains("GET / HTTP/1.1"));
    }

    @Test
    public void getRequestTest3() throws Exception {
        InputStream inputStream = new ByteArrayInputStream("GET / HTTP/1.1\r\nRange: bytes=0-99".getBytes());
        ByteArrayOutputStream outputStream = null;
        MockHttpSocket mockSocket = new MockHttpSocket(inputStream, outputStream);
        Request request = new Request(mockSocket);
        Assert.assertTrue(request.getRequest().contains("GET / HTTP/1.1"));
//        Assert.assertTrue(request.getRequest().contains("Range: bytes=0-99"));
    }

    @Test
    public void getRequestPathTest() throws Exception {
        InputStream inputStream = new ByteArrayInputStream("GET / HTTP/1.1".getBytes());
        ByteArrayOutputStream outputStream = null;
        MockHttpSocket mockSocket = new MockHttpSocket(inputStream, outputStream);
        Request request = new Request(mockSocket);
        Assert.assertEquals("/", request.getRequestURL());
    }

    @Test
    public void getRequestTypeTest() throws Exception {
        InputStream inputStream = new ByteArrayInputStream("GET / HTTP/1.1".getBytes());
        ByteArrayOutputStream outputStream = null;
        MockHttpSocket mockSocket = new MockHttpSocket(inputStream, outputStream);
        Request request = new Request(mockSocket);
        Assert.assertEquals("GET", request.getRequestType());
    }

}
