package com.jgraham.httpserver;

import java.io.*;

public class Server {

    private iHttpServerSocket serverSocket;

    public Server(iHttpServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void start() throws Exception {
        while (true) {
            iHttpSocket clientSocket = acceptConnection();
            actOnConnection(clientSocket);
        }
    }

    public void actOnConnection(iHttpSocket clientSocket) throws Exception {
        getRequest(clientSocket);
        getResponse(clientSocket);
    }

    public void close() throws Exception {
        serverSocket.close();
    }

    public String getRequest(iHttpSocket clientSocket) throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String request = in.readLine();
        return request;
    }

    public void getResponse(iHttpSocket clientSocket) throws Exception {
        OutputStream out = clientSocket.getOutputStream();
        out.write(("HTTP/1.1 200 OK\r\n\r\n").getBytes());
        out.close();
    }

    public iHttpSocket acceptConnection() throws Exception {
        return serverSocket.accept();
    }
}
