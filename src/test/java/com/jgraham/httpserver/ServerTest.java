package com.jgraham.httpserver;

import com.jgraham.httpserver.mocks.MockHttpServerSocket;
import com.jgraham.httpserver.mocks.MockHttpSocket;
import com.jgraham.httpserver.mocks.MockRequest;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;

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
        InputStream inputStream = new ByteArrayInputStream("foobar".getBytes());
        ByteArrayOutputStream outputStream = null;
        MockHttpSocket mockSocket = new MockHttpSocket(inputStream, outputStream);
        Server server = new Server(null, null);
        Assert.assertNotNull(server.getRequest(mockSocket));
    }

    @Test
    public void getResponseTest() throws Exception {
        InputStream inputStream = null;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        MockHttpSocket mockSocket = new MockHttpSocket(inputStream, outputStream);
        Server server = new Server(null, "foobar");
        MockRequest mockRequest = new MockRequest("GET / HTTP/1.1");
        server.getResponse(mockSocket, mockRequest);
        Assert.assertTrue(outputStream.toByteArray().length > 0);
    }

}
