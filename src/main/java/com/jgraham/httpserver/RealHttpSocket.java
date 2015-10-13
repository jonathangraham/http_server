package com.jgraham.httpserver;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class RealHttpSocket implements HttpSocket {

    private Socket socket;

    public RealHttpSocket(Socket socket) {
        this.socket = socket;
    }

    @Override
    public InputStream getInputStream() throws Exception {
        return socket.getInputStream();
    }

    @Override
    public OutputStream getOutputStream() throws Exception {
        return socket.getOutputStream();
    }
}
