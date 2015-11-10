package com.jgraham.httpserver.ResponseBuilder;

public class Status200Builder implements iResponseBuilder {

    public String getResponse() {
        StringBuilder response = new StringBuilder();
        response.append(getStatusLine());
        response.append(getHeader());
        response.append(getBody());
        return response.toString();
    }

    private String getStatusLine() {
        return "HTTP/1.1 200 OK\r\n";
    }

    private String getHeader() {
        return "\r\n";
    }

    private String getBody() {
        return "";
    }
}