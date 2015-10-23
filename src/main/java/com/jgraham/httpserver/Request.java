package com.jgraham.httpserver;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Request implements iRequest {

    private String request;

    public Request(iHttpSocket clientSocket) throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        this.request = in.readLine();
    }

    public String getRequest() throws Exception {
        return request;
    }

    public String getRequestType() {
        return request.split("\\s+")[0];
    }

    public String getRequestURL() {
        return request.split("\\s+")[1];
    }
}
