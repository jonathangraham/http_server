package com.jgraham.httpserver;

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
        Request request = new Request(clientSocket);
        return request.getRequest();
    }

    public void getResponse(iHttpSocket clientSocket) throws Exception {
        Response response = new Response(clientSocket);
        response.getResponse();
    }

    public iHttpSocket acceptConnection() throws Exception {
        return serverSocket.accept();
    }
}
