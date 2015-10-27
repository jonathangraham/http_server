package com.jgraham.httpserver;

public class Server {

    private iHttpServerSocket serverSocket;
    private String directory;

    public Server(iHttpServerSocket serverSocket, String directory) {
        this.serverSocket = serverSocket;
        this.directory = directory;
    }

    public void start() throws Exception {
        while (true) {
            iHttpSocket clientSocket = acceptConnection();
            actOnConnection(clientSocket);
        }
    }

    public void actOnConnection(iHttpSocket clientSocket) throws Exception {
        Request request = getRequest(clientSocket);
        getResponse(clientSocket, request);
    }

    public void close() throws Exception {
        serverSocket.close();
    }

    public Request getRequest(iHttpSocket clientSocket) throws Exception {
        return new Request(clientSocket);
    }

    public void getResponse(iHttpSocket clientSocket, iRequest request) throws Exception {
        Response response = new Response(clientSocket, request.getRequestType(), request.getRequestURL(), directory);
        response.getResponse();
    }

    public iHttpSocket acceptConnection() throws Exception {
        return serverSocket.accept();
    }
}
