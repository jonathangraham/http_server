package com.jgraham.httpserver.ResponseBuilder;

public class MethodNotAllowedBuilder implements iResponseBuilder {

    public byte[] getResponse() {
        StringBuilder response = new StringBuilder();
        response.append(getStatusLine());
        response.append(getHeader());
        response.append(getBody());
        return response.toString().getBytes();
    }

    public void modifyFile() throws Exception {}

    private String getStatusLine() {
        return "HTTP/1.1 405 Method Not Allowed\r\n";
    }

    private String getHeader() {
        return "\r\n";
    }

    private String getBody() {
        return "";
    }
}