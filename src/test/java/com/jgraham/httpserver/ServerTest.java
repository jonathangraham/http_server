package com.jgraham.httpserver;

import com.jgraham.httpserver.mocks.MockHttpServerSocket;
import com.jgraham.httpserver.mocks.MockHttpSocket;
import org.junit.Test;

import java.io.*;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class ServerTest {

    @Test
    public void closeServerSocketTest() throws Exception {
        MockHttpServerSocket serverSocket = new MockHttpServerSocket(null);
        Server server = new Server(serverSocket);
        server.close();
        assertTrue(serverSocket.wasClosed());
    }

    @Test
    public void acceptConnectionTest() throws Exception {
        MockHttpServerSocket serverSocket = new MockHttpServerSocket(null);
        Server server = new Server(serverSocket);
        iHttpSocket socket = server.acceptConnection();
        assertTrue(socket.isConnected());
    }

    @Test
    public void getRequestTest() throws Exception {
        InputStream inputStream = new ByteArrayInputStream("foobar".getBytes());
        ByteArrayOutputStream outputStream = null;
        MockHttpSocket mockSocket = new MockHttpSocket(inputStream, outputStream);
        Server server = new Server(null);
        assertEquals("foobar", server.getRequest(mockSocket));
    }

    @Test
    public void getResponseTest() throws Exception {
        InputStream inputStream = null;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        MockHttpSocket mockSocket = new MockHttpSocket(inputStream, outputStream);
        Server server = new Server(null);
        server.getResponse(mockSocket);
        assertThat(outputStream.toByteArray(), is(equalTo("HTTP/1.1 200 OK\r\n\r\n".getBytes())));
    }

}
