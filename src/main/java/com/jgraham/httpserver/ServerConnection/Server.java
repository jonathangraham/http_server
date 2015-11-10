package com.jgraham.httpserver.ServerConnection;

import com.jgraham.httpserver.Requests.Request;
import com.jgraham.httpserver.Requests.RequestParser;
import com.jgraham.httpserver.ResponseBuilder.iResponseBuilder;
import com.jgraham.httpserver.Responses.ResponseRoute;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

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
        String rawRequest = getRawRequest(clientSocket);
        Request request = getNewRequest(rawRequest);
        ResponseRoute responseRoute = getResponseRoute();
        iResponseBuilder responseBuilder = getResponseBuilder(responseRoute, request);
        String response = getResponse(responseBuilder);
        writeResponse(clientSocket, response);
    }

    public void close() throws Exception {
        serverSocket.close();
    }

    public String getRawRequest(iHttpSocket clientSocket) throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String request = "";
        try {
            request += (char) in.read();
            while (in.ready()) {
                request += (char) in.read();
            }
            return request;
        } catch (IOException e) {
            e.printStackTrace();
            return "Error reading request";
        }
    }

    public Request getNewRequest(String rawRequest) {
        RequestParser requestParser = new RequestParser();
        requestParser.generateParsedRequest(rawRequest);
        Request request = requestParser.getNewRequest();
        return request;
    }

    private ResponseRoute getResponseRoute() {
        return new ResponseRoute();
    }

    private iResponseBuilder getResponseBuilder(ResponseRoute responseRoute, Request request) {
        return responseRoute.getResponseBuilder(request, directory);
    }

    public String getResponse(iResponseBuilder responseBuilder) throws Exception {

        return responseBuilder.getResponse();
    }

    public void writeResponse(iHttpSocket clientSocket, String response) throws Exception {
        OutputStream out = clientSocket.getOutputStream();
        out.write(response.getBytes());
        out.close();
    }

    public iHttpSocket acceptConnection() throws Exception {
        return serverSocket.accept();
    }
}
