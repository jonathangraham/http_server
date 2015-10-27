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
    public void getStatusResponseForRoutePathTest() throws Exception {
        InputStream inputStream = null;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        MockHttpSocket mockSocket = new MockHttpSocket(inputStream, outputStream);
        Response response = new Response(mockSocket, "GET", "/", "/src/main/resources");
        response.getResponse();
        Assert.assertEquals("HTTP/1.1 200 OK\r\n", response.getStatus());
    }

    @Test
    public void getResponseForUnknownRouteTest() throws Exception {
        InputStream inputStream = null;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        MockHttpSocket mockSocket = new MockHttpSocket(inputStream, outputStream);
        Response response = new Response(mockSocket, "GET", "/foo", "/src/main/resources");
        response.getResponse();
        Assert.assertEquals("HTTP/1.1 404 Not Found\r\n", response.getStatus());
        Assert.assertTrue(outputStream.toString().contains("HTTP/1.1 404 Not Found"));
    }

    @Test
    public void getStatusResponseForKnownRouteTest() throws Exception {
        InputStream inputStream = null;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        MockHttpSocket mockSocket = new MockHttpSocket(inputStream, outputStream);
        Response response = new Response(mockSocket, "GET", "/file1", "/src/main/resources");
        response.getResponse();
        Assert.assertEquals("HTTP/1.1 200 OK\r\n", response.getStatus());
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
        Response response = new Response(mockSocket, "PUT", "/forms", "/src/main/resources");
        response.getResponse();
        Assert.assertTrue(outputStream.toString().contains("HTTP/1.1 200 OK\r\n"));
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

    @Test
    public void buildDirectoryContentsTest() throws Exception {
        InputStream inputStream = null;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        MockHttpSocket mockSocket = new MockHttpSocket(inputStream, outputStream);
        Response response = new Response(mockSocket, "GET", "/", "/src/main/resources");
        String directoryContents = response.buildDirectoryContents();
        Assert.assertTrue(directoryContents.length() > 0);
        Assert.assertTrue(directoryContents.contains("file1"));
        Assert.assertTrue(directoryContents.contains("image.gif"));
        Assert.assertTrue(directoryContents.contains("<a href=/file2>file2</a><br>"));
    }

    @Test
    public void getResponseForRouteTest() throws Exception {
        InputStream inputStream = null;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        MockHttpSocket mockSocket = new MockHttpSocket(inputStream, outputStream);
        Response response = new Response(mockSocket, "GET", "/", "/src/main/resources");
        response.getResponse();
        Assert.assertTrue(outputStream.toString().contains("HTTP/1.1 200 OK\r\n"));
        Assert.assertTrue(outputStream.toString().contains("<a href=/file2>file2</a><br>"));
    }

    @Test
    public void getResponseForKnownRouteTest() throws Exception {
        InputStream inputStream = null;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        MockHttpSocket mockSocket = new MockHttpSocket(inputStream, outputStream);
        Response response = new Response(mockSocket, "GET", "/file1", "/src/main/resources");
        response.getResponse();
        Assert.assertTrue(outputStream.toString().contains("HTTP/1.1 200 OK\r\n"));
        Assert.assertTrue(outputStream.toString().contains("file1 contents"));
    }

    @Test
    public void getResponseForOptionsTest() throws Exception {
        InputStream inputStream = null;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        MockHttpSocket mockSocket = new MockHttpSocket(inputStream, outputStream);
        Response response = new Response(mockSocket, "OPTIONS", "/method_options", "/src/main/resources");
        response.getResponse();
        Assert.assertTrue(outputStream.toString().contains("HTTP/1.1 200 OK\r\n"));
        Assert.assertTrue(outputStream.toString().contains("Allow: GET,HEAD,POST,OPTIONS,PUT"));
    }

    @Test
    public void getStatusForPutsMethodNotAllowedTest() throws Exception {
        InputStream inputStream = null;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        MockHttpSocket mockSocket = new MockHttpSocket(inputStream, outputStream);
        Response response = new Response(mockSocket, "PUTS", "/file1", "/src/main/resources");
        response.getResponse();
        Assert.assertTrue(outputStream.toString().contains("HTTP/1.1 405 Method Not Allowed\r\n"));
    }

    @Test
    public void getStatusForPostMethodNotAllowedTest() throws Exception {
        InputStream inputStream = null;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        MockHttpSocket mockSocket = new MockHttpSocket(inputStream, outputStream);
        Response response = new Response(mockSocket, "POST", "/text-file.txt", "/src/main/resources");
        response.getResponse();
        Assert.assertTrue(outputStream.toString().contains("HTTP/1.1 405 Method Not Allowed\r\n"));
    }

    @Test
    public void writePartialResourceToStreamTest() throws Exception {
        InputStream inputStream = null;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        MockHttpSocket mockSocket = new MockHttpSocket(inputStream, outputStream);
        Response response = new Response(mockSocket, "GET", "/file1", "/src/main/resources");
        response.writePartialResourceToStream(0, 10);
        assertThat(outputStream.toByteArray(), is(equalTo("file1 cont".getBytes())));
    }
}
