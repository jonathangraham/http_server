package com.jgraham.httpserver;

import com.jgraham.httpserver.mocks.MockHttpSocket;
import org.junit.Assert;
import org.junit.Test;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ResponseTest {

    @Test
    public void getHeaderResponseForRoutePathTest() throws Exception {
        InputStream inputStream = null;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        MockHttpSocket mockSocket = new MockHttpSocket(inputStream, outputStream);
        Response response = new Response(mockSocket, "GET", "/", "/src/main/resources");
        response.getResponse();
        Assert.assertEquals("HTTP/1.1 200 OK\r\n\r\n", response.getHeader());
    }

    @Test
    public void getResponseForUnknownRouteTest() throws Exception {
        InputStream inputStream = null;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        MockHttpSocket mockSocket = new MockHttpSocket(inputStream, outputStream);
        Response response = new Response(mockSocket, "GET", "/foo", "/src/main/resources");
        response.getResponse();
        Assert.assertEquals("HTTP/1.1 404 Not Found\r\n\r\n", response.getHeader());
        assertThat(outputStream.toByteArray(), is(equalTo("HTTP/1.1 404 Not Found\r\n\r\n".getBytes())));
    }

    @Test
    public void getHeaderResponseForKnownRouteTest() throws Exception {
        InputStream inputStream = null;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        MockHttpSocket mockSocket = new MockHttpSocket(inputStream, outputStream);
        Response response = new Response(mockSocket, "GET", "/file1", "/src/main/resources");
        response.getResponse();
        Assert.assertEquals("HTTP/1.1 200 OK\r\n\r\n", response.getHeader());
    }

    @Test
    public void getRouteTest() throws Exception {
        InputStream inputStream = null;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        MockHttpSocket mockSocket = new MockHttpSocket(inputStream, outputStream);
        Response response = new Response(mockSocket, "GET", "/file1", "/src/main/resources");
        Assert.assertEquals("/Users/jonathangraham/code/http-server/src/main/resources", response.getRoute());
    }

    @Test
    public void getPathTest() throws Exception {
        InputStream inputStream = null;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        MockHttpSocket mockSocket = new MockHttpSocket(inputStream, outputStream);
        Response response = new Response(mockSocket, "GET", "/file1", "/src/main/resources");
        Assert.assertEquals("/file1", response.getPath());
    }

    @Test
    public void getFilePathTest() throws Exception {
        InputStream inputStream = null;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        MockHttpSocket mockSocket = new MockHttpSocket(inputStream, outputStream);
        Response response = new Response(mockSocket, "GET", "/file1", "/src/main/resources");
        Assert.assertEquals("/Users/jonathangraham/code/http-server/src/main/resources/file1", response.getFilePath(response.getRoute(), response.getPath()));
        Assert.assertTrue((new File(response.getFilePath(response.getRoute(), response.getPath()))).exists());
    }

    @Test
    public void getResponseForPutTest() throws Exception {
        InputStream inputStream = null;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        MockHttpSocket mockSocket = new MockHttpSocket(inputStream, outputStream);
        Response response = new Response(mockSocket, "PUT", "/form", "/src/main/resources");
        response.getResponse();
        assertThat(outputStream.toByteArray(), is(equalTo("HTTP/1.1 200 OK\r\n\r\n".getBytes())));
    }

    @Test
    public void writeResourceToStreamTest() throws Exception {
        InputStream inputStream = null;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        MockHttpSocket mockSocket = new MockHttpSocket(inputStream, outputStream);
        Response response = new Response(mockSocket, "GET", "/file1", "/src/main/resources");
        response.writeResourceToStream();
        assertThat(outputStream.toByteArray(), is(equalTo("file1 contents".getBytes())));
    }

    @Test
    public void writeResourceToStreamNoContentTest() throws Exception {
        InputStream inputStream = null;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        MockHttpSocket mockSocket = new MockHttpSocket(inputStream, outputStream);
        Response response = new Response(mockSocket, "GET", "/foobar", "/src/main/resources");
        response.writeResourceToStream();
        assertThat(outputStream.toByteArray(), is(equalTo("".getBytes())));
    }
}
