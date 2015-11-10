package com.jgraham.httpserver.ServerConnectionTests;

import com.jgraham.httpserver.Requests.Request;
import com.jgraham.httpserver.ResponseBuilder.iResponseBuilder;
import com.jgraham.httpserver.ServerConnection.Server;
import com.jgraham.httpserver.ServerConnection.iHttpSocket;
import com.jgraham.httpserver.mocks.MockHttpServerSocket;
import com.jgraham.httpserver.mocks.MockHttpSocket;
import com.jgraham.httpserver.mocks.MockResponseBuilder;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.util.HashMap;

public class ServerTest {

    @Test
    public void closeServerSocketTest() throws Exception {
        MockHttpServerSocket serverSocket = new MockHttpServerSocket(null);
        Server server = new Server(serverSocket, null);
        server.close();
        Assert.assertTrue(serverSocket.wasClosed());
    }

    @Test
    public void acceptConnectionTest() throws Exception {
        MockHttpServerSocket serverSocket = new MockHttpServerSocket(null);
        Server server = new Server(serverSocket, null);
        iHttpSocket socket = server.acceptConnection();
        Assert.assertTrue(socket.isConnected());
    }

    @Test
    public void getRequestTest() throws Exception {
        InputStream inputStream = new ByteArrayInputStream("foobar\r\n\r\nnext line".getBytes());
        ByteArrayOutputStream outputStream = null;
        MockHttpSocket mockSocket = new MockHttpSocket(inputStream, outputStream);
        Server server = new Server(null, null);
        Assert.assertTrue(server.getRawRequest(mockSocket).contains("foobar\r\n\r\nnext line"));
    }

    @Test
    public void writeResponseTest() throws Exception {
        InputStream inputStream = null;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        MockHttpSocket mockSocket = new MockHttpSocket(inputStream, outputStream);
        Server server = new Server(null, "foobar");
        server.writeResponse(mockSocket, "HTTP/1.1 200 OK\r\n\r\nresponse to be written");
        Assert.assertTrue(outputStream.toByteArray().length > 0);
        Assert.assertTrue(outputStream.toString().contains("HTTP/1.1 200 OK\r\n\r\nresponse to be written"));
    }

    @Test
    public void getResponseTest() throws Exception {
        String response = "test response";
        iResponseBuilder responseBuilder = new MockResponseBuilder(response);
        Server server = new Server(null, "foobar");
        Assert.assertEquals(response, server.getResponse(responseBuilder));
    }

    @Test
    public void getNewRequestTest() throws Exception {
        Server server = new Server(null, "foobar");
        Assert.assertEquals(server.getNewRequest("GET / HTTP/1.1\r\n\r\n").getClass(), new Request(new HashMap<String, String>()).getClass());
    }

}