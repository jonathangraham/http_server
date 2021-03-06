package com.jgraham.httpserver.mocks;

import com.jgraham.httpserver.ServerConnection.iHttpServerSocket;
import com.jgraham.httpserver.ServerConnection.iHttpSocket;

public class MockHttpServerSocket implements iHttpServerSocket {
    private iHttpSocket mockSocket;
    private boolean wasClosed = false;


    public MockHttpServerSocket(iHttpSocket mockSocket) {
        this.mockSocket = mockSocket;
    }

    @Override
    public iHttpSocket accept() throws Exception {
        return  mockSocket = new MockHttpSocket(null, null);
    }

    @Override
    public void close() throws Exception {
        wasClosed = true;
    }

    public boolean wasClosed() {
        return wasClosed;
    }

}
