package com.jgraham.httpserver;

import java.io.OutputStream;

public class Response {

    private OutputStream out;

    public Response(iHttpSocket clientSocket) throws Exception {
        this.out = clientSocket.getOutputStream();
    }

    public void getResponse() throws Exception {
        out.write(("HTTP/1.1 200 OK\r\n\r\n").getBytes());
        out.close();
    }
}
